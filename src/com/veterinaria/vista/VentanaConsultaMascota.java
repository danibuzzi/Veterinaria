package com.veterinaria.vista;
/*version funcional fea


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConsultaMascota extends JFrame {
    // Componentes de búsqueda
    private JComboBox<String> propietarioComboBox;
    private JButton buscarButton;
    private JTable table;
    private DefaultTableModel tableModel;

    // Botones de acción centralizados (Se elimina 'Ver Detalle')
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton salirButton;

    public VentanaConsultaMascota() {
        setTitle("Consulta y Gestión de Mascotas");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Panel de Búsqueda (Filtrar por Propietario) ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Combo Box de Propietario
        propietarioComboBox = new JComboBox<>(new String[]{"[Seleccione Propietario]", "Perez, Juan", "Buz, Diego", "Monetti, Emilio"});
        propietarioComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        propietarioComboBox.setPreferredSize(new Dimension(300, 35));

        // Botón Buscar
        buscarButton = new JButton("Buscar");
        buscarButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        buscarButton.setPreferredSize(new Dimension(100, 35));
        buscarButton.setBackground(new Color(37, 99, 235)); // Azul
        buscarButton.setForeground(Color.WHITE);

        searchPanel.add(new JLabel("Filtrar por Propietario:"));
        searchPanel.add(propietarioComboBox);
        searchPanel.add(buscarButton);
        searchPanel.add(Box.createHorizontalGlue());

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Tabla de Resultados ---
        // Columnas ajustadas: ¡Se elimina "Propietario"!
        String[] columnNames = {"", "Nombre", "Fecha Nac.", "Especie", "Raza", "Sexo"};
        Object[][] data = {
                {"", "Toby", "10/20/2020", "Perro", "Mestizo", "Macho"},
                {"", "Pepito", "05/15/2019", "Perro", "Labrador", "Macho"},
                {"", "Michi", "03/10/2021", "Gato", "Siamés", "Hembra"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : super.getColumnClass(columnIndex);
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Ocultar la columna ID si no queremos que se muestre, pero que esté disponible para el Controller
        table.getColumnModel().getColumn(0).setMaxWidth(40);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Panel de Botones de Acción (SIN "Ver Detalle") ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        modificarButton = createActionButton("Modificar", new Color(255, 165, 0)); // Naranja
        eliminarButton = createActionButton("Eliminar", new Color(220, 53, 69)); // Rojo
        salirButton = createActionButton("Salir", new Color(108, 117, 125)); // Gris Oscuro

        actionPanel.add(modificarButton);
        actionPanel.add(eliminarButton);
        actionPanel.add(Box.createHorizontalStrut(50));
        actionPanel.add(salirButton);

        mainPanel.add(actionPanel);

        // --- Lógica de Eventos ---
        eliminarButton.addActionListener(e -> handleEliminarAction());
        modificarButton.addActionListener(e -> handleModificarAction());
        salirButton.addActionListener(e -> dispose());
        buscarButton.addActionListener(e -> handleBuscarAction());

        add(mainPanel);
    }

    // Método para crear botones estandarizados
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    // --- Métodos de Acción (Llamada a Controllers) ---

    private void handleBuscarAction() {
        String propietario = (String) propietarioComboBox.getSelectedItem();
        if (propietario.equals("[Seleccione Propietario]")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            // Aquí iría la llamada al Controller para cargar TODAS las mascotas
        } else {
            // Aquí iría la llamada al Controller: MascotaController.buscarPorPropietario(propietario)
            JOptionPane.showMessageDialog(this, "Buscando mascotas para: " + propietario, "Consulta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleEliminarAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            // Condición de error R2/R4 de la Tabla de Decisión: Fila no seleccionada
            JOptionPane.showMessageDialog(this, "Debe seleccionar una mascota de la lista para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idMascota = (String) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);

        // **Aquí se inicia el flujo de la Tabla de Decisión (en el Controller)**
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar a " + nombre + " (ID: " + idMascota + ")? Esta acción es irreversible.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Lógica simulada de Controller:
            // if (MascotaController.eliminar(idMascota) == true) {
            tableModel.removeRow(selectedRow); // Simulación de éxito
            JOptionPane.showMessageDialog(this, "Mascota " + nombre + " eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // } else { Mostrar error R3: No se puede eliminar por dependencias }
        }
    }

    private void handleModificarAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una mascota de la lista para modificar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idMascota = (String) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);

        // **Aquí se inicia el Caso de Prueba Simple (Navegación)**

        // Simulación: Abre la ventana de Modificación con el ID
        JOptionPane.showMessageDialog(this,
                "Iniciando Modificación para Mascota ID: " + idMascota + " (" + nombre + ").\n" +
                        "El sistema abre la Ficha de Modificación.",
                "Navegación",
                JOptionPane.INFORMATION_MESSAGE);

        // new ModificacionMascotaFrame(idMascota).setVisible(true); // Se llamaría a la siguiente pantalla
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaConsultaMascota frame = new VentanaConsultaMascota();
            frame.setVisible(true);
        });
    }
}

version linda ocn funcionalñ
import com.veterinaria.controlador.ControladorConsultaMascota;
import com.veterinaria.modelo.MascotaTableModel;
import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;





public class VentanaConsultaMascota extends JInternalFrame {

    private JComboBox<Propietario> propietarioComboBox;
    private JButton buscarButton;
    private JTable table;
    private final MascotaTableModel tableModel; // Usamos el TableModel personalizado

    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton salirButton;

    private ControladorConsultaMascota controlador; // Referencia al Controlador

    public VentanaConsultaMascota() {
        setTitle("Consulta y Gestión de Mascotas");
        setSize(950, 650);
        setClosable(true);
        setResizable(false);
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // No aplica en JInternalFrame

        // 1. Inicializar el TableModel
        this.tableModel = new MascotaTableModel();
        this.table = new JTable(tableModel);

        // 2. Ocultar la Columna 0 (ID)
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);
        idColumn.setResizable(false);

        // 3. Diseño de la Ventana (Placeholders para simplificar)

        // Implementación de componentes visuales...
        propietarioComboBox = new JComboBox<>();
        buscarButton = new JButton("Buscar Mascotas");
        modificarButton = new JButton("Modificar");
        eliminarButton = new JButton("Eliminar");
        salirButton = new JButton("Salir");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modificarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(salirButton);

        // 4. Configurar ActionCommands para el Controlador
        buscarButton.setActionCommand("BUSCAR");
        modificarButton.setActionCommand("MODIFICAR");
        eliminarButton.setActionCommand("ELIMINAR");
        salirButton.setActionCommand("SALIR");

        // Inicializar botones de acción desactivados
        setBotonesAccionHabilitados(false);

        // (Lógica para habilitar/deshabilitar los botones Modificar/Eliminar)
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                setBotonesAccionHabilitados(table.getSelectedRow() != -1);
            }
        });

        // 5. Layout (simplificado)
        // ... (Configuración de layout y adición de componentes) ...
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Filtrar por Propietario:"));
        searchPanel.add(propietarioComboBox);
        searchPanel.add(buscarButton);

        this.setLayout(new BorderLayout());
        this.add(searchPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack(); // Ajustar al contenido
    }

    // Métodos públicos para el Controlador

    public void establecerControlador(ControladorConsultaMascota controlador) {
        this.controlador = controlador;
        buscarButton.addActionListener(controlador);
        modificarButton.addActionListener(controlador);
        eliminarButton.addActionListener(controlador);
        salirButton.addActionListener(controlador);
    }

    public MascotaTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JComboBox<Propietario> getPropietarioComboBox() {
        return propietarioComboBox;
    }

    public void cargarPropietarios(ComboBoxModel<Propietario> model) {
        this.propietarioComboBox.setModel(model);
    }

    public void setBotonesAccionHabilitados(boolean enabled) {
        modificarButton.setEnabled(enabled);
        eliminarButton.setEnabled(enabled);
    }

    public ControladorConsultaMascota getControlador() {
        return controlador;
    }

    public void salir() {
        dispose();
    }

    // Métodos de feedback (Controladores)
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION);
    }
}*/





// Importaciones de la vista
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

// Importaciones de la funcionalidad MVC (Usando tu TableModel)
import com.veterinaria.controlador.ControladorConsultaMascota;
import com.veterinaria.modelo.MascotaTableModel;
import com.veterinaria.modelo.Propietario;
import javax.swing.table.TableColumn;


// Hereda de JInternalFrame para funcionar en el escritorio
public class VentanaConsultaMascota extends JInternalFrame {

    // Componentes públicos para el Controlador
    public JComboBox<Propietario> propietarioComboBox;
    public JButton buscarButton;
    public JTable table;
    public JButton modificarButton;
    public JButton eliminarButton;
    public JButton salirButton;

    // Usamos el TableModel personalizado
    private final MascotaTableModel tableModel;

    // Referencia al Controlador
    private ControladorConsultaMascota controlador;

    public VentanaConsultaMascota() {
        setTitle("Consulta y Gestión de Mascotas");

        // --- PROPIEDADES DE JINTERNALFRAME ---
        setSize(950, 650);
        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);


        // --- Panel Principal (Diseño Visual) ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Panel de Búsqueda (Diseño Visual) ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        propietarioComboBox = new JComboBox<>();
        propietarioComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        propietarioComboBox.setPreferredSize(new Dimension(300, 35));

        buscarButton = new JButton("Buscar");
        buscarButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        buscarButton.setPreferredSize(new Dimension(100, 35));
        buscarButton.setBackground(new Color(37, 99, 235));
        buscarButton.setForeground(Color.WHITE);

        searchPanel.add(new JLabel("Filtrar por Propietario:"));
        searchPanel.add(propietarioComboBox);
        searchPanel.add(buscarButton);
        searchPanel.add(Box.createHorizontalGlue());

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        // --- Tabla de Resultados (Funcionalidad y Estilo) ---

        // 1. Inicializar el TableModel
        this.tableModel = new MascotaTableModel();
        this.table = new JTable(tableModel);

        // LÓGICA DE OCULTAMIENTO (Usamos el índice 0 que corresponde al ID)
        if (table.getColumnModel().getColumnCount() > 0) {
            TableColumn idColumn = table.getColumnModel().getColumn(0);
            idColumn.setMinWidth(0);
            idColumn.setMaxWidth(0);
            idColumn.setPreferredWidth(0);
            idColumn.setResizable(false);
        }

        // 3. Aplicar Estilos a la Tabla
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        // CRÍTICO: Forzar tamaño del JScrollPane
        Dimension fixedTableSize = new Dimension(890, 400);
        scrollPane.setPreferredSize(fixedTableSize);
        scrollPane.setMaximumSize(fixedTableSize);

        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        // --- Panel de Botones de Acción (Diseño Visual) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        modificarButton = createActionButton("Modificar", new Color(255, 165, 0));
        eliminarButton = createActionButton("Eliminar", new Color(220, 53, 69));
        salirButton = createActionButton("Salir", new Color(108, 117, 125));

        actionPanel.add(modificarButton);
        actionPanel.add(eliminarButton);
        actionPanel.add(Box.createHorizontalStrut(50));
        actionPanel.add(salirButton);

        mainPanel.add(actionPanel);

        // Lógica de habilitar/deshabilitar botones
        setBotonesAccionHabilitados(false);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                setBotonesAccionHabilitados(table.getSelectedRow() != -1);
            }
        });

        // --- Configuración de Comandos y Listeners ---

        buscarButton.setActionCommand("BUSCAR");
        modificarButton.setActionCommand("MODIFICAR");
        eliminarButton.setActionCommand("ELIMINAR");
        salirButton.setActionCommand("SALIR");

        salirButton.addActionListener(e -> dispose());

        add(mainPanel);
    }

    // --- Métodos de Ayuda (Diseño Visual) ---

    // Método para crear botones estandarizados
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR (Funcionalidad) ---

    // Método para inyectar el controlador y enlazar los ActionListeners
    public void establecerControlador(ControladorConsultaMascota controlador) {
        this.controlador = controlador;
        buscarButton.addActionListener(controlador);
        modificarButton.addActionListener(controlador);
        eliminarButton.addActionListener(controlador);
        salirButton.addActionListener(controlador);
    }

    public MascotaTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JComboBox<Propietario> getPropietarioComboBox() {
        return propietarioComboBox;
    }

    // El controlador usará este método para llenar el JComboBox
    public void cargarPropietarios(ComboBoxModel<Propietario> model) {
        this.propietarioComboBox.setModel(model);
    }

    public void setBotonesAccionHabilitados(boolean enabled) {
        modificarButton.setEnabled(enabled);
        eliminarButton.setEnabled(enabled);
    }

    public ControladorConsultaMascota getControlador() {
        return controlador;
    }

    public void salir() {
        dispose();
    }

    // Métodos de feedback (Controladores)
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION);
    }
}