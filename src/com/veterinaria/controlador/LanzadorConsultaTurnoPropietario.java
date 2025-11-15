package com.veterinaria.controlador;

import com.veterinaria.modelo.TurnoPropietarioService;
import com.veterinaria.vista.VentanaTurnosPropietario;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;

/**
 * Lanzador para el módulo de Consulta de Turnos por Propietario.
 * Ensambla la Vista y el Controlador.
 */
public class LanzadorConsultaTurnoPropietario implements ILanzadorModulo {

    private final TurnoPropietarioService serviceTurnoPropietario;
    private final JDesktopPane escritorio;

    // Constructor del Lanzador: Recibe solo 2 dependencias externas
    public LanzadorConsultaTurnoPropietario(TurnoPropietarioService serviceTurnoPropietario, JDesktopPane escritorio) {
        this.serviceTurnoPropietario = serviceTurnoPropietario;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        try {
            // 1. Obtener/Crear la Vista (la cual también crea el Controlador)
            VentanaTurnosPropietario vistaConsultaTurnoPropietario = getVentanaTurnosPropietario();

            // 2. Agregar la ventana interna al escritorio
            this.escritorio.add(vistaConsultaTurnoPropietario);

            // Lógica para centrar la ventana interna
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = vistaConsultaTurnoPropietario.getSize();
            int x = (desktopSize.width - jInternalFrameSize.width) / 2;
            int y = (desktopSize.height - jInternalFrameSize.height) / 2;
            vistaConsultaTurnoPropietario.setLocation(Math.max(0, x), Math.max(0, y));

            vistaConsultaTurnoPropietario.setVisible(true);
            vistaConsultaTurnoPropietario.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            // Revisar este mensaje, ya que puede estar duplicado
            JOptionPane.showMessageDialog(null, "Error al lanzar la Consulta de Turnos de Propietario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private VentanaTurnosPropietario getVentanaTurnosPropietario() {
        // 1. CREA LA VISTA (JInternalFrame)
        VentanaTurnosPropietario vistaConsultaTurnoPropietario = new VentanaTurnosPropietario();

        // 2. CREA EL CONTROLADOR, INYECTANDO 3 ARGUMENTOS:
        // (Vista recién creada, Service, Escritorio)
        ControladorConsultaTurnosPropietario controladorConsulta = new ControladorConsultaTurnosPropietario(
                vistaConsultaTurnoPropietario,   // <-- 1. La Vista (objeto local)
                this.serviceTurnoPropietario,    // <-- 2. El Service (campo del Lanzador)
                this.escritorio                  // <-- 3. El Escritorio (campo del Lanzador)
        );
        return vistaConsultaTurnoPropietario;
    }
}
