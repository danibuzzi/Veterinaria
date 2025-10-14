package com.veterinaria.controlador;




import com.veterinaria.modelo.GestorTurno;
import com.veterinaria.modelo.TurnoDAO;
import com.veterinaria.vista.VentanaRegistroTurno2;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ControladorTurno implements ActionListener {

    private final GestorTurno gestorTurno;
    private final VentanaRegistroTurno2 vistaRegistro;

    public ControladorTurno(GestorTurno gestorTurno, VentanaRegistroTurno2 vistaRegistro) {
        this.gestorTurno = gestorTurno;
        this.vistaRegistro = vistaRegistro;

        // Conecta oyentes de Botón GUARDAR y ComboBox PROPIETARIO
        this.vistaRegistro.setControlador(this);
        this.vistaRegistro.setListenerCargaMascotas(this);

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Carga de ComboBoxes (Propietario y Tipo de Consulta)
        vistaRegistro.cargarItems(vistaRegistro.getComboTipoConsulta(), gestorTurno.cargarNombresTiposConsulta());
        vistaRegistro.cargarItems(vistaRegistro.getComboPropietario(), gestorTurno.cargarNombresPropietarios());

        // Se llama para hacer una carga inicial del combo Mascota
        actualizarMascotas();
    }

   /* private void actualizarMascotas() {
        String nombrePropietarioUnico = vistaRegistro.getNombrePropietarioSeleccionado();
        int idPropietario = 0;

        // 1. Necesitamos el ID del Propietario antes de cargar sus mascotas
        if (!nombrePropietarioUnico.startsWith("---")) {
            try {
                // Se traduce la cadena única (Nombre + DNI) a ID
                idPropietario = gestorTurno.turnoDAO.obtenerIdPropietarioPorNombre(nombrePropietarioUnico);
            } catch (SQLException e) {
                // En caso de error de BD, el ID queda en 0
                System.err.println("Error de BD al buscar ID de Propietario: " + e.getMessage());
            }
        }




        // 2. Se cargan las mascotas del ID encontrado
        List<String> nombresMascotas = gestorTurno.cargarNombresMascotas(idPropietario);
        vistaRegistro.cargarItems(vistaRegistro.getComboMascota(), nombresMascotas);
    }*/

// Archivo: ControladorTurno.java version que si andaba

   /* private void actualizarMascotas() {
        String nombrePropietarioUnico = vistaRegistro.getNombrePropietarioSeleccionado();
        int idPropietario = 0;

        // 🛑 CORRECCIÓN CLAVE: Agregar el chequeo de NULL para evitar el fallo.
        if (nombrePropietarioUnico != null &&
                !nombrePropietarioUnico.startsWith("---")) {

            try {
                // Se traduce la cadena única (Nombre + DNI) a ID
                //idPropietario =TurnoDAO.obtenerIdPropietarioPorNombre(nombrePropietarioUnico);

                //idPropietario =gestorTurno.obtenerIdPropietario(nombrePropietarioUnico);
                idPropietario =gestorTurno.obtenerIdPropietarioPorNombre(nombrePropietarioUnico);
            } catch (SQLException e) {
                System.err.println("Error de BD al buscar ID de Propietario: " + e.getMessage());
            }
        } else {
            // 🎯 IMPORTANTE: Si es NULL o el placeholder, limpia y añade el mensaje
            vistaRegistro.getComboMascota().removeAllItems();
            vistaRegistro.getComboMascota().addItem("--- Seleccione Mascota ---");
            return; // Salir del método
        }


        // 2. Se cargan las mascotas del ID encontrado (SOLO si no se salió antes)
        List<String> nombresMascotas = gestorTurno.cargarNombresMascotas(idPropietario));

        // Esto es lo que pone el texto "Seleccione Mascota" si la lista está vacía o cargará los nombres
        vistaRegistro.cargarItems(vistaRegistro.getComboMascota(), nombresMascotas);
    }
*/
    // Archivo: ControladorTurno.java (Fragmento de actualizarMascotas)

// Archivo: ControladorTurno.java (Fragmento de actualizarMascotas)

    private void actualizarMascotas() {
        String nombrePropietarioUnico = vistaRegistro.getNombrePropietarioSeleccionado();
        int idPropietario = 0;

        // ... (chequeo de NULL/Placeholder) ...
        if (nombrePropietarioUnico != null &&
                !nombrePropietarioUnico.startsWith("---")) {

            try {
                // Se traduce la cadena única (Nombre + DNI) a ID

                // 🛑 ESTA ES LA LÍNEA QUE DEBE ESTAR ACTIVA Y SIN ERRORES:
                idPropietario = gestorTurno.obtenerIdPropietarioPorNombre(nombrePropietarioUnico);

            } catch (SQLException e) {
                System.err.println("Error de BD al buscar ID de Propietario: " + e.getMessage());
            }
        } else {
            // ... (limpieza y return) ...
            vistaRegistro.getComboMascota().removeAllItems();
            vistaRegistro.getComboMascota().addItem("--- Seleccione Mascota ---");
            return;
        }

        // 2. Se cargan las mascotas del ID encontrado (SOLO si no se salió antes)
        List<String> nombresMascotas = gestorTurno.cargarNombresMascotas(nombrePropietarioUnico);

        // ... (cargar items en la vista) ...
        vistaRegistro.cargarItems(vistaRegistro.getComboMascota(), nombresMascotas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("GUARDAR_TURNO")) {

            // 1. Obtiene todos los Strings de la Vista
            String nombrePropietario = vistaRegistro.getNombrePropietarioSeleccionado();
            String nombreTipo = vistaRegistro.getNombreTipoConsultaSeleccionado();
            String nombreMascota = vistaRegistro.getNombreMascotaSeleccionada();
            String fechaTurnoStr = vistaRegistro.getFechaSeleccionadaSQL();
            String hora = vistaRegistro.getHoraSeleccionada();

            // 2. Conversión de fecha (Tu lógica de conversión de String a Date)
            // 🚨 CUIDADO AQUÍ: Esta línea tiene un error de sintaxis que hay que corregir.
            // Date fechatuno= new SimpleDateFormat(fechaTurnoStr).get2DigitYearStart();

            // Usaremos el Date original de la Vista, ¡el GestorTurno ya tiene el Date!
            Date fechatuno = vistaRegistro.getDateChooserFechaTurno().getDate(); // 🛑 NECESITAS EL GETTER DEL JDateChooser

            // 3. Llama al Gestor
            String resultado = gestorTurno.registrarTurno(nombreTipo, nombrePropietario, nombreMascota, fechatuno, hora);

            // 4. Notifica el resultado
            int tipoMensaje = resultado.startsWith("ÉXITO") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
            vistaRegistro.mostrarMensaje(resultado, tipoMensaje);

            if (resultado.startsWith("ÉXITO")) {
                vistaRegistro.dispose();
            } else {
                // 🛑 LÓGICA DE CONTROL: Re-habilitar si hubo un error (para que el usuario reintente)
                vistaRegistro.getBtnGuardar().setEnabled(true);
            }

        } else if (comando.equals("PROPIETARIO_SELECCIONADO")) {
            // Se dispara al cambiar el ComboBox de Propietario
            actualizarMascotas();
        }
    }
}