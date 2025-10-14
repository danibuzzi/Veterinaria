

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ConsultaMascotaFrame extends JFrame {
    private JTextField searchField;
    private JRadioButton nombreRadio, especieRadio, propietarioRadio;
    private JTable table;
    private DefaultTableModel tableModel;

    public ConsultaMascotaFrame() {
        setTitle("Consulta de Mascota");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Panel de búsqueda
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Campo de búsqueda
        JPanel searchFieldPanel = new JPanel(new BorderLayout(10, 0));
        searchFieldPanel.setBackground(Color.WHITE);
        searchFieldPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        searchField = new JTextField("Ingrese búsqueda");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setPreferredSize(new Dimension(700, 45));
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Ingrese búsqueda")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Ingrese búsqueda");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        searchFieldPanel.add(searchIcon, BorderLayout.EAST);

        searchPanel.add(searchFieldPanel);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Radio buttons
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        ButtonGroup group = new ButtonGroup();
        nombreRadio = new JRadioButton("Nombre", true);
        especieRadio = new JRadioButton("Especie");
        propietarioRadio = new JRadioButton("Propietario");

        Font radioFont = new Font("Segoe UI", Font.PLAIN, 15);
        nombreRadio.setFont(radioFont);
        especieRadio.setFont(radioFont);
        propietarioRadio.setFont(radioFont);

        nombreRadio.setBackground(Color.WHITE);
        especieRadio.setBackground(Color.WHITE);
        propietarioRadio.setBackground(Color.WHITE);

        group.add(nombreRadio);
        group.add(especieRadio);
        group.add(propietarioRadio);

        radioPanel.add(nombreRadio);
        radioPanel.add(especieRadio);
        radioPanel.add(propietarioRadio);

        searchPanel.add(radioPanel);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(searchPanel);

        // Tabla
        String[] columnNames = {"Nombre", "Fecha Nac.", "Especie", "Raza", "Sexo", "Propietario", "Modificar", "Eliminar"};
        Object[][] data = {
                {"Toby", "10/20/2020", "Perro", "Mestizo", "Macho", "Perez, Juan", "Modificar", "Eliminar"},
                {"Pepito", "05/15/2019", "Perro", "Labrador", "Macho", "Buz, Diego", "Modificar", "Eliminar"},
                {"Michi", "03/10/2021", "Gato", "Siamés", "Hembra", "Monetti, Emilio", "Modificar", "Eliminar"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Renderizador y editor para botones
        table.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox(), "Modificar"));
        table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), "Eliminar"));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    // Renderizador de botones
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBackground(new Color(37, 99, 235));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder());
            return this;
        }
    }

    // Editor de botones
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox, String buttonType) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            button.setBackground(new Color(37, 99, 235));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(e -> {
                fireEditingStopped();
            });

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(29, 78, 216));
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(new Color(37, 99, 235));
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                String nombre = (String) tableModel.getValueAt(row, 0);
                if (label.equals("Modificar")) {
                    JOptionPane.showMessageDialog(button, "Modificar mascota: " + nombre);
                } else if (label.equals("Eliminar")) {
                    int confirm = JOptionPane.showConfirmDialog(button,
                            "¿Está seguro que desea eliminar la mascota " + nombre + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(row);
                        JOptionPane.showMessageDialog(button, "Mascota " + nombre + " eliminada");
                    }
                }
            }
            clicked = false;
            return label;
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaMascotaFrame frame = new ConsultaMascotaFrame();
            frame.setVisible(true);
        });
    }
}
