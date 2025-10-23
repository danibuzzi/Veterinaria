package com.veterinaria.controlador;

import com.veterinaria.modelo.GestorTurno3;
import com.veterinaria.vista.VentanaRegistroTurno3;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class LanzadorRegistroTurno implements ILanzadorModulo {

    private final GestorTurno3 gestorTurno;
    private final JDesktopPane escritorio;

    // 🛑 Constructor: Recibe el Gestor y el Escritorio para inyección


    public LanzadorRegistroTurno(GestorTurno3 gestorTurno, JDesktopPane escritorio) {
        this.gestorTurno = gestorTurno;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        //  La,kamao el gestor para Registro
        if (this.gestorTurno instanceof GestorTurno3) {
            GestorTurno3 gestorV3 = (GestorTurno3) this.gestorTurno;

            // 1. Crear la Vista
            VentanaRegistroTurno3 vistaRegistro = new VentanaRegistroTurno3(gestorV3);

            // 2. Crear el Controlador e Inyectar
            ControladorTurno3 controladorTurno = new ControladorTurno3(gestorV3, vistaRegistro);

            // 3. Conectar el Controlador a la Vista
            vistaRegistro.setControlador(controladorTurno);

            // 4. Añadir y mostrar en el escritorio
            escritorio.add(vistaRegistro);
            vistaRegistro.setVisible(true);

            // 5. Centrar la ventana interna
            vistaRegistro.setLocation(
                    (escritorio.getWidth() - vistaRegistro.getWidth()) / 2,
                    (escritorio.getHeight() - vistaRegistro.getHeight()) / 2
            );


            try {
                vistaRegistro.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {}

        } else {
            // Manejo de la lógica
        }
    }
}
