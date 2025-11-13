package com.veterinaria.controlador;


import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.MascotaService;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.vista.VentanaConsultaMascota;
import com.veterinaria.vista.VentanaModificacionMascota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorConsultaMascota implements ActionListener {

    private final VentanaConsultaMascota vista;
    private final MascotaService mascotaService;
    private final JDesktopPane escritorio; // Para abrir la ventana de modificación

    public ControladorConsultaMascota(VentanaConsultaMascota vista, MascotaService mascotaService, JDesktopPane escritorio) {
        this.vista = vista;
        this.mascotaService = mascotaService;
        this.escritorio = escritorio;

        this.vista.establecerControlador(this); // Conexión Vista -> Controlador
        cargarPropietarios(); // Cargar el ComboBox al iniciar
    }

    private void cargarPropietarios() {
        try {
            List<Propietario> propietarios = mascotaService.listarPropietariosActivos();
           // Propietario opcionInicial = new Propietario(-1, "Seleccione Propietario");
            // Crear y cargar el ComboBoxModel
            DefaultComboBoxModel<Propietario> model = new DefaultComboBoxModel<>();
            model.addElement(null); // Opción por defecto (ej. "Todos" o null)
            for (Propietario p : propietarios) {
                model.addElement(p);
            }
            vista.cargarPropietarios(model);
        } catch (RuntimeException e) {
            vista.mostrarError("Error al cargar la lista de propietarios: " + e.getMessage());
        }
    }

    // --- ACCIONES DE LOS BOTONES ---

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        try {
            switch (comando) {
                case "BUSCAR":
                    buscarMascotas();
                    break;
                case "MODIFICAR":
                    iniciarModificacion();
                    break;
                case "ELIMINAR":
                    eliminarMascota();
                    break;
                case "SALIR":
                    vista.salir();
                    break;
            }
        } catch (Exception ex) {
            vista.mostrarError("Error en la operación: " + ex.getMessage());
        }
    }

    public void buscarMascotas() {
        Propietario propietarioSeleccionado = (Propietario) vista.getPropietarioComboBox().getSelectedItem();

        if (propietarioSeleccionado == null) {
            vista.mostrarError("Debe seleccionar un propietario para buscar sus mascotas.");
            vista.getTableModel().setData(List.of()); // Limpiar grilla
            return;
        }

        try {
            int idPropietario = propietarioSeleccionado.getIdPropietario();
            List<Mascota> resultados = mascotaService.listarMascotasActivasPorPropietario(idPropietario);
            vista.getTableModel().setData(resultados);
            vista.setBotonesAccionHabilitados(false); // Desactivar al recargar
        } catch (RuntimeException e) {
            vista.mostrarError("Error al buscar mascotas: " + e.getMessage());
        }
    }

    private void iniciarModificacion() {
        int selectedRow = vista.getTable().getSelectedRow();
        if (selectedRow == -1) return;

        Mascota mascotaSeleccionada = vista.getTableModel().getMascotaAt(selectedRow);

        if (mascotaSeleccionada != null) {
            try {
                // Obtener datos completos de la mascota por ID (por si se necesita más info)
                Mascota mascotaCompleta = mascotaService.obtenerMascotaPorId(mascotaSeleccionada.getIdMascota());

                //Obtener el Propietario seleccionado del JComboBox de la vista de consulta
                Propietario propietarioSeleccionado = (Propietario) vista.getPropietarioComboBox().getSelectedItem();

                // Formateao la cadena del Propietario con el orden Apellido, Nombre
                String infoPropietario;
                if (propietarioSeleccionado != null) {
                    infoPropietario = propietarioSeleccionado.getApellido() + ", " + propietarioSeleccionado.getNombre();
                } else {

                    infoPropietario = "Propietario Desconocido";
                }

                // Crear e inicializar la ventana de modificación
                VentanaModificacionMascota vistaModificacion = new VentanaModificacionMascota(mascotaCompleta.getIdMascota(), vista);

                // Crear el controlador de modificación
                ControladorModificacionMascota controladorModificacion = new ControladorModificacionMascota(
                        vistaModificacion,
                        mascotaService,
                        mascotaCompleta // Pasamos la instancia de Mascota para inicializar
                );

                // Cargar datos en la nueva ventana
                vistaModificacion.cargarDatosMascota(
                        mascotaCompleta,
                        //mascotaSeleccionada.toString() // Mostrar el nombre del propietario si está disponible
                        infoPropietario
                );

                // Mostrar la ventana

//
                SwingUtilities.invokeLater(() -> {

                    vistaModificacion.pack();

                    // Calcular la posición central.
                    // EN ESTE MOMENTO, escritorio.getWidth() DEBE SER UN VALOR VÁLIDO.
                    int x = (escritorio.getWidth() - vistaModificacion.getWidth()) / 2;
                    int y = (escritorio.getHeight() - vistaModificacion.getHeight()) / 2;

                    // Aplicar el centrado
                    vistaModificacion.setLocation(x, y);
                });

               /* vista.pack();
                // Lógica para centrar la ventana interna
                Dimension desktopSize = escritorio.getSize();
                Dimension jInternalFrameSize = vista.getSize();
                int x = (desktopSize.width - jInternalFrameSize.width) / 2;
                int y = (desktopSize.height - jInternalFrameSize.height) / 2;
                vista.setLocation(Math.max(0, x), Math.max(0, y));*/

                escritorio.add(vistaModificacion);
                vistaModificacion.setVisible(true);
                //vistaModificacion.setSelected(true);

            } catch (RuntimeException ex) {
                vista.mostrarError("Error al cargar la ficha de modificación: " + ex.getMessage());
            }
        }
    }

    private void eliminarMascota() {
        int selectedRow = vista.getTable().getSelectedRow();
        if (selectedRow == -1) return;

        Mascota mascota = vista.getTableModel().getMascotaAt(selectedRow);

        if (mascota == null) return;

        int confirm = vista.mostrarConfirmacion("¿Confirma la ELIMINACIÓN LÓGICA de la mascota '" + mascota.getNombre() + "' (ID: " + mascota.getIdMascota() + ")?");

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // La eliminación usa el ID obtenido de la instancia de Mascota
                mascotaService.eliminarMascotaLogico(mascota.getIdMascota());

                vista.mostrarError("Mascota eliminada lógicamente con éxito: " + mascota.getNombre() + ".");
                buscarMascotas(); // Refrescar la grilla
            } catch (RuntimeException ex) {
                vista.mostrarError("Error al eliminar la mascota: " + ex.getMessage());
            }
        }
    }
}