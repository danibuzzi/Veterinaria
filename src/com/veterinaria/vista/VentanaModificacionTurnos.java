package com.veterinaria.vista;

import com.toedter.calendar.JDateChooser;
import com.veterinaria.controlador.ControladorModificacionTurnos;
import com.veterinaria.controlador.ControladorGestionTurnos;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.TipoConsulta;
import javax.swing.*;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;


public class VentanaModificacionTurnos extends JInternalFrame implements InternalFrameListener {

    // --- CONSTANTES DE ESTILO PARA FORMATO CONSISTENTE ---
    private static final Color BLUE_PRIMARY = new Color(37, 99, 235);
    private static final Color GRAY_BORDER = new Color(203, 213, 225);
    private static final Color GRAY_TEXT = new Color(71, 85, 105);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 12);

    private JDateChooser dateChooserFecha;
    private JComboBox<String> horarioCombo;
    private JComboBox<TipoConsulta> tipoConsultaCombo;
    private JComboBox<Propietario> propietarioCombo;
    private JComboBox<Mascota> mascotaCombo;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final ControladorGestionTurnos controladorPrincipal;
    private final String fechaSeleccionada;
    private final Object[] datosIniciales;

    public VentanaModificacionTurnos(String fechaOriginal, Object[] datosTurno, ControladorGestionTurnos controladorPrincipal) {
        super("Modificación de Turno", true, true, true, true);

        this.controladorPrincipal = controladorPrincipal;
        this.fechaSeleccionada = fechaOriginal;
        this.datosIniciales = datosTurno;

        setSize(700, 550);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        initComponents();
        //cargarDatosIniciales();

        this.addInternalFrameListener(this); // Registrar el listener de cierre
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

    // --- MÉTODOS DE DISEÑO (Para el formato consistente) ---

    private JPanel createCalendarSection() {
        JPanel section = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        section.setBackground(Color.WHITE);
        JLabel lblFecha = new JLabel("Seleccionar Fecha:");
        lblFecha.setFont(FONT_BOLD);
        dateChooserFecha = new JDateChooser();
        dateChooserFecha.setDateFormatString("dd/MM/yyyy");
        dateChooserFecha.setFont(FONT_FIELD);
        section.add(lblFecha);
        section.add(dateChooserFecha);
        return section;
    }
    public void setFechaSeleccionada(LocalDate fecha) {
        // Asumimos que tu JDateChooser se llama 'dateChooserFecha'
        java.util.Date fechaSel = java.util.Date.from(
                fecha.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()
        );
        dateChooserFecha.setDate(fechaSel);


    }

    private JPanel createFormSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);

        horarioCombo = new JComboBox<>(); styleComboBox(horarioCombo);
        tipoConsultaCombo = new JComboBox<>(); styleComboBox(tipoConsultaCombo);
        propietarioCombo = new JComboBox<>(); styleComboBox(propietarioCombo);
        mascotaCombo = new JComboBox<>(); styleComboBox(mascotaCombo); mascotaCombo.setEnabled(false);

        section.add(createFormRow("Horario disponible", horarioCombo));
        section.add(Box.createVerticalStrut(15));
        section.add(createFormRow("Tipo de Consulta", tipoConsultaCombo));
        section.add(Box.createVerticalStrut(15));
        section.add(createFormRow("Propietario", propietarioCombo));
        section.add(Box.createVerticalStrut(15));
        section.add(createFormRow("Mascota", mascotaCombo));
        return section;
    }

    private void styleComboBox(JComboBox<?> combo) {
        // Aplica el borde gris y la fuente estándar
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

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        // Aplica el estilo Azul Primario a los botones
        button.setFont(FONT_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE_PRIMARY);
        button.setPreferredSize(new Dimension(140, 35));
        button.setBorder(null);
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnGuardar = createActionButton("Guardar");
        btnGuardar.setActionCommand("GUARDAR");

        btnCancelar = createActionButton("Cancelar");
        btnCancelar.setActionCommand("CANCELAR");

        panel.add(btnGuardar);
        panel.add(btnCancelar);
        return panel;
    }

    /*private void cargarDatosIniciales() {
        try {
            LocalDate localDate = LocalDate.parse(fechaSeleccionada);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateChooserFecha.setDate(date);
        } catch (Exception e) {}


    }*/


    private void cargarDatosIniciales() {
        // ----------------------------------------------------
        // 1. Cargar Fecha Inicial
        // ----------------------------------------------------
        try {
            LocalDate localDate = LocalDate.parse(fechaSeleccionada);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateChooserFecha.setDate(date);
        } catch (Exception e) {
            System.err.println("Error al cargar la fecha inicial: " + e.getMessage());
        }

        // ----------------------------------------------------
        // 2. OBTENER IDs DEL TURNO INICIAL y Seleccionar
        // ----------------------------------------------------
        try {
            // Asumimos que los IDs están en los índices 6, 7 y 8
            int idPropietario = (int) datosIniciales[6];
            int idMascota = (int) datosIniciales[7];
            int idTipoConsulta = (int) datosIniciales[8];

            // 3. SELECCIONAR PROPIETARIO (Usa tu método auxiliar)
            Propietario propietarioSeleccionado = seleccionarPropietarioPorId(idPropietario);

            if (propietarioSeleccionado != null) {
                // 4. DISPARAR LA CARGA DE MASCOTAS (Para que el combo de mascota se llene)
                propietarioCombo.dispatchEvent(new java.awt.event.ActionEvent(
                        propietarioCombo,
                        java.awt.event.ActionEvent.ACTION_PERFORMED,
                        "PROPIETARIO_CAMBIO") // Debe coincidir con el ActionCommand
                );

                // 5. SELECCIONAR MASCOTA y TIPO CONSULTA
                // Usamos invokeLater para dar tiempo a que las mascotas se carguen
                SwingUtilities.invokeLater(() -> {
                    seleccionarMascotaPorId(idMascota);
                });

                seleccionarTipoConsultaPorId(idTipoConsulta);

                // (Opcional: Si necesitas seleccionar la hora también)
                // String horaOriginal = (String) datosIniciales[1];
                // getHorarioCombo().setSelectedItem(horaOriginal);

            } else {
                System.err.println("Advertencia: Propietario ID " + idPropietario + " no encontrado en la lista.");
            }

        } catch (Exception e) {
            System.err.println("Error al cargar y seleccionar los combos del turno: " + e.getMessage());
        }
    }

    public void setHorariosDisponibles(List<String> horarios) {
        JComboBox comboHorarios = getHorarioCombo(); // Asume que este es el método que devuelve tu JComboBox
        comboHorarios.removeAllItems(); // Limpiar la lista existente

        if (horarios != null) {
            for (String hora : horarios) {
                comboHorarios.addItem(hora); // Agregar cada hora disponible
            }
        }

    }

    // 🛑 Añade este método público para que el Controlador pueda iniciar la selección
    public void iniciarSeleccionDeTurno() {
        cargarDatosIniciales();
        // Opcional, pero recomendado para el refresco visual en JInternalFrame:
        revalidate();
        repaint();
    }


    // --- CONEXIÓN DE LISTENERS ---

    public void setControlador(ControladorModificacionTurnos controlador) {
        propietarioCombo.addActionListener(controlador);
        propietarioCombo.setActionCommand("PROPIETARIO_CAMBIO");

        // Listener para que la vista notifique al controlador cuando la fecha cambia
        dateChooserFecha.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                try {
                    controlador.cargarHorariosDisponibles();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnGuardar.addActionListener(controlador);
        btnCancelar.addActionListener(controlador);
    }

    // --- GETTERS Y SETTERS ---
    public void setOpcionesPropietario(List<Propietario> propietarios) { propietarioCombo.removeAllItems(); propietarios.forEach(propietarioCombo::addItem); }
    public void setOpcionesMascota(List<Mascota> mascotas) { mascotaCombo.removeAllItems(); mascotas.forEach(mascotaCombo::addItem); mascotaCombo.setEnabled(true); }
    /*public void setOpcionesHorario(List<String> horarios) {
        horarioCombo.removeAllItems();
        horarios.forEach(horarioCombo::addItem); }*/

    public void setOpcionesHorario(List<String> horariosDisponibles) {
        // 1. Asegúrate de limpiar las opciones anteriores (¡CRÍTICO!)
        horarioCombo.removeAllItems();

        // 2. Iterar la lista y añadir cada String al JComboBox
        for (String horario : horariosDisponibles) {
            horarioCombo.addItem(horario);
        }

        // 3. (Opcional) Si hay un horario ya seleccionado, selecciónalo aquí.
    }
    public void setOpcionesTipoConsulta(List<TipoConsulta> tipos) {
        tipoConsultaCombo.removeAllItems();
        tipos.forEach(tipoConsultaCombo::addItem); }

    public JComboBox<Propietario> getPropietarioCombo() { return propietarioCombo; }
    public JComboBox<Mascota> getMascotaCombo() { return mascotaCombo; }
    public JComboBox<TipoConsulta> getTipoConsultaCombo() { return tipoConsultaCombo; }
    public JComboBox<String> getHorarioCombo() {
        return horarioCombo;
    }
    public JDateChooser getDateChooserFecha() { return dateChooserFecha; }
    public Object[] getDatosIniciales() { return datosIniciales; }

    // --- IMPLEMENTACIÓN DE InternalFrameListener (REEMPLAZO DEL CALLBACK) ---

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        // Notifica al ControladorGestionTurnos que debe recargar la tabla principal
        if (controladorPrincipal != null) {
            controladorPrincipal.recargarVistaPrincipal();
        }
    }

    // En VentanaModificacionTurnos.java (Método seleccionarPropietarioPorId)

   /* public Propietario seleccionarPropietarioPorId(int idBuscado) {
        //JComboBox<Propietario> comboPropietario =  this.getPropietarioCombo();
          JComboBox combo =this.getPropietarioCombo();
        for (int i = 0; i < combo.getItemCount(); i++) {
            Propietario p = (Propietario) combo.getItemAt(i); // Ya es Propietario, sin casting inseguro

            // Compara ID
            if (p.getIdPropietario() == idBuscado) {
                combo.setSelectedIndex(i);
                return p;

            }
        }
        return null;
    }*/


    public Propietario seleccionarPropietarioPorId(int idBuscado) {
        // 🛑 USAMOS EL TIPO GENÉRICO JComboBox<Propietario>
        JComboBox<Propietario> comboPropietario = this.getPropietarioCombo();

        // Iteramos de forma segura
        for (int i = 0; i < comboPropietario.getItemCount(); i++) {
            // Obtenemos el objeto Propietario directamente (no necesitamos casting explícito)
            Propietario p = comboPropietario.getItemAt(i);

            // Compara ID
            if (p.getIdPropietario() == idBuscado) {
                comboPropietario.setSelectedIndex(i);
                return p;
            }
        }
        return null;
    }

    /**
     * Busca una Mascota en el JComboBox por su ID y la selecciona.
     *
     * @return
     */
    public Mascota seleccionarMascotaPorId(int idBuscado) {
        // Reemplaza 'comboMascota' con el nombre real de tu JComboBox de Mascotas
        JComboBox combo = this.getMascotaCombo();

        for (int i = 0; i < combo.getItemCount(); i++) {
            Object item = combo.getItemAt(i);

            // Asume que el combo contiene objetos Mascota
            if (item instanceof Mascota) {
                Mascota m = (Mascota) item;

                // Compara por ID. Requiere que Mascota tenga un método getId().
                if (m.getIdMascota() == idBuscado) {
                    combo.setSelectedIndex(i);
                    return m;
                }
            }
        }
        return null;
    }
    public TipoConsulta seleccionarTipoConsultaPorId(int idBuscado) {
        // Reemplaza 'comboMascota' con el nombre real de tu JComboBox de Mascotas
        JComboBox combo = this.getTipoConsultaCombo();

        for (int i = 0; i < combo.getItemCount(); i++) {
            Object item = combo.getItemAt(i);

            // Asume que el combo contiene objetos Mascota
            if (item instanceof TipoConsulta) {
               TipoConsulta m = (TipoConsulta) item;

                // Compara por ID. Requiere que Mascota tenga un método getId().
                if (m.getIdTipoConsulta() == idBuscado) {
                    combo.setSelectedIndex(i);
                    return m;
                }
            }
        }
        return null;
    }

    //private JComboBox<Propietario>  propietarioCombo;
    private JComboBox<Mascota> comboMascota;

    public void setObjetoPropietarioSeleccionado(Propietario p) {

        propietarioCombo.setSelectedItem(p);
    }


    public void setObjetoMascotaSeleccionado(Mascota m) {
        mascotaCombo.setSelectedItem(m);
    }
    public void setObjetoTipoConsultaSeleccionado(TipoConsulta m) {
        tipoConsultaCombo.setSelectedItem(m);
    }

    // Métodos obligatorios
    @Override public void internalFrameOpened(InternalFrameEvent e) {}
    @Override public void internalFrameClosing(InternalFrameEvent e) {}
    @Override public void internalFrameIconified(InternalFrameEvent e) {}
    @Override public void internalFrameDeiconified(InternalFrameEvent e) {}
    @Override public void internalFrameActivated(InternalFrameEvent e) {}
    @Override public void internalFrameDeactivated(InternalFrameEvent e) {}
}