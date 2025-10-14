

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class ConsultaTurnosFrame extends JFrame {
    private JPanel calendarPanel;
    private JTable turnosTable;
    private DefaultTableModel tableModel;
    private YearMonth currentMonth;
    private int selectedDay = 24;
    private JLabel monthLabel;

    public ConsultaTurnosFrame() {
        currentMonth = YearMonth.of(2021, 10);
        initComponents();
    }

    private void initComponents() {
        setTitle("Consulta de turnos asignados");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        // Sección de calendario
        JPanel calendarSection = createCalendarSection();
        mainPanel.add(calendarSection);
        mainPanel.add(Box.createVerticalStrut(30));

        // Tabla de turnos
        JPanel tableSection = createTableSection();
        mainPanel.add(tableSection);

        add(mainPanel);
    }

    private JPanel createCalendarSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Título
        JLabel titleLabel = new JLabel("Seleccionar Fecha");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(titleLabel);
        section.add(Box.createVerticalStrut(15));

        // Panel del calendario
        JPanel calendarContainer = new JPanel();
        calendarContainer.setLayout(new BorderLayout());
        calendarContainer.setBackground(Color.WHITE);
        calendarContainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        calendarContainer.setMaximumSize(new Dimension(600, 300));
        calendarContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BorderLayout(0, 10));
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de navegación
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(Color.WHITE);

        JButton prevButton = createNavButton("<");
        prevButton.addActionListener(e -> changeMonth(-1));

        JButton nextButton = createNavButton(">");
        nextButton.addActionListener(e -> changeMonth(1));

        monthLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        monthLabel.setForeground(new Color(150, 150, 150));

        navPanel.add(prevButton, BorderLayout.WEST);
        navPanel.add(monthLabel, BorderLayout.CENTER);
        navPanel.add(nextButton, BorderLayout.EAST);

        calendarPanel.add(navPanel, BorderLayout.NORTH);

        // Grid del calendario
        JPanel gridPanel = createCalendarGrid();
        calendarPanel.add(gridPanel, BorderLayout.CENTER);

        calendarContainer.add(calendarPanel);
        section.add(calendarContainer);

        return section;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(40, 30));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(new Color(37, 99, 235));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    private JPanel createCalendarGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(0, 7, 2, 2));
        gridPanel.setBackground(Color.WHITE);

        // Días de la semana
        String[] daysOfWeek = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(new Color(100, 100, 100));
            gridPanel.add(label);
        }

        // Días del mes
        LocalDate firstDay = currentMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // 1 = Monday
        int daysInMonth = currentMonth.lengthOfMonth();

        // Días del mes anterior (si es necesario)
        for (int i = 1; i < dayOfWeek; i++) {
            gridPanel.add(new JLabel());
        }

        // Días del mes actual
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = createDayButton(day, true);
            gridPanel.add(dayButton);
        }

        // Días del mes siguiente
        int remainingCells = 42 - (dayOfWeek - 1 + daysInMonth);
        for (int day = 1; day <= remainingCells; day++) {
            JButton dayButton = createDayButton(day, false);
            gridPanel.add(dayButton);
        }

        return gridPanel;
    }

    private JButton createDayButton(int day, boolean isCurrentMonth) {
        JButton button = new JButton(String.valueOf(day));
        button.setPreferredSize(new Dimension(48, 32));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isCurrentMonth) {
            if (day == selectedDay) {
                button.setBackground(new Color(37, 99, 235));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(50, 50, 50));
            }

            button.addActionListener(e -> {
                selectedDay = day;
                refreshCalendar();
            });

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (day != selectedDay) {
                        button.setBackground(new Color(245, 245, 245));
                    }
                }
                public void mouseExited(MouseEvent e) {
                    if (day != selectedDay) {
                        button.setBackground(Color.WHITE);
                    }
                }
            });
        } else {
            button.setBackground(new Color(239, 246, 255));
            button.setForeground(new Color(180, 180, 180));
            button.setEnabled(false);
        }

        return button;
    }

    private JPanel createTableSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Crear modelo de tabla
        String[] columnNames = {"Hora", "Tipo Consulta", "Propietario", "Mascota", "Acciones", "Acciones"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4; // Solo las columnas de acciones son editables
            }
        };

        // Agregar datos de ejemplo
        tableModel.addRow(new Object[]{"10:00 a.m", "Cirugia", "Perez,Juan", "Toby", "Modificar", "Eliminar"});
        tableModel.addRow(new Object[]{"12:00 a.m.", "Preventiva", "Buz. Diego", "Pepito", "Modificar", "Eliminar"});

        // Crear tabla
        turnosTable = new JTable(tableModel);
        turnosTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        turnosTable.setRowHeight(35);
        turnosTable.setGridColor(new Color(50, 50, 50));
        turnosTable.setShowGrid(true);
        turnosTable.setIntercellSpacing(new Dimension(1, 1));

        // Configurar header
        JTableHeader header = turnosTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(50, 50, 50));
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(50, 50, 50)));

        // Configurar renderizador para las columnas de acciones
        turnosTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        turnosTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Modificar"));
        turnosTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        turnosTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Eliminar"));

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(turnosTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        section.add(scrollPane, BorderLayout.CENTER);

        return section;
    }

    // Clase para renderizar botones en la tabla
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setForeground(new Color(37, 99, 235));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder());
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Clase para editar botones en la tabla
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private String action;

        public ButtonEditor(JCheckBox checkBox, String action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setForeground(new Color(37, 99, 235));
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int row = turnosTable.getSelectedRow();
                if (action.equals("Modificar")) {
                    JOptionPane.showMessageDialog(button, "Modificar turno en fila " + (row + 1));
                } else if (action.equals("Eliminar")) {
                    int confirm = JOptionPane.showConfirmDialog(button,
                            "¿Está seguro de eliminar este turno?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(row);
                    }
                }
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    private void changeMonth(int delta) {
        currentMonth = currentMonth.plusMonths(delta);
        monthLabel.setText(getMonthYearString());
        refreshCalendar();
    }

    private void refreshCalendar() {
        calendarPanel.remove(1); // Remover el grid anterior
        JPanel newGrid = createCalendarGrid();
        calendarPanel.add(newGrid, BorderLayout.CENTER);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private String getMonthYearString() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[currentMonth.getMonthValue() - 1] + " " + currentMonth.getYear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaTurnosFrame frame = new ConsultaTurnosFrame();
            frame.setVisible(true);
        });
    }
}

