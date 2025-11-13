package com.veterinaria.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaRegistroTipoConsulta extends JInternalFrame {
    private JTextField txtDescripcion;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroTipoConsulta() {
        initComponents();
        setTitle("Registro de tipo de consulta");
        setSize(700, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Fuente para labels y campos
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Label y campo Descripción
        JLabel lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setFont(labelFont);
        lblDescripcion.setBounds(50, 50, 120, 40);
        lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
        mainPanel.add(lblDescripcion);

        txtDescripcion = new JTextField("Ingrese descripción");
        txtDescripcion.setFont(fieldFont);
        txtDescripcion.setForeground(Color.GRAY);
        txtDescripcion.setBounds(190, 50, 450, 40);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));

        // Placeholder behavior
        txtDescripcion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDescripcion.getText().equals("Ingrese descripción")) {
                    txtDescripcion.setText("");
                    txtDescripcion.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDescripcion.getText().isEmpty()) {
                    txtDescripcion.setText("Ingrese descripción");
                    txtDescripcion.setForeground(Color.GRAY);
                }
            }
        });
        mainPanel.add(txtDescripcion);

        // Botón Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGuardar.setBounds(180, 140, 150, 45);
        btnGuardar.setBackground(new Color(37, 99, 235));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(29, 78, 216));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(new Color(37, 99, 235));
            }
        });

        btnGuardar.addActionListener(e -> guardarTipoConsulta());
        mainPanel.add(btnGuardar);

        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSalir.setBounds(370, 140, 150, 45);
        btnSalir.setBackground(new Color(37, 99, 235));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSalir.setBackground(new Color(29, 78, 216));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSalir.setBackground(new Color(37, 99, 235));
            }
        });

        btnSalir.addActionListener(e -> salir());
        mainPanel.add(btnSalir);

        add(mainPanel);
    }

    private void guardarTipoConsulta() {
        String descripcion = txtDescripcion.getText().trim();

        if (descripcion.isEmpty() || descripcion.equals("Ingrese descripción")) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese la descripción",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar el tipo de consulta?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Tipo de consulta guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campo
            txtDescripcion.setText("Ingrese descripción");
            txtDescripcion.setForeground(Color.GRAY);
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea salir sin guardar?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaRegistroTipoConsulta().setVisible(true);
        });
    }
}
