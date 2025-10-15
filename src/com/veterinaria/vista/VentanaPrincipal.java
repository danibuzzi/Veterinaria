package com.veterinaria.vista;

import com.veterinaria.controlador.ControladorTurno;
import com.veterinaria.controlador.ControladorTurno3;
import com.veterinaria.modelo.GestorTurno3;

import javax.swing.*;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

// La Vista Principal escucha los eventos de su propio menú (Delegador)
public class VentanaPrincipal extends JFrame implements ActionListener {


    private final GestorTurno3 gestorTurno;

    private JMenuItem itemRegistroTurno;
    private JMenuItem itemVerReportes;
    private JMenuItem itemGestionClientes;

    // 1. CONSTRUCTOR
    public VentanaPrincipal(GestorTurno3 gestorTurno) {
        super("Veterinaria Los Llanos- Menú Principal");
        this.gestorTurno = gestorTurno;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // --- CONFIGURACIÓN DEL MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menuTurnos = new JMenu("Turnos");

        itemRegistroTurno = new JMenuItem("Registro de Turno");
        itemVerReportes = new JMenuItem("Ver Reportes");
        itemGestionClientes = new JMenuItem("Gestión de Clientes");

        itemRegistroTurno.addActionListener(this);
        itemRegistroTurno.setActionCommand("ABRIR_REGISTRO_TURNO");

        itemVerReportes.addActionListener(this);
        itemVerReportes.setActionCommand("ABRIR_REPORTES");

        itemGestionClientes.addActionListener(this);
        itemGestionClientes.setActionCommand("ABRIR_CLIENTES");

        menuTurnos.add(itemRegistroTurno);
        menuTurnos.addSeparator();
        menuTurnos.add(itemVerReportes);

        JMenu menuAdministracion = new JMenu("Administración");
        menuAdministracion.add(itemGestionClientes);

        menuBar.add(menuTurnos);
        menuBar.add(menuAdministracion);

        setJMenuBar(menuBar);

        add(new JLabel("Bienvenido al Sistema de Turnos V3", SwingConstants.CENTER));
    }

    // 2. OYENTE DE EVENTOS: MÉTODO actionPerfomed (NO MODIFICADO)
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("ABRIR_REGISTRO_TURNO")) {
            // Llama al método de delegación
            iniciarControladorTurno();

        } else if (comando.equals("ABRIR_REPORTES")) {
            JOptionPane.showMessageDialog(this, "Abriendo Reportes...", "Módulo", JOptionPane.INFORMATION_MESSAGE);

        } else if (comando.equals("ABRIR_CLIENTES")) {
            JOptionPane.showMessageDialog(this, "Abriendo Gestión de Clientes...", "Módulo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 3. MÉTODO DE DELEGACIÓN (CLAVE)
    private void iniciarControladorTurno() {

        // 🛑 Lógica para usar V3 si es lo que se inyectó desde Principal.java
        if (this.gestorTurno instanceof GestorTurno3) {

            // 1. Cast y creación de Vista V3
            GestorTurno3 gestorV3 = (GestorTurno3) this.gestorTurno;
            VentanaRegistroTurno3 vistaRegistro = new VentanaRegistroTurno3(gestorV3);

            // 2. Creación e inyección de Controlador V3
            ControladorTurno3 controladorTurno = new ControladorTurno3(gestorV3, vistaRegistro);

            // 3. Conecta y muestra
            vistaRegistro.setControlador(controladorTurno);
            // El controlador V3 ya es un ActionListener, por lo que podemos usarlo como listener de fecha
            //vistaRegistro.setListenerFecha(controladorTurno);
            vistaRegistro.setVisible(true);

        } else {
            // Lógica original de la V2
            // 🛑 NOTA: Necesitas tener también el archivo VentanaRegistroTurno2.java
            VentanaRegistroTurno3 vistaRegistro = new VentanaRegistroTurno3(gestorTurno);
            ControladorTurno3 controladorTurno = new ControladorTurno3(this.gestorTurno, vistaRegistro);
            vistaRegistro.setControlador(controladorTurno);
            vistaRegistro.setVisible(true);
        }
    }
}
