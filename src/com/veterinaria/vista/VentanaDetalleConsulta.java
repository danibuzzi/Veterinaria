package com.veterinaria.vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VentanaDetalleConsulta extends JInternalFrame {

    // --- CONSTANTES DE ESTILO ---
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final int FIELD_HEIGHT = 35; // Altura para campos cortos
    private static final int PADDING = 15;
    private static final int AREA_HEIGHT = 100; // Altura para cada JTextArea

    // --- 1. DECLARACIÓN DE COMPONENTES ---
    private JTextField txtValorPropietario;
    private JTextField txtValorMascota;
    private JTextField txtValorTipoPractica;
    private JTextField txtValorFecha;

    // Áreas de texto (campos largos)
    private JTextArea txtAreaResultadoEstudio;
    private JTextArea txtAreaDiagnostico;
    private JTextArea txtAreaPronostico;
    private JTextArea txtAreaTratamiento;

    private JButton btnCerrar;

    // --- CONSTRUCTOR ---
    public VentanaDetalleConsulta() {
        super("Detalle de Consulta Histórica", true, true, true, true);
        setSize(850, 750); // Tamaño ajustado para ver todos los campos
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // 1. Panel Norte (Datos Clave)
        JPanel topPanel = crearPanelDatosClave();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 2. Panel Central (Los campos JTextArea largos - apilados)
        JPanel centerPanel = crearPanelDetalleLargo();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 3. Panel Sur (Único Botón: Cerrar)
        JPanel buttonPanel = crearPanelBotones();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel crearPanelDatosClave() {
        // Usa GridBagLayout para los campos cortos (Propietario, Mascota, etc.)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BLUE_PRIMARY, 1),
                "Información General de la Consulta",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_LABEL.deriveFont(Font.BOLD, 15), BLUE_PRIMARY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Fila 1 (Propietario y Mascota) ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        panel.add(crearLabelEtiqueta("Propietario"), gbc);
        txtValorPropietario = crearCampoValor();
        gbc.gridx = 1; gbc.weightx = 0.4;
        panel.add(txtValorPropietario, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.1;
        panel.add(crearLabelEtiqueta("Mascota"), gbc);
        txtValorMascota = crearCampoValor();
        gbc.gridx = 3; gbc.weightx = 0.4;
        panel.add(txtValorMascota, gbc);

        // --- Fila 2 (Tipo Práctica y Fecha) ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        panel.add(crearLabelEtiqueta("Tipo Práctica"), gbc);
        txtValorTipoPractica = crearCampoValor();
        gbc.gridx = 1; gbc.weightx = 0.4;
        panel.add(txtValorTipoPractica, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0.1;
        panel.add(crearLabelEtiqueta("Fecha"), gbc);
        txtValorFecha = crearCampoValor();
        gbc.gridx = 3; gbc.weightx = 0.4;
        panel.add(txtValorFecha, gbc);

        return panel;
    }

    private JPanel crearPanelDetalleLargo() {
        // Usa BoxLayout verticalmente para apilar las 4 áreas de texto
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // 1. Resultado de Estudios
        txtAreaResultadoEstudio = crearAreaTexto(AREA_HEIGHT);
        panel.add(crearPanelAreaTexto("Resultado de Estudios", txtAreaResultadoEstudio));

        // 2. Diagnóstico
        txtAreaDiagnostico = crearAreaTexto(AREA_HEIGHT);
        panel.add(crearPanelAreaTexto("Diagnóstico Principal", txtAreaDiagnostico));

        // 3. Pronóstico
        txtAreaPronostico = crearAreaTexto(AREA_HEIGHT);
        panel.add(crearPanelAreaTexto("Pronóstico", txtAreaPronostico));

        // 4. Tratamiento
        txtAreaTratamiento = crearAreaTexto(AREA_HEIGHT);
        panel.add(crearPanelAreaTexto("Tratamiento", txtAreaTratamiento));

        return panel;
    }

    /**
     * Crea un mini-panel que contiene el Label de título y el JScrollPane con el JTextArea.
     */
    private JPanel crearPanelAreaTexto(String titulo, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout(0, 5)); // 5px de espacio vertical
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espacio entre áreas

        // Etiqueta del título
        JLabel label = crearLabelEtiqueta(titulo);
        label.setFont(FONT_LABEL.deriveFont(Font.BOLD, 15));
        label.setForeground(BLUE_PRIMARY);

        panel.add(label, BorderLayout.NORTH);
        panel.add(crearScrollPane(area), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        // Un solo botón de cerrar/salir, alineado a la derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, PADDING, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCerrar.setBackground(BLUE_PRIMARY);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setOpaque(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setPreferredSize(new Dimension(150, 40));
        btnCerrar.addActionListener(e -> dispose());

        buttonPanel.add(btnCerrar);
        return buttonPanel;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO ---

    private JLabel crearLabelEtiqueta(String text) {
        JLabel label = new JLabel(text + ":");
        label.setFont(FONT_LABEL);
        label.setForeground(GRAY_TEXT);
        return label;
    }

    private JTextField crearCampoValor() {
        JTextField field = new JTextField();
        field.setFont(FONT_FIELD);
        field.setEditable(false); // 🛑 Deshabilitado (Solo Lectura)
        field.setBackground(new Color(245, 245, 245));
        field.setBorder(BorderFactory.createLineBorder(GRAY_BORDER));
        field.setPreferredSize(new Dimension(200, FIELD_HEIGHT));
        return field;
    }

    private JTextArea crearAreaTexto(int height) {
        JTextArea area = new JTextArea();
        area.setFont(FONT_FIELD);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false); // 🛑 Deshabilitado (Solo Lectura)
        area.setBackground(new Color(245, 245, 245));
        area.setMinimumSize(new Dimension(100, height));
        area.setPreferredSize(new Dimension(100, height));
        return area;
    }

    private JScrollPane crearScrollPane(JTextArea area) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createLineBorder(GRAY_BORDER));
        return scroll;
    }

    // ---------------------------------------------
    // MÉTODO CLAVE: CARGAR DATOS
    // ---------------------------------------------

    /**
     * Carga todos los datos de la consulta para su visualización.
     */
    public void cargarDetalle(String nombrePropietario, String nombreMascota, String tipoPractica,
                              Date fechaConsulta, String resultadoEstudio,
                              String diagnostico, String pronostico, String tratamiento) {

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        txtValorPropietario.setText(nombrePropietario);
        txtValorMascota.setText(nombreMascota);
        txtValorTipoPractica.setText(tipoPractica);

        txtValorFecha.setText(sdfDate.format(fechaConsulta));

        // Carga de JTextArea
        txtAreaResultadoEstudio.setText(resultadoEstudio);
        txtAreaDiagnostico.setText(diagnostico);
        txtAreaPronostico.setText(pronostico);
        txtAreaTratamiento.setText(tratamiento);

        // Vuelvo al inicio de los JTextArea
        txtAreaResultadoEstudio.setCaretPosition(0);
        txtAreaDiagnostico.setCaretPosition(0);
        txtAreaPronostico.setCaretPosition(0);
        txtAreaTratamiento.setCaretPosition(0);

        revalidate();
        repaint();
    }

    public void cargarDatosConsulta(Object[] detalle) {
        if (detalle == null || detalle.length < 9) {
            this.setTitle("Detalle de Consulta - No Encontrada o Datos Inválidos");
            return;
        }

        // Extracción de datos (el casting es necesario y CRÍTICO)
        int idConsulta = (int) detalle[0];
        Date fechaConsulta = (Date) detalle[1];
       // Time hora = (Time) detalle[2];
        String nombreMascota = (String) detalle[2];
        String nombrePropietario = (String) detalle[3];
        String descripcionPractica = (String) detalle[4];
        String resultadoEstudio = (String) detalle[5];
        String diagnostico = (String) detalle[6];
        String pronostico = (String) detalle[7];
        String tratamiento = (String) detalle[8];


        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        String fecha =sdfDate.format(fechaConsulta);
        // Carga de campos cortos
        txtValorPropietario.setText(nombrePropietario);
        txtValorMascota.setText(nombreMascota);
        txtValorTipoPractica.setText(descripcionPractica);

        // Formato de fecha y hora
        //String fechaHora = sdfDate.format(fechaConsulta) + " " + hora.toString().substring(0, 5);
        txtValorFecha.setText(fecha);

        // Carga de JTextArea (Campos largos)
        txtAreaResultadoEstudio.setText(resultadoEstudio);
        txtAreaDiagnostico.setText(diagnostico);
        txtAreaPronostico.setText(pronostico);
        txtAreaTratamiento.setText(tratamiento);

        // Vuelvo al inicio de los JTextArea
        txtAreaResultadoEstudio.setCaretPosition(0);
        txtAreaDiagnostico.setCaretPosition(0);
        txtAreaPronostico.setCaretPosition(0);
        txtAreaTratamiento.setCaretPosition(0);

        // Ajustar el título
        this.setTitle("Detalle de Consulta ID: " + idConsulta);
        revalidate();
        repaint();
    }


    // --- MAIN DE PRUEBA (Sin datos simulados) ---

    /*public static void main(String[] args) {
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

            VentanaDetalleConsulta ventana = new VentanaDetalleConsulta();

            desktopPane.add(ventana);
            ventana.setVisible(true);
            framePrincipal.setVisible(true);
        });
    }*/
}