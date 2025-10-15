package com.veterinaria.controlador;

// Archivo: com/veterinaria/controlador/LanzadorGestionTurnos.java (NUEVO)


import com.veterinaria.modelo.GestorGestionTurnos; // Importa el Gestor correcto
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
    }
}