

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistroPropietarioFrame extends JFrame {

    // Form fields
    private JTextField dniField;
    private JTextField apellidosField;
    private JTextField nombresField;
    private JTextField direccionField;
    private JTextField paisField;
    private JTextField ciudadField;

    // Buttons
    private JButton registrarMascotaButton;
    private JButton guardarButton;
    private JButton salirButton;

    public RegistroPropietarioFrame() {
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        setTitle("Registro de Propietario");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize text fields
        dniField = createTextField(20);
        apellidosField = createTextField(30);
        nombresField = createTextField(30);
        direccionField = createTextField(30);
        paisField = createTextField(25);
        ciudadField = createTextField(25);

        // Initialize buttons
        registrarMascotaButton = createButton("Registrar Mascota", new Color(100, 100, 100));
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

        mainPanel.add(createFormRow("Dirección", direccionField));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("País", paisField));
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createFormRow("Ciudad", ciudadField));
        mainPanel.add(Box.createVerticalStrut(20));

        // Registrar Mascota button
        JPanel mascotaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        mascotaPanel.setBackground(Color.WHITE);
        mascotaPanel.add(Box.createHorizontalStrut(120));
        mascotaPanel.add(registrarMascotaButton);
        mascotaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainPanel.add(mascotaPanel);

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

    private void setupListeners() {
        // Guardar button
        guardarButton.addActionListener(e -> {
            if (validateForm()) {
                String message = String.format(
                        "Propietario guardado:\nDNI: %s\nNombre: %s %s\nDirección: %s\nPaís: %s\nCiudad: %s",
                        dniField.getText(),
                        nombresField.getText(),
                        apellidosField.getText(),
                        direccionField.getText(),
                        paisField.getText(),
                        ciudadField.getText()
                );
                JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }
        });

        // Registrar Mascota button
        registrarMascotaButton.addActionListener(e -> {
            if (dniField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar el DNI del propietario primero",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Abriendo ventana de Registro de Mascota...",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                // Aquí se abriría la ventana de registro de mascota
                // new RegistroMascotaFrame(dniField.getText()).setVisible(true);
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

        return true;
    }

    private void clearForm() {
        dniField.setText("");
        apellidosField.setText("");
        nombresField.setText("");
        direccionField.setText("");
        paisField.setText("");
        ciudadField.setText("");
        dniField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new RegistroPropietarioFrame().setVisible(true);
        });
    }
}
