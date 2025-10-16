package com.veterinaria.controlador;

// Archivo: com/veterinaria/controlador/LanzadorGestionTurnos.java (NUEVO)


/*import com.veterinaria.modelo.GestorGestionTurnos; // Importa el Gestor correcto
import com.veterinaria.vista.VentanaGestionTurnos;     // Importa la Vista que acabas de terminar
import javax.swing.JDesktopPane;

public class LanzadorGestionTurnos implements ILanzadorModulo {

    private final GestorGestionTurnos gestorMantenimiento;
    private final JDesktopPane escritorio;

    // El constructor recibe el Gestor y el JDesktopPane
    public LanzadorGestionTurnos(GestorGestionTurnos gestor, JDesktopPane escritorio) {
        this.gestorMantenimiento = gestor;
        this.escritorio = escritorio;
    }

    @Override
    public void lanzar() {
        // 1. Crear la Vista (JInternalFrame)
        VentanaGestionTurnos vista = new VentanaGestionTurnos();

        // 2. Crear el Controlador (inyecta la lógica de negocio y la vista)
        ControladorGestionTurnos controlador = new ControladorGestionTurnos(gestorMantenimiento, vista);

        // 3. Conectar la Vista con el Controlador para que la vista sepa quién manejará sus eventos
        vista.setControlador(controlador);

        // 4. Agregar y mostrar la ventana en el escritorio
        escritorio.add(vista);
        vista.setVisible(true);

        // Opcional: Centrar la ventana
        int x = (escritorio.getWidth() - vista.getWidth()) / 2;
        int y = (escritorio.getHeight() - vista.getHeight()) / 2;
        vista.setLocation(x, y);
    }*/


import com.veterinaria.modelo.GestorGestionTurnos;
import com.veterinaria.modelo.FactoriaServicios;
import com.veterinaria.vista.VentanaGestionTurnos;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;


public class LanzadorGestionTurnos implements ILanzadorModulo {

    private final GestorGestionTurnos gestorGestionTurnos;
    private final JDesktopPane escritorio;

    // 🛑 CONSTRUCTOR: Sólo 2 parámetros (GestorGestionTurnos y JDesktopPane)
    public LanzadorGestionTurnos(GestorGestionTurnos gestorGestionTurnos, JDesktopPane escritorio) {
        this.gestorGestionTurnos = gestorGestionTurnos;
        this.escritorio = escritorio;
    }


    @Override
    public void lanzar() {
        try {
            // 1. Crear la Vista (CRÍTICO: Pasar el escritorio)
            // Si VentanaGestionTurnos no tiene el constructor con JDesktopPane, falla aquí.
            VentanaGestionTurnos vista = new VentanaGestionTurnos(this.escritorio);

            // 2. OBTENER LA FACTORÍA Y ENSAMBLAR EL CONTROLADOR
            // 🛑 PUNTO DE FALLO PRINCIPAL: Si gestorGestionTurnos es null o getFactoria() devuelve null.
            FactoriaServicios factoria = this.gestorGestionTurnos.getFactoria();

            ControladorGestionTurnos controlador = factoria.crearControladorGestionTurnos(vista);

            // ... (Mostrar y centrar la vista)
            this.escritorio.add(vista);
            vista.setVisible(true);

        } catch (Exception e) {
            // 🛑 ESTO ES LO CRÍTICO: Imprime el error que estaba oculto
            e.printStackTrace();
            System.err.println("¡ERROR! Fallo al intentar lanzar el módulo de Gestión de Turnos.");
        }
    }
}