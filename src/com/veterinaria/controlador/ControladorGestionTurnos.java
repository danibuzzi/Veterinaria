package com.veterinaria.controlador;

import com.veterinaria.modelo.*;
import com.veterinaria.vista.VentanaGestionTurnos;
import com.veterinaria.vista.VentanaModificacionTurnos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controlador para la pantalla de Gestión de Turnos (Buscar, Modificar, Eliminar).
 */
public class ControladorGestionTurnos implements ActionListener, ListSelectionListener {

    private final VentanaGestionTurnos vista;
    private final GestorGestionTurnos gestor;

    private final TurnoDAO3 turnoDAO;
    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO;
    private final TipoConsultaDAO tipoConsultaDAO;

    // Las columnas deben coincidir con lo que trae el Gestor/DAO.
    // El ID es la COLUMNA_ID_TURNO (Columna 0).
    private final String[] COLUMNAS = {
            "ID", "Hora", "Tipo Consulta", "Propietario", "Mascota", "Estado"
    };
    private static final int COLUMNA_ID_TURNO = 0;

    // 1. CONSTRUCTOR
    public ControladorGestionTurnos(GestorGestionTurnos gestor, VentanaGestionTurnos vista, TurnoDAO3 turnoDAO, PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO, TipoConsultaDAO tipoConsultaDAO) {
        this.gestor = gestor;
        this.vista = vista;
        this.turnoDAO = turnoDAO;
        this.propietarioDAO = propietarioDAO;
        this.mascotaDAO = mascotaDAO;
        this.tipoConsultaDAO = tipoConsultaDAO;

        // Conecta los botones de la Vista al controlador
        this.vista.setControlador(this);
        //  Agregar el ListSelectionListener a la tabla
        this.vista.getTablaTurnos().getSelectionModel().addListSelectionListener(this);

        // Inicializar los botones como deshabilitados
        actualizarEstadoBotones(false);
    }

    // Dentro de la clase ControladorGestionTurnos.java

    // CORRECCIÓN A: Método fábrica para el Gestor de Modificación
    public GestorModificacionTurnos crearGestorModificacion() {
        // Pasa los 4 DAOs al constructor del Gestor (resuelve el error de inyección)
        return new GestorModificacionTurnos(
                this.turnoDAO,
                this.propietarioDAO,
                this.mascotaDAO,
                this.tipoConsultaDAO
        );
    }

// En ControladorGestionTurnos.java (método abrirVentanaModificacion)
// En ControladorGestionTurnos.java (MÉTODO COMPLETO CORREGIDO)

    private void abrirVentanaModificacion() throws SQLException {
        JTable tabla = vista.getTablaTurnos();
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un turno para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. OBTENER DATOS VISIBLES (6 columnas)
        // El modelo es el que contiene los datos, incluyendo el ID en la columna 0.
        int numColumnasVisibles = tabla.getColumnModel().getColumnCount();
        Object[] datosVisibles = new Object[numColumnasVisibles];

        // Obtener los datos visibles de la fila
        for (int i = 0; i < numColumnasVisibles; i++) {
            // Usamos el modelo de la tabla para obtener el valor
            datosVisibles[i] = tabla.getModel().getValueAt(filaSeleccionada, i);
        }

        // 2. EXTRAER EL ID DEL TURNO PARA CONSULTA
        // Asumimos que la columna 0 (el ID) es un Integer, como lo declaraste.

        int idPropietario = -1;
        int idMascota = -1;
        int idTipoConsulta = -1;
        int idTurno = -1; // Lo inicial

        try {
            idTurno = (Integer) datosVisibles[COLUMNA_ID_TURNO];
        }catch (Exception e) {
            // Muestra un mensaje de error si el ID del Turno es inválido o no se seleccionó
            JOptionPane.showMessageDialog(null, "Debe seleccionar un turno válido para modificar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return; // Detiene la apertura de la ventana si no hay ID.
        }

        // -------------------------------------------------------------------------
        // } OBTENEMOS LOS 3 IDs OCULTOS (Construyendo el Array de 9 posiciones)
        // -------------------------------------------------------------------------
        if (idTurno>0) {
            try{
                //
                idPropietario = turnoDAO.obtenerIDPropietarioPorTurnoID(String.valueOf(idTurno));

                //idPropietario = gestor.obtenerIDPropietarioPorTurnoID(String.valueOf(idTurno));
                idMascota = gestor.obtenerIDMascotaPorTurnoID(String.valueOf(idTurno));
                idTipoConsulta = gestor.obtenerIDTipoConsultaPorTurnoID(String.valueOf(idTurno));
            } catch (SQLException e) {
                // Captura errores de conexión o SQL, pero el valor de los IDs queda en -1.
                System.err.println("Error al obtener IDs ocultos: " + e.getMessage());
            }}
        else{return;}

        // 4. CREAR EL ARRAY FINAL DE 9 POSICIONES
        Object[] datosTurnoCompleto = new Object[9];

        // Llenar las 6 columnas visibles (0 a 5)
        for (int i = 0; i < numColumnasVisibles; i++) { // numColumnasVisibles es 6
            datosTurnoCompleto[i] = datosVisibles[i];
        }

        // Llenar las 3 columnas ocultas (los IDs)
        datosTurnoCompleto[6] = idPropietario;    // INDICE_ID_PROPIETARIO_TURNO
        datosTurnoCompleto[7] = idMascota;        // INDICE_ID_MASCOTA_TURNO
        datosTurnoCompleto[8] = idTipoConsulta;   // INDICE_ID_TIPO_CONSULTA

        // -------------------------------------------------------------------------

        // 5. CREACIÓN DE VISTA, GESTOR Y CONTROLADOR (Ensamblaje MVC)
        // Usamos el método fábrica para crear el Gestor de Modificación.
        GestorModificacionTurnos gestorMod = crearGestorModificacion();

        // La Vista: Le pasamos el array de 9 columnas
        VentanaModificacionTurnos vistaMod = new VentanaModificacionTurnos(
                datosTurnoCompleto[5].toString(), // Asumiendo que la fecha visible está en el índice 5
                datosTurnoCompleto,
                this
        );

        // El Controlador de Modificación (Paso del array de 9 posiciones)
        ControladorModificacionTurnos controladorModificacion = new ControladorModificacionTurnos(
                gestorMod,
                vistaMod,
                this,
                datosTurnoCompleto // ⬅️ CUARTO ARGUMENTO: Array de 9 elementos (¡No habrá más IndexOutOfBounds!)

        );

        // 6. VISUALIZACIÓN
        JDesktopPane escritorio = vista.getEscritorio();

        if (escritorio != null) {
            //vistaMod.pack();
            // Calcular la posición central en el JDesktopPane
            int x = (escritorio.getWidth() - vistaMod.getWidth()) / 2;
            int y = (escritorio.getHeight() - vistaMod.getHeight()) / 2;
            vistaMod.setLocation(x, y);

            escritorio.add(vistaMod);
            vistaMod.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Error: No se puede acceder al contenedor principal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void recargarVistaPrincipal() {
        // 1. Llama al método que carga o busca los turnos en la DB.

        buscarTurnos();

        // 2. Opcionalmente,

        // List<Turno> turnos = gestor.obtenerTodosLosTurnos();
        // vista.actualizarTabla(turnos);
    }

    // 2. MÉTODO PRINCIPAL DE EVENTOS
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "BUSCAR":
                buscarTurnos();
                break;
            case "ELIMINAR":
                eliminarTurno();
                break;
            case "MODIFICAR":
                try {
                    iniciarModificacion();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "SALIR":
                vista.dispose();
                break;
        }
    }

    // -------------------------------------------------------------------------
    // --- MÉTODOS DE GESTIÓN DE TURNOS ---
    // -------------------------------------------------------------------------

    private void buscarTurnos() {
        String fecha = vista.getFechaBusqueda();

        if (fecha.isEmpty()) {
            vista.mostrarMensaje("Debe ingresar una fecha para la búsqueda.", "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los datos del Gestor (incluye el ID en la columna 0)
        Object[][] datos = gestor.obtenerDatosParaGrilla(fecha);

        // Crear el modelo e inmediatamente pasarlo a la Vista (vacío o con datos)
        ModeloTablaNoEditable model = new ModeloTablaNoEditable(datos, COLUMNAS);
        vista.setModeloTabla(model);

        // VERIFICACIÓN Y PUNTO DE SALIDA
        if (datos.length == 0) {
            vista.mostrarMensaje("No se encontraron turnos para la fecha seleccionada.", "Información", JOptionPane.INFORMATION_MESSAGE);

            // Deshabilita botones al no haber resultados y la tabla está vacía
            actualizarEstadoBotones(false);

            return; //
        }


    }

    private void eliminarTurno() {
        JTable tabla = vista.getTablaTurnos();
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            vista.mostrarMensaje("Debe seleccionar una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObjeto = tabla.getValueAt(filaSeleccionada, COLUMNA_ID_TURNO);
        int idTurnoAborrar = (Integer) idObjeto;

        int confirmacion = vista.mostrarConfirmacion("¿Está seguro de eliminar el turno con ID: " + idTurnoAborrar + "?");

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.eliminarTurno(idTurnoAborrar)) {
                vista.mostrarMensaje("Turno eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // 🛑 CORRECCIÓN: Deshabilitar antes de recargar la tabla (o justo después)
                actualizarEstadoBotones(false); // Deshabilita los botones.

                buscarTurnos(); // Recargar la tabla (esto limpiará la selección visual)

            } else {
                vista.mostrarMensaje("Error al intentar eliminar el turno.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void iniciarModificacion() throws SQLException {
        JTable tabla = vista.getTablaTurnos();
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {

            vista.mostrarMensaje("Debe seleccionar una fila para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // Obtener la cantidad de columnas visibles en la tabla
        int numColumnas = tabla.getColumnModel().getColumnCount();
        Object[] datosTurno = new Object[numColumnas];


        for (int i = 0; i < numColumnas; i++) {
            datosTurno[i] = tabla.getValueAt(filaSeleccionada, i);
        }

        // 2. OBTENER LA FECHA SELECCIONADA (necesaria para el constructor de la Vista)

        String fechaSeleccionada = datosTurno[1].toString();

        // 3. LLAMADA AL MÉTODO DE LA VISTA

        abrirVentanaModificacion();
    }



    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Asegura que el evento solo se procese una vez
        if (!e.getValueIsAdjusting()) {
            // Habilita si hay una fila seleccionada (getSelectedRow() devuelve -1 si no hay)
            boolean filaSeleccionada = vista.getTablaTurnos().getSelectedRow() != -1;
            actualizarEstadoBotones(filaSeleccionada);
        }
    }

    // 3. MÉTODO DE UTILIDAD PARA HABILITAR/DESHABILITAR BOTONES (NUEVO)
    private void actualizarEstadoBotones(boolean habilitar) {
        vista.getBtnModificar().setEnabled(habilitar);
        vista.getBtnEliminar().setEnabled(habilitar);
    }


    public void recargarTablaTurnos() {
        // 1. OBTENER LA FECHA NECESARIA:


        // Obtener la fecha activa de la vista como String.

        String fechaStringDB = vista.getFechaSeleccionadaComoString(); // <-- DEBES CREAR ESTE MÉTODO EN LA VISTA

        if (fechaStringDB == null) {
            // Manejar el caso si no hay fecha seleccionada (por ejemplo, usar la fecha de hoy o mostrar todo)
            return;
        }

        // 2. Llama al gestor principal para obtener los datos actualizados.


        Object[][] datosParaGrilla = gestor.obtenerDatosParaGrilla(fechaStringDB);

        // 3. Llama a la vista para actualizar la tabla.
        vista.actualizarTabla(datosParaGrilla);
    }
}
