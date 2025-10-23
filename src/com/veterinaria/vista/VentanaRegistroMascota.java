package com.veterinaria.vista;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VentanaRegistroMascota extends JFrame {
    private JTextField txtNombre;

    private JDateChooser dateChooserFechaNacimiento;

    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    private JComboBox<String> cmbPropietario;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentanaRegistroMascota() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de la Mascota");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Propietario (JComboBox)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPropietario = new JLabel("Propietario");
        lblPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPropietario, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        String[] propietariosEjemplo = {"Seleccionar Propietario", "Perez, Ana Maria (DNI 123)", "Dig, Drap (DNI 456)", "Monetti, Emilio (DNI 789)"};
        cmbPropietario = new JComboBox<>(propietariosEjemplo);
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(400, 35));
        cmbPropietario.setBackground(Color.WHITE);
        cmbPropietario.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField("Ingrese nombre");
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setForeground(Color.GRAY);
        txtNombre.setPreferredSize(new Dimension(400, 35));
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        addPlaceholderBehavior(txtNombre, "Ingrese nombre");
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (JDateChooser)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento");
        lblFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblFechaNacimiento, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        // ** SOLUCIÓN FINAL: Configuración del JDateChooser **
        dateChooserFechaNacimiento = new JDateChooser();
        dateChooserFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dateChooserFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // ** AJUSTE 1: Ancho del JDateChooser (Contenedor) **
        dateChooserFechaNacimiento.setPreferredSize(new Dimension(160, 30));
        dateChooserFechaNacimiento.setMinimumSize(new Dimension(160, 30)); // Forzar tamaño mínimo

        // ** AJUSTE 2 CLAVE: Obtenemos el campo de texto interno (JTextField) **
        JTextField dateTextField = (JTextField) dateChooserFechaNacimiento.getDateEditor().getUiComponent();

        // Forzamos el ancho con setMinimumSize y setPreferredSize
        dateTextField.setMinimumSize(new Dimension(120, 30));
        dateTextField.setPreferredSize(new Dimension(120, 30));
        dateTextField.setHorizontalAlignment(JTextField.LEFT);

        // El listener se mantiene vacío
        dateChooserFechaNacimiento.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Lógica de validación o acción tras selección (si es necesaria)
            }
        });

        // Agregamos el JDateChooser al formulario principal
        formPanel.add(dateChooserFechaNacimiento, gbc);

        // 4. Especie
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblEspecie = new JLabel("Especie");
        lblEspecie.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblEspecie, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtEspecie = new JTextField("Ingrese especie");
        txtEspecie.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEspecie.setForeground(Color.GRAY);
        txtEspecie.setPreferredSize(new Dimension(400, 35));
        txtEspecie.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        addPlaceholderBehavior(txtEspecie, "Ingrese especie");
        formPanel.add(txtEspecie, gbc);

        // 5. Raza
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblRaza = new JLabel("Raza");
        lblRaza.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblRaza, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtRaza = new JTextField("Ingrese la raza");
        txtRaza.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtRaza.setForeground(Color.GRAY);
        txtRaza.setPreferredSize(new Dimension(400, 35));
        txtRaza.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        addPlaceholderBehavior(txtRaza, "Ingrese la raza");
        formPanel.add(txtRaza, gbc);

        // 6. Sexo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblSexo = new JLabel("Sexo");
        lblSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblSexo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cmbSexo = new JComboBox<>(new String[]{
                "Seleccionar sexo", "Macho", "Hembra"
        });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(250, 35));
        cmbSexo.setBackground(Color.WHITE);
        cmbSexo.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblSenasParticulares = new JLabel("Señas Particulares");
        lblSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblSenasParticulares, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setRows(6);
        txtSenasParticulares.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(200, 45));
        btnGuardar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnGuardar.setBackground(new Color(37, 99, 235));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(29, 78, 216));
            }
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(new Color(37, 99, 235));
            }
        });
        btnGuardar.addActionListener(e -> guardarMascota());

        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(200, 45));
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnSalir.setBackground(new Color(37, 99, 235));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSalir.setBackground(new Color(29, 78, 216));
            }
            public void mouseExited(MouseEvent e) {
                btnSalir.setBackground(new Color(37, 99, 235));
            }
        });
        btnSalir.addActionListener(e -> dispose());

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }


    private void addPlaceholderBehavior(JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void guardarMascota() {
        // 1. Validar Propietario
        if (cmbPropietario.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione el propietario",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validar Nombre
        if (txtNombre.getText().equals("Ingrese nombre") || txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre de la mascota",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Validar Fecha de Nacimiento (JDateChooser)
        if (dateChooserFechaNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione la fecha de nacimiento",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Validar Especie
        if (txtEspecie.getText().equals("Ingrese especie") || txtEspecie.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la especie",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 5. Validar Raza
        if (txtRaza.getText().equals("Ingrese la raza") || txtRaza.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la raza",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 6. Validar Sexo
        if (cmbSexo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione el sexo",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos para el mensaje
        String propietario = cmbPropietario.getSelectedItem().toString();
        String nombreMascota = txtNombre.getText().trim();
        String fechaNac = dateFormat.format(dateChooserFechaNacimiento.getDate());

        // Confirmar guardado
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar los datos de la mascota '" + nombreMascota + "' para el propietario: " + propietario + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Mascota registrada correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        }
    }

    private void limpiarFormulario() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("Ingrese nombre");
        txtNombre.setForeground(Color.GRAY);
        dateChooserFechaNacimiento.setDate(null);

        txtEspecie.setText("Ingrese especie");
        txtEspecie.setForeground(Color.GRAY);
        txtRaza.setText("Ingrese la raza");
        txtRaza.setForeground(Color.GRAY);
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VentanaRegistroMascota().setVisible(true);
        });
    }
}