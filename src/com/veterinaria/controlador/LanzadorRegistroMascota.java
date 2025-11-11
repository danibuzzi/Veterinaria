package com.veterinaria.controlador;

import com.veterinaria.modelo.MascotaService;
import com.veterinaria.vista.VentanaRegistroMascota;

import javax.swing.JDesktopPane;
import java.awt.Dimension;

public class LanzadorRegistroMascota implements ILanzadorModulo {

    private final MascotaService mascotaService;
    private final JDesktopPane escritorio;

    public LanzadorRegistroMascota(MascotaService mascotaService, JDesktopPane escritorio) {
        this.mascotaService = mascotaService;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        try {
            VentanaRegistroMascota vistaMascota = new VentanaRegistroMascota(escritorio);

            // Crea el controlador, inyectando el servicio y la vista
            new ControladorRegistroMascota(this.mascotaService, vistaMascota);

            // Añadir, centrar y mostrar
            this.escritorio.add(vistaMascota);

            Dimension desktopSize = escritorio.getSize();
            Dimension frameSize = vistaMascota.getSize();
            int x = (desktopSize.width - frameSize.width) / 2;
            int y = (desktopSize.height - frameSize.height) / 2;
            vistaMascota.setLocation(Math.max(0, x), Math.max(0, y));

            vistaMascota.setVisible(true);
            //vistaMascota.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            // Mostrar error si falla el lanzamiento
        }
    }
}