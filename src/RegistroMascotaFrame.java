
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegistroMascotaFrame extends JFrame {
    private JTextField txtNombre;
    private JComboBox<String> cmbFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;
    private JButton btnGuardar;
    private JButton btnSalir;

    public RegistroMascotaFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de la Mascota");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Propietario (no editable)
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
        JLabel lblPropietarioValor = new JLabel("Perez,Ana Maria");
        lblPropietarioValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPropietarioValor, gbc);

        // Nombre
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

        // Fecha nacimiento
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbFechaNacimiento = new JComboBox<>(new String[]{
                "Seleccionar fecha nacimiento", "15/01/2020", "20/03/2021", "10/06/2022"
        });
        cmbFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbFechaNacimiento.setPreferredSize(new Dimension(400, 35));
        cmbFechaNacimiento.setBackground(Color.WHITE);
        cmbFechaNacimiento.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(cmbFechaNacimiento, gbc);

        // Especie
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

        // Raza
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

        // Sexo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblSexo = new JLabel("Sexo");
        lblSexo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblSexo, gbc);

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

        // Señas Particulares
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
        // Validar campos
        if (txtNombre.getText().equals("Ingrese nombre") || txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre de la mascota",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cmbFechaNacimiento.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione la fecha de nacimiento",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtEspecie.getText().equals("Ingrese especie") || txtEspecie.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la especie",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtRaza.getText().equals("Ingrese la raza") || txtRaza.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la raza",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cmbSexo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione el sexo",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar guardado
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar los datos de la mascota?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Mascota registrada correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("Ingrese nombre");
        txtNombre.setForeground(Color.GRAY);
        cmbFechaNacimiento.setSelectedIndex(0);
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
            new RegistroMascotaFrame().setVisible(true);
        });
    }
}

