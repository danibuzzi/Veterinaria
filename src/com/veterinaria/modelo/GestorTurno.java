package com.veterinaria.modelo;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.Calendar;
import java.util.Collections;

// La clase GestorTurno ya no inicializa el DAO directamente, lo recibe en el constructor.
public class GestorTurno {

    private final TurnoDAO turnoDAO;

    public GestorTurno(TurnoDAO turnoDAO) {
        this.turnoDAO = turnoDAO;
    }

    // ----------------------------------------------------
    // LÓGICA DE HORARIOS DISPONIBLES
    // ----------------------------------------------------

    public List<String> cargarHorariosDisponibles( java.util.Date fechaSeleccionada) {

        if (fechaSeleccionada == null) {
            return Collections.singletonList("--- Seleccione Fecha ---");
        }

        Set<String> horasInicioOcupadas;
        try {
            // Llama al DAO para obtener las horas de inicio de turnos ya reservados
            horasInicioOcupadas = turnoDAO.obtenerHorasInicioOcupadas(fechaSeleccionada);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonList("Error de BD");
        }

        // --- 2. Aplicar Regla de Bloqueo de 60 minutos (30 min turno + 30 min buffer) ---
        Set<String> horariosBloqueados = new HashSet<>();
        for (String horaInicio : horasInicioOcupadas) {
            int minutosInicio = convertirA_MinutosDesdeCero(horaInicio);

            // Bloqueo 1: Hora de inicio (Ej: 08:00)
            horariosBloqueados.add(horaInicio);
            // Bloqueo 2: 30 minutos después (Ej: 08:30)
            horariosBloqueados.add(convertirA_String(minutosInicio + 30));
            // Bloqueo 3: 60 minutos después (Ej: 09:00 - el buffer)
            horariosBloqueados.add(convertirA_String(minutosInicio + 60));
        }

        // --- 3. Generar Posibles y Aplicar Filtros ---
        List<String> todosLosPosibles = generarHorariosPosibles(); // (08:00 a 19:30)
        List<String> disponibles = new ArrayList<>();
        disponibles.add("--- Seleccione Horario ---");

        Date hoy = new Date();
        boolean esHoy = esMismoDia(fechaSeleccionada, hoy);
        String horaActualStr = obtenerHoraActualFormato();

        for (String horario : todosLosPosibles) {

            // FILTRO 1: Hora Pasada (si es hoy)
            if (esHoy && horario.compareTo(horaActualStr) < 0) {
                continue;
            }

            // FILTRO 2: Horarios Bloqueados por otros turnos
            if (!horariosBloqueados.contains(horario)) {
                disponibles.add(horario);
            }
        }

        return disponibles;
    }

    // ----------------------------------------------------
    // MÉTODOS DE CARGA DE NOMBRES (USADOS POR EL CONTROLADOR)
    // ----------------------------------------------------

    public List<String> cargarNombresPropietarios() {
        try {
            return turnoDAO.obtenerNombresPropietarios();
        } catch (SQLException e) {
            System.err.println("Error al cargar Propietarios: " + e.getMessage());
            return List.of("ERROR: Conexión BD fallida");
        }
    }

    public List<String> cargarNombresMascotas(String nombrePropietario) {
        try {
            int idPropietario = turnoDAO.obtenerIdPropietarioPorNombre(nombrePropietario);
            if (idPropietario == 0) return List.of("--- Propietario no válido ---");

            return turnoDAO.obtenerNombresMascotasPorPropietario(idPropietario);
        } catch (SQLException e) {
            System.err.println("Error al cargar Mascotas: " + e.getMessage());
            return List.of("ERROR: Carga fallida");
        }
    }

    public List<String> cargarNombresTiposConsulta() {
        try {
            return turnoDAO.obtenerNombresTiposConsulta();
        } catch (SQLException e) {
            System.err.println("Error al cargar Tipos de Consulta: " + e.getMessage());
            return List.of("ERROR: Carga fallida");
        }
    }

    // ----------------------------------------------------
    // MÉTODO PRINCIPAL DE REGISTRO (Basado en tu código anterior)
    // ----------------------------------------------------

    /** Registra un nuevo turno. Retorna un mensaje de éxito o un código de error. */
    public String registrarTurno(String nombreTipoConsulta, String nombrePropietario, String nombreMascota, Date fechaTurno, String hora) {

        // 1. VALIDACIÓN
        if (nombrePropietario == null || nombrePropietario.startsWith("---") ||
                nombreMascota == null || nombreMascota.startsWith("---") ||
                nombreTipoConsulta == null || nombreTipoConsulta.startsWith("---") ||
                fechaTurno == null || hora == null || hora.isEmpty() || hora.startsWith("---")) {
            return "ERROR_VALIDACION: Debe completar todos los campos de selección y el horario.";
        }

        // 🛑 2. LUGAR Y CÓDIGO DE VALIDACIÓN DE FECHA Y HORA FUNCIONAL


        String fechaTurnoStr = new SimpleDateFormat("yyyy-MM-dd").format(fechaTurno);

        // Garantizamos HH:mm:ss para el campo TIME de tu BD
        String horaLimpia = (hora.contains(":") && hora.length() >= 5) ? hora.substring(0, 5) + ":00" : hora;



            // 2. CONVERSIÓN DE NOMBRES A IDs (CONSULTAS A BD)
        int idPropietario;
        int idMascota;
        int idTipoConsulta;

        try {
            idPropietario = turnoDAO.obtenerIdPropietarioPorNombre(nombrePropietario);
            idTipoConsulta = turnoDAO.obtenerIdTipoConsultaPorNombre(nombreTipoConsulta);

            if (idPropietario == 0 || idTipoConsulta == 0) {
                return "ERROR_BD: Propietario o Tipo de Consulta no encontrados. Revise los datos en BD.";
            }

            idMascota = turnoDAO.obtenerIdMascotaPorNombre(nombreMascota, idPropietario);
            if (idMascota == 0) {
                return "ERROR_BD: Mascota no encontrada asociada a este propietario.";
            }



        } catch (SQLException e) {
            return "ERROR_BD: Fallo crítico de conexión al buscar los IDs. Razón: " + e.getMessage();
        }

        // 3. INSERCIÓN
        try {
            // 🛑 NOTA: Necesitas la clase Turno en tu modelo para que esto compile.
            if (turnoDAO.verificarDisponibilidad(fechaTurnoStr, horaLimpia)) {
                return "ERROR_DUPLICADO: ...";
            }
            Turno turno = new Turno(idTipoConsulta, idPropietario, idMascota, fechaTurnoStr, horaLimpia);
            turnoDAO.guardar(turno);
            return "ÉXITO: Turno registrado correctamente.";

        } catch (Exception e) { // Usamos Exception por si falla la creación de Turno
            return "ERROR_BD: Problema de inserción. Razón: " + e.getMessage();
        }
    }

    // ----------------------------------------------------
    // HELPER FUNCTIONS (AUXILIARES PRIVADAS)
    // ----------------------------------------------------

    /** Genera la lista maestra de 08:00 a 19:30 (intervalos de 30 min) */
    private List<String> generarHorariosPosibles() {
        List<String> horarios = new ArrayList<>();
        // El horario de cierre es a las 20:00, el último slot comienza a las 19:30.
        for (int hora = 8; hora < 20; hora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                if (hora == 20 && minuto == 0) continue;
                String horaStr = String.format("%02d:%02d", hora, minuto);
                horarios.add(horaStr);
            }
        }
        return horarios;
    }

    /** Convierte una hora HH:mm a minutos desde las 00:00 */
    private int convertirA_MinutosDesdeCero(String hhmm) {
        String[] partes = hhmm.split(":");
        if (partes.length < 2) return 0;
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }

    /** Convierte minutos totales a HH:mm */
    private String convertirA_String(int minutosTotal) {
        int hora = (minutosTotal / 60) % 24;
        int minuto = minutosTotal % 60;
        return String.format("%02d:%02d", hora, minuto);
    }

    /** Obtiene la hora actual del sistema en formato "HH:mm" */
    private String obtenerHoraActualFormato() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    /** Compara si dos objetos Date son el mismo día (ignora la hora) */
    private boolean esMismoDia(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public int obtenerIdPropietario(String nombrePropietario) throws SQLException {
        // 🛑 Delega la responsabilidad de obtener el ID al DAO.
        return turnoDAO.obtenerIdPropietarioPorNombre(nombrePropietario);
    }

    public int obtenerIdPropietarioPorNombre(String nombrePropietario) throws SQLException {
        // 🛑 Aquí se llama al método del DAO
        return turnoDAO.obtenerIdPropietarioPorNombre(nombrePropietario);
    }



}