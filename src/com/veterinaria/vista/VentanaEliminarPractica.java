package com.veterinaria.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaEliminarPractica extends JInternalFrame {
    private JComboBox<String> comboTipoPractica;
    private JButton btnEliminar;
    private JButton btnSalir;

    public VentanaEliminarPractica() {
        initComponents();
        setTitle("Eliminar tipo de práctica");
        setSize(700, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Panel del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 30, 0);

        // Tipo de Práctica
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblTipoPractica = new JLabel("Tipo de Práctica");
        lblTipoPractica.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTipoPractica.setForeground(new Color(50, 50, 50));
        formPanel.add(lblTipoPractica, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 30, 0);
        String[] tiposPractica = {
                "Seleccionar tipo de práctica",
                "Cirugía",
                "Vacunación",
                "Desparasitación",
                "Control",
                "Radiografía",
                "Ecografía"
        };
        comboTipoPractica = new JComboBox<>(tiposPractica);
        comboTipoPractica.setPreferredSize(new Dimension(400, 40));
        comboTipoPractica.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        comboTipoPractica.setBackground(Color.WHITE);
        comboTipoPractica.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        formPanel.add(comboTipoPractica, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(new Dimension(150, 45));
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnEliminar.setBackground(new Color(220, 38, 38));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEliminar.setBackground(new Color(185, 28, 28));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEliminar.setBackground(new Color(220, 38, 38));
            }
        });
        btnEliminar.addActionListener(e -> eliminarTipoPractica());
        buttonPanel.add(btnEliminar);

        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(150, 45));
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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
        btnSalir.addActionListener(e -> dispose());
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void eliminarTipoPractica() {
        String tipoSeleccionado = (String) comboTipoPractica.getSelectedItem();

        if (tipoSeleccionado == null || tipoSeleccionado.equals("Seleccionar tipo de práctica")) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un tipo de práctica",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar el tipo de práctica \"" + tipoSeleccionado + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            // Aquí iría la lógica para eliminar de la base de datos
            JOptionPane.showMessageDialog(this,
                    "Tipo de práctica eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            comboTipoPractica.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaEliminarPractica().setVisible(true);
        });
    }
}
