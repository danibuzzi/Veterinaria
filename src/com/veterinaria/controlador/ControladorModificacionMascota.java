package com.veterinaria.controlador;

// Archivo: com/veterinaria/controlador/ControladorModificacionMascota.java


import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.MascotaService;
import com.veterinaria.vista.VentanaModificacionMascota;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorModificacionMascota implements ActionListener {

    private final VentanaModificacionMascota vista;
    private final MascotaService mascotaService;
    private final Mascota mascotaOriginal; // Instancia original, contiene el idPropietario

    public ControladorModificacionMascota(VentanaModificacionMascota vista, MascotaService mascotaService, Mascota mascotaOriginal) {
        this.vista = vista;
        this.mascotaService = mascotaService;
        this.mascotaOriginal = mascotaOriginal;

        this.vista.establecerControlador(this); // Conexión Vista -> Controlador
    }

    // --- ACCIONES DE LOS BOTONES ---

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        try {
            switch (comando) {
                case "GUARDAR":
                    guardarModificacion();
                    break;
                case "SALIR":
                    // Delegar el control de salida a la vista (puede pedir confirmación)
                    vista.salir();
                    break;
            }
        } catch (Exception ex) {
            vista.mostrarError("Error en la operación: " + ex.getMessage());
        }
    }

    private void guardarModificacion() {
        if (!vista.validarCamposObligatorios()) {
            return;
        }

        try {
            //  La vista crea una nueva instancia de Mascota
            // con los datos editados, pero le re-asigna el ID de Propietario
            // de la instancia original (mascotaOriginal) para la actualización.
            Mascota mascotaModificada = vista.getMascotaParaGuardar(mascotaOriginal.getIdPropietario());

            // Llamamos al Service para hacer la actualización en la BD
            mascotaService.actualizarMascota(mascotaModificada);

            vista.mostrarMensaje("Mascota actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose();

            // Refrescar la grilla de la ventana de consulta (Vista Padre)
            if (vista.getVistaPadre() != null) {
                vista.getVistaPadre().getControlador().buscarMascotas();
            }

        } catch (RuntimeException ex) {
            vista.mostrarError("Error al guardar la modificación: " + ex.getMessage());
        }
    }
}
