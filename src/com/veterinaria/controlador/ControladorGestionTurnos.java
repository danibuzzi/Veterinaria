package com.veterinaria.controlador;

import com.veterinaria.modelo.GestorGestionTurnos;
import com.veterinaria.vista.VentanaGestionTurnos;
import com.veterinaria.modelo.ModeloTablaNoEditable; // 🛑 Usamos la utilidad personalizada
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controlador para la pantalla de Gestión de Turnos (Buscar, Modificar, Eliminar).
 */
public class ControladorGestionTurnos implements ActionListener, ListSelectionListener {

    private final VentanaGestionTurnos vista;
    private final GestorGestionTurnos gestor;

    // Las columnas deben coincidir con lo que trae el Gestor/DAO.
    // El ID es la COLUMNA_ID_TURNO (Columna 0).
    private final String[] COLUMNAS = {
            "ID", "Hora", "Tipo Consulta", "Propietario", "Mascota", "Estado"
    };
    private static final int COLUMNA_ID_TURNO = 0;

    // 1. CONSTRUCTOR
    public ControladorGestionTurnos(GestorGestionTurnos gestor, VentanaGestionTurnos vista) {
        this.gestor = gestor; // 👈 Ahora inyecta el gestor
        this.vista = vista;

        // Conecta los botones de la Vista al controlador
        this.vista.setControlador(this);
        //  Agregar el ListSelectionListener a la tabla
        this.vista.getTablaTurnos().getSelectionModel().addListSelectionListener(this);

        // Inicializar los botones como deshabilitados
        actualizarEstadoBotones(false);
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
                iniciarModificacion();
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

            return; // ⬅️ Correcto: Salir aquí para detener el flujo.
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

    private void iniciarModificacion() {
        JTable tabla = vista.getTablaTurnos();
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            vista.mostrarMensaje("Debe seleccionar una fila para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. OBTENER EL ID del turno seleccionado (Columna 0, oculta)
        Object idObjeto = tabla.getValueAt(filaSeleccionada, COLUMNA_ID_TURNO);
        int idTurnoAActualizar = (Integer) idObjeto;

        // 2. Llama al método de la Vista para que abra la VentanaModificacionTurno
        vista.abrirVentanaModificacion(idTurnoAActualizar);

        // 2. Llama al método de la Vista para que abra la VentanaModificacionTurno
        vista.abrirVentanaModificacion(idTurnoAActualizar);
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
}
