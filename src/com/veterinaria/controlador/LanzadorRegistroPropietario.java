package com.veterinaria.controlador;

import com.veterinaria.modelo.MascotaService;
import com.veterinaria.modelo.RegistroPropietarioService;
import com.veterinaria.vista.VentanaRegistroMascota;
import com.veterinaria.vista.VentanaRegistroPropietario;

import javax.swing.JDesktopPane;
import java.awt.Dimension;

public class LanzadorRegistroPropietario implements ILanzadorModulo {

    private final RegistroPropietarioService registroPropietarioService;
    private final JDesktopPane escritorio;

    public LanzadorRegistroPropietario(RegistroPropietarioService registroPropietarioService, JDesktopPane escritorio) {
        this.registroPropietarioService = registroPropietarioService;
        this.escritorio = escritorio;
    }


    @Override
    public void lanzar() {
        try {
            VentanaRegistroPropietario vistaRegistroPropietario = new VentanaRegistroPropietario();

            // Crea el controlador, inyectando el servicio y la vista
            new ControladorRegistroPropietario(vistaRegistroPropietario, this.registroPropietarioService, escritorio);
            // Añadir, centrar y mostrar
            this.escritorio.add(vistaRegistroPropietario);

            Dimension desktopSize = escritorio.getSize();
            Dimension frameSize = vistaRegistroPropietario.getSize();
            int x = (desktopSize.width - frameSize.width) / 2;
            int y = (desktopSize.height - frameSize.height) / 2;
            vistaRegistroPropietario.setLocation(Math.max(0, x), Math.max(0, y));

            vistaRegistroPropietario.setVisible(true);
            //vistaMascota.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            // Mostrar error si falla el lanzamiento
        }
    }
}