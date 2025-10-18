package com.veterinaria.vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;

public class VentanaHistoriaClinica2 extends JInternalFrame {

    // --- CONSTANTES DE ESTILO ---
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Color RED_DELETE = new Color(220, 53, 69);
    private static final Color GREEN_ACTION = new Color(40, 167, 69);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final int FIELD_HEIGHT = 35;
    private static final int COMBO_WIDTH = 250;

    // --- 1. DECLARACIÓN DE COMPONENTES ---
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JDateChooser dateChooserFecha;
    private JButton btnBuscar;
    private JButton btnVerDetalle;
    private JButton btnSalir;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    // --- 2. CONSTRUCTOR ---
    public VentanaHistoriaClinica2() { // Cambiado a VentanaHistoriaClinica2 para evitar conflicto
        super("Historia Clínica de Mascotas", true, true, true, true);

        setSize(1050, 700);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel searchPanel = crearPanelBusqueda();
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        JScrollPane scrollTable = crearTablaResultados();
        mainPanel.add(scrollTable, BorderLayout.CENTER);

        JPanel buttonPanel = crearPanelBotones();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE LA INTERFAZ ---

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        // Estilo profesional: Borde y Título
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BLUE_PRIMARY, 1),
                "Parámetros de Búsqueda de Historia",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_BOLD.deriveFont(Font.ITALIC, 15), BLUE_PRIMARY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE; // Inicialmente sin relleno
        gbc.insets = new Insets(8, 5, 8, 5); // Márgenes más ajustados

        int col = 0;

        // --- 1. Propietario (Fila 0) ---
        gbc.gridx = col++;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST; // Alinea la etiqueta a la derecha de su celda
        panel.add(crearEtiqueta("Propietario"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellena el combo
        gbc.anchor = GridBagConstraints.WEST;
        String[] propietarios = {"Seleccionar propietario", "Perez, Ana Maria", "Monetti, Emilio"};
        comboPropietario = new JComboBox<>(propietarios);
        styleComboBox(comboPropietario);
        comboPropietario.setPreferredSize(new Dimension(COMBO_WIDTH, FIELD_HEIGHT));
        panel.add(comboPropietario, gbc);

        // --- 2. Mascota (Fila 0) ---
        gbc.gridx = col++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST; // Alinea la etiqueta a la derecha de su celda
        panel.add(crearEtiqueta("Mascota"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellena el combo
        gbc.anchor = GridBagConstraints.WEST;
        String[] mascotas = {"Seleccionar mascota", "Toby", "Pepito", "Max"};
        comboMascota = new JComboBox<>(mascotas);
        styleComboBox(comboMascota);
        comboMascota.setPreferredSize(new Dimension(COMBO_WIDTH, FIELD_HEIGHT));
        panel.add(comboMascota, gbc);

        // --- Reinicio de fila para la Fecha y el Botón ---
        col = 0;
        gbc.insets = new Insets(8, 5, 8, 5); // Restaurar insets

        // --- 3. Fecha (Fila 1) ---
        gbc.gridx = col++;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(crearEtiqueta("Fecha (opcional)"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // TEMPORAL: JTextField de reemplazo
        JTextField txtFechaTemporal = new JTextField("DD/MM/AAAA");
        txtFechaTemporal.setFont(FONT_FIELD);
        txtFechaTemporal.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        txtFechaTemporal.setPreferredSize(new Dimension(COMBO_WIDTH, FIELD_HEIGHT));
        panel.add(txtFechaTemporal, gbc);

        // --- 4. Botón Buscar (Fila 1) ---
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.4; // Permite que la celda se estire
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST; // Alinea el botón a la izquierda de la celda

        // Añadimos más margen solo a la izquierda del botón para separarlo del campo Fecha
        gbc.insets = new Insets(8, 25, 8, 10);

        btnBuscar = crearBoton("Buscar", BLUE_PRIMARY);
        btnBuscar.setPreferredSize(new Dimension(150, FIELD_HEIGHT));
        btnBuscar.setActionCommand("BUSCAR");
        panel.add(btnBuscar, gbc);

        return panel;
    }

    private JScrollPane crearTablaResultados() {
        String[] columnNames = {"ID Consulta", "Fecha", "Tipo Práctica", "Diagnóstico Principal", "Pronóstico", "Tratamiento"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados = new JTable(modeloTabla);

        // Estilo de cabecera profesional
        tablaResultados.setFont(FONT_FIELD);
        tablaResultados.setRowHeight(25);
        tablaResultados.getTableHeader().setFont(FONT_BOLD);
        tablaResultados.getTableHeader().setBackground(BLUE_PRIMARY.darker());
        tablaResultados.getTableHeader().setForeground(Color.WHITE);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajuste de ancho de columnas
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(250);
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(170);
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GRAY_BORDER, 1),
                "Resultados de la Historia Clínica",
                TitledBorder.LEFT, TitledBorder.TOP, FONT_BOLD, GRAY_TEXT
        ));

        return scrollPane;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setBackground(Color.WHITE);

        btnVerDetalle = crearBoton("Ver Detalle", GREEN_ACTION);
        btnVerDetalle.setPreferredSize(new Dimension(180, 45));
        btnVerDetalle.setActionCommand("VER_DETALLE");

        btnSalir = crearBoton("Salir", RED_DELETE);
        btnSalir.setPreferredSize(new Dimension(180, 45));
        btnSalir.setActionCommand("SALIR");

        panel.add(btnVerDetalle);
        panel.add(btnSalir);
        return panel;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO Y GETTERS ---

    private JLabel crearEtiqueta(String text) {
        JLabel label = new JLabel(text + ":");
        label.setFont(FONT_BOLD);
        label.setForeground(GRAY_TEXT);
        return label;
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_FIELD);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
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

    public void setControlador(ActionListener controlador) {
        btnBuscar.addActionListener(controlador);
        btnVerDetalle.addActionListener(controlador);
    }

    public String getPropietarioSeleccionado() { return (String) comboPropietario.getSelectedItem(); }
    public String getMascotaSeleccionada() { return (String) comboMascota.getSelectedItem(); }

    public Date getFechaDesde() { return null; }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public int getFilaSeleccionada() { return tablaResultados.getSelectedRow(); }

    public Object getIdConsultaSeleccionada() {
        int fila = getFilaSeleccionada();
        if (fila != -1) {
            return modeloTabla.getValueAt(fila, 0);
        }
        return null;
    }

    // --- MAIN DE PRUEBA (Para ver el diseño) ---
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame framePrincipal = new JFrame("PRUEBA - Escritorio de Historias Clínicas");
            framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            framePrincipal.setSize(1100, 800);

            JDesktopPane desktopPane = new JDesktopPane();
            framePrincipal.setContentPane(desktopPane);

            // ¡Importante!: Usamos la clase correcta con la herencia JInternalFrame
            VentanaHistoriaClinica2 ventana = new VentanaHistoriaClinica2();

            DefaultTableModel model = ventana.getModeloTabla();
            model.addRow(new Object[]{101, "15/05/2024", "Vacunación", "Control Anual", "Favorable", "Dosis Triple (refuerzo anual)."});
            model.addRow(new Object[]{102, "20/07/2024", "Análisis", "Sospecha de Anemia", "Reservado", "Dieta rica en hierro y Vitamina K."});
            model.addRow(new Object[]{103, "25/09/2024", "Control", "Recuperación post-análisis", "Bueno", "Continuar con suplemento B12 oral."});

            desktopPane.add(ventana);

            int x = (desktopPane.getWidth() - ventana.getWidth()) / 2;
            int y = (desktopPane.getHeight() - ventana.getHeight()) / 2;
            ventana.setLocation(x, y);

            ventana.setVisible(true);
            try {
                ventana.setSelected(true); // Opcional: Asegura que la ventana interna esté seleccionada
            } catch (java.beans.PropertyVetoException e) { }

            framePrincipal.setVisible(true);
        });
    }
}