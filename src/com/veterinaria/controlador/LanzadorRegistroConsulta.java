 package com.veterinaria.controlador;

import com.veterinaria.modelo.ConsultaService;
import com.veterinaria.vista.VentanaRegistroConsulta;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 * Lanzador para el módulo de Registro de Consultas (Ficha Clínica).
 * Ensambla la Vista, el Controlador y el Servicio.
 */
public class LanzadorRegistroConsulta implements ILanzadorModulo {

    private final ConsultaService consultaService;
    private final JDesktopPane escritorio;

    public LanzadorRegistroConsulta(ConsultaService consultaService, JDesktopPane escritorio) {
        this.consultaService = consultaService;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        try {
            // 1. Crear la Vista
            VentanaRegistroConsulta vistaConsulta = new VentanaRegistroConsulta(escritorio);

            // 2. Crear el Controlador

            ControladorRegistroConsulta controladorConsulta = new ControladorRegistroConsulta(
                    this.consultaService,
                    vistaConsulta
            );

            // 4. Añadir, centrar y mostrar
            this.escritorio.add(vistaConsulta);

            int x = (escritorio.getWidth() - vistaConsulta.getWidth()) / 2;
            int y = (escritorio.getHeight() - vistaConsulta.getHeight()) / 2;
            vistaConsulta.setLocation(Math.max(0, x), Math.max(0, y));

            vistaConsulta.setVisible(true);
            vistaConsulta.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al lanzar Registro de Consultas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}