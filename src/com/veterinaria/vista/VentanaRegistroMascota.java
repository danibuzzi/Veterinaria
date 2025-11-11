package com.veterinaria.vista;


/*import javax.swing.*;
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
        cmbPropietario.setPreferredSize(new Dimension(150, 35));
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


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaRegistroMascota extends JFrame {

    // CAMPOS DE LA VISTA
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
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
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Panel de formulario (GridBagLayout)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Propietario (JComboBox - Ancho fijo 300px)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblPropietario = createLabel("Propietario");
        formPanel.add(lblPropietario, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cmbPropietario = new JComboBox<>();
        cmbPropietario.addItem("--- Seleccionar Propietario ---");
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(300, 35));
        cmbPropietario.setBackground(Color.WHITE);
        cmbPropietario.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));

        JPanel propietarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        propietarioPanel.setBackground(Color.WHITE);
        propietarioPanel.add(cmbPropietario);

        formPanel.add(propietarioPanel, gbc);

        // 2. Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(createLabel("Nombre"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = createTextField("Ingrese nombre");
        txtNombre.setPreferredSize(new Dimension(400, 35));
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (JDateChooser)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(createLabel("Fecha nacimiento"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Ajuste para asegurar que la fecha se muestre completamente
        dcFechaNacimiento.setPreferredSize(new Dimension(160, 30));
        dcFechaNacimiento.setMinimumSize(new Dimension(160, 30));

        JTextField dateTextField = (JTextField) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setPreferredSize(new Dimension(120, 30));
        dateTextField.setMinimumSize(new Dimension(120, 30));
        dateTextField.setHorizontalAlignment(JTextField.LEFT);

        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(createLabel("Especie"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtEspecie = createTextField("Ingrese especie");
        txtEspecie.setPreferredSize(new Dimension(400, 35));
        formPanel.add(txtEspecie, gbc);

        // 5. Raza
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(createLabel("Raza"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtRaza = createTextField("Ingrese la raza");
        txtRaza.setPreferredSize(new Dimension(400, 35));
        formPanel.add(txtRaza, gbc);

        // 6. Sexo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(createLabel("Sexo"), gbc);

        gbc.gridx = 1;
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

        // 7. Señas Particulares (Con Scroll funcional)
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        JLabel lblSenasParticulares = createLabel("Señas Particulares");
        formPanel.add(lblSenasParticulares, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        txtSenasParticulares.setRows(6);
        txtSenasParticulares.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));

        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = createButton("Guardar", new Color(37, 99, 235));
        btnSalir = createButton("Salir", new Color(37, 99, 235));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- MÉTODOS DE SOPORTE DE UI ---

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        addPlaceholderBehavior(field, placeholder);
        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
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

    // --- MÉTODOS DE INTERFAZ PARA EL CONTROLADOR ---

    public boolean validarCamposObligatorios() {
        if (cmbPropietario.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione el propietario", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtNombre.getText().equals("Ingrese nombre") || txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre de la mascota", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }

        if (dcFechaNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione la fecha de nacimiento", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtEspecie.getText().equals("Ingrese especie") || txtEspecie.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la especie", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtEspecie.requestFocus();
            return false;
        }

        if (txtRaza.getText().equals("Ingrese la raza") || txtRaza.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la raza", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtRaza.requestFocus();
            return false;
        }

        if (cmbSexo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione el sexo", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public void setPropietarios(String[] propietarios) {
        cmbPropietario.removeAllItems();
        cmbPropietario.addItem("--- Seleccionar Propietario ---");
        for (String p : propietarios) {
            cmbPropietario.addItem(p);
        }
    }

    // Getters para que el Controlador pueda leer los datos
    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() {
        return cmbSexo.getSelectedIndex() > 0 ? cmbSexo.getSelectedItem().toString() : null;
    }
    public String getSenasParticulares() { return txtSenasParticulares.getText().trim(); }
    public String getPropietarioSeleccionado() {
        return cmbPropietario.getSelectedIndex() > 0 ? cmbPropietario.getSelectedItem().toString() : null;
    }

    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnSalir() { return btnSalir; }

    public void limpiarFormulario() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("Ingrese nombre");
        txtNombre.setForeground(Color.GRAY);
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("Ingrese especie");
        txtEspecie.setForeground(Color.GRAY);
        txtRaza.setText("Ingrese la raza");
        txtRaza.setForeground(Color.GRAY);
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void salir() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            VentanaRegistroMascota vista = new VentanaRegistroMascota();
            String[] propietariosDB = {"Perez, Ana Maria (DNI 123)", "Dig, Drap (DNI 456)", "Monetti, Emilio (DNI 789)"};
            vista.setPropietarios(propietariosDB);

            vista.getBtnGuardar().addActionListener(e -> {
                if (vista.validarCamposObligatorios()) {
                    JOptionPane.showMessageDialog(vista,
                            "Validación de campos obligatorios OK.",
                            "Simulación MVC", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            vista.getBtnSalir().addActionListener(e -> vista.salir());

            vista.setVisible(true);
        });
    }
}*/
/*
import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class VentanaRegistroMascota extends JFrame {

    // CAMPOS DE LA VISTA
    private JComboBox<Propietario> cmbPropietario;
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroMascota() {
        inicializarComponentes(); // <-- Método renombrado
    }

    private void inicializarComponentes() { // <-- Método renombrado
        setTitle("Registro de la Mascota");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal y formulario (GridBagLayout para la estructura)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Componentes ---
        // 1. Propietario
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(crearEtiqueta("Propietario"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbPropietario = new JComboBox<>();
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(300, 35));
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Nombre"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNombre = crearCampoTexto(300, 35); // <-- Método renombrado
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Fecha nacimiento"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(160, 35));
        JTextField dateTextField = (JTextField) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setPreferredSize(new Dimension(120, 35));
        dateTextField.setHorizontalAlignment(JTextField.LEFT);
        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Especie"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEspecie = crearCampoTexto(300, 35); // <-- Método renombrado
        formPanel.add(txtEspecie, gbc);

        // 5. Raza
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Raza"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtRaza = crearCampoTexto(300, 35); // <-- Método renombrado
        formPanel.add(txtRaza, gbc);

        // 6. Sexo
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Sexo"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        cmbSexo = new JComboBox<>(new String[]{ "Seleccionar sexo", "Macho", "Hembra" });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(250, 35));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(crearEtiqueta("Señas Particulares"), gbc); // <-- Método renombrado
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", new Color(37, 99, 235)); // <-- Método renombrado
        btnGuardar.setActionCommand("GUARDAR_MASCOTA");

        btnSalir = crearBoton("Salir", new Color(37, 99, 235)); // <-- Método renombrado
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (CLAVE) ---
    public void establecerControlador(ActionListener controlador) { // <-- Método renombrado
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
    }

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        cmbPropietario.setModel(model);
        if (model.getSize() > 0) {
            cmbPropietario.setSelectedIndex(0);
        }
    }

    public void limpiarCampos() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("");
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void salir() {
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

    // --- Getters .. ---
    public JComboBox<Propietario> getComboPropietario() { return cmbPropietario; } // <-- Renombrado
    public String getNombre() { return txtNombre.getText().trim(); } // <-- Renombrado
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); } // <-- Renombrado
    public String getEspecie() { return txtEspecie.getText().trim(); } // <-- Renombrado
    public String getRaza() { return txtRaza.getText().trim(); } // <-- Renombrado
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); } // <-- Renombrado
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); } // <-- Renombrado

    // Métodos de ayuda (con estilos)
    private JLabel crearEtiqueta(String texto) { // <-- Método renombrado
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(140, 25));
        return label;
    }

    private JTextField crearCampoTexto(int ancho, int alto) { // <-- Método renombrado
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(ancho, alto));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton crearBoton(String texto, Color colorFondo) { // <-- Método renombrado
        JButton button = new JButton(texto);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorFondo.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorFondo);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroMascota().setVisible(true);
        });
    }
}*/


/*
import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.text.JTextComponent;

public class VentanaRegistroMascota extends JFrame {

    // --- CONSTANTES DE ESTILO ---
    private static final int FIELD_HEIGHT = 35;
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);

    // CAMPOS DE LA VISTA
    private JComboBox<Propietario> cmbPropietario;
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    // Constructor que recibe el JDesktopPane para simular el lanzador
    public VentanaRegistroMascota(JDesktopPane escritorio) {
        inicializarComponentes();
    }

    // Constructor sin JDesktopPane para la inicialización real del controlador
    public VentanaRegistroMascota() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de la Mascota");
        setSize(700, 600);
        //setClosable(true);
        //setMaximizable(false);
        setResizable(false);
        //setIconifiable(true);

        // Panel principal y formulario (GridBagLayout para la estructura)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Componentes ---

        // 1. Propietario (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Propietario"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        cmbPropietario = new JComboBox<>();
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(280, FIELD_HEIGHT)); // 🛑 ANCHO AJUSTADO
        cmbPropietario.setMaximumSize(new Dimension(280, FIELD_HEIGHT));
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(crearEtiqueta("Nombre"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtNombre = crearCampoTexto(280, FIELD_HEIGHT); // 🛑 ANCHO FIJO
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (ANCHO CORREGIDO)
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(crearEtiqueta("Fecha nacimiento"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(140, FIELD_HEIGHT)); // 🛑 ANCHO CORREGIDO
        dcFechaNacimiento.setMaximumSize(new Dimension(140, FIELD_HEIGHT));

        JTextComponent dateTextField = (JTextComponent) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setPreferredSize(new Dimension(100, FIELD_HEIGHT));
        dateTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie (ANCHO REDUCIDO)
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(crearEtiqueta("Especie"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtEspecie = crearCampoTexto(140, FIELD_HEIGHT); // 🛑 ANCHO REDUCIDO
        formPanel.add(txtEspecie, gbc);

        // 5. Raza (ANCHO REDUCIDO)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(crearEtiqueta("Raza"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtRaza = crearCampoTexto(140, FIELD_HEIGHT); // 🛑 ANCHO REDUCIDO
        formPanel.add(txtRaza, gbc);

        // 6. Sexo (ANCHO REDUCIDO - COMBO)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(crearEtiqueta("Sexo"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        cmbSexo = new JComboBox<>(new String[]{ "Seleccionar sexo", "Macho", "Hembra" });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(140, FIELD_HEIGHT)); // 🛑 ANCHO REDUCIDO
        cmbSexo.setMaximumSize(new Dimension(140, FIELD_HEIGHT));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares (JTextArea)
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(crearEtiqueta("Señas Particulares"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(280, 100));
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", BLUE_PRIMARY);
        btnGuardar.setActionCommand("GUARDAR_MASCOTA");

        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
        pack();
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (GETTERS) ---
    public void establecerControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
        // CRÍTICO: El combo de propietario también necesita escuchar eventos
        cmbPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        cmbPropietario.addActionListener(controlador);
    }

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        cmbPropietario.setModel(model);
        if (model.getSize() > 0) {
            cmbPropietario.setSelectedIndex(0);
        }
    }

    public void limpiarCampos() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("");
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void salir() {
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

    // --- Getters para el Controlador (Devuelven el componente o el valor) ---
    public JComboBox<Propietario> getComboPropietario() { return cmbPropietario; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); }

    // Métodos de ayuda (con estilos)
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto + ":");
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(140, 25));
        return label;
    }

    private JTextField crearCampoTexto(int ancho, int alto) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(ancho, alto));
        field.setMaximumSize(new Dimension(ancho, alto));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton button = new JButton(texto);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorFondo.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorFondo);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroMascota().setVisible(true);
        });
    }
}*/






/*import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.text.JTextComponent;

public class VentanaRegistroMascota extends JFrame {

    private static final int FIELD_HEIGHT = 35;
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final int LABEL_WIDTH = 150; // Etiqueta ligeramente más ancha para alineación
    private static final int SHORT_FIELD_WIDTH = 150; // Ancho reducido para Fecha y Sexo
    private static final int MEDIUM_FIELD_WIDTH = 250; // Ancho medio para Especie y Raza
    private static final int LONG_FIELD_WIDTH = 380; // Ancho largo para Propietario y Nombre

    private JComboBox<Propietario> cmbPropietario;
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroMascota(JDesktopPane escritorio) {
        inicializarComponentes();
    }

    public VentanaRegistroMascota() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de la Mascota");

        // El JInternalFrame debe tener un tamaño fijo inicial adecuado
        setSize(700, 650);

        //setClosable(true);
        //setMaximizable(false);
        setResizable(true);
        //setIconifiable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Contenedor para centrar el formulario: usamos otro GridBagLayout
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Propietario (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Propietario"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Pesa para expandirse horizontalmente
        cmbPropietario = new JComboBox<>();
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(LONG_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Nombre"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = crearCampoTexto(LONG_FIELD_WIDTH, FIELD_HEIGHT);
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (FIJO/CORTO)
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Fecha nacimiento"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));

        JTextComponent dateTextField = (JTextComponent) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie (FIJO/MEDIO)
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Especie"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtEspecie = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtEspecie.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtEspecie, gbc);

        // 5. Raza (FIJO/MEDIO)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Raza"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtRaza = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtRaza.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtRaza, gbc);

        // 6. Sexo (FIJO/CORTO)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Sexo"), gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cmbSexo = new JComboBox<>(new String[]{ "Seleccionar sexo", "Macho", "Hembra" });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        cmbSexo.setMaximumSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Señas Particulares"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH; // EXPANDIR Horizontal y Vertical
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Pesa para expandirse verticalmente
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(LONG_FIELD_WIDTH, 120));
        formPanel.add(scrollPane, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;


        GridBagConstraints centerGBC = new GridBagConstraints();
        centerGBC.weightx = 1.0; // Permitir que el contenedor central se expanda
        centerGBC.weighty = 1.0; // Permitir que el contenedor central se expanda
        centerContainer.add(formPanel, centerGBC);

        mainPanel.add(centerContainer, BorderLayout.CENTER);

        // Panel de botones (CENTRADO)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", BLUE_PRIMARY);
        btnGuardar.setActionCommand("GUARDAR_MASCOTA");

        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (Se mantienen) ---

    public void establecerControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
        cmbPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        cmbPropietario.addActionListener(controlador);
    }

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        cmbPropietario.setModel(model);
        if (model.getSize() > 0) {
            cmbPropietario.setSelectedIndex(0);
        }
    }

    public void limpiarCampos() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("");
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void salir() {
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

    // --- Getters para el Controlador (Se mantienen) ---
    public JComboBox<Propietario> getComboPropietario() { return cmbPropietario; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); }


    // --- Métodos de Ayuda (Se mantienen sin comentarios) ---
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto + ":");
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, 25));
        return label;
    }

    private JTextField crearCampoTexto(int ancho, int alto) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(ancho, alto));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton button = new JButton(texto);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorFondo.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorFondo);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroMascota().setVisible(true);
        });
    }
}*/


/*
import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.text.JTextComponent;

public class VentanaRegistroMascota extends JFrame {

    private static final int FIELD_HEIGHT = 35;
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final int LABEL_WIDTH = 150;
    private static final int SHORT_FIELD_WIDTH = 150;
    private static final int MEDIUM_FIELD_WIDTH = 250;

    private static final int LONG_FIELD_WIDTH = 350;

    private static final int SENAS_PARTICULARES_WIDTH = 450;

    private JComboBox<Propietario> cmbPropietario;
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroMascota(JDesktopPane escritorio) {
        inicializarComponentes();
    }

    public VentanaRegistroMascota() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de la Mascota");

        // Ajustamos el tamaño para acomodar el ancho del JTextArea
        setSize(750, 680);

        //setClosable(true);
        //setMaximizable(false);
        setResizable(true);
        //setIconifiable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Contenedor para centrar el formulario
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Propietario (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Propietario"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbPropietario = new JComboBox<>();
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(LONG_FIELD_WIDTH, FIELD_HEIGHT)); // Usa LONG_FIELD_WIDTH (350)
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Nombre"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = crearCampoTexto(LONG_FIELD_WIDTH, FIELD_HEIGHT); // Usa LONG_FIELD_WIDTH (350)
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (FIJO/CORTO)
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Fecha nacimiento"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));

        JTextComponent dateTextField = (JTextComponent) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie (FIJO/MEDIO)
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Especie"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtEspecie = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtEspecie.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtEspecie, gbc);

        // 5. Raza (FIJO/MEDIO)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Raza"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtRaza = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtRaza.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtRaza, gbc);

        // 6. Sexo (FIJO/CORTO)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Sexo"), gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cmbSexo = new JComboBox<>(new String[]{ "Seleccionar sexo", "Macho", "Hembra" });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        cmbSexo.setMaximumSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares (EXPANDIBLE y AHORA MÁS ANCHO)
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Señas Particulares"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        // Usa el nuevo ancho mayor para el JTextArea
        scrollPane.setPreferredSize(new Dimension(SENAS_PARTICULARES_WIDTH, 120));
        formPanel.add(scrollPane, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;

        // Configuración para centrar el formulario dentro del área central del JInternalFrame
        GridBagConstraints centerGBC = new GridBagConstraints();
        centerGBC.weightx = 0.0;
        centerGBC.weighty = 1.0;
        centerContainer.add(formPanel, centerGBC);

        mainPanel.add(centerContainer, BorderLayout.CENTER);

        // Panel de botones (CENTRADO/INFERIOR)
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", BLUE_PRIMARY);
        btnGuardar.setActionCommand("GUARDAR_MASCOTA");

        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        buttonContainer.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(buttonContainer, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (Se mantienen) ---

    public void establecerControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
        cmbPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        cmbPropietario.addActionListener(controlador);
    }

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        cmbPropietario.setModel(model);
        if (model.getSize() > 0) {
            cmbPropietario.setSelectedIndex(0);
        }
    }

    public void limpiarCampos() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("");
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void salir() {
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

    // --- Getters para el Controlador (Se mantienen) ---
    public JComboBox<Propietario> getComboPropietario() { return cmbPropietario; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); }


    // --- Métodos de Ayuda (Se mantienen sin comentarios) ---
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto + ":");
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, 25));
        return label;
    }

    private JTextField crearCampoTexto(int ancho, int alto) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(ancho, alto));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton button = new JButton(texto);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorFondo.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorFondo);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroMascota().setVisible(true);
        });
    }
}*/



/*import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.text.JTextComponent;

public class VentanaRegistroMascota extends JFrame {

    private static final int FIELD_HEIGHT = 35;
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final int LABEL_WIDTH = 150;
    private static final int SHORT_FIELD_WIDTH = 150;
    private static final int MEDIUM_FIELD_WIDTH = 250;
    private static final int LONG_FIELD_WIDTH = 300; // Reducido para Nombre y Propietario
    private static final int SENAS_PARTICULARES_WIDTH = 400; // Ancho ajustado para JTextArea

    private JComboBox<Propietario> cmbPropietario;
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroMascota(JDesktopPane escritorio) {
        inicializarComponentes();
    }

    public VentanaRegistroMascota() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de la Mascota");

        setSize(700, 680);

        //setClosable(true);
        //setMaximizable(false);
        setResizable(true);
        //setIconifiable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Contenedor principal del formulario que usará GridBagLayout para centrado total
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Propietario (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Propietario"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbPropietario = new JComboBox<>();
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(LONG_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Nombre"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = crearCampoTexto(LONG_FIELD_WIDTH, FIELD_HEIGHT);
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (FIJO/CORTO)
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Fecha nacimiento"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));

        JTextComponent dateTextField = (JTextComponent) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie (FIJO/MEDIO)
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Especie"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtEspecie = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtEspecie.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtEspecie, gbc);

        // 5. Raza (FIJO/MEDIO)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Raza"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtRaza = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtRaza.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtRaza, gbc);

        // 6. Sexo (FIJO/CORTO)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Sexo"), gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cmbSexo = new JComboBox<>(new String[]{ "Seleccionar sexo", "Macho", "Hembra" });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        cmbSexo.setMaximumSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares (EXPANDIBLE)
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Señas Particulares"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(SENAS_PARTICULARES_WIDTH, 120));
        formPanel.add(scrollPane, gbc);

        // Finalizamos el formPanel con un relleno vertical para centrado
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        formPanel.add(new JLabel(""), gbc); // Celdas vacías para forzar centrado vertical

        // Centrado horizontal: Añadimos el formPanel al contenedor principal
        GridBagConstraints centerGBC = new GridBagConstraints();
        centerGBC.gridx = 1; // Columna central
        centerGBC.gridy = 0;
        centerGBC.weightx = 0.0; // NO EXPANDIR el panel de contenido
        centerGBC.weighty = 1.0;
        formContainer.add(formPanel, centerGBC);


        // Añadir peso a las celdas vacías laterales del formContainer
        centerGBC.gridx = 0; // Celda izquierda
        centerGBC.weightx = 1.0;
        formContainer.add(new JLabel(""), centerGBC);

        centerGBC.gridx = 2; // Celda derecha
        centerGBC.weightx = 1.0;
        formContainer.add(new JLabel(""), centerGBC);

        mainPanel.add(formContainer, BorderLayout.CENTER);

        // Panel de botones (CENTRADO/INFERIOR)
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", BLUE_PRIMARY);
        btnGuardar.setActionCommand("GUARDAR_MASCOTA");

        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        buttonContainer.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(buttonContainer, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR ---

    public void establecerControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
        cmbPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        cmbPropietario.addActionListener(controlador);
    }

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        cmbPropietario.setModel(model);
        if (model.getSize() > 0) {
            cmbPropietario.setSelectedIndex(0);
        }
    }

    public void limpiarCampos() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("");
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void salir() {
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

    // --- Getters para el Controlador ---
    public JComboBox<Propietario> getComboPropietario() { return cmbPropietario; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); }


    // --- Métodos de Ayuda ---
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto + ":");
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, 25));
        return label;
    }

    private JTextField crearCampoTexto(int ancho, int alto) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(ancho, alto));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton button = new JButton(texto);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorFondo.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorFondo);
            }
        });
        return button;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroMascota().setVisible(true);
        });
    }
}*/


import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.text.JTextComponent;

public class VentanaRegistroMascota extends JInternalFrame {

    private static final int FIELD_HEIGHT = 35;
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final int LABEL_WIDTH = 150;
    private static final int SHORT_FIELD_WIDTH = 150;
    private static final int MEDIUM_FIELD_WIDTH = 250;

    private static final int LONG_FIELD_WIDTH = 300;

    private static final int SENAS_PARTICULARES_WIDTH = 400;

    private JComboBox<Propietario> cmbPropietario;
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroMascota(JDesktopPane escritorio) {
        inicializarComponentes();
    }

    public VentanaRegistroMascota() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de la Mascota");

        setSize(700, 680);

        //setClosable(true);
        //setMaximizable(false);
        setResizable(true);
        //setIconifiable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Contenedor principal que usa GridBagLayout para centrar el FORMULARIO
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);

        // El panel que contiene las etiquetas y los campos
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Propietario (ANCHO FIJO AHORA)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Propietario"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cmbPropietario = new JComboBox<>();
        cmbPropietario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPropietario.setPreferredSize(new Dimension(LONG_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbPropietario, gbc);

        // 2. Nombre (ANCHO FIJO AHORA)
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Nombre"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; //
        txtNombre = crearCampoTexto(LONG_FIELD_WIDTH, FIELD_HEIGHT);
        formPanel.add(txtNombre, gbc);

        // 3. Fecha nacimiento (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Fecha nacimiento"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));

        JTextComponent dateTextField = (JTextComponent) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(dcFechaNacimiento, gbc);

        // 4. Especie (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Especie"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtEspecie = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtEspecie.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtEspecie, gbc);

        // 5. Raza (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Raza"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        txtRaza = crearCampoTexto(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT);
        txtRaza.setMaximumSize(new Dimension(MEDIUM_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(txtRaza, gbc);

        // 6. Sexo (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Sexo"), gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cmbSexo = new JComboBox<>(new String[]{ "Seleccionar sexo", "Macho", "Hembra" });
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbSexo.setPreferredSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        cmbSexo.setMaximumSize(new Dimension(SHORT_FIELD_WIDTH, FIELD_HEIGHT));
        formPanel.add(cmbSexo, gbc);

        // 7. Señas Particulares (ANCHO FIJO)
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(crearEtiqueta("Señas Particulares"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        txtSenasParticulares = new JTextArea();
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(SENAS_PARTICULARES_WIDTH, 120));
        formPanel.add(scrollPane, gbc);

        // Relleno horizontal para que el formPanel se centre en su contenedor
        gbc.gridx = 2; // Columna de relleno
        gbc.weightx = 1.0;
        formPanel.add(new JLabel(""), gbc);

        // Relleno vertical para centrar el contenido verticalmente
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        formPanel.add(new JLabel(""), gbc);

        // Añadimos el formPanel al contenedor principal (sin peso horizontal para que no se estire)
        GridBagConstraints centerGBC = new GridBagConstraints();
        centerGBC.weightx = 1.0; // Usamos el peso en el contenedor para centrar, no para estirar el formPanel
        centerGBC.weighty = 1.0;
        formContainer.add(formPanel, centerGBC);

        mainPanel.add(formContainer, BorderLayout.CENTER);

        // Panel de botones (CENTRADO/INFERIOR)
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", BLUE_PRIMARY);
        btnGuardar.setActionCommand("GUARDAR_MASCOTA");

        btnSalir = crearBoton("Salir", BLUE_PRIMARY);
        btnSalir.setActionCommand("SALIR");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        buttonContainer.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(buttonContainer, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (Se mantienen) ---

    public void establecerControlador(ActionListener controlador) {
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
        cmbPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        cmbPropietario.addActionListener(controlador);
    }

    public void cargarPropietarios(DefaultComboBoxModel<Propietario> model) {
        cmbPropietario.setModel(model);
        if (model.getSize() > 0) {
            cmbPropietario.setSelectedIndex(0);
        }
    }

    public void limpiarCampos() {
        cmbPropietario.setSelectedIndex(0);
        txtNombre.setText("");
        dcFechaNacimiento.setDate(null);
        txtEspecie.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(0);
        txtSenasParticulares.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void salir() {
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

    // --- Getters para el Controlador (Se mantienen) ---
    public JComboBox<Propietario> getComboPropietario() { return cmbPropietario; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); }


    // --- Métodos de Ayuda (Se mantienen sin comentarios) ---
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto + ":");
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, 25));
        return label;
    }

    private JTextField crearCampoTexto(int ancho, int alto) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(ancho, alto));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton button = new JButton(texto);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorFondo.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorFondo);
            }
        });
        return button;
    }
   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroMascota().setVisible(true);
        });
    }*/
}