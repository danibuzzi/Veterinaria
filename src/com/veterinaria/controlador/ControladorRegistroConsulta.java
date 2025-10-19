package com.veterinaria.controlador;

import com.veterinaria.modelo.Consulta;
import com.veterinaria.modelo.ConsultaService;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.TipoPractica;
import com.veterinaria.vista.VentanaRegistroConsulta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class ControladorRegistroConsulta implements ActionListener {

    private final VentanaRegistroConsulta vista;
    private final ConsultaService consultaService;

    public ControladorRegistroConsulta(ConsultaService consultaService, VentanaRegistroConsulta vista) {
        this.vista = vista;
        // Usar la instancia inyectada
        this.consultaService = consultaService;

        this.vista.setControlador(this);


        cargarDatosIniciales();
        JComboBox<Propietario> comboPropietario = this.vista.getComboPropietario();
        comboPropietario.setActionCommand("PROPIETARIO_CAMBIO");
        comboPropietario.addActionListener(this);


    }

    private void cargarDatosIniciales() {
        try {
            // 1. Cargar Propietarios y Tipos de Práctica
            List<Propietario> propietarios = consultaService.listarPropietarios();
            List<TipoPractica> tipos = consultaService.listarTiposPractica();

            // Compatible con Vista
            vista.cargarPropietarios(new DefaultComboBoxModel<>(propietarios.toArray(new Propietario[0])));
            vista.cargarTiposPractica(new DefaultComboBoxModel<>(tipos.toArray(new TipoPractica[0])));

            // 2. Carga inicial de Mascotas.
            if (!propietarios.isEmpty()) {
                actualizarMascotasDelPropietario();
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error de sistema al cargar datos iniciales: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }

    // LÓGICA UNIFICADA: Carga inicial y cambio de propietario
    private void actualizarMascotasDelPropietario() {
        Object selectedItem = vista.getComboPropietario().getSelectedItem();
        Propietario propietarioSeleccionado = null;

        if (selectedItem instanceof Propietario) {
            propietarioSeleccionado = (Propietario) selectedItem;
        }

        // Si el propietario es válido (ID > 0)
        if (propietarioSeleccionado != null && propietarioSeleccionado.getIdPropietario() > 0) {
            int idPropietario = propietarioSeleccionado.getIdPropietario();
            System.out.println(" id propietario "+idPropietario);

            try {
                // La lista que trae correctamente la mascota
                List<Mascota> mascotas = consultaService.listarMascotasPorPropietario(idPropietario);
                System.out.println("Lista mascotas "+mascotas.get(0).getIdMascota());
                // 🛑 Envío del DefaultComboBoxModel
                vista.cargarMascotas(new DefaultComboBoxModel<>(mascotas.toArray(new Mascota[0])));
                vista.getComboMascota().setEnabled(!mascotas.isEmpty());

            } catch (RuntimeException e) {
                // En caso de error de BD
                vista.cargarMascotas(new DefaultComboBoxModel<>());
                vista.getComboMascota().setEnabled(false);
            }

        } else {
            // Si no hay propietario seleccionado válido, vaciamos el de mascotas.
            vista.cargarMascotas(new DefaultComboBoxModel<>());
            vista.getComboMascota().setEnabled(false);
        }
    }

    // ---------------------------------------------
    // LÓGICA DE EVENTOS (ActionListener)
    // ---------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("PROPIETARIO_CAMBIO")){
            // Al cambiar el combo, llama a la función de actualización
            actualizarMascotasDelPropietario();
        }
        if (comando.equals("GUARDAR_CONSULTA")) {
            guardarConsulta();
        } else if (comando.equals("CANCELAR")) {
            vista.limpiarCampos();
            actualizarMascotasDelPropietario();
        }
    }

    /*private void guardarConsulta() {
        // ... (Tu método de guardado va aquí, asumiendo que no tiene fallos lógicos graves)
        Propietario propietario = (Propietario) vista.getComboPropietario().getSelectedItem();
        Mascota mascota = (Mascota) vista.getComboMascota().getSelectedItem();
        TipoPractica tipoPractica = (TipoPractica) vista.getComboTipoPractica().getSelectedItem();
        Date fechaUtil = vista.getFechaSeleccionada();

        if (propietario == null || propietario.getIdPropietario() == 0 ||
                mascota == null || mascota.getIdMascota() == 0 || tipoPractica == null ||
                tipoPractica.getIdTipoPractica() == 0 || fechaUtil == null) {
            JOptionPane.showMessageDialog(vista, "Debe completar todos los campos obligatorios.",
                    "Faltan Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            java.sql.Time horaActual = new java.sql.Time(System.currentTimeMillis());
            Consulta nuevaConsulta = new Consulta();

            nuevaConsulta.setIdMascota(mascota.getIdMascota());
            nuevaConsulta.setIdPropietario(propietario.getIdPropietario());
            nuevaConsulta.setIdTipoPractica(tipoPractica.getIdTipoPractica());
            nuevaConsulta.setFechaConsulta(fechaUtil);
            nuevaConsulta.setHora(horaActual);
            nuevaConsulta.setResultadoEstudio(vista.getResultadoEstudios());
            nuevaConsulta.setDiagnostico(vista.getDiagnostico());
            nuevaConsulta.setPronostico(vista.getPronostico());
            nuevaConsulta.setTratamiento(vista.getTratamiento());

            consultaService.guardarNuevaConsulta(nuevaConsulta);

            JOptionPane.showMessageDialog(vista, "¡Consulta registrada con éxito!", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();
            actualizarMascotasDelPropietario();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(vista, e.getMessage(), "Error en el formato de datos", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error de sistema al guardar la consulta: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    // Archivo: ControladorRegistroConsulta.java

    private void guardarConsulta() {
        Propietario propietario = (Propietario) vista.getComboPropietario().getSelectedItem();
        Mascota mascota = (Mascota) vista.getComboMascota().getSelectedItem();
        TipoPractica tipoPractica = (TipoPractica) vista.getComboTipoPractica().getSelectedItem();
        Date fechaUtil = vista.getFechaSeleccionada();

        // Obtener y limpiar campos de texto
        String resultadoEstudios = vista.getResultadoEstudios().trim();
        String diagnostico = vista.getDiagnostico().trim();
        String pronostico = vista.getPronostico().trim();
        String tratamiento = vista.getTratamiento().trim();

        // 🛑 VALIDACIÓN DE CAMOS
        if (propietario == null || propietario.getIdPropietario() == 0 ||
                mascota == null || mascota.getIdMascota() == 0 || tipoPractica == null ||
                tipoPractica.getIdTipoPractica() == 0 || fechaUtil == null
        || diagnostico.isEmpty() || pronostico.isEmpty() || tratamiento.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Todos los datos son obligatorios, excepto 'Resultado de Estudios'. Por favor, complete los campos faltantes.",
                    "Advertencia de Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        // 🛑 LÓGICA PARA CAMPO OPCIONAL (Resultado Estudios)
        if (resultadoEstudios.isEmpty()) {
            resultadoEstudios = "Sin estudios realizados"; // Asignar valor por defecto si está vacío
        }

        try {
            java.sql.Time horaActual = new java.sql.Time(System.currentTimeMillis());
            Consulta nuevaConsulta = new Consulta();

            // Asignación de IDs
            nuevaConsulta.setIdMascota(mascota.getIdMascota());
            nuevaConsulta.setIdPropietario(propietario.getIdPropietario());
            nuevaConsulta.setIdTipoPractica(tipoPractica.getIdTipoPractica());

            // Asignación de datos
            nuevaConsulta.setFechaConsulta(fechaUtil);
            nuevaConsulta.setHora(horaActual);

            // 🛑 Usar los valores validados/por defecto
            nuevaConsulta.setResultadoEstudio(resultadoEstudios);
            nuevaConsulta.setDiagnostico(diagnostico);
            nuevaConsulta.setPronostico(pronostico);
            nuevaConsulta.setTratamiento(tratamiento);

            consultaService.guardarNuevaConsulta(nuevaConsulta);

            JOptionPane.showMessageDialog(vista, "¡Consulta registrada con éxito!", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error de sistema al guardar la consulta: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }
}