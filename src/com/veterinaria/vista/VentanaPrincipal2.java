package com.veterinaria.vista;

import com.veterinaria.controlador.ILanzadorModulo;
import com.veterinaria.controlador.LanzadorGestionTurnos;
import com.veterinaria.controlador.LanzadorRegistroTurno;
import com.veterinaria.modelo.GestorGestionTurnos;
import com.veterinaria.modelo.GestorTurno3;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal2 extends JFrame implements ActionListener {

    //private final GestorTurno3 gestorTurno;
    private final GestorTurno3 gestorRegistro; // Renombrado de gestorTurno para claridad
    private final GestorGestionTurnos gestorGestionTurnos;
    private final JDesktopPane escritorio; // ✅ 1. Escritorio para JInternalFrame

    // ✅ 2. Variables para los Lanzadores (Factoría de Controladores)
    private final ILanzadorModulo lanzadorRegistroTurno;
    private final ILanzadorModulo lanzadorGestionTurnos;

    private JMenuItem itemRegistroTurno;
    private JMenuItem itemVerReportes;
    private JMenuItem itemGestionClientes;

    // 1. CONSTRUCTOR
    public VentanaPrincipal2(GestorTurno3 gestorRegistro,GestorGestionTurnos gestorGestionTurnos) {
        super("Veterinaria Los Llanos");
        this.gestorRegistro = gestorRegistro;
        this.gestorGestionTurnos=gestorGestionTurnos;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        // setLocationRelativeTo(null); ELIMINADO

        // ✅ 3. Inicialización del Escritorio y se establece como contenedor principal
        escritorio = new JDesktopPane();
        setContentPane(escritorio);

        // ✅ 4. Inicialización de los Lanzadores (Factoría)
        // **IMPORTANTE:** Debes tener las clases LanzadorTurno y LanzadorGestionTurnos creadas.
        this.lanzadorRegistroTurno = new LanzadorRegistroTurno(gestorRegistro, escritorio);
        this.lanzadorGestionTurnos = new LanzadorGestionTurnos(gestorGestionTurnos, escritorio);

        // --- CONFIGURACIÓN DEL MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menuTurnos = new JMenu("Turnos");
        JMenu menuAdministracion = new JMenu("Administración");

        itemRegistroTurno = new JMenuItem("Registro de Turno");
        itemVerReportes = new JMenuItem("Gestión de Turnos (Consulta/Mod.)");
        itemGestionClientes = new JMenuItem("Gestión de Clientes");

        itemRegistroTurno.addActionListener(this);
        itemRegistroTurno.setActionCommand("ABRIR_REGISTRO_TURNO");

        itemVerReportes.addActionListener(this);
        itemVerReportes.setActionCommand("ABRIR_GESTION_TURNOS");

        itemGestionClientes.addActionListener(this);
        itemGestionClientes.setActionCommand("ABRIR_CLIENTES");

        menuTurnos.add(itemRegistroTurno);
        menuTurnos.addSeparator();
        menuTurnos.add(itemVerReportes);

        menuAdministracion.add(itemGestionClientes);

        menuBar.add(menuTurnos);
        menuBar.add(menuAdministracion);

        setJMenuBar(menuBar);
    }

    // 2. OYENTE DE EVENTOS: actionPerfomed (Llamada limpia a los Lanzadores)
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("ABRIR_REGISTRO_TURNO")) {
            // ✅ Delegación limpia al Lanzador
            this.lanzadorRegistroTurno.lanzar();

        } else if (comando.equals("ABRIR_GESTION_TURNOS")) {
            // ✅ Delegación limpia al Lanzador
            this.lanzadorGestionTurnos.lanzar();

        } else if (comando.equals("ABRIR_CLIENTES")) {
            JOptionPane.showMessageDialog(this, "Abriendo Gestión de Clientes...", "Módulo", JOptionPane.INFORMATION_MESSAGE);
            // Aquí se usaría un lanzador: this.lanzadorClientes.lanzar();
        }
    }

}
