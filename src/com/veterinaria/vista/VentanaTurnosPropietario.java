package com.veterinaria.vista;



import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import com.veterinaria.controlador.ControladorConsultaTurnosPropietario;
import com.veterinaria.modelo.*;

import java.util.Date;
import java.text.SimpleDateFormat;

public class VentanaTurnosPropietario extends JInternalFrame {
    private JComboBox<String> propietarioCombo;
    private JDateChooser dateChooserFechaDesde;
    private JTable turnosTable;

    // Usamos el modelo de tabla que acepta Object[]
    private TurnoTableModelConsulta tableModel;

    private JButton buscarButton;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentanaTurnosPropietario() {
        setTitle("Consulta de Turnos por Propietario");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);

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
        //String[] propietarios = {"Monetti,Emilio", "Perez,Juan", "Buz,Diego"};
        propietarioCombo = new JComboBox<>();
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
        /*String[] columnNames = {"Fecha", "Hora", "Tipo Consulta", "Mascota"};
        Object[][] data = {
                {"07/10/2025", "10:00 a.m.", "Cirugia", "Toby"},
                {"07/10/2025", "12:00 p.m.", "Preventiva", "Pepito"}
        };

       / tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };*/

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
        pack();
        // Inicializa el combo con el texto por defecto
        propietarioCombo.addItem("0;-- Seleccionar Propietario --");
    }



    private void buscarTurnos() {
        String propietario = propietarioCombo.getSelectedItem().toString();
        Date fechaDesde = dateChooserFechaDesde.getDate();
        String fechaTexto = (fechaDesde != null) ? dateFormat.format(fechaDesde) : "N/A";



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

    //=========================================================
    // GETTERS PARA EL CONTROLADOR
    // =========================================================

    public JComboBox<String> getPropietarioCombo() {
        return propietarioCombo;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public TurnoTableModelConsulta getTableModel() {
        return tableModel;
    }

    public JTable getTurnosTable() {
        return turnosTable;
    }

    /**
     * Devuelve el ID del propietario seleccionado desde la cadena "ID;Nombre Apellido".
     * @return El ID numérico. 0 si no hay selección válida.
     */
    public int getIdPropietarioSeleccionado() {
        String seleccionado = (String) propietarioCombo.getSelectedItem();
        // El formato es "ID;Nombre Apellido"
        if (seleccionado != null && seleccionado.contains(";")) {
            try {
                // Obtenemos el ID (parte antes del ';')
                return Integer.parseInt(seleccionado.split(";")[0]);
            } catch (NumberFormatException e) {
                // Ocurre si se selecciona la opción por defecto.
                return 0;
            }
        }
        return 0;
    }

    // Método para mostrar errores al usuario
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Consulta", JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================
    // RENDERS Y UTILIDADES
    // =========================================================

    // Renderizador para centrar texto en las celdas
    static class CustomCellRenderer extends DefaultTableCellRenderer {
        public CustomCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Estilo visual
            Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
            ((JComponent) cell).setBorder(BorderFactory.createCompoundBorder(
                    lineBorder,
                    BorderFactory.createEmptyBorder(0, 5, 0, 5)));

            return cell;
        }


    }


    /**
     * Muestra un mensaje de información genérico.
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public JDateChooser getDateChooserFechaDesde() {
        return dateChooserFechaDesde;
    }


    // Renderizador para las cabeceras de la tabla
    /*static class HeaderRenderer implements TableCellRenderer {
        TableCellRenderer defaultRenderer;

        public HeaderRenderer(TableCellRenderer defaultRenderer) {
            this.defaultRenderer = defaultRenderer;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setHorizontalAlignment(SwingConstants.CENTER);

                Border blackBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
                label.setBorder(blackBorder);
                label.setBackground(new Color(230, 230, 230));
            }
            return c;
        }
    }*/

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            VentanaTurnosPropietario frame = new VentanaTurnosPropietario();
            frame.setVisible(true);
        });
    }*/


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 1. Crear el Frame Contenedor (para la prueba)
            JFrame testFrame = new JFrame("Prueba Aislada de Ventana de Turnos");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(850, 550);
            testFrame.setLayout(new BorderLayout());

            // 2. Crear las dependencias de servicio
            // ATENCIÓN: Se asume que PropietarioService y MascotaService tienen
            // constructores sin argumentos para poder instanciarlos aquí.
            PropietarioDAO propietarioService = new PropietarioDAO();
            TurnoDAO3 mascotaService = new TurnoDAO3();

            // 3. Crear el servicio principal, inyectando sus dependencias
            TurnoPropietarioService service = new TurnoPropietarioService(propietarioService, mascotaService);

            // 4. Crear la Vista e inicializar el Controlador
            VentanaTurnosPropietario vista = new VentanaTurnosPropietario();

            // 5. Crear el Controlador
            // El JDesktopPane se pasa como null, ya que no se necesita en esta prueba.
            new ControladorConsultaTurnosPropietario(
                    vista,
                    service,
                    null
            );

            // 6. Agregar y Mostrar
            testFrame.add(vista, BorderLayout.CENTER);
            vista.setVisible(true); // Mostrar la JInternalFrame
            testFrame.setLocationRelativeTo(null); // Centrar
            testFrame.setVisible(true); // Mostrar el JFrame principal
        });
    }
}