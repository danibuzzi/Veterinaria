package com.veterinaria.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaModificacionPropietario extends JFrame {
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtNombres;
    private JTextField txtFechaNacimiento;
    private JButton btnSelectorFecha;
    private JTextField txtDireccion;
    private JTextField txtPais;
    private JTextField txtCiudad;
    private JButton btnGuardar;
    private JButton btnSalir;

    // Constructor que acepta el ID del propietario a modificar (buena práctica)
    public VentanaModificacionPropietario(String idPropietario) {
        initComponents();
        cargarDatos(idPropietario);
    }

    // Constructor sin argumentos para Main (mantenido para pruebas)
    public VentanaModificacionPropietario() {
        this("0"); // Llama al constructor principal con un ID de prueba
    }

    private void initComponents() {
        setTitle("Modificación de Propietario");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal que contendrá el formulario (GridBagLayout)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel del Formulario (Centro del BorderLayout)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        int row = 0; // Contador de filas

        // Campos del Formulario (Ajuste de GBC para que el formulario quede bien centrado)

        // --- DNI ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDni = new JLabel("DNI");
        lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblDni, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.WEST;
        txtDni = new JTextField(15);
        txtDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDni.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(txtDni, gbc);
        row++;

        // --- Apellidos ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblApellidos = new JLabel("Apellidos");
        lblApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblApellidos, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtApellidos = new JTextField(30);
        txtApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellidos.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(txtApellidos, gbc);
        row++;

        // --- Nombres ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblNombres = new JLabel("Nombres");
        lblNombres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblNombres, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtNombres = new JTextField(30);
        txtNombres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombres.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(txtNombres, gbc);
        row++;

        // --- Fecha de Nacimiento con Selector ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblFechaNacimiento = new JLabel("Fecha Nac.");
        lblFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblFechaNacimiento, gbc);

        // Panel para agrupar el JTextField y el botón
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fechaPanel.setBackground(Color.WHITE);

        txtFechaNacimiento = new JTextField(15);
        txtFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFechaNacimiento.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        txtFechaNacimiento.setPreferredSize(new Dimension(150, 24));

        btnSelectorFecha = new JButton("🗓️");
        btnSelectorFecha.setPreferredSize(new Dimension(30, 24));
        btnSelectorFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSelectorFecha.setMargin(new Insets(0, 0, 0, 0));
        btnSelectorFecha.setFocusPainted(false);
        btnSelectorFecha.addActionListener(e -> mostrarSelectorFecha());

        fechaPanel.add(txtFechaNacimiento);
        fechaPanel.add(btnSelectorFecha);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(fechaPanel, gbc);
        row++;

        // --- Dirección ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDireccion = new JLabel("Dirección");
        lblDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblDireccion, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtDireccion = new JTextField(30);
        txtDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDireccion.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(txtDireccion, gbc);
        row++;

        // --- País ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPais = new JLabel("País");
        lblPais.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPais, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        txtPais = new JTextField(20);
        txtPais.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPais.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(txtPais, gbc);
        row++;

        // --- Ciudad ---
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblCiudad = new JLabel("Ciudad");
        lblCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblCiudad, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtCiudad = new JTextField(22);
        txtCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCiudad.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(txtCiudad, gbc);
        row++;

        // Añadir el panel de formulario al centro del mainPanel
        mainPanel.add(formPanel, BorderLayout.CENTER);


        // **********************************************
        // --- Panel de botones (Parte Inferior - SUR) ---
        // *** FlowLayout.CENTER para CENTRAR los botones ***
        // **********************************************
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15)); // 30px entre botones, 15px de padding vertical
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(150, 40));
        btnGuardar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnGuardar.setBackground(new Color(0, 120, 215));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(0, 100, 195));
            }
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(new Color(0, 120, 215));
            }
        });
        btnGuardar.addActionListener(e -> guardar());

        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(150, 40));
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSalir.setBackground(new Color(0, 120, 215));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSalir.setBackground(new Color(0, 100, 195));
            }
            public void mouseExited(MouseEvent e) {
                btnSalir.setBackground(new Color(0, 120, 215));
            }
        });
        btnSalir.addActionListener(e -> salir());

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void cargarDatos(String idPropietario) {
        // Simulación: Carga de datos del propietario con el ID
        txtDni.setText("2569708");
        txtApellidos.setText("Cerruti");
        txtNombres.setText("Lucia Malena");
        txtFechaNacimiento.setText("15/09/1980");
        txtDireccion.setText("Los Tilos 500");
        txtPais.setText("Argentina");
        txtCiudad.setText("Mar del Palta");
    }

    private void mostrarSelectorFecha() {
        JOptionPane.showMessageDialog(this,
                "Aquí se abriría el componente JCalendar/JDateChooser para seleccionar la fecha.",
                "Seleccionar Fecha",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void guardar() {
        if (validarCampos()) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea guardar los cambios del propietario?",
                    "Confirmar modificación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Lógica de Modificación (Llamada al Controller)
                JOptionPane.showMessageDialog(
                        this,
                        "Propietario modificado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            }
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir sin guardar los cambios?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private boolean validarCampos() {
        if (txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DNI es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los nombres son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        if (txtFechaNacimiento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Fecha de Nacimiento es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaNacimiento.requestFocus();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaModificacionPropietario("101").setVisible(true); // Se prueba con un ID
        });
    }
}