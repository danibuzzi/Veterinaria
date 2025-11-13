package com.veterinaria.vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConsultaPropietario extends JInternalFrame {
    private JTextField searchField;
    private JButton buscarTextoButton; // El nuevo botón de texto
    private JRadioButton apellidoRadio;
    private JRadioButton dniRadio;
    private JTable table;
    private DefaultTableModel tableModel;

    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton salirButton;

    public VentanaConsultaPropietario() {
        setTitle("Consulta y Gestión de Propietarios");
        setSize(780, 480); // Ligeramente más ancho para el botón Buscar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Panel Superior (Búsqueda y Filtros) ---
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(Color.WHITE);

        // 1. Panel de Búsqueda (FlowLayout CENTER)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        searchField = new JTextField("Ingrese búsqueda", 30);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(350, 35)); // Altura fija
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Placeholder behavior
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Ingrese búsqueda")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Ingrese búsqueda");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // **NUEVO BOTÓN BUSCAR**
        buscarTextoButton = new JButton("Buscar");
        buscarTextoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buscarTextoButton.setPreferredSize(new Dimension(100, 35));
        buscarTextoButton.setBackground(new Color(37, 99, 235));
        buscarTextoButton.setForeground(Color.WHITE);
        buscarTextoButton.setFocusPainted(false);
        buscarTextoButton.setBorderPainted(false);
        buscarTextoButton.addActionListener(e -> handleBuscarAction());

        searchPanel.add(searchField);
        searchPanel.add(buscarTextoButton); // Se añade el botón
        topContainer.add(searchPanel);
        topContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        // 2. Panel de radio buttons
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        apellidoRadio = new JRadioButton("Apellido");
        apellidoRadio.setSelected(true);
        apellidoRadio.setBackground(Color.WHITE);
        apellidoRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        apellidoRadio.setFocusPainted(false);

        dniRadio = new JRadioButton("DNI");
        dniRadio.setBackground(Color.WHITE);
        dniRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dniRadio.setFocusPainted(false);

        ButtonGroup group = new ButtonGroup();
        group.add(apellidoRadio);
        group.add(dniRadio);

        radioPanel.add(apellidoRadio);
        radioPanel.add(dniRadio);

        topContainer.add(radioPanel);
        topContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(topContainer, BorderLayout.NORTH);

        // --- Tabla de Resultados (Centro) ---
        String[] columnNames = {"Apellidos", "Nombres", "Dirección", "Ciudad", "País"};
        Object[][] data = {
                {"Perez", "Elvira Manuela", "Los Tilos 60", "Córdoba", "Argentina"},
                {"Dig", "Drap", "Nuca casa 400", "Lima", "Perú"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 230, 230));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Panel de Botones de Acción (Sur) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        actionPanel.setBackground(Color.WHITE);

        modificarButton = createActionButton("Modificar", new Color(255, 165, 0)); // Naranja
        eliminarButton = createActionButton("Eliminar", new Color(220, 53, 69)); // Rojo
        salirButton = createActionButton("Salir", new Color(108, 117, 125)); // Gris Oscuro

        actionPanel.add(modificarButton);
        actionPanel.add(eliminarButton);
        actionPanel.add(salirButton);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // --- Lógica de Eventos ---
        modificarButton.addActionListener(e -> handleModificarAction());
        eliminarButton.addActionListener(e -> handleEliminarAction());
        salirButton.addActionListener(e -> dispose());

        setContentPane(mainPanel);
    }

    // Método de ayuda para crear botones estandarizados
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void handleBuscarAction() {
        String query = searchField.getText().trim();
        String filter = apellidoRadio.isSelected() ? "Apellido" : "DNI";

        if (query.isEmpty() || query.equals("Ingrese búsqueda")) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor para la búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simulación: Llamada al PropietarioController.buscar(query, filter)
        JOptionPane.showMessageDialog(this,
                "Buscando por " + filter + ": '" + query + "'.",
                "Búsqueda",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleModificarAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario de la lista para modificar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String apellido = (String) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this, "Iniciando Modificación para el propietario: " + apellido + ".\n(Abre la ficha de modificación)", "Navegación", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleEliminarAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario de la lista para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String apellido = (String) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar a " + nombre + " " + apellido + "? Esta acción es irreversible.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Lógica de eliminación (Tabla de Decisión R1: Éxito)
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Propietario " + apellido + " eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaConsultaPropietario frame = new VentanaConsultaPropietario();
            frame.setVisible(true);
        });
    }
}