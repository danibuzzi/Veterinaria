package com.veterinaria.vista;

/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser; // *** ESTA ES LA CLASE EXTERNA NECESARIA ***
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaRegistroPropietario extends JFrame {

    // Form fields
    private JTextField dniField;
    private JTextField apellidosField;
    private JTextField nombresField;

    // CAMBIO: Componente JDateChooser de la librería jcalendar
    private JDateChooser dateChooserFechaNacimiento;

    private JTextField direccionField;
    private JTextField paisField;
    private JTextField ciudadField;

    // Buttons
    private JButton guardarButton;
    private JButton salirButton;

    // Formato de fecha para mostrar
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentanaRegistroPropietario() {
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        setTitle("Registro de Propietario");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize text fields
        dniField = createTextField(20);
        apellidosField = createTextField(30);
        nombresField = createTextField(30);

        // Inicialización de JDateChooser
        dateChooserFechaNacimiento = new JDateChooser();
        dateChooserFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dateChooserFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateChooserFechaNacimiento.setPreferredSize(new Dimension(180, 32)); // Ajuste de tamaño

        direccionField = createTextField(30);
        paisField = createTextField(25);
        ciudadField = createTextField(25);

        // Initialize buttons
        guardarButton = createButton("Guardar", new Color(0, 120, 215));
        salirButton = createButton("Salir", new Color(0, 120, 215));
    }

    private JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 1));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Add form fields
        mainPanel.add(createFormRow("DNI", dniField));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Apellidos", apellidosField));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Nombres", nombresField));
        mainPanel.add(Box.createVerticalStrut(15));

        // FILA DE FECHA DE NACIMIENTO (Usando JDateChooser)
        mainPanel.add(createDateChooserRow("Fecha Nac.", dateChooserFechaNacimiento));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Dirección", direccionField));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("País", paisField));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Ciudad", ciudadField));
        mainPanel.add(Box.createVerticalStrut(30));

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(guardarButton);
        buttonPanel.add(salirButton);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    // Método genérico para crear filas de JTextField
    private JPanel createFormRow(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 32));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(field);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    // NUEVO: Método específico para la fila de JDateChooser
    private JPanel createDateChooserRow(String labelText, JDateChooser dateChooser) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 32));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));

        // Añade el JDateChooser directamente
        dateChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(dateChooser);

        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private void setupListeners() {
        // Guardar button
        guardarButton.addActionListener(e -> {
            if (validateForm()) {
                String fechaNacimiento = "";
                if (dateChooserFechaNacimiento.getDate() != null) {
                    fechaNacimiento = dateFormat.format(dateChooserFechaNacimiento.getDate());
                }

                String message = String.format(
                        "Propietario REGISTRADO:\nDNI: %s\nNombre: %s %s\nFecha Nac.: %s",
                        dniField.getText(),
                        nombresField.getText(),
                        apellidosField.getText(),
                        fechaNacimiento
                );
                JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }
        });

        // Salir button
        salirButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea salir?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    private boolean validateForm() {
        if (dniField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DNI es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            dniField.requestFocus();
            return false;
        }

        if (apellidosField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            apellidosField.requestFocus();
            return false;
        }

        if (nombresField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los nombres son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            nombresField.requestFocus();
            return false;
        }

        // VALIDACIÓN DE JDateChooser
        if (dateChooserFechaNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La Fecha de Nacimiento es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void clearForm() {
        dniField.setText("");
        apellidosField.setText("");
        nombresField.setText("");
        dateChooserFechaNacimiento.setDate(null); // Limpiar el JDateChooser
        direccionField.setText("");
        paisField.setText("");
        ciudadField.setText("");
        dniField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Configuración de LookAndFeel para una mejor visualización
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new VentanaRegistroPropietario().setVisible(true);
        });
    }
}*/


/*import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaRegistroPropietario extends JFrame {

    // CAMPOS DE TEXTO
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtNombres;

    // JDateChooser
    private JDateChooser dcFechaNacimiento;

    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtPais;
    private JTextField txtCiudad;

    // 🛑 BOTONES RENOMBRADOS
    private JButton btnGuardar;
    private JButton btnSalir;

    // Formato de fecha para mostrar
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentanaRegistroPropietario() {
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        setTitle("Registro de Propietario");
        setSize(700, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inicialización de campos de texto
        txtDni = createNumericTextField(15, 10);
        txtApellidos = createTextField(30);
        txtNombres = createTextField(30);

        // Inicialización de JDateChooser
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dcFechaNacimiento.setPreferredSize(new Dimension(120, 32));

        txtDireccion = createTextField(30);
        txtTelefono = createNumericTextField(20, 15);
        txtEmail = createTextField(30);
        txtPais = createTextField(25);
        txtCiudad = createTextField(25);

        // 🛑 Inicialización de BOTONES RENOMBRADOS
        btnGuardar = createButton("Guardar", new Color(0, 120, 215));
        btnSalir = createButton("Salir", new Color(0, 120, 215));
    }

    // Método para campos de texto estándar
    private JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 1));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        return field;
    }

    // Método para campos numéricos con límite (usado en DNI y Teléfono)
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
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Add form fields
        mainPanel.add(createFormRow("DNI", txtDni));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Apellidos", txtApellidos));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Nombres", txtNombres));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createDateChooserRow("Fecha Nac.", dcFechaNacimiento));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Dirección", txtDireccion));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Teléfono", txtTelefono));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Email", txtEmail));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("País", txtPais));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Ciudad", txtCiudad));
        mainPanel.add(Box.createVerticalStrut(30));

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnGuardar); // 🛑 Botón
        buttonPanel.add(btnSalir);   // 🛑 Botón
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    // Método genérico para crear filas de JTextField
    private JPanel createFormRow(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 32));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));

        // Ajuste de tamaño específico para DNI y Teléfono
        if (labelText.equals("DNI") || labelText.equals("Teléfono")) {
            field.setPreferredSize(new Dimension(150, 32));
            field.setMaximumSize(new Dimension(150, 32));
            panel.add(field);
            panel.add(Box.createHorizontalGlue());
        } else if (labelText.equals("Email")) {
            field.setPreferredSize(new Dimension(250, 32));
            field.setMaximumSize(new Dimension(250, 32));
            panel.add(field);
            panel.add(Box.createHorizontalGlue());
        } else {
            panel.add(field);
            panel.add(Box.createHorizontalGlue());
        }

        return panel;
    }

    // Método específico para la fila de JDateChooser
    private JPanel createDateChooserRow(String labelText, JDateChooser dateChooser) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 32));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));

        dateChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(dateChooser);

        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private void setupListeners() {
        // Guardar button
        btnGuardar.addActionListener(e -> { // 🛑 Uso de btnGuardar
            if (validateForm()) {
                // Lógica MVC: Aquí se debería llamar al controlador para registrar.

                String message = String.format(
                        "Se guardó con éxito el propietario:\n" +
                                "DNI: %s\nNombre: %s %s\nEmail: %s",
                        txtDni.getText(),
                        txtNombres.getText(),
                        txtApellidos.getText(),
                        txtEmail.getText()
                );

                JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }
        });

        // Salir button
        btnSalir.addActionListener(e -> { // 🛑 Uso de btnSalir
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea salir?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    private boolean validateForm() {
        // --- VALIDACIÓN DE OBLIGATORIEDAD DE TODOS LOS CAMPOS ---

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

        // --- VALIDACIÓN DE FORMATO DE EMAIL ---
        if (!isValidEmail(txtEmail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una dirección de Email válida.", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    // Método de validación de Email (Patrón básico)
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private void clearForm() {
        txtDni.setText("");
        txtApellidos.setText("");
        txtNombres.setText("");
        dcFechaNacimiento.setDate(null);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPais.setText("");
        txtCiudad.setText("");
        txtDni.requestFocus();
    }

    // GETTERS PARA EL CONTROLADOR MVC (Renombrados)
    public String getDni() { return txtDni.getText().trim(); }
    public String getApellidos() { return txtApellidos.getText().trim(); }
    public String getNombres() { return txtNombres.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getDireccion() { return txtDireccion.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPais() { return txtPais.getText().trim(); }
    public String getCiudad() { return txtCiudad.getText().trim(); }

    // 🛑 GETTERS DE BOTONES para que el Controlador pueda añadir listeners
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnSalir() { return btnSalir; }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new VentanaRegistroPropietario().setVisible(true);
        });
    }
}*/



import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaRegistroPropietario extends JInternalFrame {

    // CAMPOS DE TEXTO
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtNombres;

    // JDateChooser
    private JDateChooser dcFechaNacimiento;

    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtPais;
    private JTextField txtCiudad;

    // BOTONES
    private JButton btnGuardar;
    private JButton btnSalir;

    // Formato de fecha para mostrar
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentanaRegistroPropietario() {
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        setTitle("Registro de Propietario");
        setSize(700, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);

        // Inicialización de campos de texto
        txtDni = createNumericTextField(15, 10);
        txtApellidos = createTextField(30);
        txtNombres = createTextField(30);

        // 🛑 AJUSTE DE ANCHO DEL JDateChooser
        dcFechaNacimiento = new JDateChooser();
        dcFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        dcFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dcFechaNacimiento.setPreferredSize(new Dimension(120, 32)); // ⬅️ ANCHO REDUCIDO A 120px

        txtDireccion = createTextField(30);
        txtTelefono = createNumericTextField(20, 15);
        txtEmail = createTextField(30);
        txtPais = createTextField(25);
        txtCiudad = createTextField(25);

        // Inicialización de BOTONES
        btnGuardar = createButton("Guardar", new Color(0, 120, 215));
        btnSalir = createButton("Salir", new Color(0, 120, 215));
    }

    // Método para campos de texto estándar
    private JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 1));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        return field;
    }

    // Método para campos numéricos con límite (usado en DNI y Teléfono)
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
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Add form fields
        mainPanel.add(createFormRow("DNI", txtDni));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Apellidos", txtApellidos));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Nombres", txtNombres));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createDateChooserRow("Fecha Nac.", dcFechaNacimiento));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Dirección", txtDireccion));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Teléfono", txtTelefono));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Email", txtEmail));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("País", txtPais));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Ciudad", txtCiudad));
        mainPanel.add(Box.createVerticalStrut(30));

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    // Método genérico para crear filas de JTextField
    private JPanel createFormRow(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 32));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));

        // Ajuste de tamaño específico para DNI y Teléfono
        if (labelText.equals("DNI") || labelText.equals("Teléfono")) {
            field.setPreferredSize(new Dimension(150, 32));
            field.setMaximumSize(new Dimension(150, 32));
            panel.add(field);
            panel.add(Box.createHorizontalGlue());
        } else if (labelText.equals("Email")) {
            field.setPreferredSize(new Dimension(250, 32));
            field.setMaximumSize(new Dimension(250, 32));
            panel.add(field);
            panel.add(Box.createHorizontalGlue());
        } else {
            panel.add(field);
            panel.add(Box.createHorizontalGlue());
        }

        return panel;
    }

    // Método específico para la fila de JDateChooser
    private JPanel createDateChooserRow(String labelText, JDateChooser dateChooser) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 32));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));

        // 🛑 LÍNEA CLAVE: Forzar el tamaño máximo para que BoxLayout no lo estire
        dateChooser.setMaximumSize(new Dimension(120, 32));
        dateChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(dateChooser);

        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private void setupListeners() {
        // Guardar button
        btnGuardar.addActionListener(e -> {
            if (validateForm()) {
                // Lógica MVC: Aquí se debería llamar al controlador para registrar.

                String message = String.format(
                        "Se guardó con éxito el propietario:\n" +
                                "DNI: %s\nNombre: %s %s\nEmail: %s",
                        txtDni.getText(),
                        txtNombres.getText(),
                        txtApellidos.getText(),
                        txtEmail.getText()
                );

                JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }
        });

        // Salir button
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea salir?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    private boolean validateForm() {
        // --- VALIDACIÓN DE OBLIGATORIEDAD DE TODOS LOS CAMPOS ---

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

        // --- VALIDACIÓN DE FORMATO DE EMAIL ---
        if (!isValidEmail(txtEmail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una dirección de Email válida.", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    // Método de validación de Email (Patrón básico)
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private void clearForm() {
        txtDni.setText("");
        txtApellidos.setText("");
        txtNombres.setText("");
        dcFechaNacimiento.setDate(null);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPais.setText("");
        txtCiudad.setText("");
        txtDni.requestFocus();
    }

    // GETTERS PARA EL CONTROLADOR MVC
    public String getDni() { return txtDni.getText().trim(); }
    public String getApellidos() { return txtApellidos.getText().trim(); }
    public String getNombres() { return txtNombres.getText().trim(); }
    public Date getFechaNacimiento() { return dcFechaNacimiento.getDate(); }
    public String getDireccion() { return txtDireccion.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPais() { return txtPais.getText().trim(); }
    public String getCiudad() { return txtCiudad.getText().trim(); }

    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnSalir() { return btnSalir; }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new VentanaRegistroPropietario().setVisible(true);
        });
    }
}