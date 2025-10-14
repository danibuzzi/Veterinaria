import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistroConsultaFrame extends JFrame {
    // --- 1. DECLARACIÓN DE COMPONENTES ---
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JComboBox<String> comboTipoPractica;
    private JTextArea txtResultadoEstudios;
    private JTextArea txtDiagnostico;
    private JTextArea txtPronostico;
    private JTextArea txtTratamiento;
    private JButton btnGuardar;
    private JButton btnSalir;

    // --- 2. CONSTRUCTOR ---
    public RegistroConsultaFrame() {
        setTitle("Registro de Consulta");
        // Tamaño reducido para forzar compacidad
        setSize(850, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        // Panel de Formulario: Usa GridBagLayout con espaciado mínimo
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // REDUCCIÓN CRÍTICA: INSETS PEQUEÑOS PARA MINIMIZAR ESPACIO VERTICAL
        gbc.insets = new Insets(5, 10, 5, 10);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // --- 3. CONSTRUCCIÓN DE COMPONENTES DE ENTRADA ---

        // Fila 0: Propietario
        gbc.gridy = 0;
        agregarCampoCombo(formPanel, gbc, "Propietario",
                comboPropietario = crearComboPropietario(fieldFont), labelFont);

        // Fila 1: Mascota
        gbc.gridy = 1;
        agregarCampoCombo(formPanel, gbc, "Mascota",
                comboMascota = crearComboMascota(fieldFont), labelFont);

        // Fila 2: Tipo de Práctica
        gbc.gridy = 2;
        agregarCampoCombo(formPanel, gbc, "Tipo de Práctica",
                comboTipoPractica = crearComboPractica(fieldFont), labelFont);

        // Fila 3: Resultado de Estudios (Altura ajustada)
        gbc.gridy = 3;
        agregarAreaTexto(formPanel, gbc, "Resultado de Estudios",
                txtResultadoEstudios = crearAreaTexto(fieldFont), 120); // Altura reducida

        // Fila 4: Diagnóstico (Altura ajustada)
        gbc.gridy = 4;
        agregarAreaTexto(formPanel, gbc, "Diagnóstico",
                txtDiagnostico = crearAreaTexto(fieldFont), 60); // Altura reducida

        // Fila 5: Pronóstico (Altura ajustada)
        gbc.gridy = 5;
        agregarAreaTexto(formPanel, gbc, "Pronóstico",
                txtPronostico = crearAreaTexto(fieldFont), 60); // Altura reducida

        // Fila 6: Tratamiento (Altura ajustada)
        gbc.gridy = 6;
        agregarAreaTexto(formPanel, gbc, "Tratamiento",
                txtTratamiento = crearAreaTexto(fieldFont), 80); // Altura reducida

        // Añadir el panel del formulario (en el centro del mainPanel)
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- 4. PANEL DE BOTONES ---
        JPanel buttonPanel = crearPanelBotones();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- 5. MÉTODOS AUXILIARES DE CONSTRUCCIÓN Y LAYOUT ---

    private JComboBox<String> crearComboPropietario(Font font) {
        String[] propietarios = {"Seleccionar propietario", "Perez,Ana Maria", "Monetti,Emilio", "Cerruti,Lucia Malena"};
        JComboBox<String> combo = new JComboBox<>(propietarios);
        combo.setFont(font);
        combo.setPreferredSize(new Dimension(500, 35)); // Altura reducida
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JComboBox<String> crearComboMascota(Font font) {
        String[] mascotas = {"Seleccionar mascota", "Toby", "Pepito", "Max", "Luna"};
        JComboBox<String> combo = new JComboBox<>(mascotas);
        combo.setFont(font);
        combo.setPreferredSize(new Dimension(500, 35)); // Altura reducida
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JComboBox<String> crearComboPractica(Font font) {
        String[] tiposPractica = {"Seleccionar tipo de práctica", "Cirugía", "Vacunación", "Control General", "Análisis de Sangre", "Radiografía"};
        JComboBox<String> combo = new JComboBox<>(tiposPractica);
        combo.setFont(font);
        combo.setPreferredSize(new Dimension(500, 35)); // Altura reducida
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JTextArea crearAreaTexto(Font font) {
        JTextArea area = new JTextArea();
        area.setFont(font);
        area.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    // Método para agregar ComboBoxes (campos cortos)
    private void agregarCampoCombo(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(component, gbc);
    }

    // Método para agregar Áreas de Texto (campos largos)
    private void agregarAreaTexto(JPanel panel, GridBagConstraints gbc, String labelText, JTextArea area, int height) {
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        // La etiqueta se ancla arriba a la derecha para JTextAreas
        gbc.anchor = GridBagConstraints.NORTHEAST;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, height));
        panel.add(scroll, gbc);
    }

    private JButton crearBoton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 45)); // Altura reducida
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 86, 179));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
        button.addActionListener(listener);
        return button;
    }

    private JPanel crearPanelBotones() {
        // Espaciado vertical del panel de botones reducido
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", e -> guardarConsulta());
        btnSalir = crearBoton("Salir", e -> salir());

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    // --- 6. LÓGICA DE EVENTOS ---

    private void guardarConsulta() {
        if (comboPropietario.getSelectedIndex() == 0 ||
                comboMascota.getSelectedIndex() == 0 ||
                comboTipoPractica.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete los campos obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea guardar la consulta?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Consulta registrada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        comboPropietario.setSelectedIndex(0);
        comboMascota.setSelectedIndex(0);
        comboTipoPractica.setSelectedIndex(0);
        txtResultadoEstudios.setText("");
        txtDiagnostico.setText("");
        txtPronostico.setText("");
        txtTratamiento.setText("");
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

    // --- 7. MÉTODO MAIN ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistroConsultaFrame frame = new RegistroConsultaFrame();
            frame.setVisible(true);
        });
    }
}