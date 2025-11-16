package com.veterinaria.vista;

//import com.veterinaria.controlador.ControladorRegistroConsulta;
import com.veterinaria.controlador.ControladorRegistroConsulta;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.TipoPractica;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VentanaRegistroConsulta extends JInternalFrame {

    // --- CONSTANTES DE ESTILO ---
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final int FIELD_HEIGHT = 35;
    private static final int CONTROL_WIDTH = 500;

    // --- 1. DECLARACIÓN DE COMPONENTES ---
    private JLabel lblFechaActual;
    private JComboBox<Propietario> comboPropietario;
    private JComboBox<Mascota> comboMascota;
    private JComboBox<TipoPractica> comboTipoPractica;
    private JTextArea txtResultadoEstudios;
    private JTextArea txtDiagnostico;
    private JTextArea txtPronostico;
    private JTextArea txtTratamiento;
    private JButton btnGuardar;
    private JButton btnCancelar;

    // --- 2. CONSTRUCTOR ---
    public VentanaRegistroConsulta(JDesktopPane escritorioContenedor) {
        super("Registro de Nueva Consulta", true, true, true, true);

        // El tamaño ajustado para el diseño compacto y las alturas definidas
        setSize(750, 720);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        // Margen reducido para evitar espacio en blanco excesivo en los bordes
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = createFormPanel();

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- 3. CONSTRUCCIÓN DEL PANEL DE FORMULARIO (ESTRUCTURA DE DOS PANELES) ---
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        // Inicialización de componentes
        comboPropietario = new JComboBox<>(); styleComboBox(comboPropietario);
        comboMascota = new JComboBox<>(); styleComboBox(comboMascota); comboMascota.setEnabled(false);
        comboTipoPractica = new JComboBox<>(); styleComboBox(comboTipoPractica);
        txtResultadoEstudios = crearAreaTexto();
        txtDiagnostico = crearAreaTexto();
        txtPronostico = crearAreaTexto();
        txtTratamiento = crearAreaTexto();

        // ----------------------------------------------------
        // A) DATOS DE LA CITA (Simula "Seleccionar Fecha" y "Detalles del Turno")
        // ----------------------------------------------------
        JPanel panelCita = new JPanel();
        panelCita.setLayout(new BoxLayout(panelCita, BoxLayout.Y_AXIS));
        panelCita.setBackground(Color.WHITE);

        // Panel de Fecha (Simula el grupo "Seleccionar Fecha")
        JPanel panelFecha = createFechaPanel();
        panelCita.add(panelFecha);

        // Panel de Propietario/Mascota (Simula el grupo "Detalles del Turno")
        JPanel panelDetallesTurno = createDetallesTurnoPanel();
        panelCita.add(Box.createVerticalStrut(10)); // Espacio entre grupos
        panelCita.add(panelDetallesTurno);

        formPanel.add(panelCita);
        formPanel.add(Box.createVerticalStrut(15)); // Espacio entre grupos principales

        // ----------------------------------------------------
        // B) DETALLES CLÍNICOS (Simula los campos específicos de la Consulta)
        // ----------------------------------------------------
        JPanel panelDetallesConsulta = createDetallesClinicosPanel();
        formPanel.add(panelDetallesConsulta);

        return formPanel;
    }

    private JPanel createFechaPanel() {
        JPanel panelFecha = new JPanel(new GridBagLayout());
        panelFecha.setBackground(Color.WHITE);

        // Borde estilo Reserva de Turnos: Borde simple como "Seleccionar Fecha"
        panelFecha.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GRAY_BORDER, 1),
                "Fecha de la Consulta",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_BOLD.deriveFont(Font.ITALIC, 15), BLUE_PRIMARY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE; // No rellenamos completamente para centrar mejor
        gbc.insets = new Insets(10, 20, 10, 20); // Márgenes internos

        lblFechaActual = new JLabel();
        lblFechaActual.setFont(FONT_FIELD);
        lblFechaActual.setText(obtenerFechaActualFormateada());

        // Columna 0: Etiqueta "Fecha"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel("Fecha:");
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(GRAY_TEXT);
        panelFecha.add(lbl, gbc);

        // Columna 1: Valor de la Fecha del Sistema
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panelFecha.add(lblFechaActual, gbc);

        // Ajustamos el tamaño para que se vea como un campo
        lblFechaActual.setPreferredSize(new Dimension(CONTROL_WIDTH, FIELD_HEIGHT));
        lblFechaActual.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        lblFechaActual.setHorizontalAlignment(SwingConstants.CENTER);


        return panelFecha;
    }

    private JPanel createDetallesTurnoPanel() {
        JPanel panelDetallesTurno = new JPanel(new GridBagLayout());
        panelDetallesTurno.setBackground(Color.WHITE);

        //  Borde estilo Reserva de Turnos: Línea y Título en color azul
        panelDetallesTurno.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BLUE_PRIMARY, 1),
                "Detalles de la Cita",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_BOLD.deriveFont(Font.ITALIC, 15), BLUE_PRIMARY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        int fila = 0;

        agregarFila(panelDetallesTurno, gbc, fila++, "Propietario", comboPropietario, 1.0, GridBagConstraints.HORIZONTAL);
        agregarFila(panelDetallesTurno, gbc, fila++, "Mascota", comboMascota, 1.0, GridBagConstraints.HORIZONTAL);
        agregarFila(panelDetallesTurno, gbc, fila++, "Pràctica profesional", comboTipoPractica, 1.0, GridBagConstraints.HORIZONTAL);

        return panelDetallesTurno;
    }

    private JPanel createDetallesClinicosPanel() {
        JPanel panelDetallesConsulta = new JPanel(new GridBagLayout());
        panelDetallesConsulta.setBackground(Color.WHITE);

        // Borde estilo Reserva de Turnos: Línea y Título en color azul
        panelDetallesConsulta.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BLUE_PRIMARY, 1),
                "Detalles Clínicos",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_BOLD.deriveFont(Font.ITALIC, 15), BLUE_PRIMARY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        // Asignamos peso vertical a cada fila de JTextArea para que se expandan por igual (0.25 para 4 filas)
        gbc.weighty = 1.0;
        //double rowWeight = 1.0 / 4.0;

        int fila = 0;



        // Alturas de campos de texto ajustadas a las dimensiones originales
       /* agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Resultado de Estudios", new JScrollPane(txtResultadoEstudios), 120);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Diagnóstico", new JScrollPane(txtDiagnostico), 120);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Pronóstico", new JScrollPane(txtPronostico), 120);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Tratamiento", new JScrollPane(txtTratamiento), 120);
*/
        // Utilizamos el método auxiliar modificado sin altura fija
        /*agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Resultado de Estudios", new JScrollPane(txtResultadoEstudios), 100);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Diagnóstico", new JScrollPane(txtDiagnostico), 100);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Pronóstico", new JScrollPane(txtPronostico), 100);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Tratamiento", new JScrollPane(txtTratamiento), 100);*/
        //agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Resultado de Estudios", new JScrollPane(txtResultadoEstudios), rowWeight);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Resultado de Estudios", new JScrollPane(txtResultadoEstudios), 100, 120);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Diagnóstico", new JScrollPane(txtDiagnostico), 100, 120);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Pronóstico", new JScrollPane(txtPronostico), 100, 120);
        agregarFilaArea(panelDetallesConsulta, gbc, fila++, "Tratamiento", new JScrollPane(txtTratamiento), 100, 120);

        return panelDetallesConsulta;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO Y LAYOUT (SIN CAMBIOS) ---

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_FIELD);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        combo.setPreferredSize(new Dimension(CONTROL_WIDTH, FIELD_HEIGHT));
    }

    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea();
        area.setFont(FONT_FIELD);
        area.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    // Método para agregar JComponents de una sola línea (Combos/Labels)
    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String labelText, JComponent component, double weightX, int fill) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel(labelText + ":");
        label.setFont(FONT_BOLD);
        label.setForeground(GRAY_TEXT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = weightX;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = fill;
        panel.add(component, gbc);
    }

    // Método para agregar JTextAreas (campos multi-línea)
    private void agregarFilaArea(JPanel panel, GridBagConstraints gbc, int fila, String labelText, JScrollPane scrollPane, int height,int rowWeight) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        gbc.weighty = rowWeight;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel(labelText + ":");
        label.setFont(FONT_BOLD);
        label.setForeground(GRAY_TEXT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = rowWeight;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;

        scrollPane.setPreferredSize(new Dimension(CONTROL_WIDTH, height));
        panel.add(scrollPane, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar");
        btnGuardar.setActionCommand("GUARDAR_CONSULTA");

        btnCancelar = crearBoton("Cancelar");
        btnCancelar.setActionCommand("CANCELAR");

        panel.add(btnGuardar);
        panel.add(btnCancelar);
        return panel;
    }

    private JButton crearBoton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, FIELD_HEIGHT));
        button.setFont(FONT_BOLD);
        button.setBackground(BLUE_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(null);
        return button;
    }

    // --- 4. CONEXIÓN DE CONTROLADOR (PATRÓN MVC) ---
    public void setControlador(ControladorRegistroConsulta controlador) {
        btnGuardar.addActionListener(controlador);
        btnCancelar.addActionListener(controlador);

        comboPropietario.addActionListener(controlador);
        comboPropietario.setActionCommand("PROPIETARIO_CAMBIO");
    }

    private String obtenerFechaActualFormateada() {
        // 1. Obtener el instante actual del sistema
        Date fechaActual = new Date();

        // 2. Definir el formato requerido (dd/MM/yyyy)
        // 💡 NOTA: Las 'M' deben ser mayúsculas para el mes.
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        // 3. Formatear y devolver la cadena
        return formato.format(fechaActual);
    }

    // --- 5. GETTERS Y SETTERS PARA EL CONTROLADOR ---

    public void setFechaHoraActual() {
        String fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
        lblFechaActual.setText(fecha);
    }

    public void setOpcionesPropietario(List<Propietario> propietarios) {
        comboPropietario.removeAllItems();
        propietarios.forEach(comboPropietario::addItem);
    }

    public void setOpcionesMascota(List<Mascota> mascotas) {
        comboMascota.removeAllItems();
        mascotas.forEach(comboMascota::addItem);
        comboMascota.setEnabled(true);
    }

    public void setOpcionesTipoPractica(List<TipoPractica> tipos) {
        comboTipoPractica.removeAllItems();
        tipos.forEach(comboTipoPractica::addItem);
    }

    public Propietario getPropietarioSeleccionado() {
        return (Propietario) comboPropietario.getSelectedItem();
    }
    public Mascota getMascotaSeleccionada() { return (Mascota) comboMascota.getSelectedItem(); }
    public TipoPractica getTipoPracticaSeleccionada() { return (TipoPractica) comboTipoPractica.getSelectedItem(); }
    public String getResultadoEstudios() { return txtResultadoEstudios.getText(); }
    public String getDiagnostico() { return txtDiagnostico.getText(); }
    public String getPronostico() { return txtPronostico.getText(); }
    public String getTratamiento() { return txtTratamiento.getText(); }
    public JComboBox<Propietario> getPropietarioCombo() { return comboPropietario; }
    public JComboBox<Mascota> getMascotaCombo() { return comboMascota; }

    public void limpiarCampos() {
        comboPropietario.setSelectedIndex(0);
        comboMascota.removeAllItems();
        comboMascota.setEnabled(false);
        comboTipoPractica.setSelectedIndex(0);
        txtResultadoEstudios.setText("");
        txtDiagnostico.setText("");
        txtPronostico.setText("");
        txtTratamiento.setText("");
        setFechaHoraActual();
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (CLAVE) ---
    public void setControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
        btnCancelar.addActionListener(controlador);
    }

    // Getters para el controlador (obtener OBJETOS seleccionados)
    public JComboBox<Propietario> getComboPropietario() { return comboPropietario; }
    public JComboBox<Mascota> getComboMascota() { return comboMascota; }
    public JComboBox<TipoPractica> getComboTipoPractica() { return comboTipoPractica; }
    //public JComboBox<String> getComboHora() { return comcomboHora; }

    public Date getFechaSeleccionada() { return new Date(lblFechaActual.getText()); }

    // Métodos para cargar datos (reciben DefaultComboBoxModel<T>)
    /*public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {

        comboPropietario.setModel(model);
    }*/

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        comboPropietario.setModel(model);

        if (model.getSize() > 0) {
            comboPropietario.setSelectedIndex(0);
        }
    }

    public void cargarMascotas(DefaultComboBoxModel<Mascota> model) {
        comboMascota.setModel(model);
    }

    public void cargarTiposPractica(DefaultComboBoxModel<TipoPractica> model) {
        comboTipoPractica.setModel(model);
    }


}