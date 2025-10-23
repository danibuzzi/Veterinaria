package com.veterinaria.vista;

import javax.swing.*;
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
}