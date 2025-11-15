package com.veterinaria.vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener; // Usamos ActionListener para el controlador
import java.util.Date;
import java.text.SimpleDateFormat;

public class VentanaHistoriaClinica extends JInternalFrame {

    // --- CONSTANTES DE ESTILO ---
    private static final Color BLUE_PRIMARY = new Color(0, 123, 255);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final int FIELD_HEIGHT = 35;

    // --- 1. DECLARACIÓN DE COMPONENTES ---
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JDateChooser dateChooserFecha; // El JCalendar
    private JButton btnBuscar;
    private JButton btnVerDetalle; // Nuevo botón para ver el detalle de la consulta
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    // --- 2. CONSTRUCTOR ---
    public VentanaHistoriaClinica() {
        super("Historia Clínica de Mascotas", true, true, true, true);

        // El tamaño de ventana se ajusta a una JInternalFrame grande
        setSize(1050, 700);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. PANEL SUPERIOR DE BÚSQUEDA Y FILTRO ---
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

        // Estilo TitledBorder consistente con el diseño de Registro/Turnos
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BLUE_PRIMARY, 1),
                "Parámetros de Búsqueda de Historia",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_BOLD.deriveFont(Font.ITALIC, 15), BLUE_PRIMARY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        int col = 0;

        // --- 1. Propietario (Fila 0, Columna 0 y 1) ---
        gbc.gridx = col++;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(crearEtiqueta("Propietario:"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        String[] propietarios = {"Seleccionar propietario", "Perez, Ana Maria", "Monetti, Emilio"};
        comboPropietario = new JComboBox<>(propietarios);
        comboPropietario.setFont(FONT_FIELD);
        comboPropietario.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        panel.add(comboPropietario, gbc);

        // --- 2. Mascota (Fila 0, Columna 2 y 3) ---
        gbc.gridx = col++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(crearEtiqueta("Mascota:"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        String[] mascotas = {"Seleccionar mascota", "Toby", "Pepito", "Max"};
        comboMascota = new JComboBox<>(mascotas);
        comboMascota.setFont(FONT_FIELD);
        comboMascota.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        panel.add(comboMascota, gbc);

        // Reiniciamos columna para la fecha/botón
        col = 0;

        // --- 3. JCalendar para la Fecha (Fila 1, Columna 0 y 1) ---
        gbc.gridx = col++;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(crearEtiqueta("Fecha desde:"), gbc); // Cambiamos el label a 'Fecha desde'

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        dateChooserFecha = new JDateChooser();
        dateChooserFecha.setFont(FONT_FIELD);
        dateChooserFecha.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        dateChooserFecha.setDateFormatString("dd/MM/yyyy");
        panel.add(dateChooserFecha, gbc);

        // --- 4. Botón Buscar (Fila 1, Columna 3) ---
        gbc.gridx = 3; // Ubicamos el botón en la columna 3
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.EAST;
        btnBuscar = crearBoton("Buscar", BLUE_PRIMARY);
        btnBuscar.setPreferredSize(new Dimension(150, FIELD_HEIGHT));
        btnBuscar.setActionCommand("BUSCAR");
        panel.add(btnBuscar, gbc);

        return panel;
    }

    private JScrollPane crearTablaResultados() {
        // Columnas EXACTAS según el requerimiento
        String[] columnNames = {"ID Consulta", "Fecha", "Tipo Práctica", "Diagnóstico Principal", "Pronóstico", "Tratamiento"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            // Hacemos que la tabla no sea editable directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados = new JTable(modeloTabla);

        // Estilo y apariencia de la tabla
        tablaResultados.setFont(FONT_FIELD);
        tablaResultados.setRowHeight(25);
        tablaResultados.getTableHeader().setFont(FONT_BOLD);
        tablaResultados.getTableHeader().setBackground(BLUE_PRIMARY.darker());
        tablaResultados.getTableHeader().setForeground(Color.WHITE);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajuste de ancho de columnas para mejor visualización
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(80); // ID (oculto en el controlador)
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(120); // Fecha
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(150); // Tipo Práctica
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(250); // Diagnóstico
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(170); // Pronóstico
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(250); // Tratamiento

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GRAY_BORDER, 1),
                "Resultados de la Historia Clínica",
                TitledBorder.LEFT, TitledBorder.TOP, FONT_BOLD, GRAY_TEXT
        ));

        return scrollPane;
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnVerDetalle = crearBoton("Ver Detalle", new Color(40, 167, 69)); // Verde para la acción
        btnVerDetalle.setPreferredSize(new Dimension(180, 45));
        btnVerDetalle.setActionCommand("VER_DETALLE");

        JButton btnSalir = crearBoton("Salir", new Color(220, 53, 69)); // Rojo para Salir/Cerrar
        btnSalir.setPreferredSize(new Dimension(180, 45));
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnVerDetalle);
        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO ---

    private JLabel crearEtiqueta(String text) {
        JLabel label = new JLabel(text + ":");
        label.setFont(FONT_BOLD);
        label.setForeground(GRAY_TEXT);
        return label;
    }

    private JButton crearBoton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    // --- 3. CONEXIÓN DEL CONTROLADOR (MVC) ---
    public void setControlador(ActionListener controlador) {
        btnBuscar.addActionListener(controlador);
        btnVerDetalle.addActionListener(controlador);
        // Si usas el mismo controlador para la ventana principal:
        // btnSalir.addActionListener(controlador);
    }

    // --- 4. GETTERS PARA EL CONTROLADOR ---

    public String getPropietarioSeleccionado() {
        return (String) comboPropietario.getSelectedItem();
    }

    public String getMascotaSeleccionada() {
        return (String) comboMascota.getSelectedItem();
    }

    public Date getFechaDesde() {
        return dateChooserFecha.getDate();
    }

    public String getFechaDesdeFormateada() {
        Date fecha = dateChooserFecha.getDate();
        if (fecha != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        }
        return null;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public int getFilaSeleccionada() {
        return tablaResultados.getSelectedRow();
    }

    // Asumimos que el ID de la consulta está en la columna 0
    public Object getIdConsultaSeleccionada() {
        int fila = getFilaSeleccionada();
        if (fila != -1) {
            return modeloTabla.getValueAt(fila, 0);
        }
        return null;
    }

    // --- MAIN DE PRUEBA (Para ver el diseño) ---

    public static void main(String[] args) {
        // 1. Aseguramos el Look and Feel (necesario para JCalendar)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // 2. Crear el Frame Principal (Contenedor de toda la aplicación)
            JFrame framePrincipal = new JFrame("PRUEBA - Escritorio de Historias Clínicas");
            framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            framePrincipal.setSize(1100, 800);

            // 3. Crear el JDesktopPane (El escritorio)
            JDesktopPane desktopPane = new JDesktopPane();
            framePrincipal.setContentPane(desktopPane);

            // 4. Crear la Ventana Interna
            VentanaHistoriaClinica ventana = new VentanaHistoriaClinica(); // <-- ¡Debe crearse sin errores aquí!

            // ... (código de simulación de datos) ...

            // 5. AGREGAR LA VENTANA (Línea donde te da error)
            desktopPane.add(ventana);

            // ... (código de posicionamiento) ...

            ventana.setVisible(true);
            framePrincipal.setVisible(true);


        });
    }
}