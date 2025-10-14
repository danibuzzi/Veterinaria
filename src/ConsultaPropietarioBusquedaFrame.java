

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ConsultaPropietarioBusquedaFrame extends JFrame {
    private JTextField searchField;
    private JRadioButton apellidoRadio;
    private JRadioButton dniRadio;
    private JTable table;
    private DefaultTableModel tableModel;

    public ConsultaPropietarioBusquedaFrame() {
        setTitle("Consulta de propietario");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setMaximumSize(new Dimension(700, 40));

        searchField = new JTextField("Ingrese búsqueda", 30);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

        JButton searchButton = new JButton("🔍");
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Panel de radio buttons
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.setMaximumSize(new Dimension(700, 30));

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

        mainPanel.add(radioPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tabla
        String[] columnNames = {"Apellidos", "Nombres", "Dirección", "País", "", ""};
        Object[][] data = {
                {"Perez", "Elvira Manuela", "Los Tilos 60", "Argentina", "Modificar", "Eliminar"},
                {"Dig", "Drap", "Nuca casa 400", "Perú", "Modificar", "Eliminar"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4; // Solo las columnas de botones son editables
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Renderizador para botones
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Modificar"));
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Eliminar"));

        // Ajustar anchos de columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.setMaximumSize(new Dimension(640, 150));
        scrollPane.setPreferredSize(new Dimension(640, 150));

        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    // Clase para renderizar botones en la tabla
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(new Color(37, 99, 235));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Clase para editar botones en la tabla
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox, String buttonType) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setForeground(new Color(37, 99, 235));
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> {
                fireEditingStopped();
            });

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setForeground(new Color(29, 78, 216));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setForeground(new Color(37, 99, 235));
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                if (label.equals("Modificar")) {
                    JOptionPane.showMessageDialog(button, "Modificar propietario en fila " + (row + 1));
                } else if (label.equals("Eliminar")) {
                    int confirm = JOptionPane.showConfirmDialog(
                            button,
                            "¿Está seguro de eliminar este propietario?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(row);
                        JOptionPane.showMessageDialog(button, "Propietario eliminado");
                    }
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaPropietarioBusquedaFrame frame = new ConsultaPropietarioBusquedaFrame();
            frame.setVisible(true);
        });
    }
}
