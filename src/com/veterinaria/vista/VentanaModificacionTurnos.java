package com.veterinaria.vista;

import com.toedter.calendar.JDateChooser;
import com.veterinaria.controlador.ControladorModificacionTurnos;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

public class VentanaModificacionTurnos extends JInternalFrame {

    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 12);

    private JDateChooser dateChooserFecha;
    private JComboBox<String> horarioCombo;
    private JComboBox<String> tipoConsultaCombo;
    private JComboBox<Propietario> propietarioCombo;
    private JComboBox<Mascota> mascotaCombo;

    private final ModificacionCallback callback;
    private final String fechaSeleccionada;
    private final Object[] datosIniciales;

    public VentanaModificacionTurnos(String fechaOriginal, Object[] datosTurno, ModificacionCallback callback) {
        super("Modificación de Turno ID: " + datosTurno[0], true, true, true, true);

        this.callback = callback;
        this.fechaSeleccionada = fechaOriginal;
        this.datosIniciales = datosTurno;

        setSize(700, 550);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        initComponents();
        cargarDatosIniciales();

        // Listener para el callback al cerrar la ventana
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (callback != null) {
                    callback.notificarCierreModificacion();
                }
            }
        });
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(createCalendarSection(), BorderLayout.NORTH);
        mainPanel.add(createFormSection(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createCalendarSection() {
        JPanel section = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        section.setBackground(Color.WHITE);

        JLabel lblFecha = new JLabel("Seleccionar Fecha:");
        lblFecha.setFont(FONT_BOLD);
        lblFecha.setForeground(GRAY_TEXT);
        lblFecha.setPreferredSize(new Dimension(120, 30));

        dateChooserFecha = new JDateChooser();
        dateChooserFecha.setDateFormatString("dd/MM/yyyy");
        dateChooserFecha.setFont(FONT_FIELD);
        dateChooserFecha.setPreferredSize(new Dimension(150, 30));
        dateChooserFecha.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        dateChooserFecha.setMinSelectableDate(new Date());

        section.add(lblFecha);
        section.add(dateChooserFecha);
        return section;
    }

    private JPanel createFormSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        section.add(createFormRow("Horario disponible", new JScrollPane(createHorarioCombo())));
        section.add(Box.createVerticalStrut(15));
        section.add(createFormRow("Tipo de Consulta", createTipoConsultaCombo()));
        section.add(Box.createVerticalStrut(15));
        section.add(createFormRow("Propietario", createPropietarioCombo()));
        section.add(Box.createVerticalStrut(15));
        section.add(createFormRow("Mascota", createMascotaCombo()));

        return section;
    }

    private JComboBox<String> createHorarioCombo() { horarioCombo = new JComboBox<>(); styleComboBox(horarioCombo); return horarioCombo; }
    private JComboBox<String> createTipoConsultaCombo() { tipoConsultaCombo = new JComboBox<>(); styleComboBox(tipoConsultaCombo); return tipoConsultaCombo; }
    private JComboBox<Propietario> createPropietarioCombo() { propietarioCombo = new JComboBox<>(); styleComboBox(propietarioCombo); return propietarioCombo; }
    private JComboBox<Mascota> createMascotaCombo() { mascotaCombo = new JComboBox<>(); styleComboBox(mascotaCombo); mascotaCombo.setEnabled(false); return mascotaCombo; }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_FIELD);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(GRAY_BORDER, 1));
        combo.setPreferredSize(new Dimension(150, 30));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    private JPanel createFormRow(String labelText, JComponent component) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(FONT_BOLD);
        label.setForeground(GRAY_TEXT);
        label.setPreferredSize(new Dimension(120, 35));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(label, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);
        return row;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton guardarButton = createActionButton("Guardar");
        guardarButton.setActionCommand("GUARDAR");

        JButton cancelarButton = createActionButton("Cancelar");
        cancelarButton.setActionCommand("CANCELAR");

        panel.add(guardarButton);
        panel.add(cancelarButton);
        return panel;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE_PRIMARY);
        button.setPreferredSize(new Dimension(140, 35));
        button.setBorder(null);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void cargarDatosIniciales() {
        try {
            LocalDate localDate = LocalDate.parse(fechaSeleccionada);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateChooserFecha.setDate(date);
        } catch (Exception e) { /* Ignorar si falla */ }
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR ---

    public void setControlador(ControladorModificacionTurnos controlador) {
        propietarioCombo.addActionListener(controlador);
        propietarioCombo.setActionCommand("PROPIETARIO_CAMBIO");

        dateChooserFecha.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                controlador.cargarHorariosDisponibles();
            }
        });

        // Asignar listeners a botones
        ((JButton)((JPanel)createButtonPanel().getComponent(0)).getComponent(0)).addActionListener(controlador); // Guardar
        ((JButton)((JPanel)createButtonPanel().getComponent(0)).getComponent(1)).addActionListener(controlador); // Cancelar
    }

    // Métodos para rellenar combos
    public void setOpcionesPropietario(List<Propietario> propietarios) { propietarioCombo.removeAllItems(); propietarios.forEach(propietarioCombo::addItem); }
    public void setOpcionesMascota(List<Mascota> mascotas) { mascotaCombo.removeAllItems(); mascotas.forEach(mascotaCombo::addItem); mascotaCombo.setEnabled(true); }
    public void setOpcionesHorario(List<String> horarios) { horarioCombo.removeAllItems(); horarios.forEach(horarioCombo::addItem); }
    public void setOpcionesTipoConsulta(List<String> tipos) { tipoConsultaCombo.removeAllItems(); tipos.forEach(tipoConsultaCombo::addItem); }

    // Getters para el controlador
    public JComboBox<Propietario> getPropietarioCombo() { return propietarioCombo; }
    public JComboBox<Mascota> getMascotaCombo() { return mascotaCombo; }
    public JComboBox<String> getTipoConsultaCombo() { return tipoConsultaCombo; }
    public JComboBox<String> getHorarioCombo() { return horarioCombo; }
    public JDateChooser getDateChooserFecha() { return dateChooserFecha; }
    public Object[] getDatosIniciales() { return datosIniciales; }
}
