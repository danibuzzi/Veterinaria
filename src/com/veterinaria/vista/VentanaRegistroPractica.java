package com.veterinaria.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaRegistroPractica extends JInternalFrame {
    private JTextField txtDescripcion;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroPractica() {
        initComponents();
        setTitle("Registro de tipo de práctica");
        setSize(700, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 20);

        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        formPanel.add(lblDescripcion, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDescripcion = new JTextField(40);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDescripcion.setPreferredSize(new Dimension(500, 40));
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(txtDescripcion, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        btnGuardar = createButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        buttonPanel.add(btnGuardar);

        btnSalir = createButton("Salir");
        btnSalir.addActionListener(e -> salir());
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setBackground(new Color(37, 99, 235));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(29, 78, 216));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(37, 99, 235));
            }
        });

        return button;
    }

    private void guardar() {
        String descripcion = txtDescripcion.getText().trim();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese una descripción",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar el tipo de práctica?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Aquí iría la lógica para guardar en la base de datos
            JOptionPane.showMessageDialog(this,
                    "Tipo de práctica guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VentanaRegistroPractica().setVisible(true);
        });
    }
}

