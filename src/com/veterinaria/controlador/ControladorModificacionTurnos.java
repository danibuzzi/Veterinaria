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

    // Referenciamos  directamente al Controlador Principal para notificar la recarga.
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

        // Asignamos el array completo que viene del Controlador de Gestión.
        this.datosIniciales = datosSeleccionados;



        vista.setControlador(this);

        cargarOpcionesIniciales();
        seleccionarDatosIniciales();
    }

    public void abrirVentanaModificacion(String fecha, Object[] datosTurno) {
        // Crear la instancia de la ventana
        VentanaModificacionTurnos vista =
                new VentanaModificacionTurnos(fecha, datosTurno, controladorPrincipal);



        //  Le indicamos a la ventana que ya puede seleccionar los ítems del turno
        vista.iniciarSeleccionDeTurno();


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


   //Seleccion de datos iniciales

    private void seleccionarDatosIniciales() {

        // Definición local de índices
        final int INDICE_FECHA = 5;
        final int INDICE_HORA = 1;
        final int INDICE_ID_PROPIETARIO_TURNO = 6;
        final int INDICE_ID_MASCOTA_TURNO = 7;
        final int INDICE_ID_TIPO_CONSULTA = 8;

        try {
            // ----------------- A. CONVERSIÓN Y ESTABLECIMIENTO DE FECHA -----------------
            Object fechaObjeto = datosIniciales[INDICE_FECHA];

            // Validacion fecha
            if (fechaObjeto instanceof java.util.Date) {
                java.util.Date fechaDate = (java.util.Date) fechaObjeto;

                // Convertir a LocalDate
                LocalDate fechaLocalDate = fechaDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // Establecer en Vista
                vista.setFechaSeleccionada(fechaLocalDate);

            } else if (fechaObjeto instanceof String) {
                // Manejar si eldato viene como String
                try {
                    java.sql.Date fechaSQL = java.sql.Date.valueOf((String) fechaObjeto);
                    LocalDate fechaLocalDate = fechaSQL.toLocalDate();
                    vista.setFechaSeleccionada(fechaLocalDate);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error de formato de fecha String: " + e.getMessage());
                }
            }

            // ----------------- SELECCIÓN DE COMBOS (USANDO safeParseInt) -----------------

            // HORA (Se selecciona después de que la fecha cargue los horarios)
            Object horaObj = datosIniciales[INDICE_HORA];
            if (horaObj != null) {
                vista.getHorarioCombo().setSelectedItem(String.valueOf(horaObj));
            }

            // PROPIETARIO
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

            // MASCOTA
            int idMascotaTurno = parseoSeguroInt(datosIniciales[INDICE_ID_MASCOTA_TURNO]);
            if (idMascotaTurno > 0) {
                Mascota mBusqueda = new Mascota();
                mBusqueda.setIdMascota(idMascotaTurno);
                vista.getMascotaCombo().setSelectedItem(mBusqueda);
            }

        } catch (Exception e) {
            // Manejador de errores para capturar errores inesperados
            System.err.println("Error durante la selección de datos iniciales: " + e.getMessage());
            JOptionPane.showMessageDialog(vista, "ERROR FATAL EN SELECCIÓN: " + e.getMessage(), "Fallo en Selección", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Método auxiliar para la Mascota
    private void cargarYSeleccionarMascota(int idPropietario, int idMascotaTurno) {
        // Dispare accion de "PROPIETARIO_CAMBIO"r,de forma manual.
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
                // Carga de mascotas al cambiar el propietario.
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

    // Guardado de modificacion de Turno

    private void guardarModificacion() {
        Propietario prop = (Propietario) vista.getPropietarioCombo().getSelectedItem();
        Mascota masc = (Mascota) vista.getMascotaCombo().getSelectedItem();
        String nombreTipoConsulta = vista.getTipoConsultaCombo().getSelectedItem().toString(); // Nombre de la consulta
        //  Ontencion de un objeto TipoConsulta,
        TipoConsulta tipoConsultaSeleccionada =
                (TipoConsulta) vista.getTipoConsultaCombo().getSelectedItem();

        // Obtener el ID de ese objeto para pasarlo al Gestor/DAO.

        //int idTipoConsulta = tipoConsultaSeleccionada.getIdTipoConsulta();
        String hora = (String) vista.getHorarioCombo().getSelectedItem();
        Date date = vista.getDateChooserFecha().getDate();

        // Obtener el ID del Tipo de Consulta
        // Ya que la tabla turno usa idTipoConsulta (INT) y no el String
        //int idTipoConsulta;
        int idTipoConsulta=0;
        try {

            idTipoConsulta = tipoConsultaSeleccionada.getIdTipoConsulta();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener ID de Tipo de Consulta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (date == null || prop == null || masc == null || nombreTipoConsulta == null || hora == null) {
            JOptionPane.showMessageDialog(vista, "Todos los campos deben estar seleccionados.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Formateamos la fecha a String (YYYY-MM-DD) para que coincida con tu constructor
        LocalDate nuevaFecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String fechaStringDB = nuevaFecha.toString(); // Formato estándar "YYYY-MM-DD"

        // Validación de fecha para no permitir fechas pasadas
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
        // --- CONSTRUCCIÓN DEL TURNO USANDO Constructor-

        Turno turno = new Turno(idTurnoOriginal,
                idTipoConsulta,
                prop.getIdPropietario(),
                masc.getIdMascota(),
                fechaStringDB, // Usamos String como lo pide tu constructor
                hora
        );


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
            // Si el objeto ya es Integer , lo devuelve directamente
            if (obj instanceof Integer) {
                return (int) obj;
            }
            // Se busca parsear la representación String
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


