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
            // 1. Crear la Vista (la que ya diseñamos con los combos tipados)
            VentanaRegistroConsulta vistaConsulta = new VentanaRegistroConsulta(escritorio);

            // 2. Crear el Controlador (Asumimos que tiene este constructor)
            ControladorRegistroConsulta controladorConsulta = new ControladorRegistroConsulta(
                    this.consultaService, // <-- ✅ Servicio (Modelo)
                    vistaConsulta       // <-- ✅ Vista
            );
            // 🛑 NOTA: EL CONTROLADOR DEBE RECIBIR EL SERVICE EN SU CONSTRUCTOR O TENER UN SETTER.
            // Para simplificar, asumiremos que usa el constructor que ya diseñamos:
            // new ControladorRegistroConsulta(VentanaRegistroConsulta vista)
            // donde el Service se inicializa dentro del Controlador.

            // 3. El Controlador ya se encarga de: vista.setControlador(this) y cargar datos iniciales.

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