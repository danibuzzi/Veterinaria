package com.veterinaria.controlador;

import com.veterinaria.modelo.GestorModificacionTurnos;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.vista.VentanaModificacionTurnos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ControladorModificacionTurnos implements ActionListener {

    private final GestorModificacionTurnos gestor;
    private final VentanaModificacionTurnos vista;
    private final Object[] datosIniciales; // Datos del turno seleccionado

    private static final int INDICE_ID = 0;

    public ControladorModificacionTurnos(GestorModificacionTurnos gestor, VentanaModificacionTurnos vista, ModificacionCallback callback) {
        this.gestor = gestor;
        this.vista = vista;
        this.datosIniciales = vista.getDatosIniciales(); // Asumiendo que la Vista tiene un getter para esto

        vista.setControlador(this); // Inyectar este controlador en la Vista

        // Carga inicial de datos y opciones
        cargarOpcionesIniciales();
        seleccionarDatosIniciales(); // Selecciona el ítem correcto en los combos
    }

    private void cargarOpcionesIniciales() {
        // 1. Cargar todos los Propietarios
        List<Propietario> propietarios = gestor.obtenerTodosLosPropietariosActivos();
        vista.setOpcionesPropietario(propietarios);

        // 2. Cargar todos los Tipos de Consulta
        vista.setOpcionesTipoConsulta(gestor.obtenerTiposDeConsulta());

        // 3. Cargar Horarios para la fecha inicial (disponibilidad)
        cargarHorariosDisponibles();
    }

    private void seleccionarDatosIniciales() {
        // Ejemplo de selección inicial para un combo (debe ser completado para todos)
        // String propietarioStrInicial = (String) datosIniciales[3];
        // Aquí se buscaría el objeto Propietario que hace match con propietarioStrInicial
        // Y se llamaría a vista.getPropietarioCombo().setSelectedItem(objetoPropietario);

        // Luego, se llama a la lógica en cascada para cargar la Mascota original
        // int idPropietarioInicial = ((Propietario)vista.getPropietarioCombo().getSelectedItem()).getIdPropietario();
        // List<Mascota> mascotasIniciales = gestor.obtenerMascotasPorPropietario(idPropietarioInicial);
        // vista.setOpcionesMascota(mascotasIniciales);
        // vista.getMascotaCombo().setSelectedItem(/* Mascota original */);
    }

    // -----------------------------------------------------------
    // --- LÓGICA DE CONTROL DE EVENTOS ---
    // -----------------------------------------------------------

    public void cargarHorariosDisponibles() {
        // Obtener la fecha seleccionada del JDateChooser
        Date date = vista.getDateChooserFecha().getDate();
        LocalDate fecha = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        if (fecha != null) {
            List<String> horarios = gestor.obtenerHorariosDisponibles(fecha);
            vista.setOpcionesHorario(horarios);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "PROPIETARIO_CAMBIO":
                Propietario propietarioSeleccionado = (Propietario) vista.getPropietarioCombo().getSelectedItem();
                if (propietarioSeleccionado != null) {
                    List<Mascota> mascotas = gestor.obtenerMascotasPorPropietario(propietarioSeleccionado.getIdPropietario());
                    vista.setOpcionesMascota(mascotas);
                }
                break;
            case "GUARDAR":
                guardarModificacion();
                break;
            case "CANCELAR":
                vista.dispose();
                // El callback se ejecuta automáticamente con el InternalFrameAdapter
                break;
        }
    }

    private void guardarModificacion() {
        // 1. Obtener valores seleccionados
        Propietario prop = (Propietario) vista.getPropietarioCombo().getSelectedItem();
        Mascota masc = (Mascota) vista.getMascotaCombo().getSelectedItem();
        String tipoConsulta = (String) vista.getTipoConsultaCombo().getSelectedItem();
        String hora = (String) vista.getHorarioCombo().getSelectedItem();

        LocalDate nuevaFecha = vista.getDateChooserFecha().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 2. Validación de Fecha
        if (nuevaFecha.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(vista, "La nueva fecha del turno no puede ser anterior a hoy.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Construir el objeto Turno (Se asume la existencia de la clase Turno)
        // Turno turno = new Turno((Integer)datosIniciales[INDICE_ID], nuevaFecha, hora, tipoConsulta, prop.getIdPropietario(), masc.getIdMascota());

        // 4. Llamar al Gestor
        // if (gestor.actualizarTurno(turno)) {
        //     JOptionPane.showMessageDialog(vista, "Turno modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        //     vista.dispose(); // Cierra la ventana, lo que activa el callback
        // } else {
        //     JOptionPane.showMessageDialog(vista, "Error al modificar el turno. Verifique disponibilidad.", "Error", JOptionPane.ERROR_MESSAGE);
        // }
    }
}
