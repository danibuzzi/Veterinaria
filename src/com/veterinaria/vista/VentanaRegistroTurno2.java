package com.veterinaria.vista;

import com.toedter.calendar.JDateChooser;
import com.veterinaria.modelo.GestorTurno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.border.TitledBorder;

// 🛑 CAMBIO CLAVE: Nombre de la clase cambiado a VentanaRegistroTurno2
public class VentanaRegistroTurno2 extends JFrame {
    private final GestorTurno gestorTurno;
    private JComboBox<String> comboTipoConsulta;
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JDateChooser dateChooserFechaTurno;
    private JTextField txtHora;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroTurno2(GestorTurno gestorTurno) {
        super("Reserva de Turnos");
        this.gestorTurno = gestorTurno;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Panel principal con espacio alrededor
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 2. Contenedor CENTRAL: Usaremos un panel para apilar la fecha y el formulario (BorderLayout)
        JPanel centerContainer = new JPanel(new BorderLayout(0, 15)); // Espacio vertical
        centerContainer.setBackground(Color.WHITE);

        // El calendario va en el NORTE del centerContainer
        centerContainer.add(crearPanelCalendario(), BorderLayout.NORTH);

        // El formulario va en el CENTRO. Lo metemos en un FlowLayout para centrarlo horizontalmente.
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formWrapper.setBackground(Color.WHITE);
        formWrapper.add(crearPanelFormulario());

        centerContainer.add(formWrapper, BorderLayout.CENTER);

        // 3. Añadir el contenedor central y los botones al mainPanel
        mainPanel.add(centerContainer, BorderLayout.CENTER);
        mainPanel.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(mainPanel);

        pack();
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE INTERFAZ ---

    private JPanel crearPanelCalendario() {
        /*dateChooserFechaTurno = new JDateChooser(new Date());
        dateChooserFechaTurno.setDateFormatString("yyyy-MM-dd");
        dateChooserFechaTurno.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooserFechaTurno.setPreferredSize(new Dimension(300, 35));*/

        dateChooserFechaTurno = new JDateChooser(new Date());
        dateChooserFechaTurno.setDateFormatString("dd/MM/yyyy"); // ✅ ¡CORRECCIÓN!
        dateChooserFechaTurno.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooserFechaTurno.setPreferredSize(new Dimension(100, 35));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Seleccionar Fecha",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK
        ));

        JPanel dateWrapper = new JPanel(new BorderLayout());
        dateWrapper.setPreferredSize(new Dimension(350, 75));
        dateWrapper.setBackground(Color.WHITE);
        dateWrapper.add(dateChooserFechaTurno, BorderLayout.NORTH);

        panel.add(dateWrapper);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Detalles del Turno",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK
        ));

        panel.setPreferredSize(new Dimension(450, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Inicialización de ComboBoxes y campo de texto
        comboPropietario = new JComboBox<>();
        comboPropietario.addItem("--- Seleccione Propietario ---");
        comboMascota = new JComboBox<>();
        comboMascota.addItem("--- Seleccione Mascota ---");
        comboTipoConsulta = new JComboBox<>();
        comboTipoConsulta.addItem("--- Seleccione Tipo de Consulta ---");
        txtHora = new JTextField("10:00:00", 10);

        // 4 campos en el formulario
        añadirComponente(panel, new JLabel("Propietario:"), comboPropietario, 0, gbc, labelFont, fieldFont);
        añadirComponente(panel, new JLabel("Mascota:"), comboMascota, 1, gbc, labelFont, fieldFont);
        añadirComponente(panel, new JLabel("Tipo de Consulta:"), comboTipoConsulta, 2, gbc, labelFont, fieldFont);
        añadirComponente(panel, new JLabel("Hora (HH:MM:SS):"), txtHora, 3, gbc, labelFont, fieldFont);

        // Relleno vertical
        gbc.gridx = 0; gbc.gridy = 4; gbc.weighty = 1.0;
        panel.add(new JLabel(""), gbc);

        return panel;
    }

    private void añadirComponente(JPanel panel, JLabel label, JComponent component, int y, GridBagConstraints gbc, Font labelFont, Font fieldFont) {
        gbc.gridx = 0; gbc.gridy = y; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        label.setFont(labelFont);
        panel.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        component.setFont(fieldFont);

        component.setPreferredSize(new Dimension(200, 35));
        panel.add(component, gbc);
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnSalir = crearBotonGeneral("Salir", new Color(220, 53, 69));
        btnSalir.addActionListener(e -> dispose());

        btnGuardar = crearBotonPrincipal("Guardar Turno");
        btnGuardar.setActionCommand("GUARDAR_TURNO");

        buttonPanel.add(btnGuardar);

        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    public JButton getBtnGuardar() {
        return btnGuardar; // Agregado para que el Controlador pueda manipularlo
    }
    public JDateChooser getDateChooserFechaTurno() {
        return dateChooserFechaTurno;
    }
    private JButton crearBotonPrincipal(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
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

    // --- MÉTODOS DE DATOS (Getters para el Controlador) ---
    // (Lógica de listeners simplificada para evitar el NullPointerException del Controlador)

    public void setControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
    }

    public void setListenerCargaMascotas(ActionListener listener) {
        comboPropietario.addActionListener(listener);
        comboPropietario.setActionCommand("PROPIETARIO_SELECCIONADO");
    }

    public void cargarItems(JComboBox<String> combo, List<String> items) {
        combo.removeAllItems();
        for (String item : items) {
            combo.addItem(item);
        }
    }

    public String getNombreTipoConsultaSeleccionado() { return (String) comboTipoConsulta.getSelectedItem(); }
    public String getNombrePropietarioSeleccionado() { return (String) comboPropietario.getSelectedItem(); }
    public String getNombreMascotaSeleccionada() { return (String) comboMascota.getSelectedItem(); }

    public String getFechaSeleccionadaSQL() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        return (dateChooserFechaTurno.getDate() != null) ? formatoFecha.format(dateChooserFechaTurno.getDate()) : null;
    }

    public String getHoraSeleccionada() { return txtHora.getText(); }

    public JComboBox<String> getComboMascota() { return comboMascota; }
    public JComboBox<String> getComboTipoConsulta() { return comboTipoConsulta; }
    public JComboBox<String> getComboPropietario() { return comboPropietario; }

    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Resultado", tipoMensaje);
    }
}