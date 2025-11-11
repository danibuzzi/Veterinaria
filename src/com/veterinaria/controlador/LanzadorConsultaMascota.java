// Archivo: com/veterinaria/controlador/LanzadorConsultaMascota.java

package com.veterinaria.controlador;

import com.veterinaria.modelo.MascotaService;
import com.veterinaria.vista.VentanaConsultaMascota;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;

/**
 * Lanzador para el módulo de Consulta y Gestión de Mascotas.
 * Ensambla la Vista y el Controlador.
 */
public class LanzadorConsultaMascota implements ILanzadorModulo {

    private final MascotaService mascotaService;
    private final JDesktopPane escritorio;

    public LanzadorConsultaMascota(MascotaService mascotaService, JDesktopPane escritorio) {
        this.mascotaService = mascotaService;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        try {
            // 1. Crear la Vista (JInternalFrame)
            VentanaConsultaMascota vistaConsulta = new VentanaConsultaMascota();

            // 2. Crear el Controlador, inyectando la vista, el servicio y el escritorio
            // para que el controlador pueda abrir la ventana de Modificación sobre él.
            ControladorConsultaMascota controladorConsulta = new ControladorConsultaMascota(
                    vistaConsulta,
                    this.mascotaService,
                    this.escritorio
            );

            // 3. Añadir, centrar y mostrar
            this.escritorio.add(vistaConsulta);

            // Lógica para centrar la ventana interna
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = vistaConsulta.getSize();
            int x = (desktopSize.width - jInternalFrameSize.width) / 2;
            int y = (desktopSize.height - jInternalFrameSize.height) / 2;
            vistaConsulta.setLocation(Math.max(0, x), Math.max(0, y));

            vistaConsulta.setVisible(true);
            vistaConsulta.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al lanzar la Consulta de Mascotas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
