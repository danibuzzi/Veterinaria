package com.veterinaria.controlador;




import com.veterinaria.modelo.GestorGestionTurnos;
import com.veterinaria.modelo.FactoriaServicios;
import com.veterinaria.vista.VentanaGestionTurnos;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.*;


public class LanzadorGestionTurnos implements ILanzadorModulo {

    private final GestorGestionTurnos gestorGestionTurnos;
    private final JDesktopPane escritorio;

    // 🛑 CONSTRUCTOR: (GestorGestionTurnos y JDesktopPane)
    public LanzadorGestionTurnos(GestorGestionTurnos gestorGestionTurnos, JDesktopPane escritorio) {
        this.gestorGestionTurnos = gestorGestionTurnos;
        this.escritorio = escritorio;
    }


    @Override
    public void lanzar() {
        try {
            // 1. Crear la Vista  Pasar el escritorio)

            VentanaGestionTurnos vista = new VentanaGestionTurnos(this.escritorio);

            // 2. OBTENER LA FACTORÍA Y ENSAMBLAR EL CONTROLADOR

            FactoriaServicios factoria = this.gestorGestionTurnos.getFactoria();

            ControladorGestionTurnos controlador = factoria.crearControladorGestionTurnos(vista);

            // ... (Mostrar y centrar la vista)
            this.escritorio.add(vista);

            //Agregado centrado

            // Lógica para centrar la ventana interna
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = vista.getSize();
            int x = (desktopSize.width - jInternalFrameSize.width) / 2;
            int y = (desktopSize.height - jInternalFrameSize.height) / 2;
            vista.setLocation(Math.max(0, x), Math.max(0, y));

            vista.setVisible(true);
            vista.setSelected(true);



            vista.setVisible(true);

        } catch (Exception e) {
            //  Imprime el error que estaba oculto
            e.printStackTrace();
            System.err.println("¡ERROR! Fallo al intentar lanzar el módulo de Gestión de Turnos.");
        }
    }
}