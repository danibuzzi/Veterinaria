

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EliminarTipoConsultaFrame extends JFrame {
    private JComboBox<String> tipoConsultaCombo;
    private JButton eliminarButton;
    private JButton salirButton;

    public EliminarTipoConsultaFrame() {
        setTitle("Eliminar tipo de consulta");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 30, 0);

        // Tipo de Consulta
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel tipoConsultaLabel = new JLabel("Tipo de Consulta");
        tipoConsultaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tipoConsultaLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(tipoConsultaLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 30, 0);
        String[] tiposConsulta = {
                "Seleccionar tipo de consulta",
                "Cirugía",
                "Preventiva",
                "Emergencia",
                "Vacunación",
                "Control"
        };
        tipoConsultaCombo = new JComboBox<>(tiposConsulta);
        tipoConsultaCombo.setPreferredSize(new Dimension(350, 40));
        tipoConsultaCombo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tipoConsultaCombo.setBackground(Color.WHITE);
        tipoConsultaCombo.setBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)));
        formPanel.add(tipoConsultaCombo, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Eliminar button
        eliminarButton = new JButton("Eliminar");
        eliminarButton.setPreferredSize(new Dimension(150, 45));
        eliminarButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setBackground(new Color(220, 38, 38));
        eliminarButton.setBorderPainted(false);
        eliminarButton.setFocusPainted(false);
        eliminarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        eliminarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                eliminarButton.setBackground(new Color(185, 28, 28));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                eliminarButton.setBackground(new Color(220, 38, 38));
            }
        });

        eliminarButton.addActionListener(e -> eliminarTipoConsulta());
        buttonPanel.add(eliminarButton);

        // Salir button
        salirButton = new JButton("Salir");
        salirButton.setPreferredSize(new Dimension(150, 45));
        salirButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        salirButton.setForeground(Color.WHITE);
        salirButton.setBackground(new Color(37, 99, 235));
        salirButton.setBorderPainted(false);
        salirButton.setFocusPainted(false);
        salirButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        salirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                salirButton.setBackground(new Color(29, 78, 216));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                salirButton.setBackground(new Color(37, 99, 235));
            }
        });

        salirButton.addActionListener(e -> dispose());
        buttonPanel.add(salirButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void eliminarTipoConsulta() {
        if (tipoConsultaCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor seleccione un tipo de consulta para eliminar.",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String tipoSeleccionado = (String) tipoConsultaCombo.getSelectedItem();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar el tipo de consulta '" + tipoSeleccionado + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Aquí iría la lógica para eliminar el tipo de consulta de la base de datos
            JOptionPane.showMessageDialog(
                    this,
                    "Tipo de consulta eliminado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tipoConsultaCombo.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EliminarTipoConsultaFrame frame = new EliminarTipoConsultaFrame();
            frame.setVisible(true);
        });
    }
}

