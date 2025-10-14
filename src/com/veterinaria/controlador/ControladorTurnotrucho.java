package com.veterinaria.controlador;

import com.veterinaria.modelo.TurnoDAO;
import com.veterinaria.vista.VentanaRegistroTurno2;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ControladorTurnotrucho implements ActionListener {

    private VentanaRegistroTurno2 vista;
    private TurnoDAO modeloDAO;

    // Constructor del Controlador
    public ControladorTurnotrucho(VentanaRegistroTurno2 vista, TurnoDAO modeloDAO) {
        this.vista = vista;
        this.modeloDAO = modeloDAO;

        // 🎯 CORRECCIÓN DE TIMING: Usar invokeLater para que la configuración se haga
        // DESPUÉS de que la Vista haya terminado de dibujar sus componentes.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Configuración segura de los listeners de la Vista
                vista.setControlador(ControladorTurnotrucho.this);
                vista.setListenerCargaMascotas(ControladorTurnotrucho.this);
                // NOTA: Se ha eliminado la llamada a cargarDatosIniciales()

                // Si tienes otra lógica de inicialización (ej. cargar propietarios), debe ir aquí.
                // Ejemplo (Si usas la DAO para propietarios):
                // try {
                //     List<String> propietarios = modeloDAO.obtenerNombresPropietarios();
                //     vista.cargarItems(vista.getComboPropietario(), propietarios);
                // } catch (Exception ex) { /* Manejo de error */ }
            }
        });
    }



    // 🛑 MÉTODO ACTUALIZAR MASCOTAS CORREGIDO PARA EVITAR NULLPOINTER (Línea 44)
    // Archivo: ControladorTurno.java

    private void actualizarMascotas() {
        String nombrePropietario = vista.getNombrePropietarioSeleccionado();

        // CORRECCIÓN: Evitar NullPointerException
        String seleccionado = String.valueOf(nombrePropietario);

        if (seleccionado.equals("null") ||
                seleccionado.startsWith("--- Seleccione Propietario ---") ||
                seleccionado.trim().isEmpty()) {

            vista.getComboMascota().removeAllItems();
            vista.getComboMascota().addItem("--- Seleccione Mascota ---");
            return;
        }

        try {
            // 🛑 CAMBIO CLAVE: Comentar o corregir la línea que da error de compilación.
            // La comentaremos para que puedas compilar y ver la interfaz.
            // List<String> mascotas = modeloDAO.obtenerNombresMascotasPorPropietario(nombrePropietario);

            // ⚠️ OJO: Para que el programa compile, si no existe el DAO, usa una lista vacía
            // O, si sabes el nombre correcto del método, úsalo aquí.

            // Creamos una lista vacía temporal para permitir la compilación y la ejecución.
            // Si el método SÍ existe pero tiene otro nombre, reemplaza la línea comentada.
            java.util.List<String> mascotas = new java.util.ArrayList<>();

            vista.cargarItems(vista.getComboMascota(), mascotas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar mascotas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if ("GUARDAR_TURNO".equals(comando)) {
            // ... Lógica para guardar ...
        } else if ("PROPIETARIO_SELECCIONADO".equals(comando)) {
            // Esto se dispara cuando se carga la Vista por primera vez, fallando.
            actualizarMascotas();
        }
    }


}