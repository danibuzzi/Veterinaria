package com.veterinaria.vista;



import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;

public class VentanaTurnosPropietario extends JFrame {
    private JComboBox<String> propietarioCombo;
    private JDateChooser dateChooserFechaDesde;
    private JTable turnosTable;
    private DefaultTableModel tableModel;
    private JButton buscarButton;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentanaTurnosPropietario() {
        setTitle("Consulta de Turnos por Propietario");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con filtros (GridBagLayout)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- 1. Propietario ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel propietarioLabel = new JLabel("Propietario");
        propietarioLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        topPanel.add(propietarioLabel, gbc);

        gbc.gridx = 1;
        String[] propietarios = {"Monetti,Emilio", "Perez,Juan", "Buz,Diego"};
        propietarioCombo = new JComboBox<>(propietarios);
        propietarioCombo.setPreferredSize(new Dimension(250, 35));
        propietarioCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        propietarioCombo.setBackground(Color.WHITE);
        propietarioCombo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        topPanel.add(propietarioCombo, gbc);

        // --- 2. Fecha Desde (JDateChooser) ---
        gbc.gridx = 2;
        JLabel fechaLabel = new JLabel("Fecha Desde");
        fechaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        topPanel.add(fechaLabel, gbc);

        gbc.gridx = 3;
        dateChooserFechaDesde = new JDateChooser();
        dateChooserFechaDesde.setDateFormatString("dd/MM/yyyy");
        dateChooserFechaDesde.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Ajuste de tamaño robusto para el JDateChooser
        dateChooserFechaDesde.setPreferredSize(new Dimension(160, 35));
        JTextField dateTextField = (JTextField) dateChooserFechaDesde.getDateEditor().getUiComponent();
        dateTextField.setPreferredSize(new Dimension(120, 35));
        dateTextField.setMinimumSize(new Dimension(120, 35));
        dateTextField.setHorizontalAlignment(JTextField.LEFT);
        dateChooserFechaDesde.setDate(new Date());

        topPanel.add(dateChooserFechaDesde, gbc);

        // --- 3. Botón Buscar ---
        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 15, 5);
        buscarButton = new JButton("Buscar");
        buscarButton.setPreferredSize(new Dimension(100, 35));
        buscarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buscarButton.setBackground(new Color(37, 99, 235));
        buscarButton.setForeground(Color.WHITE);
        buscarButton.setFocusPainted(false);
        buscarButton.setBorderPainted(false);
        buscarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarButton.addActionListener(e -> buscarTurnos());
        topPanel.add(buscarButton, gbc);

        // Panel de tabla
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        JLabel turnosLabel = new JLabel("Turnos");
        turnosLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        turnosLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Crear tabla (Columnas: Fecha, Hora, Tipo Consulta, Mascota)
        String[] columnNames = {"Fecha", "Hora", "Tipo Consulta", "Mascota"};
        Object[][] data = {
                {"07/10/2025", "10:00 a.m.", "Cirugia", "Toby"},
                {"07/10/2025", "12:00 p.m.", "Preventiva", "Pepito"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        turnosTable = new JTable(tableModel);
        turnosTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        turnosTable.setRowHeight(35);

        // Configuración de las líneas de la cuadrícula
        turnosTable.setGridColor(Color.BLACK);
        turnosTable.setShowGrid(true);
        turnosTable.setShowVerticalLines(true);
        turnosTable.setShowHorizontalLines(true);
        turnosTable.setIntercellSpacing(new Dimension(1, 1));

        // ** MODIFICACIÓN CLAVE PARA FORZAR EL BORDE DE CADA COLUMNA EN LA CABECERA **
        turnosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        turnosTable.getTableHeader().setBackground(Color.WHITE);

        // 1. Quitar el borde que engloba toda la cabecera (que oculta las líneas internas)
        turnosTable.getTableHeader().setBorder(null);

        // 2. Usar un Renderer para forzar los bordes de cada celda de la cabecera
        turnosTable.getTableHeader().setDefaultRenderer(new HeaderRenderer(turnosTable.getTableHeader().getDefaultRenderer()));

        // Configurar anchos de columnas
        turnosTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        turnosTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        turnosTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        turnosTable.getColumnModel().getColumn(3).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(turnosTable);
        // El borde del scrollpane garantiza que la tabla esté rodeada
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tablePanel.add(turnosLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón Salir
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton salirButton = new JButton("Salir");
        salirButton.setPreferredSize(new Dimension(150, 45));
        salirButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        salirButton.setBackground(new Color(37, 99, 235));
        salirButton.setForeground(Color.WHITE);
        salirButton.setFocusPainted(false);
        salirButton.setBorderPainted(false);
        salirButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salirButton.addActionListener(e -> dispose());

        bottomPanel.add(salirButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void buscarTurnos() {
        String propietario = propietarioCombo.getSelectedItem().toString();
        Date fechaDesde = dateChooserFechaDesde.getDate();
        String fechaTexto = (fechaDesde != null) ? dateFormat.format(fechaDesde) : "N/A";

        // Lógica de búsqueda simulada
        tableModel.setRowCount(0);

        if (propietario.equals("Monetti,Emilio")) {
            tableModel.addRow(new Object[]{"07/10/2025", "10:00 a.m.", "Cirugia", "Toby"});
            tableModel.addRow(new Object[]{"08/10/2025", "12:00 p.m.", "Preventiva", "Pepito"});
        } else if (propietario.equals("Buz,Diego")) {
            tableModel.addRow(new Object[]{"15/10/2025", "09:30 a.m.", "Vacunación", "Michi"});
        }

        JOptionPane.showMessageDialog(this,
                "Buscando turnos para Propietario: " + propietario + " desde Fecha: " + fechaTexto,
                "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
    }

    // Nueva clase Renderer para forzar los bordes de celda en la cabecera
    private static class HeaderRenderer implements TableCellRenderer {
        private final TableCellRenderer defaultRenderer;

        public HeaderRenderer(TableCellRenderer defaultRenderer) {
            this.defaultRenderer = defaultRenderer;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                // Centrar el texto en la cabecera
                label.setHorizontalAlignment(SwingConstants.CENTER);

                // Definir un borde para cada celda de la cabecera
                // Borde: (Top, Izquierda, Abajo, Derecha)
                Border blackBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
                label.setBorder(blackBorder);
                label.setBackground(Color.WHITE); // Mantener el fondo blanco
            }
            return c;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            VentanaTurnosPropietario frame = new VentanaTurnosPropietario();
            frame.setVisible(true);
        });
    }
}