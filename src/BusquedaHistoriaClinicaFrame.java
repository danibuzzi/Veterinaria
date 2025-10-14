
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import java.awt.image.BufferedImage; // Necesario para crear la imagen dummy

public class BusquedaHistoriaClinicaFrame extends JFrame {
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JTextField txtFechaBusqueda;
    private JButton btnBuscar;
    private JButton btnSalir;
    private JTable tablaResultados;

    public BusquedaHistoriaClinicaFrame() {
        setTitle("Búsqueda de Historia Clínica");
        // Tamaño de ventana para acomodar la grilla
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. PANEL SUPERIOR DE BÚSQUEDA ---
        JPanel searchPanel = crearPanelBusqueda();
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // --- 2. PANEL CENTRAL DE RESULTADOS (JTable) ---
        JScrollPane scrollTable = crearTablaResultados();
        mainPanel.add(scrollTable, BorderLayout.CENTER);

        // --- 3. PANEL INFERIOR DE BOTONES ---
        JPanel buttonPanel = crearPanelBotones();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE LA INTERFAZ ---

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Parámetros de Búsqueda",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Propietario (Fila 0, Columna 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPropietario = new JLabel("Propietario:");
        lblPropietario.setFont(labelFont);
        panel.add(lblPropietario, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        String[] propietarios = {"Seleccionar propietario", "Perez,Ana Maria", "Monetti,Emilio"};
        comboPropietario = new JComboBox<>(propietarios);
        comboPropietario.setFont(fieldFont);
        comboPropietario.setPreferredSize(new Dimension(250, 35));
        panel.add(comboPropietario, gbc);

        // Mascota (Fila 0, Columna 2)
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setFont(labelFont);
        panel.add(lblMascota, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        String[] mascotas = {"Seleccionar mascota", "Toby", "Pepito", "Max"};
        comboMascota = new JComboBox<>(mascotas);
        comboMascota.setFont(fieldFont);
        comboMascota.setPreferredSize(new Dimension(250, 35));
        panel.add(comboMascota, gbc);

        // Campo de Fecha (Fila 1, Columna 0 y 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblFecha = new JLabel("Fecha (DD/MM/AAAA):");
        lblFecha.setFont(labelFont);
        panel.add(lblFecha, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        // Solo JTextField, sin el botón de calendario.
        txtFechaBusqueda = new JTextField(15);
        txtFechaBusqueda.setFont(fieldFont);
        txtFechaBusqueda.setPreferredSize(new Dimension(250, 35));
        panel.add(txtFechaBusqueda, gbc);

        // Botón Buscar (Fila 1, Columna 3)
        gbc.gridx = 3;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.EAST;
        btnBuscar = crearBotonBusqueda("Buscar");
        btnBuscar.setPreferredSize(new Dimension(150, 35));
        btnBuscar.addActionListener(e -> buscarConsultas());
        panel.add(btnBuscar, gbc);

        return panel;
    }

    private JScrollPane crearTablaResultados() {
        // Columnas actualizadas: Se eliminó "Veterinario" y se corrigió "Tratamiento"
        String[] columnNames = {"Fecha", "Tipo Práctica", "Diagnóstico Principal", "Pronóstico", "Tratamiento"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tablaResultados = new JTable(model);
        tablaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaResultados.setRowHeight(25);
        tablaResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Ajuste de ancho de columnas para mejor visualización
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(120); // Fecha
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(180); // Tipo Práctica
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(280); // Diagnóstico
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(170); // Pronóstico
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(280); // Tratamiento

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Resultados de la Historia Clínica",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK
        ));

        return scrollPane;
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnSalir = crearBotonGeneral("Salir", new Color(220, 53, 69));
        btnSalir.addActionListener(e -> dispose());

        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    private JButton crearBotonBusqueda(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 86, 179));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
        return button;
    }

    private JButton crearBotonGeneral(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    // --- LÓGICA DE BÚSQUEDA (Simulación) ---

    private void buscarConsultas() {
        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setRowCount(0);

        if (comboPropietario.getSelectedIndex() == 0 || comboMascota.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar Propietario y Mascota para buscar.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mascota = (String) comboMascota.getSelectedItem();
        String fecha = txtFechaBusqueda.getText().trim();

        // SIMULACIÓN DE DATOS
        if (mascota.equals("Toby")) {
            if (fecha.isEmpty() || fecha.equals("DD/MM/AAAA")) {
                // Resultados para Toby sin filtro de fecha
                model.addRow(new Object[]{"15/05/2024", "Vacunación", "Control Anual", "Favorable", "Dosis Triple (refuerzo anual)"});
                model.addRow(new Object[]{"20/07/2024", "Análisis", "Sospecha de Anemia", "Reservado", "Dieta rica en hierro y Vitamina K"});
            }

            // Simulación de búsqueda por fecha específica
            if (fecha.equals("25/09/2024")) {
                model.addRow(new Object[]{"25/09/2024", "Control", "Recuperación post-análisis", "Bueno", "Continuar con suplemento B12 oral."});
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron consultas para la fecha ingresada.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (mascota.equals("Pepito")) {
            model.addRow(new Object[]{"01/08/2024", "Cirugía", "Fractura de pata", "Favorable", "Inmovilización con férula por 4 semanas."});
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron consultas para los parámetros seleccionados.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BusquedaHistoriaClinicaFrame frame = new BusquedaHistoriaClinicaFrame();
            frame.setVisible(true);
        });
    }
}