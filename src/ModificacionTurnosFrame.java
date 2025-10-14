

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class ModificacionTurnosFrame extends JFrame {
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color BLUE_HOVER = new Color(29, 78, 216);
    private static final Color GRAY_LIGHT = new Color(241, 245, 249);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Color GRAY_DISABLED = new Color(148, 163, 184);

    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private YearMonth currentYearMonth;
    private int selectedDay = 24;

    private JComboBox<String> horarioCombo;
    private JTextField tipoConsultaField;
    private JTextField propietarioField;
    private JTextField mascotaField;

    public ModificacionTurnosFrame() {
        setTitle("Modificación de turnos");
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        currentYearMonth = YearMonth.of(2021, 10);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel del calendario
        JPanel calendarSection = createCalendarSection();
        mainPanel.add(calendarSection, BorderLayout.NORTH);

        // Panel del formulario
        JPanel formSection = createFormSection();
        mainPanel.add(formSection, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createCalendarSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Título
        JLabel titleLabel = new JLabel("Seleccionar Fecha");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(GRAY_TEXT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        section.add(titleLabel, BorderLayout.NORTH);

        // Panel del calendario con borde
        JPanel calendarContainer = new JPanel(new BorderLayout());
        calendarContainer.setBackground(GRAY_LIGHT);
        calendarContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRAY_BORDER, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Header del calendario con navegación
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(GRAY_LIGHT);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton prevButton = createNavigationButton("<", true);
        JButton nextButton = createNavigationButton(">", false);

        monthYearLabel = new JLabel(getMonthYearText(), SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        monthYearLabel.setForeground(GRAY_DISABLED);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthYearLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        calendarContainer.add(headerPanel, BorderLayout.NORTH);

        // Grid del calendario
        calendarPanel = new JPanel(new GridLayout(7, 7, 2, 2));
        calendarPanel.setBackground(GRAY_LIGHT);
        updateCalendar();

        calendarContainer.add(calendarPanel, BorderLayout.CENTER);
        section.add(calendarContainer, BorderLayout.CENTER);

        return section;
    }

    private JButton createNavigationButton(String text, boolean isPrev) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(BLUE_PRIMARY);
        button.setBackground(GRAY_LIGHT);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(226, 232, 240));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(GRAY_LIGHT);
            }
        });

        button.addActionListener(e -> {
            if (isPrev) {
                currentYearMonth = currentYearMonth.minusMonths(1);
            } else {
                currentYearMonth = currentYearMonth.plusMonths(1);
            }
            monthYearLabel.setText(getMonthYearText());
            updateCalendar();
        });

        return button;
    }

    private String getMonthYearText() {
        String month = currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return month + " " + currentYearMonth.getYear();
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        // Días de la semana
        String[] dayNames = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
        for (String day : dayNames) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 10));
            label.setForeground(GRAY_DISABLED);
            calendarPanel.add(label);
        }

        // Días del mes
        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int daysInMonth = currentYearMonth.lengthOfMonth();

        YearMonth prevMonth = currentYearMonth.minusMonths(1);
        int daysInPrevMonth = prevMonth.lengthOfMonth();

        // Días del mes anterior
        for (int i = dayOfWeek - 1; i > 0; i--) {
            int day = daysInPrevMonth - i + 1;
            JLabel label = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            label.setForeground(GRAY_DISABLED);
            calendarPanel.add(label);
        }

        // Días del mes actual
        for (int day = 1; day <= daysInMonth; day++) {
            final int currentDay = day;
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            dayButton.setBorder(null);
            dayButton.setFocusPainted(false);
            dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (day == selectedDay) {
                dayButton.setBackground(BLUE_PRIMARY);
                dayButton.setForeground(Color.WHITE);
                dayButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
            } else {
                dayButton.setBackground(GRAY_LIGHT);
                dayButton.setForeground(GRAY_TEXT);
            }

            dayButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (currentDay != selectedDay) {
                        dayButton.setBackground(new Color(226, 232, 240));
                    }
                }
                public void mouseExited(MouseEvent e) {
                    if (currentDay != selectedDay) {
                        dayButton.setBackground(GRAY_LIGHT);
                    }
                }
            });

            dayButton.addActionListener(e -> {
                selectedDay = currentDay;
                updateCalendar();
            });

            calendarPanel.add(dayButton);
        }

        // Días del mes siguiente
        int remainingCells = 42 - (dayOfWeek + daysInMonth - 1);
        for (int day = 1; day <= remainingCells; day++) {
            JLabel label = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            label.setForeground(GRAY_DISABLED);
            calendarPanel.add(label);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private JPanel createFormSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Horario disponible
        section.add(createFormRow("Horario disponible", createHorarioCombo()));
        section.add(Box.createVerticalStrut(15));

        // Tipo de Consulta
        tipoConsultaField = new JTextField("Consulta general");
        section.add(createFormRow("Tipo de Consulta", tipoConsultaField));
        section.add(Box.createVerticalStrut(15));

        // Propietario Mascota
        propietarioField = new JTextField("Perez, Joselo Daniel");
        JPanel propietarioPanel = new JPanel(new BorderLayout());
        propietarioPanel.setBackground(Color.WHITE);
        propietarioPanel.add(propietarioField, BorderLayout.CENTER);

        JButton searchButton = new JButton("🔍");
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchButton.setForeground(GRAY_DISABLED);
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        propietarioPanel.add(searchButton, BorderLayout.EAST);

        section.add(createFormRow("Propietario\nMascota", propietarioPanel));
        section.add(Box.createVerticalStrut(15));

        // Mascota
        mascotaField = new JTextField("Toby");
        section.add(createFormRow("Mascota", mascotaField));

        return section;
    }

    private JComboBox<String> createHorarioCombo() {
        String[] horarios = {
                "Seleccionar horario",
                "09:00 - 10:00",
                "10:00 - 11:00",
                "11:00 - 12:00",
                "14:00 - 15:00",
                "15:00 - 16:00"
        };
        horarioCombo = new JComboBox<>(horarios);
        styleComboBox(horarioCombo);
        return horarioCombo;
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setForeground(GRAY_TEXT);
        combo.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        combo.setFocusable(true);
    }

    private JPanel createFormRow(String labelText, JComponent component) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel("<html>" + labelText.replace("\n", "<br>") + "</html>");
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(GRAY_TEXT);
        label.setPreferredSize(new Dimension(120, 35));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (component instanceof JTextField) {
            JTextField field = (JTextField) component;
            field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            field.setForeground(GRAY_TEXT);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(GRAY_BORDER, 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        }

        row.add(label, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);

        return row;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton guardarButton = createActionButton("Guardar");
        guardarButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Turno modificado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JButton salirButton = createActionButton("Salir");
        salirButton.addActionListener(e -> System.exit(0));

        panel.add(guardarButton);
        panel.add(salirButton);

        return panel;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE_PRIMARY);
        button.setPreferredSize(new Dimension(140, 35));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BLUE_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BLUE_PRIMARY);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ModificacionTurnosFrame frame = new ModificacionTurnosFrame();
            frame.setVisible(true);
        });
    }
}
