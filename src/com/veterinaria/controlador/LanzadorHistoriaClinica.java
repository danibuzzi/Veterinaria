package com.veterinaria.controlador;

import com.veterinaria.modelo.HistoriaClinicaService;
import com.veterinaria.vista.VentanaHistoriaClinica2;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 * Lanzador para el módulo de Historia Clínica.
 */
public class LanzadorHistoriaClinica implements ILanzadorModulo {

    private final HistoriaClinicaService historiaClinicaService;
    private final JDesktopPane escritorio;

    // CONSTRUCTOR: Recibe el Service y el Contenedor
    public LanzadorHistoriaClinica(HistoriaClinicaService historiaClinicaService, JDesktopPane escritorio) {
        this.historiaClinicaService = historiaClinicaService;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        try {
            // 1. Crear la Vista
            VentanaHistoriaClinica2 vistaListado = new VentanaHistoriaClinica2(escritorio);

            // 2. CREAR EL CONTROLADOR
            // Mantenemos los 3 argumentos tal como lo tienes
            ControladorHistoriaClinica controlador = new ControladorHistoriaClinica(
                    this.historiaClinicaService, // El Modelo/Service
                    vistaListado,               // La Vista
                    this.escritorio             // El tercer argumento que necesitas
            );

            // 🛑 CRÍTICO: La conexión vista-controlador sucede dentro del constructor de 'ControladorHistoriaClinica'
            // NO se necesita vistaListado.setControlador(controlador); aquí.

            // 3. Añadir, centrar y mostrar
            this.escritorio.add(vistaListado);

            int x = (escritorio.getWidth() - vistaListado.getWidth()) / 2;
            int y = (escritorio.getHeight() - vistaListado.getHeight()) / 2;
            vistaListado.setLocation(Math.max(0, x), Math.max(0, y));

            vistaListado.setVisible(true);
            vistaListado.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al lanzar Historia Clínica: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}