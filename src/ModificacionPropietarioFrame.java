


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModificacionPropietarioFrame extends JFrame {
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtNombres;
    private JTextField txtDireccion;
    private JTextField txtPais;
    private JTextField txtCiudad;
    private JButton btnGuardar;
    private JButton btnSalir;

    public ModificacionPropietarioFrame() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Modificación de Propietario");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDni = new JLabel("DNI");
        lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(lblDni, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.WEST;
        txtDni = new JTextField(15);
        txtDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDni.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        mainPanel.add(txtDni, gbc);

        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblApellidos = new JLabel("Apellidos");
        lblApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(lblApellidos, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtApellidos = new JTextField();
        txtApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellidos.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        mainPanel.add(txtApellidos, gbc);

        // Nombres
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblNombres = new JLabel("Nombres");
        lblNombres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(lblNombres, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtNombres = new JTextField();
        txtNombres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombres.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        mainPanel.add(txtNombres, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDireccion = new JLabel("Dirección");
        lblDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(lblDireccion, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        txtDireccion = new JTextField();
        txtDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDireccion.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        mainPanel.add(txtDireccion, gbc);

        // País
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPais = new JLabel("País");
        lblPais.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(lblPais, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        txtPais = new JTextField(20);
        txtPais.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPais.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        mainPanel.add(txtPais, gbc);

        // Ciudad
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblCiudad = new JLabel("Ciudad");
        lblCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(lblCiudad, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtCiudad = new JTextField(22);
        txtCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCiudad.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        mainPanel.add(txtCiudad, gbc);

        // Panel de botones
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 5, 8, 5);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
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
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void cargarDatos() {
        txtDni.setText("2569708");
        txtApellidos.setText("Cerruti");
        txtNombres.setText("Lucia Malena");
        txtDireccion.setText("Los Tilos 500");
        txtPais.setText("Argentina");
        txtCiudad.setText("Mar del Palta");
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
                JOptionPane.showMessageDialog(
                        this,
                        "Propietario modificado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
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
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModificacionPropietarioFrame().setVisible(true);
        });
    }
}
