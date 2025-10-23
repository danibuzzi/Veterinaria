package com.veterinaria.controlador;



import com.veterinaria.modelo.GestorTurno3;
import com.veterinaria.modelo.Turno;
import com.veterinaria.vista.VentanaRegistroTurno3;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorTurno3 implements ActionListener {

    private final GestorTurno3 gestorTurno;
    private final VentanaRegistroTurno3 vistaRegistro;

    public ControladorTurno3(GestorTurno3 gestorTurno, VentanaRegistroTurno3 vistaRegistro) {

        // ✅  Asigna los parámetros a las variables de la clase.

        this.gestorTurno = gestorTurno;
        this.vistaRegistro = vistaRegistro;

        // CONEXIONES DE OYENTES

        this.vistaRegistro.setControlador(this); // Botones (ActionListener)
        this.vistaRegistro.setListenerCargaMascotas(this); // Propietario (ActionListener)

        // ✅ CONEXIÓN CORRECTA DEL JDateChooser (PropertyChangeListener)

        this.vistaRegistro.setListenerFecha(e -> {
            if ("date".equals(e.getPropertyName())) {
                Date nuevaFecha = vistaRegistro.getDateChooserFechaTurno().getDate();
                cargarHorariosDisponibles(nuevaFecha);
            }
        });

        // Carga de datos iniciales
        cargarDatosIniciales();
    }
// Manejar cambio de fecha

    private void manejarCambioFecha() {
        //  Obtener la fecha seleccionada del componente de la Vista
        Date fechaSeleccionada = vistaRegistro.getDateChooserFechaTurno().getDate();

        // Si hay fecha seleccionada...
        if (fechaSeleccionada != null) {
            // Llamar al Gestor para obtener la lista de horarios disponibles para esa fecha
            List<String> horarios = gestorTurno.cargarHorariosDisponibles(fechaSeleccionada);

            // Cargar la nueva lista en el ComboBox de la Vista
            vistaRegistro.cargarItems(vistaRegistro.getComboHora(), horarios);
        }
    }

    private void cargarDatosIniciales() {
       // Cargar Tipos de Consulta
        List<String> tiposConsulta = gestorTurno.cargarNombresTiposConsulta();
        tiposConsulta.add(0, "--- Seleccione Tipo de Consulta ---");

        //  Usamos la lista modificada (tiposConsulta)
        vistaRegistro.cargarItems(vistaRegistro.getComboTipoConsulta(), tiposConsulta);


        // Cargar Propietarios
        List<String> propietarios = gestorTurno.cargarNombresPropietarios();
        propietarios.add(0, "--- Seleccione Propietario ---");

        // Usar la lista modificada (propietarios)
        vistaRegistro.cargarItems(vistaRegistro.getComboPropietario(), propietarios);


        // Mascota (Inicia solo con la opción de control)
        List<String> mascotasInicial = new ArrayList<>();
        mascotasInicial.add("--- Seleccione Mascota ---");
        vistaRegistro.cargarItems(vistaRegistro.getComboMascota(), mascotasInicial);


        // Cargamos los horarios
        cargarHorariosDisponibles(vistaRegistro.getDateChooserFechaTurno().getDate());
    }

     //CArga de mascotas
    private void cargarMascotas() {
        String nombrePropietario = vistaRegistro.getNombrePropietarioSeleccionado();
        List<String> mascotas = gestorTurno.cargarNombresMascotasPorPropietario(nombrePropietario);
        vistaRegistro.cargarItems(vistaRegistro.getComboMascota(), mascotas);
    }

    // Carga de hoarios dispnibles
    private void cargarHorariosDisponibles(Date fechaSeleccionada) {
        if (fechaSeleccionada == null) return;

        List<String> horarios = gestorTurno.cargarHorariosDisponibles(fechaSeleccionada);
        vistaRegistro.cargarItems(vistaRegistro.getComboHora(), horarios);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        // ... (Lógica de los comandos)
        if (comando.equals("GUARDAR_TURNO")) {
           // vistaRegistro.getBtnGuardar().setEnabled(false);
            registrarTurno();
            // ... (otros comandos)
        } else if (comando.equals("PROPIETARIO_SELECCIONADO")) {
            cargarMascotas();
        } /*else if (comando.equals("FECHA_CAMBIADA")) {
            Date nuevaFecha = vistaRegistro.getDateChooserFechaTurno().getDate();
            cargarHorariosDisponibles(nuevaFecha);
        }*/
    }

    private boolean validarCampos() {
        // Verificar Propietario
        if (vistaRegistro.getComboPropietario().getSelectedItem().toString().startsWith("---")) {
            return false;
        }
        // Verificar Mascota
        if (vistaRegistro.getComboMascota().getSelectedItem().toString().startsWith("---")) {
            return false;
        }
        // Verificar Tipo de Consulta
        if (vistaRegistro.getComboTipoConsulta().getSelectedItem().toString().startsWith("---")) {
            return false;
        }
        // Verificar Hora
        if (vistaRegistro.getComboHora().getSelectedItem().toString().startsWith("---")) {
            return false;
        }

        // Si la fecha es null (aunque JDateChooser suele inicializar), es un error.
        if (vistaRegistro.getDateChooserFechaTurno().getDate() == null) {
            return false;
        }

        return true; // Todos los campos son válidos
    }


// Limpieza da campos (cancelar)

    private void limpiarCampos() {

        // VERIFICACIÓN DE SEGURIDAD: Solo  se establece el índice si hay ítems.

        // Propietario
        if (vistaRegistro.getComboPropietario().getItemCount() > 0) {
            vistaRegistro.getComboPropietario().setSelectedIndex(0);
        }

        // Mascota
        if (vistaRegistro.getComboMascota().getItemCount() > 0) {
            // Al seleccionar el propietario en el índice 0, esto dispara un evento.
            // Es mejor cargar la lista inicial directamente:
            cargarMascotasIniciales();
        }

        // Tipo de Consulta
        if (vistaRegistro.getComboTipoConsulta().getItemCount() > 0) {
            vistaRegistro.getComboTipoConsulta().setSelectedIndex(0);
        }

        // Hora
        if (vistaRegistro.getComboHora().getItemCount() > 0) {
            vistaRegistro.getComboHora().setSelectedIndex(0);
        }

        // Opcional: Volver a la fecha de hoy
        vistaRegistro.getDateChooserFechaTurno().setDate(new Date());
    }

    //  REstablecemos mascotas a su estado inicial

    private void cargarMascotasIniciales() {

        List<String> mascotasInicial = new ArrayList<>();
        mascotasInicial.add("--- Seleccione Mascota ---");
        //vistaRegistro.cargarItems(vistaRegistro.getComboMascota(), mascotasInicial);
    }

    // Registro del turno
    private void registrarTurno() {

        // VALIDACIÓN de Campos
        if (!validarCampos()) {
            vistaRegistro.mostrarMensajeError("Debe seleccionar todos los campos obligatorios.");
            vistaRegistro.getBtnGuardar().setEnabled(true);
            return; // Sale si la validación falla
        }

        // CREACIÓN DEL OBJETO TURNO (Asignar valores, etc.)

        Date fechaTurno = vistaRegistro.getDateChooserFechaTurno().getDate();
        String horaTurno = vistaRegistro.getHoraSeleccionada(); // Formato HH:MM:SS
        String nombrePropietario = vistaRegistro.getNombrePropietarioSeleccionado();
        String nombreMascota = vistaRegistro.getNombreMascotaSeleccionada();
        String tipoConsulta = vistaRegistro.getNombreTipoConsultaSeleccionado();


        // 3. REGISTRO EN EL GESTOR con PARÁMETROS INDIVIDUALES

        try {
            // 3. REGISTRO EN EL GESTOR (Se ejecuta y si hay error, salta al 'catch')
            gestorTurno.registrarTurno(
                    tipoConsulta,
                    nombrePropietario,
                    nombreMascota,
                    fechaTurno,
                    horaTurno);

            // 4. ÉXITO (Si llegamos aquí, el registro fue exitoso)
            vistaRegistro.mostrarMensajeExito("El turno ha sido registrado con éxito.");

            limpiarCampos();
            return;

        } catch (Exception e) {


            e.printStackTrace();

            vistaRegistro.mostrarMensajeError("Error al registrar el turno. Detalle: " + e.getMessage());
            vistaRegistro.getBtnGuardar().setEnabled(true);
        }
    }
}