package com.veterinaria.controlador;



import com.veterinaria.modelo.*;
import com.veterinaria.vista.VentanaTurnosPropietario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;


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
            // 1. Obtener la lista de propietarios con ID
            List<String> propietarios = turnoPropietarioService.listarPropietariosConId();

            // 2. Vaciar el combo para evitar duplicados
            vista.getPropietarioCombo().removeAllItems();

            // 3. Llenar el combo
            if (propietarios.isEmpty()) {
                vista.getPropietarioCombo().addItem("No hay propietarios registrados");
                vista.getPropietarioCombo().setEnabled(false);
            } else {
                for (String p : propietarios) {
                    vista.getPropietarioCombo().addItem(p);
                }
                vista.getPropietarioCombo().setEnabled(true);
            }
        } catch (Exception e) {
            vista.mostrarError("Error al cargar la lista de propietarios: " + e.getMessage());
        }
    }


    /**
     * Extrae el ID del propietario del String seleccionado en el JComboBox.
     * El formato es "ID;Nombre Apellido".
     * @param item El String seleccionado.
     * @return El ID del propietario (int), o -1 si el formato es incorrecto.
     */
    private int extraerIdPropietario(String item) {
        if (item != null && item.contains(";")) {
            try {
                // Divide el String en el separador ';' y toma la primera parte (el ID)
                return Integer.parseInt(item.split(";")[0].trim());
            } catch (NumberFormatException e) {
                // El ID no es un número válido
                return -1;
            }
        }
        return -1; // No hay ítem seleccionado o formato incorrecto
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBuscarButton()) {
            consultarTurnos();
        }
    }


    private void consultarTurnos() {
        String propietarioSeleccionado = (String) vista.getPropietarioCombo().getSelectedItem();
        Date fechaDesdeDate = vista.getDateChooserFechaDesde().getDate();
        String fechaDesdeSql;

        // 1. Validar selección de Propietario y obtener ID
        int idPropietario = extraerIdPropietario(propietarioSeleccionado);
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

        // 3. Formatear la fecha para la consulta SQL (yyyy-MM-dd)
        try {
            fechaDesdeSql = sqlDateFormat.format(fechaDesdeDate);
        } catch (Exception e) {
            vista.mostrarError("Error al procesar la fecha: " + e.getMessage());
            return;
        }

        // 4. Consultar al Service/Gestor con DOBLE FILTRO
        // Esta lista contiene [ID, Fecha, Hora, Tipo Consulta, Mascota, Estado]
        List<Object[]> turnos = null;
        try {
            turnos = turnoPropietarioService.obtenerTurnosPorPropietario(idPropietario, fechaDesdeSql);
        } catch (Exception e) {
            // Manejar errores del DAO/Service (e.g., SQLException)
            vista.mostrarError("Error al consultar la base de datos: " + e.getMessage());
            vista.getTableModel().setDatos(java.util.Collections.emptyList());
            return;
        }


        // 5. Actualizar la Vista (Modelo y Ocultar Columnas)
        vista.getTableModel().setDatos(turnos);

        // CRÍTICO: Llamamos a ocultar las columnas DESPUÉS de actualizar el modelo
        if (turnos != null && !turnos.isEmpty()) {
            vista.ocultarColumnasTabla();
        }

        // 6. Mostrar mensaje si no hay resultados
        if (turnos == null || turnos.isEmpty()) {
            vista.mostrarMensaje("No se encontraron turnos agendados para ese propietario a partir de la fecha seleccionada.");
        } else {
            vista.mostrarMensaje("Consulta realizada con éxito. Mostrando " + turnos.size() + " turno(s).");
        }

        // 7. Asegurarse de que la tabla se redibuje
        vista.getTurnosTable().repaint();
    }
}