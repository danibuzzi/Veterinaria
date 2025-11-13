package com.veterinaria.controlador;



import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.PropietarioService;
import com.veterinaria.vista.VentanaModificacionPropietario;
import com.veterinaria.vista.VentanaConsultaPropietario; // Para refrescar la grilla padre

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ControladorModificacionPropietario implements ActionListener {
    private ControladorModificacionPropietario controlador;
    private final VentanaModificacionPropietario vista;
    private final PropietarioService propietarioService;
    private final Propietario propietarioOriginal; // Instancia original con el ID

    public ControladorModificacionPropietario(VentanaModificacionPropietario vista, PropietarioService propietarioService, Propietario propietarioOriginal) {
        this.vista = vista;
        this.propietarioService = propietarioService;
        this.propietarioOriginal = propietarioOriginal;

        this.vista.establecerControlador(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        try {
            switch (comando) {
                case "GUARDAR":
                    guardarModificacion();
                    break;
                case "SALIR":
                    vista.dispose();
                    break;
            }
        } catch (Exception ex) {
            vista.mostrarError("Error en la operación: " + ex.getMessage());
        }
    }

    private void guardarModificacion() {
        // 1. Validaciones de la Vista (Campos vacíos)
        if (!vista.validarCamposObligatorios()) {
            return;
        }

        try {
            // 2. Mapear datos de la Vista a una nueva instancia del Modelo
            // Usamos el constructor de Propietario

            // Conversión de Date (JDateChooser) a LocalDate
            LocalDate fechaNacimientoLocal = null;
            Date fechaUtil = vista.getFechaNacimiento();
            if (fechaUtil != null) {
                fechaNacimientoLocal = fechaUtil.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            // Creamos la instancia con los datos actualizados, manteniendo el ID y DNI original
            Propietario propietarioModificado = new Propietario(
                    propietarioOriginal.getIdPropietario(), // ID ORIGINAL
                    propietarioOriginal.getDni(), // DNI ORIGINAL (no se modifica)
                    vista.getNombres(),
                    vista.getApellidos(),
                    fechaNacimientoLocal,
                    vista.getDireccion(),
                    vista.getPais(),
                    vista.getCiudad(),
                    vista.getTelefono(),
                    vista.getEmail()
                    //propietarioOriginal.isActivo() // Mantenemos el estado activo
            );

            // 3. Llamada al Service (Aquí se realizan las validaciones de formato (Email, Teléfono))
            propietarioService.actualizarPropietario(propietarioModificado);

            vista.mostrarMensaje("Propietario actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose();

            // 4. Refrescar la grilla de la ventana de consulta (Vista Padre)
            VentanaConsultaPropietario vistaPadre = vista.getVistaPadre();
            if (vistaPadre != null) {
                ControladorConsultaPropietario controladorConsulta = vistaPadre.getControlador();
               // (ControladorConsultaPropietario) vistaPadre.getControlador().buscarPropietarios();
            }

        } catch (IllegalArgumentException ex) {
            // Captura errores de validación (campos vacíos, DNI, Email, Teléfono)
            vista.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            vista.mostrarError("Error al guardar la modificación: " + ex.getMessage());
        }
    }

}