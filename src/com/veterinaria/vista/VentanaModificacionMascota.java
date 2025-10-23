package com.veterinaria.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class VentanaModificacionMascota extends JFrame {
    // Campos de entrada
    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;

    // Componentes del Propietario
    private JLabel lblPropietarioInfo;
    private JLabel lblPropietarioLabel; // Nuevo Label para "Propietario"

    // Botones
    private JButton btnGuardar;
    private JButton btnSalir;

    // Constructor que recibe el ID de la mascota
    public VentanaModificacionMascota(String idMascota) {
        initComponents();
        cargarDatosMascota(idMascota);
    }

    private void initComponents() {
        setTitle("Modificación de Mascota");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Panel principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- Panel del formulario ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // **********************************************
        // ******* AJUSTE PARA ALINEAR PROPIETARIO *******
        // **********************************************

        // Propietario Label (Etiqueta "Propietario")
        gbc.gridx = 0;
        gbc.gridy = 0;
        lblPropietarioLabel = new JLabel("Propietario");
        lblPropietarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPropietarioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPropietarioLabel.setPreferredSize(new Dimension(140, 25)); // Mismo ancho que las otras etiquetas
        formPanel.add(lblPropietarioLabel, gbc);

        // Propietario Info (Apellido y Nombre)
        gbc.gridx = 1;
        lblPropietarioInfo = new JLabel();
        lblPropietarioInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPropietarioInfo.setForeground(Color.BLACK); // Color estándar
        lblPropietarioInfo.setPreferredSize(new Dimension(350, 40)); // Mismo ancho que los JTextField
        formPanel.add(lblPropietarioInfo, gbc);

        // Se añade un espacio para separarlo visualmente de los campos editables
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);

        // **********************************************
        // ******** CAMPOS DE MASCOTA (gridy + 1) *******
        // **********************************************

        // Nombre (Ahora en gridy = 2)
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblNombre = new JLabel("nombre");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNombre.setPreferredSize(new Dimension(140, 25));
        formPanel.add(lblNombre, gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNombre.setPreferredSize(new Dimension(350, 40));
        formPanel.add(txtNombre, gbc);

        // Fecha nacimiento (Ahora en gridy = 3)
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblFechaNacimiento = new JLabel("<html>fecha<br>nacimiento</html>");
        lblFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblFechaNacimiento.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFechaNacimiento.setPreferredSize(new Dimension(140, 40));
        formPanel.add(lblFechaNacimiento, gbc);

        gbc.gridx = 1;
        txtFechaNacimiento = new JTextField(20);
        txtFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtFechaNacimiento.setPreferredSize(new Dimension(350, 40));
        formPanel.add(txtFechaNacimiento, gbc);

        // Especie (Ahora en gridy = 4)
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblEspecie = new JLabel("especie");
        lblEspecie.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEspecie.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEspecie.setPreferredSize(new Dimension(140, 25));
        formPanel.add(lblEspecie, gbc);

        gbc.gridx = 1;
        txtEspecie = new JTextField(35);
        txtEspecie.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtEspecie.setPreferredSize(new Dimension(600, 40));
        formPanel.add(txtEspecie, gbc);

        // Raza (Ahora en gridy = 5)
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblRaza = new JLabel("raza");
        lblRaza.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblRaza.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRaza.setPreferredSize(new Dimension(140, 25));
        formPanel.add(lblRaza, gbc);

        gbc.gridx = 1;
        txtRaza = new JTextField(35);
        txtRaza.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtRaza.setPreferredSize(new Dimension(600, 40));
        formPanel.add(txtRaza, gbc);

        // Sexo (Ahora en gridy = 6)
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel lblSexo = new JLabel("sexo");
        lblSexo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSexo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSexo.setPreferredSize(new Dimension(140, 25));
        formPanel.add(lblSexo, gbc);

        gbc.gridx = 1;
        String[] sexos = {"macho", "hembra"};
        cmbSexo = new JComboBox<>(sexos);
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cmbSexo.setPreferredSize(new Dimension(350, 40));
        cmbSexo.setBackground(Color.WHITE);
        formPanel.add(cmbSexo, gbc);

        // Señas Particulares (Ahora en gridy = 7)
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        JLabel lblSenasParticulares = new JLabel("<html>señas<br>particulares</html>");
        lblSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSenasParticulares.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSenasParticulares.setPreferredSize(new Dimension(140, 40));
        formPanel.add(lblSenasParticulares, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        txtSenasParticulares = new JTextArea(7, 35);
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        txtSenasParticulares.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(600, 150));
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- Panel de botones ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = new JButton("Guardar");
        // Estilos y acciones de Guardar
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGuardar.setPreferredSize(new Dimension(180, 45));
        btnGuardar.setBackground(new Color(0, 120, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(e -> guardarMascota());
        btnGuardar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(0, 100, 220));
            }
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(new Color(0, 120, 255));
            }
        });

        btnSalir = new JButton("Salir");
        // Estilos y acciones de Salir
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSalir.setPreferredSize(new Dimension(180, 45));
        btnSalir.setBackground(new Color(0, 120, 255));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.addActionListener(e -> salir());
        btnSalir.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSalir.setBackground(new Color(0, 100, 220));
            }
            public void mouseExited(MouseEvent e) {
                btnSalir.setBackground(new Color(0, 120, 255));
            }
        });

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Método que simula la carga de datos (llamado por el Controller)
    private void cargarDatosMascota(String idMascota) {
        // Simulación de carga de datos: se trae el propietario de la mascota con ese ID
        String nombrePropietario = (idMascota.equals("102")) ? "Buz, Diego" : "Perez, Juan";

        // 1. Mostrar Propietario (Apellido y Nombre)
        lblPropietarioInfo.setText(nombrePropietario);

        // 2. Cargar datos de Mascota
        txtNombre.setText("Toby");
        txtFechaNacimiento.setText("10/20/2020");
        txtEspecie.setText("perro (canis lupus)");
        txtRaza.setText("mestizo");
        cmbSexo.setSelectedItem("macho");
        txtSenasParticulares.setText("Cola cortada, pelaje blanco con manchas marrones en ambas orejas..");
    }

    private void guardarMascota() {
        // Lógica de la Tabla de Decisión de MODIFICACIÓN comienza aquí

        // 1. Condición: Campos obligatorios (Validación básica)
        if (txtNombre.getText().trim().isEmpty() ||
                txtFechaNacimiento.getText().trim().isEmpty() ||
                txtEspecie.getText().trim().isEmpty() ||
                txtRaza.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Error: Todos los campos obligatorios deben estar completos.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Condición: Confirmar guardado
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea guardar los cambios?",
                "Confirmar Modificación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Llamada al MascotaController.modificar(datos)

            JOptionPane.showMessageDialog(this,
                    "Mascota modificada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea salir sin guardar?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaModificacionMascota frame = new VentanaModificacionMascota("101");
            frame.setVisible(true);
        });
    }
}