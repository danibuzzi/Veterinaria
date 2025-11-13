package com.veterinaria.controlador;

import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.PropietarioService;
import com.veterinaria.vista.VentanaConsultaPropietario;
import com.veterinaria.vista.VentanaModificacionPropietario;
import com.veterinaria.modelo.PropietarioTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;

public class ControladorConsultaPropietario implements ActionListener, ListSelectionListener {

    private final VentanaConsultaPropietario vista;
    private final PropietarioService propietarioService;
    private final JDesktopPane escritorio;

    // Columnas a mostrar en la grilla (DNI, Apellidos, Nombres, Email, Telefono)
    private static final String[] COLUMNAS = {"", "DNI", "Apellidos", "Nombres", "Email", "Teléfono"};
    private static final int COLUMNA_ID = 0;
    public ControladorConsultaPropietario(VentanaConsultaPropietario vista, PropietarioService propietarioService, JDesktopPane escritorio) {
        this.vista = vista;
        this.propietarioService = propietarioService;
        this.escritorio = escritorio;

        this.vista.establecerControlador(this); // Conecta botones (Buscar, Modificar, Eliminar, Salir)
        this.vista.getTable().getSelectionModel().addListSelectionListener(this); // Listener para la selección de fila

        actualizarEstadoBotones(false); // Inicialmente deshabilitados
    }

    // --- 1. Eventos de Botón (ActionListener) ---
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            switch (comando) {
                case "BUSCAR":
                    buscarPropietarios();
                    break;
                case "MODIFICAR":
                    System.out.println("Ingrese a modificar");
                    iniciarModificacion();
                    break;
                case "ELIMINAR":
                    eliminarPropietario();
                    break;
                case "SALIR":
                    vista.dispose();
                    break;
            }
        } catch (Exception ex) {
            vista.mostrarError("Error en la operación: " + ex.getMessage());
        }
    }

    // --- 2. Eventos de Selección de Fila (ListSelectionListener) ---
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Solo procesar el evento al finalizar el ajuste de la selección
        if (!e.getValueIsAdjusting()) {
            boolean filaSeleccionada = vista.getTable().getSelectedRow() != -1;
            actualizarEstadoBotones(filaSeleccionada);
        }
    }

    // --- 3. Lógica de Búsqueda ---
    public void buscarPropietarios() {
        String textoBusqueda = vista.getSearchField().getText().trim();
        String tipoBusqueda = vista.getApellidoRadio().isSelected() ? "APELLIDO" : "DNI";

        try {
            // 1. Obtener datos del Service
            List<Propietario> resultados = propietarioService.buscarPropietarios(textoBusqueda, tipoBusqueda);

            // 2. Mapear datos a la tabla (Aquí necesitamos un TableModel)

            PropietarioTableModel model = new PropietarioTableModel(resultados, COLUMNAS);
            vista.setTableModel(model);

            // Si la búsqueda fue exitosa, pero no hubo resultados:
            if (resultados.isEmpty()) {
                vista.mostrarMensaje("No se encontraron propietarios con el criterio de búsqueda.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            // Deshabilitar botones después de una nueva búsqueda (hasta que se seleccione una fila)
            actualizarEstadoBotones(false);

        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
            // Limpiar la tabla en caso de error de validación
            vista.setTableModel(new PropietarioTableModel(List.of(), COLUMNAS));
            actualizarEstadoBotones(false);
        }
    }

    // --- 4. Lógica de Modificación ---
    private void iniciarModificacion() {
        int selectedRow = vista.getTable().getSelectedRow();
        if (selectedRow == -1) {
            vista.mostrarError("Debe seleccionar un propietario de la lista para modificar.");
            return; // Ya chequeado por el botón deshabilitado, pero por seguridad
        }
        try {
            // 1. Obtener ID de la fila seleccionada (se asume que la columna 0 contiene el ID)
           // int idPropietario = (int) vista.getTableModel().getPropietarioAt(selectedRow).getIdPropietario();
            //.getTable().getValueAt(selectedRow, COLUMNA_ID);
           // int idPropietario;

          Propietario propietarioDelModelo =vista.getTableModel().getPropietarioAt(selectedRow);
// DEBUG PUNTO 1: ¿Qué ID se obtiene del modelo?
            System.out.println("DEBUG MODIFICAR - ID obtenido del Modelo: " + propietarioDelModelo.getIdPropietario());

            // 2. Control de Nulidad (El PropietarioTableModel puede estar devolviendo null)
            if (propietarioDelModelo == null || propietarioDelModelo.getIdPropietario() <= 0) {
                vista.mostrarError("No se pudo obtener la información completa del propietario de la fila seleccionada. El registro puede ser inválido.");
                return;
            }


            // Obtener la instancia completa de Propietario desde el Service
            Propietario propietarioOriginal = propietarioService.obtenerPropietarioPorId(propietarioDelModelo.getIdPropietario());

          //DEBUG PUNTO 2: ¿Qué ID se obtiene del servicio?
            System.out.println("DEBUG MODIFICAR - ID obtenido del Servicio: " + propietarioOriginal.getIdPropietario());

            // 3. Ensamblaje MVC para Modificación
            VentanaModificacionPropietario vistaMod = new VentanaModificacionPropietario(String.valueOf(propietarioOriginal.getIdPropietario()), vista);

            // El controlador necesita la vista, el service y la instancia original del propietario
            ControladorModificacionPropietario controladorMod = new ControladorModificacionPropietario(
                    vistaMod,
                    propietarioService,
                    propietarioOriginal
            );

            // 4. Cargar datos en la Vista (la vista lo debe hacer con su método)
            //vistaMod.cargarDatosPropietario(propietarioOriginal);
            vistaMod.setDatosPropietario(propietarioOriginal);
            // 5. Visualización (Aplicando la lógica de centrado)
            if (escritorio != null) {
                escritorio.add(vistaMod);
                vistaMod.setVisible(true);

                // Centrado definitivo (usando invokeLater como se corrigió)
                SwingUtilities.invokeLater(() -> {
                    vistaMod.pack();
                    int x = (escritorio.getWidth() - vistaMod.getWidth()) / 2;
                    int y = (escritorio.getHeight() - vistaMod.getHeight()) / 2;
                    vistaMod.setLocation(Math.max(0, x), Math.max(0, y));
                });
            }

        } catch (Exception ex) {
            vista.mostrarError("Error al iniciar la modificación: " + ex.getMessage());
        }
    }

    // Archivo: ControladorConsultaPropietario.java (MÉTODO iniciarModificacion)

    /*private void iniciarModificacion() {
        int selectedRow = vista.getTable().getSelectedRow();
        if (selectedRow == -1) {
            vista.mostrarError("Debe seleccionar un propietario de la lista para modificar.");
            return;
        }

        Propietario propietarioSeleccionado = null; // Declaramos fuera del try para visibilidad

        try {
            // 1. Obtener objeto y validar la nulidad (¡Correcto!)
            propietarioSeleccionado = vista.getTableModel().getPropietarioAt(selectedRow);

            if (propietarioSeleccionado == null || propietarioSeleccionado.getIdPropietario() <= 0) {
                vista.mostrarError("Error: El registro seleccionado no es válido.");
                return;
            }

            // 2. Obtener instancia completa del Service (Verificamos la BD)
            Propietario propietarioOriginal = propietarioService.obtenerPropietarioPorId(propietarioSeleccionado.getIdPropietario());

            if (propietarioOriginal == null) {
                vista.mostrarError("Error: No se pudo encontrar el propietario en la base de datos.");
                return;
            }

            // 3. Ensamblaje MVC para Modificación (Correcto)
            VentanaModificacionPropietario vistaMod = new VentanaModificacionPropietario(String.valueOf(propietarioOriginal.getIdPropietario()), vista);
            ControladorModificacionPropietario controladorMod = new ControladorModificacionPropietario(
                    vistaMod,
                    propietarioService,
                    propietarioOriginal
            );

            // 4. Cargar datos en la Vista
            vistaMod.setDatosPropietario(propietarioOriginal); // <-- REVISAR ESTE MÉTODO EN LA OTRA VISTA

            // 5. Visualización (Si el escritorio no es null, debería abrir)
            if (escritorio != null) {
                escritorio.add(vistaMod);
                vistaMod.setVisible(true);

                // Centrado... (Correcto)
                SwingUtilities.invokeLater(() -> {
                    vistaMod.pack();
                    int x = (escritorio.getWidth() - vistaMod.getWidth()) / 2;
                    int y = (escritorio.getHeight() - vistaMod.getHeight()) / 2;
                    vistaMod.setLocation(Math.max(0, x), Math.max(0, y));
                });
            }else {
                vistaMod.setVisible(true); // No hará nada si no ha sido añadido a un JDesktopPane.

                // Aquí es donde debería fallar y mostrar un error si la vista consulta no es un internal frame.
                vista.mostrarError("Error interno: No se pudo obtener la referencia al escritorio principal (JDesktopPane). La ventana de modificación no se puede abrir.");
            }

            vistaMod.setVisible(true);

        } catch (Exception ex) {
            // MUESTRE EL ERROR EXACTO EN EL CUADRO DE DIÁLOGO
            vista.mostrarError("ERROR AL ABRIR MODIFICACIÓN: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            ex.printStackTrace(); // ¡Y EN LA CONSOLA!
        }
    }*/

    // --- 5. Lógica de Eliminación ---
    private void eliminarPropietario() {
        int selectedRow = vista.getTable().getSelectedRow();
        if (selectedRow == -1) return;

        try {
            int idPropietario = (int) vista.getTable().getValueAt(selectedRow, COLUMNA_ID);
            String nombreCompleto = vista.getTable().getValueAt(selectedRow, 3).toString() + " " + vista.getTable().getValueAt(selectedRow, 2).toString(); // Nombres + Apellidos

            int confirm = vista.mostrarConfirmacion("¿Confirma la ELIMINACIÓN  del propietario: " + nombreCompleto + "?");

            if (confirm == JOptionPane.YES_OPTION) {
                propietarioService.eliminarPropietario(idPropietario);
                vista.mostrarMensaje("Propietario eliminado  con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                buscarPropietarios(); // Refrescar la grilla (vuelve a deshabilitar botones)
            }
        } catch (IllegalStateException ex) {
            // Captura el error de integridad referencial del Service
            vista.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            vista.mostrarError("Error al eliminar el propietario: " + ex.getMessage());
        }
    }

    // --- 6. Utilidad ---
    private void actualizarEstadoBotones(boolean habilitar) {
        vista.setBotonesAccionHabilitados(habilitar);
    }

   /* private void modificarPropietario() {
        int selectedRow = vista.getTable().getSelectedRow();
        if (selectedRow == -1) {
            vista.mostrarError("Debe seleccionar un propietario para modificar.");
            return;
        }

        int idPropietario = vista.getTableModel().getIdPropietarioAt(selectedRow);

        try {
            // 1. CARGAR el objeto Propietario completo del Service
            Propietario propietarioOriginal = propietarioService.obtenerPropietarioPorId(idPropietario);

            if (propietarioOriginal == null) {
                vista.mostrarError("Error: No se pudo cargar el propietario seleccionado.");
                return;
            }

            // --- LÓGICA DE CONVERSIÓN DE FECHA (CRÍTICO) ---
            // Su modelo usa LocalDate, pero el método de la vista espera java.util.Date.
            java.util.Date fechaNacimientoUtil = null;
            if (propietarioOriginal.getFechaNacimiento() != null) {
                fechaNacimientoUtil = java.util.Date.from(
                        propietarioOriginal.getFechaNacimiento()
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                );
            }
            // ------------------------------------------------

            // 2. Crear la Vista de Modificación
            VentanaModificacionPropietario vistaModificacion =
                    new VentanaModificacionPropietario(String.valueOf(idPropietario), (VentanaConsultaPropietario) vista);

            // 3. ¡LA LLAMADA QUE AHORA FUNCIONARÁ!
            // Llamamos al método de la Vista, desglosando el objeto Propietario en los 9 parámetros:
            vistaModificacion.setDatosPropietario(
                    propietarioOriginal.getDni(),
                    propietarioOriginal.getApellido(),
                    propietarioOriginal.getNombre(),
                    fechaNacimientoUtil, // Aquí se usa la fecha convertida
                    propietarioOriginal.getDireccion(),
                    propietarioOriginal.getTelefono(),
                    propietarioOriginal.getEmail(),
                    propietarioOriginal.getPais(),
                    propietarioOriginal.getCiudad()
            );

            // 4. Crear el Controlador de Modificación e inyectar dependencias
            ControladorModificacionPropietario controlador =
                    new ControladorModificacionPropietario(vistaModificacion, propietarioService, propietarioOriginal);

            // 5. Mostrar la ventana
            escritorio.add(vistaModificacion);
            vistaModificacion.setVisible(true);

        } catch (RuntimeException ex) {
            vista.mostrarError("Error al cargar la ficha de modificación: " + ex.getMessage());
        }
    }*/

}