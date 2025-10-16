package com.veterinaria.controlador;

import com.veterinaria.modelo.Turno;
import  com.veterinaria.modelo.TurnoDAO3;
import com.veterinaria.modelo.GestorModificacionTurnos;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.TipoConsulta;
import com.veterinaria.vista.VentanaModificacionTurnos;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class ControladorModificacionTurnos implements ActionListener {

    private final GestorModificacionTurnos gestor;
    private final VentanaModificacionTurnos vista;
    private final Object[] datosIniciales;
    // Referencia directa al Controlador Principal para notificar la recarga.
    private final ControladorGestionTurnos controladorPrincipal;

    private static final int INDICE_ID = 0;

    public ControladorModificacionTurnos(
            GestorModificacionTurnos gestor,
            VentanaModificacionTurnos vista,
            ControladorGestionTurnos controladorPrincipal,
            Object[] datosSeleccionados

    ) {
        this.gestor = gestor;
        this.vista = vista;
        this.controladorPrincipal = controladorPrincipal;

        //CAMBIO CLAVE: Asignar el array completo que viene del Controlador de Gestión.
        this.datosIniciales = datosSeleccionados;


        // YA NO NECESITAS: vista.setDatosIniciales(datosSeleccionados) ni this.datosIniciales = vista.getDatosIniciales();

        vista.setControlador(this);

        cargarOpcionesIniciales();
        seleccionarDatosIniciales();
    }

    public void abrirVentanaModificacion(String fecha, Object[] datosTurno) {
        // 1. Crear la instancia de la ventana
        VentanaModificacionTurnos vista =
                new VentanaModificacionTurnos(fecha, datosTurno, controladorPrincipal);

        //  PASO CRÍTICO AÑADIDO: Cargar los Combos desde la Base de Datos
        // Asumimos que tienes un DAO para obtener los datos
        //vista.setOpcionesPropietario(turnoDAO.ob.obtenerTodosLosPropietarios());
        //vista.setOpcionesTipoConsulta(turnoDAO.obtenerTodosLosTiposConsulta());

        //  PASO FINAL: Le indicamos a la ventana que ya puede seleccionar los ítems del turno
        vista.iniciarSeleccionDeTurno();

        // ... (Mostrar la ventana, agregarla al escritorio, etc.) ...
    }

    /*public ControladorModificacionTurnos(GestorModificacionTurnos gestor, VentanaModificacionTurnos vista, ControladorGestionTurnos controladorPrincipal) throws SQLException {
        this.gestor = gestor;
        this.vista = vista;
        this.controladorPrincipal = controladorPrincipal;
        this.datosIniciales = vista.getDatosIniciales();

        vista.setControlador(this);

        cargarOpcionesIniciales();
        seleccionarDatosIniciales();
    }*/


    @SuppressWarnings("unchecked")
    private void cargarOpcionesIniciales() {
        try {

            // Carga los datos necesarios para llenar los JComboBox al iniciar.
            List<Propietario> propietarios = gestor.obtenerTodosLosPropietariosActivos();
            vista.setOpcionesPropietario(propietarios);

            // Carga y setea Tipo Consulta DENTRO del try
            //vista.setOpcionesTipoConsulta(gestor.obtenerTiposDeConsulta());
            // Convertimos la lista devuelta por el gestor al tipo exacto que espera la vista.

            vista.setOpcionesTipoConsulta((List<com.veterinaria.modelo.TipoConsulta>) (List<?>) gestor.obtenerTiposDeConsulta());

           // cargarHorariosDisponibles();

        } catch (SQLException e) {
            // 🛑 MANEJA LA EXCEPCIÓN AQUÍ
            System.err.println("Error de SQL al cargar opciones iniciales: " + e.getMessage());
            // Muestra un mensaje al usuario
            JOptionPane.showMessageDialog(vista,
                    "Error de conexión o base de datos al cargar opciones de modificación.",
                    "Error de Carga", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Maneja otras excepciones si las hay
            System.err.println("Error general al cargar opciones: " + e.getMessage());
        }
    }



    /*private void seleccionarDatosIniciales() {

        // Índices de los IDs (Según tu imagen)
        final int INDICE_FECHA = 5;
        final int INDICE_ID_PROPIETARIO_TURNO = 6;
        final int INDICE_ID_MASCOTA_TURNO = 7;
        final int INDICE_ID_TIPO_CONSULTA = 8;
        final int INDICE_HORA = 1;

        try {
            // ... (Tu código de Conversión de Fecha y selección de Hora - Esto ya funciona) ...

            // 🛑 CLAVE 1: SELECCIONAR PROPIETARIO
            // 1. Obtener ID del índice 6.
            int idPropietarioTurno = Integer.parseInt(datosIniciales[INDICE_ID_PROPIETARIO_TURNO].toString());
            // 2. Crear objeto de búsqueda (DEBE tener equals/hashCode por ID)
            Propietario pBusqueda = new Propietario();
            pBusqueda.setIdPropietario(idPropietarioTurno);
            // 3. Seleccionar
            vista.getPropietarioCombo().setSelectedItem(pBusqueda);

            // 🛑 CLAVE 2: SELECCIONAR MASCOTA
            // 1. Obtener ID del índice 7.
            int idMascotaTurno = Integer.parseInt(datosIniciales[INDICE_ID_MASCOTA_TURNO].toString());
            // 2. Crear objeto de búsqueda (DEBE tener equals/hashCode por ID)
            Mascota mBusqueda = new Mascota();
            mBusqueda.setIdMascota(idMascotaTurno);
            // 3. Seleccionar
            vista.getMascotaCombo().setSelectedItem(mBusqueda);

            // 🛑 CLAVE 3: SELECCIONAR TIPO DE CONSULTA
            // 1. Obtener ID del índice 8.
            int idTipoConsultaTurno = Integer.parseInt(datosIniciales[INDICE_ID_TIPO_CONSULTA].toString());
            // 2. Crear objeto de búsqueda (DEBE tener equals/hashCode por ID)
            TipoConsulta tBusqueda = new TipoConsulta();
            tBusqueda.setIdTipoConsulta(idTipoConsultaTurno);
            // 3. Seleccionar
            vista.getTipoConsultaCombo().setSelectedItem(tBusqueda);

        } catch (Exception e) {
            // Si hay un error, el problema es que el índice es nulo o la conversión falla.
            JOptionPane.showMessageDialog(vista, "Error al seleccionar los datos iniciales. Revise los índices: " + e.getMessage(), "Fallo en Selección", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    /*private void seleccionarDatosIniciales() {

        // ÍNDICES PROPORCIONADOS POR TI
        final int INDICE_FECHA = 5;
        final int INDICE_HORA = 1;
        final int INDICE_ID_PROPIETARIO_TURNO = 6;
        final int INDICE_ID_MASCOTA_TURNO = 7;
        final int INDICE_ID_TIPO_CONSULTA = 8;

        // 🛑 AGREGAMOS EL TRY-CATCH GRANDE PARA IDENTIFICAR LA LÍNEA QUE FALLA
        try {
            // ----------------- A. CONVERSIÓN Y ESTABLECIMIENTO DE FECHA -----------------
            Object fechaObjeto = datosIniciales[INDICE_FECHA];

            if (fechaObjeto != null) { // 🛑 MANEJO DE NULL EN FECHA
                java.util.Date fechaDate = null;

                // 1. Conversión a java.util.Date
                if (fechaObjeto instanceof java.util.Date) {
                    fechaDate = (java.util.Date) fechaObjeto;
                } else if (fechaObjeto instanceof String) {
                    // Esto podría fallar si el formato String no es compatible con valueOf
                    fechaDate = java.sql.Date.valueOf((String) fechaObjeto);
                }

                // 2. Conversión a LocalDate y establecer en Vista (Dispara la carga de horarios)
                if (fechaDate != null) {
                    LocalDate fechaLocalDate = fechaDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    vista.setFechaSeleccionada(fechaLocalDate);
                }
            }

            // ----------------- B. SELECCIÓN DE COMBOS -----------------

            // 1. HORA (String) - Si la carga de horarios funciona, esto seleccionará el ítem
            Object horaObj = datosIniciales[INDICE_HORA];
            if (horaObj != null) { // 🛑 MANEJO DE NULL EN HORA
                vista.getHorarioCombo().setSelectedItem((String) horaObj);
            }

            // 2. PROPIETARIO (Requiere equals/hashCode en Propietario.java)
            Object idPropObj = datosIniciales[INDICE_ID_PROPIETARIO_TURNO];
            if (idPropObj != null) { // 🛑 MANEJO DE NULL EN ID
                int idPropietarioTurno = Integer.parseInt(idPropObj.toString()); // Conversión robusta
                Propietario pBusqueda = new Propietario();
                pBusqueda.setIdPropietario(idPropietarioTurno);
                vista.getPropietarioCombo().setSelectedItem(pBusqueda);
            }
            // ... Repetir lógica para Mascota y TipoConsulta ...

            // 3. TIPO CONSULTA (Requiere equals/hashCode en TipoConsulta.java)
            Object idTipoObj = datosIniciales[INDICE_ID_TIPO_CONSULTA];
            if (idTipoObj != null) { // 🛑 MANEJO DE NULL EN ID
                int idTipoConsultaTurno = Integer.parseInt(idTipoObj.toString());
                TipoConsulta tBusqueda = new TipoConsulta();
                tBusqueda.setIdTipoConsulta(idTipoConsultaTurno);
                vista.getTipoConsultaCombo().setSelectedItem(tBusqueda);
            }

            // 4. MASCOTA (Requiere equals/hashCode en Mascota.java)
            Object idMascObj = datosIniciales[INDICE_ID_MASCOTA_TURNO];
            if (idMascObj != null) { // 🛑 MANEJO DE NULL EN ID
                int idMascotaTurno = Integer.parseInt(idMascObj.toString());
                Mascota mBusqueda = new Mascota();
                mBusqueda.setIdMascota(idMascotaTurno);
                vista.getMascotaCombo().setSelectedItem(mBusqueda);
            }


        } catch (Exception e) {
            // 🛑 ESTO ES LO QUE ESTÁS VIENDO. Si ahora ves un mensaje diferente a 'null', ¡ya está mejor!
            JOptionPane.showMessageDialog(vista, "Error al seleccionar los datos: " + e.getMessage(), "Fallo en Selección", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    /*private void seleccionarDatosIniciales() {

        // ÍNDICES
        final int INDICE_FECHA = 5;
        final int INDICE_HORA = 1;
        final int INDICE_ID_PROPIETARIO_TURNO = 6;
        final int INDICE_ID_MASCOTA_TURNO = 7;
        final int INDICE_ID_TIPO_CONSULTA = 8;

        try {
            // ----------------- A. CONVERSIÓN Y ESTABLECIMIENTO DE FECHA -----------------
            Object fechaObjeto = datosIniciales[INDICE_FECHA];

            if (fechaObjeto != null) { // 🛑 CRÍTICO: Verificar null en FECHA
                java.util.Date fechaDate = null;

                if (fechaObjeto instanceof java.util.Date) {
                    fechaDate = (java.util.Date) fechaObjeto;
                } else if (fechaObjeto instanceof String) {
                    // Conversión segura de String a Date (asumiendo formato SQL "YYYY-MM-DD")
                    fechaDate = java.sql.Date.valueOf((String) fechaObjeto);
                }

                if (fechaDate != null) {
                    // Conversión final a LocalDate y establecer en Vista (Dispara la carga de horarios)
                    LocalDate fechaLocalDate = fechaDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    vista.setFechaSeleccionada(fechaLocalDate);
                }
            }

            // ----------------- B. SELECCIÓN DE COMBOS (Hora y Objetos) -----------------

            // 1. HORA
            Object horaObj = datosIniciales[INDICE_HORA];
            if (horaObj != null) { // 🛑 CRÍTICO: Verificar null en HORA
                vista.getHorarioCombo().setSelectedItem((String) horaObj);
            }

            // 2. PROPIETARIO
            Object idPropObj = datosIniciales[INDICE_ID_PROPIETARIO_TURNO];
            if (idPropObj != null) { // 🛑 CRÍTICO: Verificar null en ID PROPIETARIO
                int idPropietarioTurno = Integer.parseInt(idPropObj.toString());
                Propietario pBusqueda = new Propietario();
                pBusqueda.setIdPropietario(idPropietarioTurno);
                vista.getPropietarioCombo().setSelectedItem(pBusqueda);
            }

            // 3. TIPO CONSULTA
            Object idTipoObj = datosIniciales[INDICE_ID_TIPO_CONSULTA];
            if (idTipoObj != null) { // 🛑 CRÍTICO: Verificar null en ID TIPO CONSULTA
                int idTipoConsultaTurno = Integer.parseInt(idTipoObj.toString());
                TipoConsulta tBusqueda = new TipoConsulta();
                tBusqueda.setIdTipoConsulta(idTipoConsultaTurno);
                vista.getTipoConsultaCombo().setSelectedItem(tBusqueda);
            }

            // 4. MASCOTA
            Object idMascObj = datosIniciales[INDICE_ID_MASCOTA_TURNO];
            if (idMascObj != null) { // 🛑 CRÍTICO: Verificar null en ID MASCOTA
                int idMascotaTurno = Integer.parseInt(idMascObj.toString());
                Mascota mBusqueda = new Mascota();
                mBusqueda.setIdMascota(idMascotaTurno);
                vista.getMascotaCombo().setSelectedItem(mBusqueda);
            }

        } catch (Exception e) {
            // Si el error persiste, ahora mostrará la causa REAL (ej: error de formato, no Null)
            JOptionPane.showMessageDialog(vista, "Error al seleccionar los datos: " + e.getMessage(), "Fallo en Selección", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    // Archivo: ControladorModificacionTurnos.java

// (Debajo de tus campos de clase, como datosIniciales)

// 🛑 Definir los índices como constantes de clase (Si ya los tienes, ignora esto)
// private static final int INDICE_FECHA = 5;
// private static final int INDICE_HORA = 1;
// private static final int INDICE_ID_PROPIETARIO_TURNO = 6;
// private static final int INDICE_ID_MASCOTA_TURNO = 7;
// private static final int INDICE_ID_TIPO_CONSULTA = 8;


    private void seleccionarDatosIniciales() {

        // 🛑 Definición local de índices (Usar si no están como campos de clase)
        final int INDICE_FECHA = 5;
        final int INDICE_HORA = 1;
        final int INDICE_ID_PROPIETARIO_TURNO = 6;
        final int INDICE_ID_MASCOTA_TURNO = 7;
        final int INDICE_ID_TIPO_CONSULTA = 8;

        try {
            // ----------------- A. CONVERSIÓN Y ESTABLECIMIENTO DE FECHA -----------------
            Object fechaObjeto = datosIniciales[INDICE_FECHA];

            // 🛑 Lógica Súper Segura para la Fecha
            if (fechaObjeto instanceof java.util.Date) {
                java.util.Date fechaDate = (java.util.Date) fechaObjeto;

                // 1. Convertir a LocalDate
                LocalDate fechaLocalDate = fechaDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // 2. Establecer en Vista (Esto DEBE cargar la fecha y disparar el evento)
                vista.setFechaSeleccionada(fechaLocalDate);

            } else if (fechaObjeto instanceof String) {
                // Manejar si viene como String (por ejemplo, "2025-10-17")
                try {
                    java.sql.Date fechaSQL = java.sql.Date.valueOf((String) fechaObjeto);
                    LocalDate fechaLocalDate = fechaSQL.toLocalDate();
                    vista.setFechaSeleccionada(fechaLocalDate);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error de formato de fecha String: " + e.getMessage());
                }
            }

            // ----------------- B. SELECCIÓN DE COMBOS (USANDO safeParseInt) -----------------

            // 1. HORA (Se selecciona después de que la fecha cargue los horarios)
            Object horaObj = datosIniciales[INDICE_HORA];
            if (horaObj != null) {
                vista.getHorarioCombo().setSelectedItem(String.valueOf(horaObj));
            }

            // 2. PROPIETARIO
            int idPropietarioTurno = parseoSeguroInt(datosIniciales[INDICE_ID_PROPIETARIO_TURNO]);
            if (idPropietarioTurno > 0) {
                Propietario pBusqueda = new Propietario();
                pBusqueda.setIdPropietario(idPropietarioTurno);
                vista.getPropietarioCombo().setSelectedItem(pBusqueda);
            }

            // 3. TIPO CONSULTA
            int idTipoConsultaTurno = parseoSeguroInt(datosIniciales[INDICE_ID_TIPO_CONSULTA]);
            if (idTipoConsultaTurno > 0) {
                TipoConsulta tBusqueda = new TipoConsulta();
                tBusqueda.setIdTipoConsulta(idTipoConsultaTurno);
                vista.getTipoConsultaCombo().setSelectedItem(tBusqueda);
            }

            // 4. MASCOTA
            int idMascotaTurno = parseoSeguroInt(datosIniciales[INDICE_ID_MASCOTA_TURNO]);
            if (idMascotaTurno > 0) {
                Mascota mBusqueda = new Mascota();
                mBusqueda.setIdMascota(idMascotaTurno);
                vista.getMascotaCombo().setSelectedItem(mBusqueda);
            }

        } catch (Exception e) {
            // Manejador de errores que ahora solo debería capturar errores inesperados
            System.err.println("Error durante la selección de datos iniciales: " + e.getMessage());
            JOptionPane.showMessageDialog(vista, "ERROR FATAL EN SELECCIÓN: " + e.getMessage(), "Fallo en Selección", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Método auxiliar para la Mascota
    private void cargarYSeleccionarMascota(int idPropietario, int idMascotaTurno) {
        // La acción de "PROPIETARIO_CAMBIO" no se dispara automáticamente en el constructor, así que lo hacemos manualmente.
        List<Mascota> mascotas = gestor.obtenerMascotasPorPropietario(idPropietario);
        vista.setOpcionesMascota(mascotas);

        JComboBox<Mascota> comboMascota = vista.getMascotaCombo();
        for (int i = 0; i < comboMascota.getItemCount(); i++) {
            Mascota m = comboMascota.getItemAt(i);
            if (m.getIdMascota() == idMascotaTurno) {
                comboMascota.setSelectedItem(m);
                break;
            }
        }
    }


    // Carga los horarios disponibles para la fecha actual seleccionada en el JDateChooser.
    public void cargarHorariosDisponibles() throws Exception {
        Date date = vista.getDateChooserFecha().getDate();
        LocalDate fecha = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        if (fecha != null) {
            List<String> horarios = gestor.obtenerHorariosDisponibles(fecha);
            vista.setOpcionesHorario(horarios);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "PROPIETARIO_CAMBIO":
                // Carga en cascada: selecciona mascotas al cambiar el propietario.
                Propietario propietarioSeleccionado = (Propietario) vista.getPropietarioCombo().getSelectedItem();
                if (propietarioSeleccionado != null) {
                    List<Mascota> mascotas = gestor.obtenerMascotasPorPropietario(propietarioSeleccionado.getIdPropietario());
                    vista.setOpcionesMascota(mascotas);
                }
                break;
            case "GUARDAR":
                guardarModificacion();
                break;
            case "CANCELAR":
                // Solo cierra la ventana. El InternalFrameListener de la vista notificará el cierre al principal.
                vista.dispose();
                break;
        }
    }

// ... código anterior ...

    private void guardarModificacion() {
        Propietario prop = (Propietario) vista.getPropietarioCombo().getSelectedItem();
        Mascota masc = (Mascota) vista.getMascotaCombo().getSelectedItem();
        String nombreTipoConsulta = vista.getTipoConsultaCombo().getSelectedItem().toString(); // Nombre de la consulta
        //  Intentamos obtener un objeto TipoConsulta,
        TipoConsulta tipoConsultaSeleccionada =
                (TipoConsulta) vista.getTipoConsultaCombo().getSelectedItem();

        // Obtener el ID de ese objeto para pasarlo al Gestor/DAO.
        //int idTipoConsulta = tipoConsultaSeleccionada.getIdTipoConsulta();
        String hora = (String) vista.getHorarioCombo().getSelectedItem();
        Date date = vista.getDateChooserFecha().getDate();

        // Obtener el ID del Tipo de Consulta (DEBES IMPLEMENTAR ESTO EN EL GESTOR)
        // Ya que la tabla turno usa idTipoConsulta (INT) y no el String
        //int idTipoConsulta;
        int idTipoConsulta=0;
        try {
            // Ejemplo: obtenerIdTipoConsulta debe buscar el INT correspondiente al String
            //idTipoConsulta = gestor.obtenerIdTipoConsulta(nombreTipoConsulta);
            idTipoConsulta = tipoConsultaSeleccionada.getIdTipoConsulta();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener ID de Tipo de Consulta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (date == null || prop == null || masc == null || nombreTipoConsulta == null || hora == null) {
            JOptionPane.showMessageDialog(vista, "Todos los campos deben estar seleccionados.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Formatear la fecha a String (YYYY-MM-DD) para que coincida con tu constructor
        LocalDate nuevaFecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String fechaStringDB = nuevaFecha.toString(); // Formato estándar "YYYY-MM-DD"

        // Validación de fecha: no permitir fechas pasadas
        if (nuevaFecha.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(vista, "La nueva fecha del turno no puede ser anterior a hoy.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idTurnoOriginal = (int) datosIniciales[INDICE_ID];
        System.out.println("Id turno original "+idTurnoOriginal);
        System.out.println("id tipo consulta "+idTipoConsulta);
        System.out.println("id mascota "+masc.getIdMascota());
        System.out.println("id propietario "+prop.getIdPropietario());
        System.out.println("Fecha "+fechaStringDB);
        System.out.println("hora "+hora);
        // --- CONSTRUCCIÓN DEL TURNO USANDO TU FIRMA REAL ---
        // (idTipoConsulta, idPropietario, idMascota, fechaTurno, hora)
        Turno turno = new Turno(idTurnoOriginal,
                idTipoConsulta,
                prop.getIdPropietario(),
                masc.getIdMascota(),
                fechaStringDB, // Usamos String como lo pide tu constructor
                hora
        );

        // NOTA: Si necesitas el idTurno para la modificación, tu constructor de Turno
        // deberá ser actualizado en tu clase Turno para recibirlo.

        if (gestor.actualizarTurno(turno)) {
            JOptionPane.showMessageDialog(vista, "Turno modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            controladorPrincipal.recargarTablaTurnos(); // <--- LLAMA AL MÉTODO QUE RECARGA LA TABLA
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al modificar el turno. Verifique disponibilidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int parseoSeguroInt(Object obj) {
        if (obj == null) {
            return 0; // Si es nulo, devuelve 0 (ID no válido)
        }
        try {
            // Si el objeto ya es Integer (frecuente en Object[]), lo devuelve directamente
            if (obj instanceof Integer) {
                return (int) obj;
            }
            // Intenta parsear la representación String
            String s = String.valueOf(obj).trim();
            if (s.isEmpty() || s.equalsIgnoreCase("null")) {
                return 0;
            }
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // Si no es un número válido, devuelve 0
            return 0;
        }
    }

}


