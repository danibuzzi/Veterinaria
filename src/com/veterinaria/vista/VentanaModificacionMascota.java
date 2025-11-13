/*package com.veterinaria.vista;

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
}*/

package com.veterinaria.vista;

/*import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaModificacionMascota extends JFrame {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // CAMPOS DE LA VISTA (Para acceso del Controlador)
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;

    // Componentes del Propietario (solo lectura)
    private JLabel lblPropietarioInfo;

    // Botones
    private JButton btnGuardar;
    private JButton btnSalir;

    private String idMascotaActual;

    public VentanaModificacionMascota(String idMascota) {
        this.idMascotaActual = idMascota;
        initComponents();
        // El Controlador debe invocar setDatosMascota(datos) al iniciar
    }

    private void initComponents() {
        setTitle("Modificación de Mascota");
        setSize(700, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Panel principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // --- Panel del formulario (GridBagLayout) ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ----------------------------------------------------
        // 1. Propietario (Inicialmente vacío)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPropietarioLabel = createLabel("Propietario");
        formPanel.add(lblPropietarioLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        lblPropietarioInfo = new JLabel(""); // INICIALMENTE VACÍO
        lblPropietarioInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPropietarioInfo.setForeground(new Color(37, 99, 235));
        lblPropietarioInfo.setPreferredSize(new Dimension(300, 35));
        formPanel.add(lblPropietarioInfo, gbc);

        // Espacio separador
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 10, 0);
        formPanel.add(Box.createRigidArea(new Dimension(0, 0)), gbc);

        gbc.insets = new Insets(6, 5, 6, 5);

        // ----------------------------------------------------
        // 2. Nombre (Inicialmente vacío)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Nombre"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombre = createTextField(300, 35); // Se inicializa sin texto
        formPanel.add(txtNombre, gbc);

        // ----------------------------------------------------
        // 3. Fecha nacimiento (JDateChooser) (Inicialmente nulo)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Fecha nacimiento"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        dcFechaNacimiento = new JDateChooser(); // Se inicializa con fecha nula
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dcFechaNacimiento.setPreferredSize(new Dimension(160, 35));

        JTextField dateTextField = (JTextField) dcFechaNacimiento.getDateEditor().getUiComponent();
        dateTextField.setPreferredSize(new Dimension(120, 35));
        dateTextField.setHorizontalAlignment(JTextField.LEFT);

        formPanel.add(dcFechaNacimiento, gbc);

        // ----------------------------------------------------
        // 4. Especie (Inicialmente vacío)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Especie"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEspecie = createTextField(300, 35);
        formPanel.add(txtEspecie, gbc);

        // ----------------------------------------------------
        // 5. Raza (Inicialmente vacío)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Raza"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtRaza = createTextField(300, 35);
        formPanel.add(txtRaza, gbc);

        // ----------------------------------------------------
        // 6. Sexo (Opciones estáticas)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Sexo"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        String[] sexos = {"Macho", "Hembra"};
        cmbSexo = new JComboBox<>(sexos);
        cmbSexo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cmbSexo.setPreferredSize(new Dimension(150, 35));
        cmbSexo.setBackground(Color.WHITE);
        cmbSexo.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        formPanel.add(cmbSexo, gbc);

        // ----------------------------------------------------
        // 7. Señas Particulares (Inicialmente vacío)
        // ----------------------------------------------------
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(createLabel("Señas Particulares"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0;

        txtSenasParticulares = new JTextArea(5, 30); // Se inicializa sin texto
        txtSenasParticulares.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtSenasParticulares.setLineWrap(true);
        txtSenasParticulares.setWrapStyleWord(true);
        txtSenasParticulares.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(txtSenasParticulares);
        scrollPane.setPreferredSize(new Dimension(400, 100));

        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // --- Panel de botones ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = createButton("Guardar", new Color(37, 99, 235));

        btnSalir = createButton("Salir", new Color(37, 99, 235));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- Métodos de Ayuda para Creación de Componentes (Sin cambios) ---

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(140, 25));
        return label;
    }

    private JTextField createTextField(int width, int height) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(width, height));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 45));
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

    // --- Métodos para que el Controlador establezca los datos (Setters) ---

    public void setPropietarioInfo(String nombreCompleto) {
        lblPropietarioInfo.setText(nombreCompleto);
    }

    public void setDatosMascota(String nombre, Date fechaNacimiento, String especie, String raza, String sexo, String senasParticulares) {
        txtNombre.setText(nombre);
        dcFechaNacimiento.setDate(fechaNacimiento);
        txtEspecie.setText(especie);
        txtRaza.setText(raza);
        // Normalización del sexo para el JComboBox
        String sexoNormalizado = sexo.substring(0, 1).toUpperCase() + sexo.substring(1).toLowerCase();
        cmbSexo.setSelectedItem(sexoNormalizado);
        txtSenasParticulares.setText(senasParticulares);
    }

    // --- Métodos para que el Controlador acceda a los datos (Getters) ---

    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSenasParticulares() { return txtSenasParticulares.getText().trim(); }

    public String getIdMascotaActual() { return idMascotaActual; }

    // Obtiene los componentes para asignar ActionListeners
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnSalir() { return btnSalir; }

    // Método de validación de obligatorios (la lógica más profunda va en el Controlador)
    public boolean validarCamposObligatorios() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtEspecie.getText().trim().isEmpty() ||
                txtRaza.getText().trim().isEmpty() ||
                dcFechaNacimiento.getDate() == null) {

            JOptionPane.showMessageDialog(this,
                    "Error: Los campos Nombre, Fecha de Nacimiento, Especie y Raza son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void salir() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea salir sin guardar?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    // El main es solo para pruebas de la UI, sin carga de datos
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaModificacionMascota vista = new VentanaModificacionMascota("101");

            // LÓGICA DE PRUEBA: El Controlador asigna los ActionListeners
            vista.getBtnGuardar().addActionListener(e -> {
                if(vista.validarCamposObligatorios()) {
                    JOptionPane.showMessageDialog(vista, "Controlador: Recibiendo datos para modificar Mascota ID " + vista.getIdMascotaActual(), "Guardar", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            vista.getBtnSalir().addActionListener(e -> vista.salir());

            vista.setVisible(true);
        });
    }
}*/

import com.veterinaria.controlador.ControladorModificacionMascota;
import com.veterinaria.modelo.Mascota;
import com.toedter.calendar.JDateChooser; // Necesitas la librería jcalendar.jar
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// Archivo: com/veterinaria/vista/VentanaModificacionMascota.java




public class VentanaModificacionMascota extends JInternalFrame {

    private final int idMascotaActual;
    private final VentanaConsultaMascota vistaPadre; // Para refrescar la grilla

    // Componentes de entrada
    private JTextField txtNombre;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtEspecie;
    private JTextField txtRaza;
    private JComboBox<String> cmbSexo;
    private JTextArea txtSenasParticulares;

    // Componentes de solo lectura (Propietario)
    private JLabel lblPropietarioInfo;

    // Botones
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaModificacionMascota(int idMascota, VentanaConsultaMascota vistaPadre) {
        this.idMascotaActual = idMascota;
        this.vistaPadre = vistaPadre;
        initComponents();
        // Inicializar valores por defecto o estáticos
        cmbSexo.addItem("Macho");
        cmbSexo.addItem("Hembra");
    }

    private void initComponents() {
        setTitle("Modificación de Mascota");
        setSize(700, 600);
        setClosable(true);
        setResizable(false);

        // ... (Diseño de la UI - Se omite el boilerplate de diseño) ...
        // Se asume un Layout simple como GridLayout o GridBagLayout.

        // Inicialización de componentes clave
        txtNombre = new JTextField(20);
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        txtEspecie = new JTextField(20);
        txtRaza = new JTextField(20);
        cmbSexo = new JComboBox<>();
        txtSenasParticulares = new JTextArea(5, 20);
        lblPropietarioInfo = new JLabel("Cargando Propietario..."); // Se actualiza por el controlador
        btnGuardar = new JButton("Guardar Modificación");
        btnSalir = new JButton("Salir");

        btnGuardar.setActionCommand("GUARDAR");
        btnSalir.setActionCommand("SALIR");

        // (Configuración y adición de componentes al JInternalFrame)
        // ...

        // Ejemplo de layout muy simple:
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.add(new JLabel("Propietario:"));
        formPanel.add(lblPropietarioInfo);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Fecha Nacimiento:"));
        formPanel.add(dcFechaNacimiento);
        formPanel.add(new JLabel("Especie:"));
        formPanel.add(txtEspecie);
        formPanel.add(new JLabel("Raza:"));
        formPanel.add(txtRaza);
        formPanel.add(new JLabel("Sexo:"));
        formPanel.add(cmbSexo);
        formPanel.add(new JLabel("Señas Particulares:"));
        formPanel.add(new JScrollPane(txtSenasParticulares));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);

        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR ---

    public void establecerControlador(ControladorModificacionMascota controlador) {
        btnGuardar.addActionListener(controlador);
        btnSalir.addActionListener(controlador);
    }

    // Método para cargar los datos en la UI (llamado por el controlador)
    public void cargarDatosMascota(Mascota mascota, String nombrePropietario) {
        txtNombre.setText(mascota.getNombre());
        txtEspecie.setText(mascota.getEspecie());
        txtRaza.setText(mascota.getRaza());
        cmbSexo.setSelectedItem(mascota.getSexo());
        txtSenasParticulares.setText(mascota.getSeniasParticulares());
        lblPropietarioInfo.setText(nombrePropietario);

        if (mascota.getFechaNacimiento() != null) {
            // Conversión de LocalDate a java.util.Date para JDateChooser
            Date date = Date.from(mascota.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
            dcFechaNacimiento.setDate(date);
        }
    }

    // : Obtiene la Mascota actualizada de la UI (usando el constructor/setters)
    public Mascota getMascotaParaGuardar(int idPropietarioOriginal) {
        Mascota mascota = new Mascota(); // Usa el constructor por defecto

        mascota.setIdMascota(this.idMascotaActual);
        // El ID de propietario no cambia en esta vista, se debe reasignar
        mascota.setIdPropietario(idPropietarioOriginal);

        mascota.setNombre(txtNombre.getText().trim());
        mascota.setEspecie(txtEspecie.getText().trim());
        mascota.setRaza(txtRaza.getText().trim());
        mascota.setSexo((String) cmbSexo.getSelectedItem());
        mascota.setSeniasParticulares(txtSenasParticulares.getText());

        if (dcFechaNacimiento.getDate() != null) {
            LocalDate fechaNac = dcFechaNacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            mascota.setFechaNacimiento(fechaNac);
        }

        return mascota;
    }

    // Métodos de feedback y validación
    public boolean validarCamposObligatorios() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtEspecie.getText().trim().isEmpty() ||
                txtRaza.getText().trim().isEmpty() ||
                dcFechaNacimiento.getDate() == null) {

            JOptionPane.showMessageDialog(this,
                    "Error: Los campos Nombre, Fecha de Nacimiento, Especie y Raza son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public VentanaConsultaMascota getVistaPadre() {
        return vistaPadre;
    }

    public void salir() {
        dispose();
    }

    // Métodos para que el Controlador acceda a los datos (Getters) ---

    public String getNombre() { return txtNombre.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getEspecie() { return txtEspecie.getText().trim(); }
    public String getRaza() { return txtRaza.getText().trim(); }
    public String getSexo() { return (String) cmbSexo.getSelectedItem(); }
    public String getSeniasParticulares() { return txtSenasParticulares.getText().trim(); }
}