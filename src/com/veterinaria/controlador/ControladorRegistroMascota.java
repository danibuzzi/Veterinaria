package com.veterinaria.controlador;

import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.MascotaService;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.vista.VentanaRegistroMascota;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class ControladorRegistroMascota implements ActionListener {

    private final VentanaRegistroMascota vista;
    private final MascotaService mascotaService;

    public ControladorRegistroMascota(MascotaService mascotaService, VentanaRegistroMascota vista) {
        this.vista = vista;
        this.mascotaService = mascotaService;

        this.vista.establecerControlador(this);

        // Carga inicial
        cargarPropietarios();
    }

    private void cargarPropietarios() {
        try {
            List<Propietario> propietarios = mascotaService.listarPropietariosActivos();

            DefaultComboBoxModel<Propietario> model = new DefaultComboBoxModel<>();

            // Añadir el elemento de selección (ID 0)
            model.addElement(new Propietario(0, "--- Seleccionar Propietario ---", ""));

            for (Propietario p : propietarios) {
                model.addElement(p);
            }

            vista.cargarPropietarios(model);

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error de sistema al cargar propietarios: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
            vista.cargarPropietarios(new DefaultComboBoxModel<>());
        }
    }

    // ---------------------------------------------
    // LÓGICA DE EVENTOS (ActionListener)
    // ---------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("GUARDAR_MASCOTA")) {
            guardarMascota();
        } else if (comando.equals("SALIR")) {
            // Se asume que la vista maneja el JOptionPanel de confirmación de salida
            vista.salir();
        }
    }

    private void guardarMascota() {

        // 1. Obtener datos de la vista
        Propietario propietarioSeleccionado = (Propietario) vista.getComboPropietario().getSelectedItem();
        String nombre = vista.getNombre();
        Date fechaUtil = vista.getFechaNacimiento();
        String especie = vista.getEspecie();
        String raza = vista.getRaza();
        String sexo = vista.getSexo();
        String seniasParticulares = vista.getSeniasParticulares();

        // 2. VALIDACIÓN DE CAMPOS OBLIGATORIOS (mínima)
        if (propietarioSeleccionado == null || propietarioSeleccionado.getIdPropietario() == 0 ||
                nombre.isEmpty() || especie.isEmpty() || raza.isEmpty() ||
                fechaUtil == null || sexo.equals("Seleccionar sexo")) {

            JOptionPane.showMessageDialog(vista,
                    "Los campos Propietario, Nombre, Fecha de Nacimiento, Especie, Raza y Sexo son obligatorios.",
                    "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Conversión de Date (java.util) a LocalDate (java.time) para el Modelo
        LocalDate fechaNacimiento = fechaUtil.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();


        // 4. CREAR EL OBJETO MASCOTA USANDO EL CONSTRUCTOR
        try {
            Mascota nuevaMascota = new Mascota(
                    propietarioSeleccionado.getIdPropietario(),
                    nombre,
                    fechaNacimiento,
                    especie,
                    raza,
                    sexo,
                    seniasParticulares,
                    true
            );

            // 5. Llamada al Servicio para registrar
            mascotaService.registrarNuevaMascota(nuevaMascota);

            // 6. Éxito y limpieza
            JOptionPane.showMessageDialog(vista, "¡Mascota registrada con éxito!", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();

        } catch (IllegalArgumentException e) {
            // Errores de validación de negocio
            JOptionPane.showMessageDialog(vista, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            // Errores de sistema/BD
            JOptionPane.showMessageDialog(vista,
                    "Error al intentar registrar la mascota: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }
}

/*
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.MascotaService;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.vista.VentanaRegistroMascota;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class ControladorRegistroMascota implements ActionListener {

    private final VentanaRegistroMascota vista;
    private final MascotaService servicioMascota;

    public ControladorRegistroMascota(MascotaService servicioMascota, VentanaRegistroMascota vista) {
        this.vista = vista;
        this.servicioMascota = servicioMascota;


        this.vista.establecerControlador(this);

        cargarPropietarios();
    }

    private void cargarPropietarios() {
        try {
            // Asumo que tienes un MascotaService que llama al PropietarioDAO
            List<Propietario> propietarios = servicioMascota.listarPropietariosActivos();

            DefaultComboBoxModel<Propietario> model = new DefaultComboBoxModel<>();

            // Primer elemento para seleccionar
            // El constructor de 3 args es el que mejor se ajusta a estos elementos de selección
            model.addElement(new Propietario(0, "--- Seleccionar Propietario ---", ""));

            for (Propietario p : propietarios) {
                model.addElement(p);
            }

            vista.cargarPropietarios(model);

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error de sistema al cargar propietarios: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
            vista.cargarPropietarios(new DefaultComboBoxModel<>());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("GUARDAR_MASCOTA")) {
            guardarMascota();
        } else if (comando.equals("SALIR")) {
            vista.salir();
        }
    }

    private void guardarMascota() {

        // 1. Obtener datos de la vista usando los Getters (obtenerX)
        Propietario propietarioSeleccionado = (Propietario) vista.obtenerComboPropietario().getSelectedItem();
        String nombre = vista.obtenerNombre();
        Date fechaUtil = vista.obtenerFechaNacimiento();
        String especie = vista.obtenerEspecie();
        String raza = vista.obtenerRaza(); //
        String sexo = vista.obtenerSexo();
        String seniasParticulares = vista.obtenerSenasParticulares();

        // 2. VALIDACIÓN (Básica)
        if (propietarioSeleccionado == null || propietarioSeleccionado.getIdPropietario() == 0 ||
                nombre.isEmpty() || especie.isEmpty() || raza.isEmpty() ||
                fechaUtil == null || sexo.equals("Seleccionar sexo")) {

            JOptionPane.showMessageDialog(vista,
                    "Los campos Propietario, Nombre, Fecha de Nacimiento, Especie, Raza y Sexo son obligatorios.",
                    "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Conversión de Date a LocalDate
        LocalDate fechaNacimiento = fechaUtil.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();


        // 4. CREAR EL OBJETO MASCOTA USANDO EL CONSTRUCTOR DE 8 ARGUMENTOS (sin idMascota)
        try {
            Mascota nuevaMascota = new Mascota(
                    propietarioSeleccionado.getIdPropietario(),
                    nombre,
                    fechaNacimiento,
                    especie,
                    raza, // <-- ¡PARAMETRO AÑADIDO Y SIN ERROR!
                    sexo,
                    seniasParticulares,
                    true // activa
            );

            // 5. Llamada al Servicio para registrar
            servicioMascota.registrarNuevaMascota(nuevaMascota);

            // 6. Éxito y limpieza
            JOptionPane.showMessageDialog(vista, "¡Mascota registrada con éxito!", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampos();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(vista, "Error de Validación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al intentar registrar la mascota: " + e.getMessage(),
                    "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }*/
