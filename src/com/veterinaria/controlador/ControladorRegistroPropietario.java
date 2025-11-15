package com.veterinaria.controlador;

import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.RegistroPropietarioService;
import com.veterinaria.vista.VentanaRegistroPropietario;
import com.toedter.calendar.JDateChooser;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controlador que maneja la lógica de registro de un nuevo Propietario.
 */
public class ControladorRegistroPropietario implements ActionListener {

    private final VentanaRegistroPropietario vista;
    private final RegistroPropietarioService service;
    private final JDesktopPane escritorio; // Para centralizar la ventana si se desea

    public ControladorRegistroPropietario(VentanaRegistroPropietario vista, RegistroPropietarioService service, JDesktopPane escritorio) {
        this.vista = vista;
        this.service = service;
        this.escritorio = escritorio;

        // Asignar el listener al botón de Guardar
        this.vista.getBtnGuardar().addActionListener(this);
        // Asignar el listener al botón de Salir (Cerrar la ventana)
        this.vista.getBtnSalir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGuardar()) {
            registrarPropietario();
        } else if (e.getSource() == vista.getBtnSalir()) {
            vista.dispose();
        }
    }

    /**
     * Recolecta los datos de la vista, crea el objeto Propietario y llama al servicio para el registro.
     */
    private void registrarPropietario() {
        try {
            // 1. Recolectar datos de la vista
            String dni = vista.getDni();
            String apellidos = vista.getApellidos();
            String nombres = vista.getNombres();
            Date fechaNacDate = vista.getFechaNacimiento();
            String direccion = vista.getDireccion();
            String telefono = vista.getTelefono();
            String email = vista.getEmail();
            String pais = vista.getPais();
            String ciudad = vista.getCiudad();

            // Conversión de Date a LocalDate (necesario para el constructor de Propietario)
            LocalDate fechaNacimiento = null;
            if (fechaNacDate != null) {
                fechaNacimiento = fechaNacDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            // 2. Crear el objeto Propietario usando el constructor completo (sin ID)
            Propietario nuevoPropietario = new Propietario(
                    dni,
                    nombres,
                    apellidos,
                    fechaNacimiento,
                    direccion,
                    pais,
                    ciudad,
                    telefono,
                    email
            );

            // 3. Llamar al servicio para la validación y registro
            service.registrarPropietario(nuevoPropietario);

            // 4. Éxito: Mostrar mensaje y limpiar
            JOptionPane.showMessageDialog(vista,
                    "Propietario registrado con éxito. ID Asignado: " + nuevoPropietario.getIdPropietario(),
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();

        } catch (IllegalArgumentException ex) {
            // Error de Validación (capturado por el Service)
            JOptionPane.showMessageDialog(vista,
                    "Error de Validación:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            // Error de Sistema/Base de Datos (capturado por el DAO/Service)
            JOptionPane.showMessageDialog(vista,
                    "Error de Sistema: No se pudo completar el registro.\n" + ex.getMessage(),
                    "Error Grave",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}