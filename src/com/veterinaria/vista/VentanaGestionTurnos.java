package com.veterinaria.vista;


import com.veterinaria.controlador.ControladorGestionTurnos;
import com.veterinaria.modelo.Turno;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

//AHORA UTILIZA LOS ESTILOS DEFINIDOS EN LA GUÍA DE REGISTRO DE TURNOS
public class    VentanaGestionTurnos extends JInternalFrame {
    private com.toedter.calendar.JDateChooser dateChooserFecha;
    // --- CONSTANTES DE ESTILO (Tomadas de VentanaRegistroTurno3) ---
    private static final Color COLOR_PRIMARIO = new Color(0, 123, 255); // Azul moderno (Modificar)
    private static final Color COLOR_DESTRUCTIVO = new Color(220, 53, 69); // Rojo (Eliminar)
    private static final Color COLOR_SECUNDARIO = new Color(108, 117, 125); // Gris neutro (Salir)
    private static final Color COLOR_FONDO_CLARO = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(173, 216, 230); // Azul claro para bordes
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    private final JDesktopPane escritorioContenedor; // <--- Nuevo campo
    // Componentes de la interfaz
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnSalir;

    // 1. CONSTRUCTOR
    public VentanaGestionTurnos(JDesktopPane escritorioContenedor) {
        super("Agenda: Consulta y Gestión de Turnos Asignados", true, true, true, true);
        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);
        this.escritorioContenedor = escritorioContenedor;

        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);

        // Layout principal con espaciado consistente
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        getContentPane().setBackground(COLOR_FONDO_CLARO);

        // --- 1. Panel de Búsqueda (Norte) ---
        add(crearPanelBusqueda(), BorderLayout.NORTH);

        // --- 2. Panel Central (Tabla de Resultados) ---
        JScrollPane scrollPane = crearPanelTabla();
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. Panel Inferior (Sur) ---
        add(crearPanelAcciones(), BorderLayout.SOUTH);

        //  4. DESHABILITAR BOTONES INICIALMENTE (Mover lógica de inicializarAcciones)
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

        // 4. Inicializar botones deshabilitados y agregar oyente de selección
        //inicializarAcciones();
    }

    // CONSTRUCTOR ADICIONAL (para compatibilidad sin argumentos)
    public VentanaGestionTurnos() {
        this(null); // Llama al constructor principal pasándole 'null'
    }

    // -------------------------------------------------------------------------
    // --- MÉTODOS AUXILIARES DE CONSTRUCCIÓN (Diseño Unificado) ---
    // -------------------------------------------------------------------------

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(COLOR_FONDO_CLARO);

        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 2),
                "Seleccionar Día y Buscar Agenda",
                SwingConstants.LEFT,
                SwingConstants.TOP,
                FONT_TITLE, // Usando la fuente de título
                COLOR_PRIMARIO
        ));

        // Placeholder del Calendario o campo de fecha
        JLabel lblFecha = new JLabel("Fecha Seleccionada:");
        lblFecha.setFont(FONT_FIELD);

        // Inicialización del JDateChooser fecha

        dateChooserFecha = new com.toedter.calendar.JDateChooser();
        dateChooserFecha.setDateFormatString("dd/MM/yyyy"); // Formato visible
        dateChooserFecha.setPreferredSize(new Dimension(150, 35));
        dateChooserFecha.setFont(FONT_FIELD);


        btnBuscar = crearBotonPrincipal("Buscar Agenda");
        btnBuscar.setActionCommand("BUSCAR");


        panel.add(lblFecha);
        panel.add(dateChooserFecha);
        panel.add(btnBuscar);

        return panel;
    }

    //Oculta columna indicada de la tabla

    public void ocultarColumna(JTable tabla, int columnIndex) {
        if (tabla.getColumnModel().getColumnCount() > columnIndex) {
            // Obtiene la columna a ocultar
            TableColumn columna = tabla.getColumnModel().getColumn(columnIndex);

            // Remueve visualmente la columna del TableColumnModel.
            // Los datos siguen estando disponibles en el TableModel.
            tabla.getColumnModel().removeColumn(columna);
        }
    }

    private JScrollPane crearPanelTabla() {
        String[] columnas = {"ID", "Hora", "Tipo Consulta", "Propietario", "Mascota"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTurnos = new JTable(modeloTabla);
        ocultarColumna(tablaTurnos,0);
        tablaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTurnos.setRowHeight(25);
        tablaTurnos.setFont(FONT_FIELD); // Fuente para las celdas
        tablaTurnos.getTableHeader().setFont(FONT_TITLE); // Fuente para el encabezado

        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        scrollPane.setBackground(COLOR_FONDO_CLARO);

        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 2),
                "Resultados de la Búsqueda",
                SwingConstants.LEFT,
                SwingConstants.TOP,
                FONT_TITLE,
                COLOR_PRIMARIO
        ));

        return scrollPane;
    }

    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panel.setBackground(COLOR_FONDO_CLARO);

        // Modificar (Botón Principal: Azul)
        btnModificar = crearBotonPrincipal("Modificar Turno");
        btnModificar.setActionCommand("MODIFICAR");

        // Eliminar (Botón Destructivo: Rojo)
        btnEliminar = crearBotonDestructivo("Eliminar Turno");
        btnEliminar.setActionCommand("ELIMINAR");

        // Salir (Botón Secundario: Gris)
        btnSalir = crearBotonSecundario("Salir");
        btnSalir.setActionCommand("SALIR");

        // Agregamos los botones
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(Box.createHorizontalStrut(30));
        panel.add(btnSalir);

        return panel;
    }


    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

   /* private void inicializarAcciones() {
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

        // Oyente de selección de fila (para habilitar/deshabilitar botones)
        tablaTurnos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean filaSeleccionada = tablaTurnos.getSelectedRow() != -1;
                btnModificar.setEnabled(filaSeleccionada);
                btnEliminar.setEnabled(filaSeleccionada);
            }
        });
    }*/

    // -------------------------------------------------------------------------
    // --- ESTILOS DE BOTONES UNIFICADOS (Tomados de VentanaRegistroTurno3) ---
    // -------------------------------------------------------------------------

    private JButton crearBotonPrincipal(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(160, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(COLOR_PRIMARIO);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        // Efecto Hover Azul
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 86, 179));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(COLOR_PRIMARIO);
            }
        });
        return button;
    }

    private JButton crearBotonDestructivo(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(160, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(COLOR_DESTRUCTIVO);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        // Efecto Hover Rojo
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(194, 39, 53));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(COLOR_DESTRUCTIVO);
            }
        });
        return button;
    }

    private JButton crearBotonSecundario(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(COLOR_SECUNDARIO);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    // -------------------------------------------------------------------------
    // --- MÉTODOS DE CONEXIÓN Y UTILIDAD PARA EL CONTROLADOR ---
    // -------------------------------------------------------------------------

    public void setControlador(ControladorGestionTurnos controlador) {

        // 🛑 AGREGAR LÓGICA DE REMOCIÓN ANTES DE AGREGAR PARA CADA BOTÓN:

        // Buscar
        for(java.awt.event.ActionListener al : btnBuscar.getActionListeners()) btnBuscar.removeActionListener(al);
        this.btnBuscar.addActionListener(controlador);

        // Modificar
        for(java.awt.event.ActionListener al : btnModificar.getActionListeners()) btnModificar.removeActionListener(al);
        this.btnModificar.removeActionListener(controlador);


        // Ahora añades el nuevo (y único) listener
        btnModificar.addActionListener(controlador);
        btnModificar.setActionCommand("MODIFICAR");


        // Eliminar
        for(java.awt.event.ActionListener al : btnEliminar.getActionListeners()) btnEliminar.removeActionListener(al);
        this.btnEliminar.addActionListener(controlador);

        // Salir
        for(java.awt.event.ActionListener al : btnSalir.getActionListeners()) btnSalir.removeActionListener(al);
        this.btnSalir.addActionListener(controlador);
    }


    public JTable getTablaTurnos() {
        return tablaTurnos;
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }


    public void setModeloTabla(DefaultTableModel modelo) {
        this.modeloTabla = modelo;
        tablaTurnos.setModel(this.modeloTabla);

        //  Ocultar la Columna 0 (ID)
        if (tablaTurnos.getColumnModel().getColumnCount() > 0) {
            tablaTurnos.getColumnModel().getColumn(0).setMinWidth(0);
            tablaTurnos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaTurnos.getColumnModel().getColumn(0).setWidth(0);
        }

        // --- Ocultar Columna 5 (Estado) ---
        TableColumn columnaEstado = tablaTurnos.getColumnModel().getColumn(5);
        columnaEstado.setMinWidth(0);
        columnaEstado.setMaxWidth(0);
        columnaEstado.setPreferredWidth(0);

        //  AJUSTAR LOS ANCHOS DE LAS COLUMNAS VISIBLES (Hora, Tipo, Prop., Mascota)
        configurarAnchoColumnas();
    }

    private void configurarAnchoColumnas() {
        // Columnas que el usuario ve: [1:Hora, 2:Tipo Consulta, 3:Propietario, 4:Mascota]

        if (tablaTurnos.getColumnModel().getColumnCount() < 5) {
            return; // Salir si no hay suficientes columnas
        }

        // 1. Hora (Columna 1): Angosta
        tablaTurnos.getColumnModel().getColumn(1).setPreferredWidth(80);

        // 2. Tipo Consulta (Columna 2): Moderada
        tablaTurnos.getColumnModel().getColumn(2).setPreferredWidth(150);

        // 3. Propietario (Columna 3): Ancha
        tablaTurnos.getColumnModel().getColumn(3).setPreferredWidth(250);

        // 4. Mascota (Columna 4): Ancha
        tablaTurnos.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    public void mostrarTurnos(Object[][] datos) {
        limpiarTabla();
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    public Object getIDTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila != -1) {
            return modeloTabla.getValueAt(fila, 0);
        }
        return null;
    }

    public Object[] getDatosTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila != -1) {
            Object[] datosFila = new Object[modeloTabla.getColumnCount()];
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                datosFila[i] = modeloTabla.getValueAt(fila, i);
            }
            return datosFila;
        }
        return null;
    }

    public String getFechaBusqueda() {
        java.util.Date fecha = dateChooserFecha.getDate();

        if (fecha == null) {
            return ""; // Devuelve vacío si no hay fecha seleccionada
        }

        // Convertir el objeto Date al String de formato "yyyy-MM-dd" para el DAO/Gestor
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    /**
     * Establece la fecha visible en el JDateChooser a partir de un String.
     * Útil para inicializar la vista con la fecha actual o una fecha guardada.
     * @param fechaString La fecha en formato "yyyy-MM-dd" o "dd/MM/yyyy".
     */
    public void setFechaBusqueda(String fechaString) {
        if (fechaString == null || fechaString.isEmpty()) {
            dateChooserFecha.setDate(null);
            return;
        }

        // Intentar parsear con el formato  para mostrar (dd/MM/yyyy)
        java.text.SimpleDateFormat formatDisplay = new java.text.SimpleDateFormat("dd/MM/yyyy");

        // Si tu DAO usa "yyyy-MM-dd", el string de entrada probablemente venga así
        java.text.SimpleDateFormat formatDAO = new java.text.SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date fecha = formatDAO.parse(fechaString);
            dateChooserFecha.setDate(fecha);
        } catch (java.text.ParseException e) {
            try {
                // Intenta con el formato de display si el primero falla
                java.util.Date fecha = formatDisplay.parse(fechaString);
                dateChooserFecha.setDate(fecha);
            } catch (java.text.ParseException ex) {
                // Manejar error de formato si es necesario
                System.err.println("Error al parsear la fecha: " + fechaString);
                dateChooserFecha.setDate(null);
            }
        }
    }


    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    // Método necesario para la confirmación de Eliminación
    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar Acción", JOptionPane.YES_NO_OPTION);
    }

    public JDesktopPane getEscritorio() {
        return this.escritorioContenedor;
    }

    /*public void actualizarTabla(Object[] turnos) {
        // Asumo que 'tablaTurnos' es el nombre de tu JTable.
        DefaultTableModel modelo = (DefaultTableModel) tablaTurnos.getModel();

        // 1. Limpiar filas existentes
        modelo.setRowCount(0);

        // 2. Llenar con los nuevos datos
      /*  if (turnos != null) {
            for (Object[]tturno : turnos) {
                // Ejemplo de cómo llenar la fila (ADAPTAR A TUS COLUMNAS):
                modelo.addRow(new Object[]{
                        String.valueOf(turno.getIdTurno()),
                        turno.getFechaTurno(),
                        turno.getHora(),
                        // ... Añadir más campos si tu tabla los tiene (Propietario, Mascota, etc.)
                });
            }*/



    public void actualizarTabla(Object[][] datosGrilla) {
        // Ya no necesitas el bucle 'for (Turno turno : turnos)'
        // Este método asume que 'tablaTurnos' y 'modelo' ya están definidos.

        DefaultTableModel modelo = (DefaultTableModel) tablaTurnos.getModel();
        modelo.setRowCount(0); // Limpiar filas

        if (datosGrilla != null) {
            // Usar un bucle for-each simple para añadir las filas de la matriz
            for (Object[] fila : datosGrilla) {
                modelo.addRow(fila); // Añade directamente la fila de Object[]
            }
        }
    }

    public String getFechaSeleccionadaComoString() {
        Date date = dateChooserFecha.getDate(); // Asume que 'dateChooserFecha' es el nombre de tu JDateChooser

        if (date == null) {
            return null;
        }

        // 1. Convertir Date a LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 2. Formatear como String (el método toString() de LocalDate usa el formato YYYY-MM-DD)
        return localDate.toString();
    }



}
