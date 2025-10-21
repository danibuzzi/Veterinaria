package com.veterinaria.vista;

import com.toedter.calendar.JDateChooser;
import com.veterinaria.controlador.ControladorHistoriaClinica; // Importar el controlador
import com.veterinaria.modelo.Mascota; // Importar el modelo Mascota
import com.veterinaria.modelo.Propietario; // Importar el modelo Propietario

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class VentanaHistoriaClinica2 extends JInternalFrame {

    // --- CONSTANTES DE ESTILO ---
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final int FIELD_HEIGHT = 35;

    // --- 1. DECLARACIÓN DE COMPONENTES ---
    // Componentes de la vista, usando los tipos de modelo
    private JComboBox<Propietario> comboPropietario;
    private JComboBox<Mascota> comboMascota;
    private JDateChooser dateChooserFecha;
    private JButton btnBuscar;
    private JButton btnVerDetalle;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnSalir;

    // Columnas de la tabla: "ID Consulta" es la columna 0, siempre oculta.
    private static final String[] COLUMNAS_TABLA = {"","Fecha", "Tipo Práctica", "Diagnóstico", "Pronóstico", "Tratamiento"};
    private final JDesktopPane escritorio;

    // --- 2. CONSTRUCTOR ---
    public VentanaHistoriaClinica2(JDesktopPane escritorio) {
        super("Historia Clínica de Mascotas", true, true, true, true);
        this.escritorio = escritorio;
        setSize(1050, 700);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        initComponents();
        // **IMPORTANTE**: No llamar a ocultarColumnaID aquí. Se llama en mostrarResultados.
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

    // --- MÉTODOS DE CONSTRUCCIÓN DE LA INTERFAZ (Se mantienen sin cambios) ---

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

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

        // Estilo de encabezado
        JTableHeader header = tablaResultados.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.BLACK);
        header.setOpaque(true);

        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // Asignación de anchos de columna
        // La columna 0 (ID) se ocultará dinámicamente.
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(120); // Fecha
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(150); // Tipo Práctica
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(250); // Diagnóstico Principal
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(170); // Pronóstico
        // La columna 5 (Tratamiento) toma el resto gracias a AUTO_RESIZE_LAST_COLUMN

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GRAY_BORDER, 1),
                "Resultados de la Historia Clínica",
                TitledBorder.LEFT, TitledBorder.TOP, FONT_BOLD, GRAY_TEXT
        ));

        return scrollPane;
    }

    /**
     * Oculta la columna del ID (columna 0) para que no sea visible al usuario.
     * **CORREGIDO**: Se asegura de que la tabla y sus columnas existan.
     */
    private void ocultarColumnaID() {
        if (tablaResultados != null && tablaResultados.getColumnCount() > 0) {
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

        btnVerDetalle = crearBoton("Ver Detalle", BLUE_PRIMARY);
        btnVerDetalle.setPreferredSize(new Dimension(180, 45));
        btnVerDetalle.setActionCommand("VER_DETALLE");

        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
        btnSalir.setPreferredSize(new Dimension(180, 45));
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnVerDetalle);
        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO (Se mantienen sin cambios) ---

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
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    // --- 3. CONEXIÓN DEL CONTROLADOR (MVC) Y GETTERS NECESARIOS ---

    /**
     * Establece el controlador como ActionListener para los botones.
     */
    public void setControlador(ActionListener controlador) {
        btnBuscar.addActionListener(controlador);
        btnVerDetalle.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
    }

    // Getters para los componentes que interactúan con el controlador

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnVerDetalle() {
        return btnVerDetalle;
    }

    public JComboBox<Propietario> getComboPropietario() {
        return comboPropietario;
    }

    public JComboBox<Mascota> getComboMascota() {
        return comboMascota;
    }

    public Date getFechaDesde() {
        return dateChooserFecha.getDate();
    }

    /**
     * Recupera el ID de la consulta de la fila seleccionada, aunque la columna esté oculta.
     */
    public Object getIdConsultaSeleccionada() {
        int fila = getFilaSeleccionada();
        if (fila != -1) {
            // El ID siempre está en la columna 0 del modelo
            return modeloTabla.getValueAt(fila, 0);
        }
        return null;
    }

    private int getFilaSeleccionada() {
        return tablaResultados.getSelectedRow();
    }

    // 🛑 Método de utilidad para mostrar mensajes
    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, getTitle(), tipoMensaje);
    }

    /**
     * Muestra los resultados en la tabla y llama a ocultarColumnaID().
     */
    public void mostrarResultados(List<Object[]> datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
        // CRUCIAL: Ocultar el ID después de cargar nuevos datos.
        ocultarColumnaID();
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }


    // --- MAIN DE PRUEBA (Se mantiene sin cambios) ---

   /* public static void main(String[] args) {
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

            VentanaHistoriaClinica2 ventana = new VentanaHistoriaClinica2(escritorio);

            desktopPane.add(ventana);
            ventana.setVisible(true);
            framePrincipal.setVisible(true);
        });
    }*/
}