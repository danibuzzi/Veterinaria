

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ConsultaTurnosPropietarioFrame extends JFrame {
    private JComboBox<String> propietarioCombo;
    private JTable turnosTable;
    private DefaultTableModel tableModel;

    public ConsultaTurnosPropietarioFrame() {
        setTitle("Consulta de Turnos por propietario");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con campo Propietario
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel propietarioLabel = new JLabel("Propietario");
        propietarioLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        propietarioLabel.setPreferredSize(new Dimension(100, 25));

        String[] propietarios = {"Monetti,Emilio", "Perez,Juan", "Buz,Diego"};
        propietarioCombo = new JComboBox<>(propietarios);
        propietarioCombo.setPreferredSize(new Dimension(300, 35));
        propietarioCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        propietarioCombo.setBackground(Color.WHITE);
        propietarioCombo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        topPanel.add(propietarioLabel);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(propietarioCombo);

        // Panel de tabla
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        JLabel turnosLabel = new JLabel("Turnos");
        turnosLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        turnosLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Crear tabla
        String[] columnNames = {"Hora", "Tipo Consulta", "Mascota", "", ""};
        Object[][] data = {
                {"10:00 a.m", "Cirugia", "Toby", "Modificar", "Eliminar"},
                {"12:00 a.m.", "Preventiva", "Pepito", "Modificar", "Eliminar"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 3; // Solo las columnas de botones son editables
            }
        };

        turnosTable = new JTable(tableModel);
        turnosTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        turnosTable.setRowHeight(35);
        turnosTable.setGridColor(Color.BLACK);
        turnosTable.setShowGrid(true);
        turnosTable.setIntercellSpacing(new Dimension(1, 1));
        turnosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        turnosTable.getTableHeader().setBackground(Color.WHITE);
        turnosTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        // Configurar anchos de columnas
        turnosTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        turnosTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        turnosTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        turnosTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        turnosTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Renderizador para botones
        ButtonRenderer buttonRenderer = new ButtonRenderer();
        turnosTable.getColumnModel().getColumn(3).setCellRenderer(buttonRenderer);
        turnosTable.getColumnModel().getColumn(4).setCellRenderer(buttonRenderer);

        // Editor para botones
        turnosTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), "Modificar"));
        turnosTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Eliminar"));

        JScrollPane scrollPane = new JScrollPane(turnosTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tablePanel.add(turnosLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Renderizador de botones en la tabla
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setForeground(new Color(37, 99, 235));
            setBackground(Color.WHITE);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor de botones en la tabla
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox, String buttonType) {
            super(checkBox);
            this.label = buttonType;
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setForeground(new Color(37, 99, 235));
            button.setBackground(Color.WHITE);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                if (label.equals("Modificar")) {
                    JOptionPane.showMessageDialog(button,
                            "Modificar turno de la fila " + (row + 1));
                } else if (label.equals("Eliminar")) {
                    int confirm = JOptionPane.showConfirmDialog(button,
                            "¿Está seguro que desea eliminar este turno?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(row);
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
            ConsultaTurnosPropietarioFrame frame = new ConsultaTurnosPropietarioFrame();
            frame.setVisible(true);
        });
    }
}
