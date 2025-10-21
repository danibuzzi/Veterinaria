package com.veterinaria.controlador;

import com.veterinaria.modelo.HistoriaClinicaService;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.vista.VentanaHistoriaClinica2;
import com.veterinaria.vista.VentanaDetalleConsulta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import javax.swing.DefaultComboBoxModel; // Necesario para cargar modelos

public class ControladorHistoriaClinica implements ActionListener {

    private final VentanaHistoriaClinica2 vistaListado;
    private final HistoriaClinicaService service;
    private final JDesktopPane desktopPane;

    // 🛑 ¡SE HAN ELIMINADO LOS HASHMAPS!

    public ControladorHistoriaClinica(HistoriaClinicaService service, VentanaHistoriaClinica2 vistaListado, JDesktopPane desktopPane) {
        this.service = service;
        this.vistaListado = vistaListado;
        this.desktopPane = desktopPane;

        // 1. Configurar Listeners y ActionCommands
        this.vistaListado.setControlador(this);
        this.vistaListado.getBtnBuscar().setActionCommand("BUSCAR");
        this.vistaListado.getBtnVerDetalle().setActionCommand("VER_DETALLE");

        // Listener para el cambio de Propietario
        JComboBox<Propietario> comboPropietario = this.vistaListado.getComboPropietario();
        comboPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        comboPropietario.addActionListener(this);

        // 2. Cargar datos iniciales
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        try {
            // 1. Cargar Propietarios
            List<Propietario> propietarios = service.listarPropietarios();
            JComboBox<Propietario> comboPropietario = vistaListado.getComboPropietario();

            // Usamos DefaultComboBoxModel para manejar objetos
            DefaultComboBoxModel<Propietario> modeloPropietario = new DefaultComboBoxModel<>();

            // 🛑 Item por defecto/filtro, usa un Propietario con ID 0 y nombre especial
            modeloPropietario.addElement(new Propietario(0, "TODOS", ""));

            for (Propietario p : propietarios) {
                modeloPropietario.addElement(p);
            }
            comboPropietario.setModel(modeloPropietario);

            // 2. Inicializar Mascota
            cargarMascotas(0); // Carga la lista de mascotas con ID 0 al inicio
        } catch (RuntimeException e) {
            vistaListado.mostrarMensaje("Error al cargar datos iniciales: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método auxiliar para cargar mascotas por ID de propietario
    private void cargarMascotas(int idPropietario) {
        JComboBox<Mascota> comboMascota = vistaListado.getComboMascota();
        DefaultComboBoxModel<Mascota> modeloMascota = new DefaultComboBoxModel<>();

        // 🛑 Item por defecto/filtro
        modeloMascota.addElement(new Mascota(0, "TODAS", 0)); // Usamos un constructor dummy si es necesario.

        if (idPropietario != 0) {
            try {
                List<Mascota> mascotas = service.listarMascotasPorPropietario(idPropietario);
                for (Mascota m : mascotas) {
                    modeloMascota.addElement(m);
                }
            } catch (RuntimeException e) {
                vistaListado.mostrarMensaje("Error al cargar mascotas: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
        comboMascota.setModel(modeloMascota);
    }

    private void buscarConsultas() {
        // 🛑 1. Obtener el objeto Propietario directamente del JComboBox
        Propietario propietarioSeleccionado = (Propietario) vistaListado.getComboPropietario().getSelectedItem();

        if (propietarioSeleccionado == null || propietarioSeleccionado.getIdPropietario() == 0) {
            vistaListado.mostrarMensaje("Seleccione un Propietario específico para buscar su historial.", JOptionPane.WARNING_MESSAGE);
            vistaListado.mostrarResultados(List.of()); // Limpiar la tabla
            return;
        }

        int idPropietario = propietarioSeleccionado.getIdPropietario();

        try {
            // 🛑 Llamada al service para obtener List<Object[]>
            List<Object[]> resultados = service.listarConsultasResumen(idPropietario);

            // NOTA: Si quisieras filtrar por mascota, harías lo mismo aquí:
            // Mascota mascotaSeleccionada = (Mascota) vistaListado.getComboMascota().getSelectedItem();
            // int idMascota = mascotaSeleccionada.getIdMascota();
            // Y adaptarías el DAO para usar idMascota en el WHERE.

            vistaListado.mostrarResultados(resultados);

            if (resultados.isEmpty()) {
                vistaListado.mostrarMensaje("No se encontraron consultas para el propietario seleccionado.", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            vistaListado.mostrarMensaje("Error al realizar la búsqueda de consultas: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // verDetalleConsulta() se mantiene igual ya que solo usa el ID de la tabla.
    private void verDetalleConsulta() {
        // 1. Obtener el ID de la consulta de la tabla (Columna 0 oculta)
        Object idObj = vistaListado.getIdConsultaSeleccionada();

        if (idObj == null) {
            vistaListado.mostrarMensaje("Seleccione una consulta de la tabla para ver su detalle.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idConsulta = (int) idObj;

            // 2. Llamar al service para obtener el Object[] de detalle
            Object[] detalle = service.consultarDetalle(idConsulta);

            if (detalle == null) {
                vistaListado.mostrarMensaje("Error: No se pudo obtener el detalle de la consulta.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Crear y mostrar la VentanaDetalleConsulta
            VentanaDetalleConsulta ventanaDetalle = new VentanaDetalleConsulta();
            ventanaDetalle.cargarDatosConsulta(detalle); // <--- Se usa el Object[]

            desktopPane.add(ventanaDetalle);
            ventanaDetalle.setVisible(true);

            // Centrar la ventana en el escritorio
            int x = (desktopPane.getWidth() - ventanaDetalle.getWidth()) / 2;
            int y = (desktopPane.getHeight() - ventanaDetalle.getHeight()) / 2;
            ventanaDetalle.setLocation(x, y);

            ventanaDetalle.setSelected(true);

        } catch (Exception e) {
            vistaListado.mostrarMensaje("Error al abrir el detalle: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "PROPIETARIO_CAMBIO":
                // 🛑 Obtener el ID del objeto Propietario seleccionado
                Propietario p = (Propietario) vistaListado.getComboPropietario().getSelectedItem();
                int idPropietario = (p != null) ? p.getIdPropietario() : 0;
                cargarMascotas(idPropietario);
                break;
            case "BUSCAR":
                buscarConsultas();
                break;
            case "VER_DETALLE":
                verDetalleConsulta();
                break;
            default:
                break;
        }
    }
}