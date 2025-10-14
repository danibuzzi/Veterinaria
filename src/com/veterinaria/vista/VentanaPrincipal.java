package com.veterinaria.vista;

import com.veterinaria.controlador.ControladorTurno;
import com.veterinaria.modelo.GestorTurno;

import javax.swing.*;
import java.awt.event.ActionListener;



// Esta clase es la Vista Principal (JFrame)
// Archivo: com/vetapp/vista/VentanaPrincipal.java


import java.awt.event.ActionEvent;

// La Vista Principal escucha los eventos de su propio menú (Delegador)
public class VentanaPrincipal extends JFrame implements ActionListener {

    private final GestorTurno gestorTurno;

    // Agregamos otras opciones de menú para simular el menú completo
    private JMenuItem itemRegistroTurno;
    private JMenuItem itemVerReportes; // Nueva opción
    private JMenuItem itemGestionClientes; // Nueva opción

    // 1. CONSTRUCTOR: SOLO CONFIGURA LA VISTA Y SUS OYENTES
    public VentanaPrincipal(GestorTurno gestorTurno) {
        super("Veterinaria Los Llanos- Menú Principal");
        this.gestorTurno = gestorTurno; // (ID)

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // --- 1. CONFIGURACIÓN DEL MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menuTurnos = new JMenu("Turnos");
        JMenu menuAdministracion = new JMenu("Administración");

        // Opción 1: Registrar Turno (La que lanza la nueva ventana)
        itemRegistroTurno = new JMenuItem("Registrar Turno");
        itemRegistroTurno.setActionCommand("ABRIR_REGISTRO_TURNO");
        itemRegistroTurno.addActionListener(this); // La VentanaPrincipal se añade como oyente

        // Otras Opciones
        itemVerReportes = new JMenuItem("Ver Reportes");
        itemVerReportes.setActionCommand("ABRIR_REPORTES");
        itemVerReportes.addActionListener(this);

        itemGestionClientes = new JMenuItem("Gestión de Clientes");
        itemGestionClientes.setActionCommand("ABRIR_CLIENTES");
        itemGestionClientes.addActionListener(this);

        menuTurnos.add(itemRegistroTurno);

        menuAdministracion.add(itemVerReportes);
        menuAdministracion.add(itemGestionClientes);

        menuBar.add(menuTurnos);
        menuBar.add(menuAdministracion);
        setJMenuBar(menuBar);

    }

    // 2. OYENTE DE EVENTOS: SOLO SE EJECUTA AL HACER CLIC
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("ABRIR_REGISTRO_TURNO")) {
            // ¡Esta función solo se llama cuando el usuario hace clic!
            iniciarControladorTurno();

        } else if (comando.equals("ABRIR_REPORTES")) {
            JOptionPane.showMessageDialog(this, "Abriendo Reportes...", "Módulo", JOptionPane.INFORMATION_MESSAGE);
            // Aquí iría el código para iniciar el ControladorReportes

        } else if (comando.equals("ABRIR_CLIENTES")) {
            JOptionPane.showMessageDialog(this, "Abriendo Gestión de Clientes...", "Módulo", JOptionPane.INFORMATION_MESSAGE);
            // Aquí iría el código para iniciar el ControladorClientes
        }
    }

    // 3. MÉTODO DE DELEGACIÓN (Modular)
    private void iniciarControladorTurno() {
        // 1. Crea la Vista específica (JFrame)
        VentanaRegistroTurno2 vistaRegistro = new VentanaRegistroTurno2(gestorTurno);

//        // 2. Crea el Controlador e INYECTA el Gestor y la Vista
        ControladorTurno controladorTurno = new ControladorTurno(this.gestorTurno, vistaRegistro);

        // 3. Conecta el Controlador a la Vista (para que el botón funcione)
        vistaRegistro.setControlador(controladorTurno);

        // 4. Muestra la nueva ventana
        vistaRegistro.setVisible(true);
    }
}