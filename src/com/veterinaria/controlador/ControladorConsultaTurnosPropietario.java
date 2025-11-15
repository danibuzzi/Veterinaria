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
        JComboBox<String> combo = vista.getPropietarioCombo();

        // Obtener la lista del Service
        List<String> propietarios = turnoPropietarioService.listarPropietariosConId();

        // Limpiar y poblar el combo
        combo.removeAllItems();
        combo.addItem("0;-- Seleccione Propietario --");
        for (String p : propietarios) {
            combo.addItem(p);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Buscar")) { // Comando del botón "Buscar"
            buscarTurnos();
        }
        // Manejar otros eventos si es necesario
    }

    /**
     * Lógica principal de búsqueda: Obtiene el ID seleccionado, la fecha y consulta el Service.
     */
    private void buscarTurnos() {
        int idPropietario = vista.getIdPropietarioSeleccionado();
        Date fechaDesdeDate = vista.getDateChooserFechaDesde().getDate();
        String fechaDesdeSql = null;

        // 1. Validar selección de Propietario
        if (idPropietario <= 0) {
            vista.mostrarError("Debe seleccionar un Propietario válido.");
            vista.getTableModel().setDatos(java.util.Collections.emptyList());
            return;
        }

        // 2. Validar selección de Fecha Desde
        if (fechaDesdeDate == null) {
            vista.mostrarError("Debe seleccionar una Fecha Desde válida.");
            vista.getTableModel().setDatos(java.util.Collections.emptyList());
            return;
        }

        // 3. Formatear la fecha para la consulta SQL
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
            vista.mostrarError("No se encontraron turnos agendados para ese propietario a partir de la fecha seleccionada.");
        }

        // Asegurarse de que la tabla se redibuje
        SwingUtilities.invokeLater(() -> {
            vista.getTurnosTable().revalidate();
            vista.getTurnosTable().repaint();
        });
    }
}