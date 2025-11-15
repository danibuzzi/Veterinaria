package com.veterinaria.vista;



import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class VentanaHistoriaClinica3 extends JInternalFrame {

    // --- CONSTANTES DE ESTILO (Asegurando el azul primario de VentanaRegistroConsulta) ---
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235); // Fondo de botones
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final int FIELD_HEIGHT = 35;

    // --- 1. DECLARACIÓN DE COMPONENTES ---
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JDateChooser dateChooserFecha;
    private JButton btnBuscar;
    private JButton btnVerDetalle;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnSalir;

    // Columnas de la tabla (Incluye ID que será ocultado)
    private static final String[] COLUMNAS_TABLA = {"ID Consulta", "Fecha", "Tipo Práctica", "Diagnóstico Principal", "Pronóstico", "Tratamiento"};


    // --- 2. CONSTRUCTOR ---
    public VentanaHistoriaClinica3() {
        super("Historia Clínica de Mascotas", true, true, true, true);
        setSize(1050, 700);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);

        initComponents();
        // LLAMADA CLAVE PARA OCULTAR EL ID (Columna 0)
        ocultarColumnaID();
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

        // Estilo TitledBorder consistente
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

        // --- 1. Propietario ---
        gbc.gridx = col++;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(crearEtiqueta("Propietario"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        comboPropietario = new JComboBox<>();
        comboPropietario.setFont(FONT_FIELD);
        comboPropietario.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        panel.add(comboPropietario, gbc);

        // --- 2. Mascota ---
        gbc.gridx = col++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(crearEtiqueta("Mascota"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        comboMascota = new JComboBox<>();
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
        panel.add(crearEtiqueta("Fecha desde"), gbc);

        gbc.gridx = col++;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        dateChooserFecha = new JDateChooser();
        dateChooserFecha.setFont(FONT_FIELD);
        dateChooserFecha.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        dateChooserFecha.setDateFormatString("dd/MM/yyyy");
        panel.add(dateChooserFecha, gbc);

        // --- 4. Botón Buscar (Fila 1, Columna 3) ---
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.EAST;
        // Fondo AZUL, Texto BLANCO
        btnBuscar = crearBoton("Buscar", BLUE_PRIMARY);
        btnBuscar.setPreferredSize(new Dimension(150, FIELD_HEIGHT));
        btnBuscar.setActionCommand("BUSCAR");
        panel.add(btnBuscar, gbc);

        return panel;
    }

    private JScrollPane crearTablaResultados() {
        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados = new JTable(modeloTabla);

        // Estilo y apariencia de la tabla
        tablaResultados.setFont(FONT_FIELD);
        tablaResultados.setRowHeight(25);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 🛑 ESTILO DE GRILLA PARA COINCIDIR CON "CONSULTA DE TURNOS":
        // 1. Encabezado: Fondo AZUL_PRIMARY (sin oscurecer) y texto BLANCO
        tablaResultados.getTableHeader().setFont(FONT_BOLD);
        tablaResultados.getTableHeader().setBackground(BLUE_PRIMARY);
        tablaResultados.getTableHeader().setForeground(Color.WHITE);

        // 2. Texto de las celdas: NEGRO
        tablaResultados.setForeground(Color.BLACK);

        // Ajuste de ancho de columnas (se mantiene igual)
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

    // MÉTODO CLAVE: OCULTAR LA COLUMNA ID (Técnica robusta)
    private void ocultarColumnaID() {
        if (tablaResultados.getColumnCount() > 0) {
            TableColumnModel tcm = tablaResultados.getColumnModel();

            // Oculta la primera columna (ID Consulta)
            tcm.getColumn(0).setMinWidth(0);
            tcm.getColumn(0).setMaxWidth(0);
            tcm.getColumn(0).setPreferredWidth(0);
            tcm.getColumn(0).setResizable(false);
        }
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        // 🛑 Botón Ver Detalle: Fondo AZUL, Texto BLANCO
        btnVerDetalle = crearBoton("Ver Detalle", BLUE_PRIMARY);
        btnVerDetalle.setPreferredSize(new Dimension(180, 45));
        btnVerDetalle.setActionCommand("VER_DETALLE");

        // 🛑 Botón Salir: Fondo AZUL, Texto BLANCO
        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
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
        // CORRECCIÓN: COLOR DE TEXTO BLANCO (Requerido)
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        // Ajustamos el borde para que se vea limpio
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    // --- 3. CONEXIÓN DEL CONTROLADOR (MVC) ---
    public void setControlador(ActionListener controlador) {
        btnBuscar.addActionListener(controlador);
        btnVerDetalle.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
    }

    // --- 4. GETTERS PARA EL CONTROLADOR (Se mantienen como estaban) ---

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

    // MÉTODO CLAVE: Obtiene el ID seleccionado (aunque esté oculto)
    public Object getIdConsultaSeleccionada() {
        int fila = getFilaSeleccionada();
        if (fila != -1) {
            // El ID siempre está en la columna 0 del modelo
            return modeloTabla.getValueAt(fila, 0);
        }
        return null;
    }

    // Métodos de Carga para Combos (para el Controlador)
    public JComboBox<String> getComboPropietario() { return comboPropietario; }
    public JComboBox<String> getComboMascota() { return comboMascota; }

    public void mostrarResultados(List<Object[]> datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
        // Re-ocultar la columna ID después de cargar nuevos datos
        ocultarColumnaID();
    }


    // --- MAIN DE PRUEBA (Sin datos simulados) ---

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

            VentanaHistoriaClinica3 ventana = new VentanaHistoriaClinica3();

            desktopPane.add(ventana);
            ventana.setVisible(true);
            framePrincipal.setVisible(true);
        });
    }
}
