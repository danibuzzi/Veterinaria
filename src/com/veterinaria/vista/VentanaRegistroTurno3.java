package com.veterinaria.vista;


/*

import com.toedter.calendar.JDateChooser;
import com.veterinaria.controlador.ControladorTurno3; // Importa el nuevo controlador
import com.veterinaria.modelo.GestorTurno3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.border.TitledBorder;

// 🛑 CLASE INDEPENDIENTE (Copia de VentanaRegistroTurno2)
public class VentanaRegistroTurno3 extends JFrame {

    private final GestorTurno3 gestorTurno;

    private JComboBox<String> comboTipoConsulta;
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JDateChooser dateChooserFechaTurno;
    private JComboBox<String> cmbHora;
    private JButton btnGuardar;
    private JButton btnSalir;

    public VentanaRegistroTurno3(GestorTurno3 gestorTurno) {
        super("Reserva de Turnos V3 - Filtro Avanzado");
        this.gestorTurno = gestorTurno;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // ... (Resto de la inicialización de la interfaz) ...
        setSize(450, 450);
        setLocationRelativeTo(null);

        // Inicialización de componentes (Asegúrate de cambiar txtHora por cmbHora en tu diseño)
        comboTipoConsulta = new JComboBox<>();
        comboPropietario = new JComboBox<>();
        comboMascota = new JComboBox<>();
        dateChooserFechaTurno = new JDateChooser();
        cmbHora = new JComboBox<>();
        btnGuardar = new JButton("Guardar Turno");
        btnSalir = new JButton("Salir");

        // ... (Código de layout.
        // Simplificación:
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Tipo de Consulta:")); panel.add(comboTipoConsulta);
        panel.add(new JLabel("Propietario:")); panel.add(comboPropietario);
        panel.add(new JLabel("Mascota:")); panel.add(comboMascota);
        panel.add(new JLabel("Fecha Turno:")); panel.add(dateChooserFechaTurno);
        panel.add(new JLabel("Hora:")); panel.add(cmbHora);
        panel.add(btnGuardar); panel.add(btnSalir);
        this.add(panel, BorderLayout.CENTER);

    }

    // ----------------------------------------------------
    // GETTERS Y SETTERS V3
    // ----------------------------------------------------

    public void setControlador(ControladorTurno3 controlador) {
        btnGuardar.addActionListener(controlador);
        btnGuardar.setActionCommand("GUARDAR_TURNO");
        btnSalir.addActionListener(e -> dispose());
    }

    public void setListenerCargaMascotas(ActionListener listener) {
        comboPropietario.addActionListener(listener);
        comboPropietario.setActionCommand("PROPIETARIO_SELECCIONADO");
    }
/*

    /*public void setListenerFecha(ActionListener listener) {
        dateChooserFechaTurno.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {

                Date fechaSeleccionada = dateChooserFechaTurno.getDate();


                if (gestorTurno.esFechaPasada(fechaSeleccionada)) {
                    JOptionPane.showMessageDialog(this, "No se puede reservar turnos en una fecha pasada.",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    dateChooserFechaTurno.setDate(new Date()); // Resetea a hoy o a null
                    cmbHora.removeAllItems();
                    cmbHora.addItem("Fecha pasada - Bloqueado");
                    return;
                }

                // Si es válida, dispara el evento para cargar los horarios
                listener.actionPerformed(new ActionEvent(dateChooserFechaTurno, ActionEvent.ACTION_PERFORMED, "FECHA_CAMBIADA"));
            }
        });
    }

    public void cargarItems(JComboBox<String> combo, List<String> items) {
        combo.removeAllItems();
        for (String item : items) { combo.addItem(item); }
    }


    /*public void mostrarMensaje(String mensaje, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, "Gestión de Turnos", tipo);
    }


    // ----------------------------------------------------
    // GETTERS (Asegurándonos de que están todos los necesarios)
    // ----------------------------------------------------


    public JComboBox<String> getComboTipoConsulta() { return comboTipoConsulta; }
    public JComboBox<String> getComboPropietario() { return comboPropietario; }
    public JComboBox<String> getComboMascota() { return comboMascota; }

    // GETTERS PARA LOS VALORES SELECCIONADOS
    public String getNombreTipoConsultaSeleccionado() { return (String) comboTipoConsulta.getSelectedItem(); }
    public String getNombrePropietarioSeleccionado() { return (String) comboPropietario.getSelectedItem(); }
    public String getNombreMascotaSeleccionada() { return (String) comboMascota.getSelectedItem(); }
    public String getHoraSeleccionada() { return (String) cmbHora.getSelectedItem(); }

    // GETTERS PARA COMPONENTES ESPECIALES
    public JDateChooser getDateChooserFechaTurno() { return dateChooserFechaTurno; }
    public JComboBox<String> getCmbHora() { return cmbHora; }
    public JButton getBtnGuardar() { return btnGuardar; }
}*/


import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener; // Necesario para escuchar cambios en JDateChooser
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import com.veterinaria.modelo.GestorTurno;
import com.veterinaria.modelo.GestorTurno3;

import javax.swing.border.TitledBorder;

public class VentanaRegistroTurno3 extends JInternalFrame{
    private final GestorTurno3 gestorTurno;
    private JComboBox<String> comboTipoConsulta;
    private JComboBox<String> comboPropietario;
    private JComboBox<String> comboMascota;
    private JComboBox<String> comboHora;
    private JDateChooser dateChooserFechaTurno;
    // Se elimina private JTextField txtHora;
    private JButton btnGuardar;
    private JButton btnSalir;

    // --- COLORES Y FUENTES ---
    private static final Color COLOR_PRIMARIO = new Color(0, 123, 255); // Azul moderno
    private static final Color COLOR_FONDO_CLARO = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(173, 216, 230); // Azul claro para bordes
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 14);

    public VentanaRegistroTurno3(GestorTurno3 gestorTurno) {
        super("Reserva de Turnos");
        this.gestorTurno = gestorTurno;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(250, 300);

        setClosable(true);
        setResizable(false);
        setMaximizable(true);
        setIconifiable(true);

        // 1. Panel principal con espacio alrededor
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COLOR_FONDO_CLARO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // Más relleno

        // 2. Contenedor CENTRAL: Usaremos un panel para apilar la fecha y el formulario
        JPanel centerContainer = new JPanel(new BorderLayout(0, 20)); // Más espacio vertical
        centerContainer.setBackground(COLOR_FONDO_CLARO);

        centerContainer.add(crearPanelCalendario(), BorderLayout.NORTH);

        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formWrapper.setBackground(COLOR_FONDO_CLARO);
        formWrapper.add(crearPanelFormulario());

        centerContainer.add(formWrapper, BorderLayout.CENTER);

        // 3. Añadir el contenedor central y los botones al mainPanel
        mainPanel.add(centerContainer, BorderLayout.CENTER);
        mainPanel.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(mainPanel);
        pack(); // Ajusta al tamaño preferido
        setResizable(false); // Estética mejorada al no permitir redimensionar
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE INTERFAZ ---

    private JPanel crearPanelCalendario() {
        dateChooserFechaTurno = new JDateChooser(new Date());
        dateChooserFechaTurno.setDateFormatString("dd/MM/yyyy");
        dateChooserFechaTurno.setFont(FONT_FIELD);
        dateChooserFechaTurno.setPreferredSize(new Dimension(300, 35));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(COLOR_FONDO_CLARO);

        // Estilo de borde mejorado
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 2), "Seleccionar Fecha",
                TitledBorder.LEFT, TitledBorder.TOP, FONT_TITLE, COLOR_PRIMARIO
        ));

        JPanel dateWrapper = new JPanel(new BorderLayout());
        dateWrapper.setPreferredSize(new Dimension(380, 75)); // Aumentado ligeramente
        dateWrapper.setBackground(COLOR_FONDO_CLARO);
        dateWrapper.add(dateChooserFechaTurno, BorderLayout.NORTH);

        panel.add(dateWrapper);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO_CLARO);

        // Estilo de borde mejorado
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 2), "Detalles del Turno",
                TitledBorder.LEFT, TitledBorder.TOP, FONT_TITLE, COLOR_PRIMARIO
        ));

        panel.setPreferredSize(new Dimension(480, 350)); // Más espacio

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 15, 12, 15); // Más padding interno

        // Inicialización de ComboBoxes
        comboPropietario = new JComboBox<>();
        comboPropietario.addItem("--- Seleccione Propietario ---");
        comboMascota = new JComboBox<>();
        comboMascota.addItem("--- Seleccione Mascota ---");
        comboTipoConsulta = new JComboBox<>();
        comboTipoConsulta.addItem("--- Seleccione Tipo de Consulta ---");
        comboHora = new JComboBox<>(); // ComboBox para la Hora
        comboHora.addItem("--- Seleccione Hora ---");

        // 4 campos en el formulario
        añadirComponente(panel, new JLabel("Propietario:"), comboPropietario, 0, gbc);
        añadirComponente(panel, new JLabel("Mascota:"), comboMascota, 1, gbc);
        añadirComponente(panel, new JLabel("Tipo de Consulta:"), comboTipoConsulta, 2, gbc);
        añadirComponente(panel, new JLabel("Hora:"), comboHora, 3, gbc);
        // Relleno vertical
        gbc.gridx = 0; gbc.gridy = 4; gbc.weighty = 1.0;
        panel.add(new JLabel(""), gbc);

        return panel;
    }

    // Método auxiliar unificado
    private void añadirComponente(JPanel panel, JLabel label, JComponent component, int y, GridBagConstraints gbc) {
        // Columna 0 (Label)
        gbc.gridx = 0; gbc.gridy = y; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        label.setFont(FONT_LABEL);
        panel.add(label, gbc);

        // Columna 1 (Componente)
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        component.setFont(FONT_FIELD);

        // Ajustamos el ancho para un look más moderno
        component.setPreferredSize(new Dimension(250, 35));
        panel.add(component, gbc);
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(COLOR_FONDO_CLARO);

        btnSalir = crearBotonGeneral("Salir", new Color(108, 117, 125)); // Gris neutro
        btnSalir.addActionListener(e -> dispose());

        btnGuardar = crearBotonPrincipal("Guardar Turno");
        btnGuardar.setActionCommand("GUARDAR_TURNO");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnSalir);
        return buttonPanel;
    }

    // Diseño de botón principal (Guardar)
    private JButton crearBotonPrincipal(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(160, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Negrita para el principal
        button.setBackground(COLOR_PRIMARIO);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 86, 179)); // Hover más oscuro
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(COLOR_PRIMARIO);
            }
        });
        return button;
    }

    // Diseño de botón secundario (Salir)
    private JButton crearBotonGeneral(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    // --- MÉTODOS DE DATOS Y GETTERS ACTUALIZADOS ---

    public void setControlador(ActionListener controlador) {

        // ✅ PASO CLAVE: LIMPIAR OYENTES
        // Esto asegura que, incluso si setControlador() se llama dos veces desde VentanaPrincipal,
        // el botón 'Guardar' solo tendrá UN ActionListener.
        for (ActionListener al : btnGuardar.getActionListeners()) {
            btnGuardar.removeActionListener(al);
        }

        // CONEXIÓN ÚNICA
        btnGuardar.addActionListener(controlador);
        btnGuardar.setActionCommand("GUARDAR_TURNO");
        //btnGuardar.addActionListener(controlador);
    }

    // Método para que el controlador escuche el cambio de fecha
    public void setListenerCargaHorarios(PropertyChangeListener listener) {
        dateChooserFechaTurno.addPropertyChangeListener("date", listener);
    }

    public void setListenerCargaMascotas(ActionListener listener) {
        comboPropietario.addActionListener(listener);
        comboPropietario.setActionCommand("PROPIETARIO_SELECCIONADO");
    }

    public void cargarItems(JComboBox<String> combo, List<String> items) {
        combo.removeAllItems();
        for (String item : items) {
            combo.addItem(item);
        }
        // Selecciona el primer elemento por defecto (que debería ser el de control)
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }
    }

    // Getter de la hora ACTUALIZADO para usar el ComboBox

    public String getHoraSeleccionada() {
        String horaSeleccionada = (String) comboHora.getSelectedItem();

        if (horaSeleccionada != null && !horaSeleccionada.startsWith("---")) {

            // 1. Limpiar el string de cualquier ":00" duplicado o exceso (ej. si es "08:30:00:00")
            // Esto normaliza la cadena a su formato base HH:MM
            String horaBase = horaSeleccionada.replaceAll(":\\d{2}$", ""); // Elimina el último :SS si existe

            // 2. Usar una regex estricta para asegurar que la hora base sea HH:MM
            if (horaBase.matches("\\d{2}:\\d{2}")) {
                // ✅ Devuelve el formato correcto HH:MM:00
                return horaBase + ":00";
            }

            // Si el formato es totalmente inesperado, devolvemos la hora original (para que falle la validación, no la BD)
            return horaSeleccionada;
        }

        return horaSeleccionada;
    }

    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // VOID: Muestra un mensaje de error
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setListenerFecha(PropertyChangeListener listener) {
        // Es el mismo código, solo cambiamos el nombre del método
        dateChooserFechaTurno.addPropertyChangeListener("date", listener);
    }

    // Getters existentes
    public String getNombreTipoConsultaSeleccionado() { return (String) comboTipoConsulta.getSelectedItem(); }
    public String getNombrePropietarioSeleccionado() { return (String) comboPropietario.getSelectedItem(); }
    public String getNombreMascotaSeleccionada() { return (String) comboMascota.getSelectedItem(); }
    public Date getFechaSeleccionada() { return dateChooserFechaTurno.getDate(); } // Devuelve Date para el Gestor

    public JDateChooser getDateChooserFechaTurno() { return dateChooserFechaTurno; }
    public JComboBox<String> getComboHora() { return comboHora; }
    public JComboBox<String> getComboMascota() { return comboMascota; }
    public JComboBox<String> getComboTipoConsulta() { return comboTipoConsulta; }
    public JComboBox<String> getComboPropietario() { return comboPropietario; }
    public JButton getBtnGuardar() { return btnGuardar; }

    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Resultado", tipoMensaje);
    }
}