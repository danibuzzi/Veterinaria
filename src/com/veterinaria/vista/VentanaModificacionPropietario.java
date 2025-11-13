/*package com.veterinaria.vista;

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

    // Constructor que acepta el ID del propietario a modificar
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
}*/
package com.veterinaria.vista;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;

public class VentanaModificacionPropietario extends JInternalFrame {

    // CAMPOS DE LA VISTA (Para que el Controlador los acceda)
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtNombres;
    private JDateChooser dcFechaNacimiento;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtPais;
    private JTextField txtCiudad;

    // BOTONES
    private JButton btnGuardar;
    private JButton btnSalir;

    // Constructor que acepta el ID del propietario a modificar
    public VentanaModificacionPropietario(String idPropietario) {
        initComponents();
        // NOTA: La carga de datos debe ser iniciada por el Controlador
    }

    // Constructor sin argumentos (Mantenido para Main de prueba)
    public VentanaModificacionPropietario() {
        this("0");
    }

    private void initComponents() {
        setTitle("Modificación de Propietario");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal (BorderLayout)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel del Formulario (Centro)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Por defecto sigue siendo horizontal para los otros campos
        gbc.insets = new Insets(10, 5, 10, 5);

        int row = 0;

        // *****************************************************************
        // --- 1. DNI (Numérico y MÁS ANGOSTO) ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDni = createLabel("DNI");
        formPanel.add(lblDni, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Dejamos el weightx para que el panel se estire
        gbc.anchor = GridBagConstraints.WEST;


        txtDni = createNumericTextField(15, 10);
        txtDni.setPreferredSize(new Dimension(150, 28));
        txtDni.setMaximumSize(new Dimension(150, 28)); // Asegurar tamaño máximo

        JPanel dniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dniPanel.setBackground(Color.WHITE);
        dniPanel.add(txtDni);
        formPanel.add(dniPanel, gbc);
        row++;

        // *****************************************************************
        // --- 2. Apellidos ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Apellidos"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtApellidos = createTextField(30);
        formPanel.add(txtApellidos, gbc);
        row++;

        // *****************************************************************
        // --- 3. Nombres ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Nombres"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtNombres = createTextField(30);
        formPanel.add(txtNombres, gbc);
        row++;

        // *****************************************************************
        // --- 4. Fecha de Nacimiento (JDateChooser MÁS ANGOSTO) ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Fecha Nac."), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dcFechaNacimiento.setPreferredSize(new Dimension(120, 28));
        dcFechaNacimiento.setMaximumSize(new Dimension(120, 28)); // Forzar tamaño máximo

        // 🛑 SOLUCIÓN: Usar un JPanel con FlowLayout.LEFT para contener el JDateChooser
        // y evitar que GridBagLayout lo estire.
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fechaPanel.setBackground(Color.WHITE);
        fechaPanel.add(dcFechaNacimiento);
        formPanel.add(fechaPanel, gbc);
        row++;

        // *****************************************************************
        // --- 5. Dirección ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Dirección"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtDireccion = createTextField(30);
        formPanel.add(txtDireccion, gbc);
        row++;

        // *****************************************************************
        // --- 6. Teléfono (Numérico y MÁS ANGOSTO) ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Teléfono"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        txtTelefono = createNumericTextField(20, 15);
        txtTelefono.setPreferredSize(new Dimension(180, 28));
        txtTelefono.setMaximumSize(new Dimension(180, 28)); // Asegurar tamaño máximo

        JPanel telefonoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        telefonoPanel.setBackground(Color.WHITE);
        telefonoPanel.add(txtTelefono);
        formPanel.add(telefonoPanel, gbc);
        row++;

        // *****************************************************************
        // --- 7. Email ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Email"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtEmail = createTextField(30);
        txtEmail.setPreferredSize(new Dimension(300, 28));
        formPanel.add(txtEmail, gbc);
        row++;

        // *****************************************************************
        // --- 8. País ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("País"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        txtPais = createTextField(20);
        txtPais.setPreferredSize(new Dimension(200, 28));
        formPanel.add(txtPais, gbc);
        row++;

        // *****************************************************************
        // --- 9. Ciudad ---
        // *****************************************************************
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createLabel("Ciudad"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtCiudad = createTextField(22);
        txtCiudad.setPreferredSize(new Dimension(200, 28));
        formPanel.add(txtCiudad, gbc);
        row++;

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // **********************************************
        // --- Panel de Botones (Parte Inferior - SUR) ---
        // **********************************************
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = createButton("Guardar", new Color(0, 120, 215));
        btnSalir = createButton("Salir", new Color(0, 120, 215));

         //Se remueven los ActionListeners de la Vista, los debe poner el Controlador


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

    private JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 28));
        return field;
    }

    // Método para crear campos que solo acepten números y con límite
    private JTextField createNumericTextField(int visualColumns, int maxChars) {
        JTextField field = createTextField(visualColumns);
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String resultingText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                if (resultingText.length() <= maxChars && text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String resultingText = currentText.substring(0, offset) + string + currentText.substring(offset);

                if (resultingText.length() <= maxChars && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });
        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

    // --- LÓGICA DE LA VISTA (Minimalista para MVC) ---

    // Este método SÍ debe ser público para ser llamado por el Controlador en su listener.
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

    // Validación de obligatoriedad (La única validación permitida en la Vista)
    public boolean validarCamposObligatorios() {
        // ... (el código de validación de campos vacíos se mantiene igual)
        if (txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DNI es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los nombres son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        if (dcFechaNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La Fecha de Nacimiento es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Dirección es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            txtDireccion.requestFocus();
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El Teléfono es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El Email es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        if (txtPais.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El País es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            txtPais.requestFocus();
            return false;
        }
        if (txtCiudad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Ciudad es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            txtCiudad.requestFocus();
            return false;
        }

        return true;
    }

    // --- MÉTODOS DE CARGA Y GETTERS (Interfaz para el Controlador) ---

    public void setDatosPropietario(String dni, String apellidos, String nombres, Date fechaNacimiento, String direccion, String telefono, String email, String pais, String ciudad) {
        txtDni.setText(dni);
        txtApellidos.setText(apellidos);
        txtNombres.setText(nombres);
        dcFechaNacimiento.setDate(fechaNacimiento);
        txtDireccion.setText(direccion);
        txtTelefono.setText(telefono);
        txtEmail.setText(email);
        txtPais.setText(pais);
        txtCiudad.setText(ciudad);

        // Bloquear DNI si el negocio lo requiere para la modificación
        txtDni.setEditable(false);
    }

    // Getters para que el Controlador pueda leer los datos ingresados
    public String getDni() { return txtDni.getText().trim(); }
    public String getApellidos() { return txtApellidos.getText().trim(); }
    public String getNombres() { return txtNombres.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getDireccion() { return txtDireccion.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPais() { return txtPais.getText().trim(); }
    public String getCiudad() { return txtCiudad.getText().trim(); }

    // Getters de Botones para que el Controlador pueda añadir ActionListeners
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnSalir() { return btnSalir; }

    // Main de prueba
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaModificacionPropietario vista = new VentanaModificacionPropietario("101");

            // Simulación de carga de datos (Normalmente lo haría el Controlador)
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                vista.setDatosPropietario("2569708", "Cerruti", "Lucia Malena", sdf.parse("15/09/1980"), "Los Tilos 500", "3514000000", "lucia@mail.com", "Argentina", "Mar del Palta");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Se añaden los listeners para que la ventana sea funcional en la prueba.
            vista.getBtnGuardar().addActionListener(e -> {
                if (vista.validarCamposObligatorios()) {
                    JOptionPane.showMessageDialog(vista, "Validación de campos obligatorios OK. Ahora el Controlador debería validar la lógica de negocio (Email, Fecha, etc.)", "MVC", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            vista.getBtnSalir().addActionListener(e -> vista.salir());

            vista.setVisible(true);
        });
    }
}