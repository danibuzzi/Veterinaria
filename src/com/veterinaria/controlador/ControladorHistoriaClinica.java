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


        this.vistaListado.getBtnSalir().setActionCommand("SALIR");

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
        modeloMascota.addElement(new Mascota(0, ".Seleccione un propietario.", 0)); // Usamos un constructor dummy si es necesario.

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

   /* private void buscarConsultas() {
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


            vistaListado.mostrarResultados(resultados);

            if (resultados.isEmpty()) {
                vistaListado.mostrarMensaje("No se encontraron consultas para el propietario seleccionado.", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            vistaListado.mostrarMensaje("Error al realizar la búsqueda de consultas: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }*/

    private void buscarConsultas() {
        // 1. OBTENER VALORES DE LOS TRES CAMPOS OBLIGATORIOS

        // Propietario
        Propietario propietario = (Propietario) vistaListado.getComboPropietario().getSelectedItem();

        // Mascota

        Mascota mascota = (Mascota) vistaListado.getComboMascota().getSelectedItem();

        // Fecha
        // 🛑 ASUME que tienes un getter para el componente de fecha
        java.util.Date utilDate = vistaListado.getFechaDesde();

        // 2. 🛑 VALIDACIÓN GENERAL ESTRICTA (TODOS LOS CAMPOS OBLIGATORIOS)

        // Verificamos si alguno de los campos obligatorios falta o es inválido (ID <= 0)
        boolean propietarioSeleccionado = (propietario != null && propietario.getIdPropietario() > 0);
        boolean mascotaSeleccionada = (mascota != null && mascota.getIdMascota() > 0);
        boolean fechaIngresada = (utilDate != null);

        if (!propietarioSeleccionado || !mascotaSeleccionada || !fechaIngresada) {
            // Un solo mensaje de error si falta CUALQUIER campo
            vistaListado.mostrarMensaje(
                    "Debe seleccionar el Propietario, la Mascota y la Fecha de Búsqueda para realizar la consulta.",
                    JOptionPane.WARNING_MESSAGE
            );
            vistaListado.mostrarResultados(List.of()); // Limpiar la tabla si la validación falla
            return;
        }

        // 3. PREPARACIÓN DE DATOS Y LLAMADA AL SERVICIO

        // Si llegamos aquí, sabemos que los tres valores son válidos y no null.

        Integer idPropietario = propietario.getIdPropietario();
        Integer idMascota = mascota.getIdMascota();

        // Convertir la fecha de java.util.Date a java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            // 🛑 Llamada al nuevo método del servicio que acepta los 3 filtros
            // ¡DEBES CREAR ESTE MÉTODO EN EL SERVICIO!
            List<Object[]> resultados = service.buscarConsultasResumen(
                    idPropietario,
                    idMascota,
                    sqlDate
            );

            vistaListado.mostrarResultados(resultados);

            if (resultados.isEmpty()) {
                vistaListado.mostrarMensaje("No se encontraron consultas con los criterios seleccionados.", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            vistaListado.mostrarMensaje("Error al buscar: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // Visualizar el datos de la consulta seleccionada.

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

            case "SALIR":
                vistaListado.dispose();
                break; // Salir del switch
            default:
                break;
        }
    }
}