import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

// Asegúrate de que NO haya línea "package" antes de esta línea.
public class ReservaTurnosFrame extends JFrame {

    // Componentes del formulario (variables globales)
    private JComboBox<String> comboHorarios;
    private JComboBox<String> comboTipoConsulta;
    private JTextField txtPropietario;
    private JComboBox<String> comboMascota;
    private JButton btnGuardar;
    private JButton btnSalir;

    // --- CONSTRUCTOR DE LA VENTANA ---
    public ReservaTurnosFrame() {
        setTitle("Reserva de Turnos");
        setSize(850, 580);
        // Importante: DISPOSE_ON_CLOSE permite que el resto del programa siga ejecutándose.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel Principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Panel de Contenido (usa GridBagLayout para dividirlo en dos columnas)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;

        // 1. Panel de la Izquierda: CALENDARIO (Placeholder)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Ocupa la mitad del ancho
        gbc.weighty = 1.0;
        JPanel calendarPlaceholder = crearPlaceholderCalendario();
        contentPanel.add(calendarPlaceholder, gbc);

        // 2. Panel de la Derecha: FORMULARIO DE DATOS
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Ocupa la otra mitad del ancho
        gbc.weighty = 1.0;
        JPanel formPanel = crearPanelFormulario();
        contentPanel.add(formPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Panel de Botones (parte inferior)
        JPanel buttonPanel = crearPanelBotones();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE PANELES ---

    private JPanel crearPlaceholderCalendario() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Seleccionar Fecha", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JLabel placeholder = new JLabel("[Aquí va su JCalendar o simulación]", SwingConstants.CENTER);
        placeholder.setPreferredSize(new Dimension(350, 400));
        placeholder.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.add(placeholder, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        int y = 0;

        // 1. Horarios disponibles
        gbc.gridx = 0; gbc.gridy = y++;
        panel.add(new JLabel("Horarios disponibles"), gbc);

        gbc.gridy = y++;
        String[] horarios = {"Seleccionar horario", "09:00", "10:30", "14:00"};
        comboHorarios = crearCombo(horarios);
        panel.add(comboHorarios, gbc);

        // 2. Tipo de Consulta
        gbc.gridy = y++;
        panel.add(new JLabel("Tipo de Consulta"), gbc);

        gbc.gridy = y++;
        String[] tipos = {"Seleccionar tipo de consulta", "Control Anual", "Vacunación", "Urgencia"};
        comboTipoConsulta = crearCombo(tipos);
        panel.add(comboTipoConsulta, gbc);

        // 3. Propietario (Campo de Búsqueda)
        gbc.gridy = y++;
        panel.add(new JLabel("Propietario"), gbc);

        gbc.gridy = y++;
        JPanel searchPanel = crearCampoBusqueda("Buscar Propietario...");
        panel.add(searchPanel, gbc);

        // 4. Mascota
        gbc.gridy = y++;
        panel.add(new JLabel("Mascota"), gbc);

        gbc.gridy = y++;
        String[] mascotas = {"Seleccionar mascota", "Toby", "Pepito", "Max"};
        comboMascota = crearCombo(mascotas);
        panel.add(comboMascota, gbc);

        // Espaciador (para empujar el contenido hacia arriba)
        gbc.gridy = y++;
        gbc.weighty = 1.0;
        panel.add(new JPanel(), gbc);

        return panel;
    }

    // --- MÉTODOS AUXILIARES ---

    private JComboBox<String> crearCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(250, 35));
        return combo;
    }

    private JPanel crearCampoBusqueda(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(215, 35));

        JButton btnSearch = new JButton("🔍");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch.setMargin(new Insets(2, 5, 2, 5));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(field, BorderLayout.CENTER);
        panel.add(btnSearch, BorderLayout.EAST);
        panel.setPreferredSize(new Dimension(250, 35));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBotonGeneral("Guardar", new Color(0, 123, 255));
        btnSalir = crearBotonGeneral("Salir", new Color(30, 30, 30));

        getRootPane().setDefaultButton(btnGuardar);

        // Acción del botón Salir para cerrar solo esta ventana
        btnSalir.addActionListener(e -> dispose());

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    private JButton crearBotonGeneral(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BusquedaHistoriaClinicaFrame frame = new BusquedaHistoriaClinicaFrame();
            frame.setVisible(true);
        });
    }
}