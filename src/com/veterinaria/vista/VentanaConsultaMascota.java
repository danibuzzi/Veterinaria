package com.veterinaria.vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConsultaMascota extends JFrame {
    // Componentes de búsqueda
    private JComboBox<String> propietarioComboBox;
    private JButton buscarButton;
    private JTable table;
    private DefaultTableModel tableModel;

    // Botones de acción centralizados (Se elimina 'Ver Detalle')
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton salirButton;

    public VentanaConsultaMascota() {
        setTitle("Consulta y Gestión de Mascotas");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Panel de Búsqueda (Filtrar por Propietario) ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Combo Box de Propietario
        propietarioComboBox = new JComboBox<>(new String[]{"[Seleccione Propietario]", "Perez, Juan", "Buz, Diego", "Monetti, Emilio"});
        propietarioComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        propietarioComboBox.setPreferredSize(new Dimension(300, 35));

        // Botón Buscar
        buscarButton = new JButton("Buscar");
        buscarButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        buscarButton.setPreferredSize(new Dimension(100, 35));
        buscarButton.setBackground(new Color(37, 99, 235)); // Azul
        buscarButton.setForeground(Color.WHITE);

        searchPanel.add(new JLabel("Filtrar por Propietario:"));
        searchPanel.add(propietarioComboBox);
        searchPanel.add(buscarButton);
        searchPanel.add(Box.createHorizontalGlue());

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Tabla de Resultados ---
        // Columnas ajustadas: ¡Se elimina "Propietario"!
        String[] columnNames = {"", "Nombre", "Fecha Nac.", "Especie", "Raza", "Sexo"};
        Object[][] data = {
                {"", "Toby", "10/20/2020", "Perro", "Mestizo", "Macho"},
                {"", "Pepito", "05/15/2019", "Perro", "Labrador", "Macho"},
                {"", "Michi", "03/10/2021", "Gato", "Siamés", "Hembra"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : super.getColumnClass(columnIndex);
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Ocultar la columna ID si no queremos que se muestre, pero que esté disponible para el Controller
        table.getColumnModel().getColumn(0).setMaxWidth(40);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Panel de Botones de Acción (SIN "Ver Detalle") ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        modificarButton = createActionButton("Modificar", new Color(255, 165, 0)); // Naranja
        eliminarButton = createActionButton("Eliminar", new Color(220, 53, 69)); // Rojo
        salirButton = createActionButton("Salir", new Color(108, 117, 125)); // Gris Oscuro

        actionPanel.add(modificarButton);
        actionPanel.add(eliminarButton);
        actionPanel.add(Box.createHorizontalStrut(50));
        actionPanel.add(salirButton);

        mainPanel.add(actionPanel);

        // --- Lógica de Eventos ---
        eliminarButton.addActionListener(e -> handleEliminarAction());
        modificarButton.addActionListener(e -> handleModificarAction());
        salirButton.addActionListener(e -> dispose());
        buscarButton.addActionListener(e -> handleBuscarAction());

        add(mainPanel);
    }

    // Método para crear botones estandarizados
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    // --- Métodos de Acción (Llamada a Controllers) ---

    private void handleBuscarAction() {
        String propietario = (String) propietarioComboBox.getSelectedItem();
        if (propietario.equals("[Seleccione Propietario]")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            // Aquí iría la llamada al Controller para cargar TODAS las mascotas
        } else {
            // Aquí iría la llamada al Controller: MascotaController.buscarPorPropietario(propietario)
            JOptionPane.showMessageDialog(this, "Buscando mascotas para: " + propietario, "Consulta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleEliminarAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            // Condición de error R2/R4 de la Tabla de Decisión: Fila no seleccionada
            JOptionPane.showMessageDialog(this, "Debe seleccionar una mascota de la lista para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idMascota = (String) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);

        // **Aquí se inicia el flujo de la Tabla de Decisión (en el Controller)**
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar a " + nombre + " (ID: " + idMascota + ")? Esta acción es irreversible.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Lógica simulada de Controller:
            // if (MascotaController.eliminar(idMascota) == true) {
            tableModel.removeRow(selectedRow); // Simulación de éxito
            JOptionPane.showMessageDialog(this, "Mascota " + nombre + " eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // } else { Mostrar error R3: No se puede eliminar por dependencias }
        }
    }

    private void handleModificarAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una mascota de la lista para modificar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idMascota = (String) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);

        // **Aquí se inicia el Caso de Prueba Simple (Navegación)**

        // Simulación: Abre la ventana de Modificación con el ID
        JOptionPane.showMessageDialog(this,
                "Iniciando Modificación para Mascota ID: " + idMascota + " (" + nombre + ").\n" +
                        "El sistema abre la Ficha de Modificación.",
                "Navegación",
                JOptionPane.INFORMATION_MESSAGE);

        // new ModificacionMascotaFrame(idMascota).setVisible(true); // Se llamaría a la siguiente pantalla
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaConsultaMascota frame = new VentanaConsultaMascota();
            frame.setVisible(true);
        });
    }
}
