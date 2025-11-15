package com.veterinaria.controlador;


import com.veterinaria.modelo.*;
import com.veterinaria.vista.VentanaTurnosPropietario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;

/**
 * Controlador para la Consulta de Turnos por Propietario.
 */
public class ControladorConsultaTurnosPropietario implements ActionListener {

    private final VentanaTurnosPropietario vista;
    private final TurnoPropietarioService turnoPropietarioService;
    private final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato para SQL
    private final JDesktopPane escritorio;

    public ControladorConsultaTurnosPropietario(VentanaTurnosPropietario vista, TurnoPropietarioService turnoPropietarioService, JDesktopPane escritorio) {
        this.vista = vista;
        this.turnoPropietarioService = turnoPropietarioService;
        this.escritorio = escritorio;

        // Inicializar listeners
        vista.getBuscarButton().addActionListener(this);

        // Carga inicial del ComboBox de propietarios
        cargarPropietarios();
    }

    /**
     * Carga el JComboBox con los propietarios, usando el formato ID;Nombre Apellido.
     */
    private void cargarPropietarios() {
        try {
            // 1. Obtener la lista del Service
            // La lista viene como List<String> donde cada String es "ID;Nombre Apellido"
            List<String> propietarios = turnoPropietarioService.listarPropietariosConId();

            // 2. Limpiar y repoblar el JComboBox
            vista.getPropietarioCombo().removeAllItems();
            vista.getPropietarioCombo().addItem("Seleccione un Propietario"); // Valor por defecto y ayuda

            // 3. Añadir todos los propietarios obtenidos
            for (String propietario : propietarios) {
                vista.getPropietarioCombo().addItem(propietario);
            }
        } catch (Exception e) {
            vista.mostrarError("Error al cargar la lista de propietarios: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBuscarButton()) {
            consultarTurnos();
        }
    }

    private void consultarTurnos() {
        String propietarioSeleccionado = (String) vista.getPropietarioCombo().getSelectedItem();
        Date fechaDesdeDate = vista.getFechaDesdeDate();
        String fechaDesdeSql = null;
        int idPropietario = -1;

        // 1. Validar y obtener el ID del Propietario
        if (propietarioSeleccionado == null || propietarioSeleccionado.equals("Seleccione un Propietario")) {
            vista.mostrarError("Debe seleccionar un Propietario válido.");
            vista.getTableModel().setDatos(java.util.Collections.emptyList());
            return;
        }

        try {
            // El formato es "ID;Nombre Apellido". Separamos por ';' para obtener el ID.
            idPropietario = Integer.parseInt(propietarioSeleccionado.split(";")[0].trim());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Error al obtener el ID del propietario seleccionado.");
            vista.getTableModel().setDatos(java.util.Collections.emptyList());
            return;
        }

        // 2. Validar selección de Fecha Desde
        if (fechaDesdeDate == null) {
            vista.mostrarError("Debe seleccionar una Fecha Desde válida.");
            vista.getTableModel().setDatos(java.util.Collections.emptyList());
            return;
        }

        // 3. Formatear la fecha para la consulta SQL (yyyy-MM-dd)
        try {
            fechaDesdeSql = sqlDateFormat.format(fechaDesdeDate);
        } catch (Exception e) {
            vista.mostrarError("Error al procesar la fecha: " + e.getMessage());
            return;
        }

        // 4. Consultar al Service/Gestor con DOBLE FILTRO
        // Esta lista contiene [Fecha, Hora, Tipo Consulta, Mascota]
        List<Object[]> turnos = turnoPropietarioService.obtenerTurnosPorPropietario(idPropietario, fechaDesdeSql);

        // 5. Actualizar la Vista
        vista.getTableModel().setDatos(turnos);

        // 6. Mostrar mensaje si no hay resultados
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("No se encontraron turnos agendados para ese propietario a partir de la fecha seleccionada.");
        } else {
            vista.mostrarMensaje("Consulta realizada con éxito. Mostrando " + turnos.size() + " turno(s).");
        }

        // Asegurarse de que la tabla se redibuje
        SwingUtilities.invokeLater(() -> {
            vista.getTurnosTable().revalidate();
            vista.getTurnosTable().repaint();
        });
    }
}