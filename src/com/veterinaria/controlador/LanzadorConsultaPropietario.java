package com.veterinaria.controlador;


import com.veterinaria.modelo.MascotaService;
import com.veterinaria.modelo.PropietarioService;
import com.veterinaria.vista.VentanaConsultaMascota;
import com.veterinaria.vista.VentanaConsultaPropietario;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;

/**
 * Lanzador para el módulo de Consulta y Gestión de Mascotas.
 * Ensambla la Vista y el Controlador.
 */
public class LanzadorConsultaPropietario implements ILanzadorModulo {

    private final PropietarioService propietarioService;
    private final JDesktopPane escritorio;

    public LanzadorConsultaPropietario(PropietarioService propietarioService, JDesktopPane escritorio) {
        this.propietarioService = propietarioService;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        try {
            // 1. Crear la Vista (JInternalFrame)
            VentanaConsultaPropietario vistaConsulta = new VentanaConsultaPropietario();

            // 2. Crear el Controlador, inyectando la vista, el servicio y el escritorio
            // para que el controlador pueda abrir la ventana de Modificación sobre él.
            ControladorConsultaPropietario controladorConsulta = new ControladorConsultaPropietario(
                    vistaConsulta,
                    this.propietarioService,
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
