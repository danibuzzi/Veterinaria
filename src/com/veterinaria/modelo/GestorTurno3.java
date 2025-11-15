package com.veterinaria.modelo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GestorTurno3 {

    private final TurnoDAO3 turnoDAO; // Asegúrate de que este es TurnoDAO3

    // CONSTANTES DECLARADAS
    private static final int DURACION_CONSULTA_MINUTOS = 30;
    private static final int HORA_INICIO_MINUTOS = 8 * 60;   // 08:00
    private static final int HORA_FIN_MINUTOS = 20 * 60;     // 20:00 (último turno comienza a las 19:45)

    public GestorTurno3(TurnoDAO3 turnoDAO) {
        this.turnoDAO = turnoDAO;
    }

    // ----------------------------
    // LÓGICA DE CARGA DE DATOS
    // ---------------------------

    public List<String> cargarNombresTiposConsulta() {
        try {
            return turnoDAO.obtenerNombresTiposConsulta();
        } catch (SQLException e) {
            System.err.println("Error al cargar Tipos de Consulta: " + e.getMessage());

            return Collections.emptyList();
        }
    }

    public List<String> cargarNombresPropietarios() {
        try {
            return turnoDAO.obtenerNombresPropietarios();
        } catch (SQLException e) {
            System.err.println("Error al cargar Propietarios: " + e.getMessage());

            return Collections.emptyList();
        }
    }

    public List<String> cargarNombresMascotasPorPropietario(String nombrePropietario) {
        if (nombrePropietario == null || nombrePropietario.startsWith("---")) {
            return Collections.emptyList();
        }
        try {
            int idPropietario = turnoDAO.obtenerIdPropietario(nombrePropietario);
            return turnoDAO.obtenerNombresMascotasPorPropietario(idPropietario);
        } catch (SQLException e) {
            System.err.println("Error al cargar Mascotas: " + e.getMessage());

            return Collections.emptyList();
        }
    }

    // ----------------------------------------------------
    // LÓGICA DE HORARIOS DISPONIBLES
    // ----------------------------------------------------

    public List<String> cargarHorariosDisponibles(java.util.Date fechaSeleccionada) {

        if (fechaSeleccionada == null) {
            return Collections.singletonList("--- Seleccione Fecha ---");
        }

        Set<String> horasInicioOcupadas;
        try {
            horasInicioOcupadas = turnoDAO.obtenerHorasInicioOcupadas(fechaSeleccionada);
        } catch (SQLException e) {
            System.err.println("Error al obtener horas ocupadas de la BD: " + e.getMessage());

            return Collections.singletonList("--- Error de conexión ---");
        }

        List<String> horariosDisponibles = new ArrayList<>();

        int horaDeInicioBusqueda = obtenerMinutosDeInicio(fechaSeleccionada);

        // Bucle para generar todos los slots posibles (desde horaDeInicioBusqueda hasta HORA_FIN_MINUTOS)
        for (int minutosTotal = horaDeInicioBusqueda;
             minutosTotal < HORA_FIN_MINUTOS;
             minutosTotal += DURACION_CONSULTA_MINUTOS) {

            String horaInicioStr = convertirA_String(minutosTotal);

            // Filtro de horas ocupadas
            if (!horasInicioOcupadas.contains(horaInicioStr)) {
                horariosDisponibles.add(horaInicioStr);
            }
        }

        if (horariosDisponibles.isEmpty()) {
            return Collections.singletonList("--- No hay horarios disponibles ---");
        }

        horariosDisponibles.add(0, "--- Seleccione Hora ---");
        return horariosDisponibles;
    }

    // ----------------------------------------------------
    // LÓGICA DE REGISTRO DEL TURNO
    // ----------------------------------------------------

    public String registrarTurno(String nombreTipo, String nombrePropietario, String nombreMascota, Date fecha, String hora) {

        if (nombreTipo == null || nombreTipo.startsWith("---") ||
                nombrePropietario == null || nombrePropietario.startsWith("---") ||
                nombreMascota == null || nombreMascota.startsWith("---") ||
                hora == null || hora.startsWith("---") || fecha == null) {
            return "ERROR: Debe seleccionar todos los campos.";
        }

        if (esFechaPasada(fecha)) {
            return "ERROR: No se puede reservar turnos en una fecha pasada.";
        }

        try {
            // Obtener IDs
            int idTipo = turnoDAO.obtenerIdTipoConsulta(nombreTipo);
            int idPropietario = turnoDAO.obtenerIdPropietario(nombrePropietario);
            int idMascota = turnoDAO.obtenerIdMascota(nombreMascota);

            if (idTipo == -1 || idPropietario == -1 || idMascota == -1) {
                return "ERROR: No se pudo encontrar uno de los IDs seleccionados en la base de datos.";
            }

            // Formatear Fecha y Hora para SQL
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fechaTurnoStr = formatoFecha.format(fecha);

            String horaConSegundos = hora + ":00"; // .

            // Creamos el objeto Turno y guardar
            //Turno nuevoTurno = new Turno();

            Turno nuevoTurno = new Turno(idTipo, idPropietario,
                    idMascota, fechaTurnoStr, horaConSegundos);
            /*nuevoTurno.setIdTipoConsulta(idTipo);
            nuevoTurno.setIdPropietario(idPropietario);
            nuevoTurno.setIdMascota(idMascota);
            nuevoTurno.setFechaTurno(fechaTurnoStr);
            nuevoTurno.setHora(hora + ":00"); // Hora debe ir como HH:MM:SS*/

            if (turnoDAO.guardar(nuevoTurno)) {
                return "ÉXITO: Turno reservado con éxito para el " + fechaTurnoStr + " a las " + hora + ".";
            } else {
                return "ERROR: Falló el registro en la base de datos.";
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar turno: " + e.getMessage());

            return "ERROR: Falló la conexión o la consulta a la base de datos. Detalle: " + e.getMessage();
        }
    }

    // ----------------------------------------------------
    // MÉTODOS AUXILIARES DE FECHA/HORA
    // ----------------------------------------------------

    /** Bloquea fechas estrictamente anteriores a HOY (ignorando la hora). */
    public boolean esFechaPasada(Date fechaSeleccionada) {
        if (fechaSeleccionada == null) {
            return false;
        }
        Date hoySinHora = obtenerFechaSinHora(new Date());
        Date fechaSelSinHora = obtenerFechaSinHora(fechaSeleccionada);

        return fechaSelSinHora.before(hoySinHora);
    }

    private int obtenerMinutosDeInicio(Date fechaSeleccionada) {
        if (esMismoDia(fechaSeleccionada, new Date())) {
            // Es HOY: empezamos desde la hora actual redondeada.
            String horaActualStr = new SimpleDateFormat("HH:mm").format(new Date());
            int minutosActuales = obtenerMinutos(horaActualStr);

            // Redondeamos al siguiente turno posible de 15 minutos
            int modulo = minutosActuales % DURACION_CONSULTA_MINUTOS;
            int minutosRedondeados = minutosActuales;

            if (modulo > 0) {
                minutosRedondeados = minutosActuales - modulo + DURACION_CONSULTA_MINUTOS;
            } else if (modulo == 0) {
                minutosRedondeados = minutosActuales;
            }

            // Aseguramos que no empezamos antes del inicio de jornada (8:00)
            return Math.max(minutosRedondeados, HORA_INICIO_MINUTOS);

        } else {
            // Es fecha futura: Empezamos a las 8:00 AM.
            return HORA_INICIO_MINUTOS;
        }
    }

    /** Auxiliar: Devuelve un objeto Date sin hora (00:00:00). */
    private Date obtenerFechaSinHora(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** Comparamos si dos objetos Date son el mismo día (ignora la hora) */
    public boolean esMismoDia(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /** Convierte HH:mm a minutos totales del día (desde las 00:00) */
    private int obtenerMinutos(String hhmm) {
        String[] partes = hhmm.split(":");
        if (partes.length < 2) return 0;
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }

    /** Convierte minutos totales a formato HH:mm */
    private String convertirA_String(int minutosTotal) {
        int hora = (minutosTotal / 60) % 24;
        int minuto = minutosTotal % 60;
        return String.format("%02d:%02d", hora, minuto);
    }

    //metodos para consulta turnos propietario

    /**
     * Carga todos los propietarios para el JComboBox (formato ID;Nombre Apellido).
     */
    public List<String> cargarPropietariosParaConsulta() {
        try {
            return turnoDAO.obtenerPropietariosParaCombo();
        } catch (SQLException e) {
            System.err.println("Error al cargar propietarios para la consulta: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene la lista de turnos para un propietario específico.
     * @param idPropietario ID del propietario seleccionado.
     * @return Lista de Object[] con [Fecha Formateada, Hora, Tipo Consulta, Mascota].
     */
    /*public List<Object[]> obtenerTurnosPorPropietario(int idPropietario) {
        if (idPropietario <= 0) {
            return Collections.emptyList();
        }
        try {
            // El DAO ya devuelve la lista de Object[] mapeada
            return turnoDAO.obtenerTurnosPorPropietario(idPropietario);
        } catch (SQLException e) {
            System.err.println("Error al obtener turnos para propietario ID " + idPropietario + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }*/
}
