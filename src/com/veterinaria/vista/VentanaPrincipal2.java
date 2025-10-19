package com.veterinaria.vista;

import com.veterinaria.controlador.ILanzadorModulo;
import com.veterinaria.controlador.LanzadorGestionTurnos;
import com.veterinaria.controlador.LanzadorRegistroConsulta;
import com.veterinaria.controlador.LanzadorRegistroTurno;
import com.veterinaria.modelo.ConsultaService;
import com.veterinaria.modelo.GestorGestionTurnos;
import com.veterinaria.modelo.GestorTurno3;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal2 extends JFrame implements ActionListener {

    private final JDesktopPane escritorio; // ✅ 1. Escritorio para JInternalFrame

    // ✅ 2. Variables para los Lanzadores (Factoría de Controladores)
   private final ILanzadorModulo lanzadorRegistroTurno;
   private final ILanzadorModulo lanzadorGestionTurnos;
    private final ILanzadorModulo lanzadorRegistroConsulta;

    private JMenuItem itemRegistroTurno;
    private JMenuItem itemGestionTurnos;
    private JMenuItem itemRegistroConsulta;

    // 1. CONSTRUCTOR
    public VentanaPrincipal2(GestorTurno3 gestorRegistro, GestorGestionTurnos gestorGestionTurnos, ConsultaService consultaService) {
        super("Veterinaria Los Llanos");
        //private final GestorTurno3 gestorTurno;
        // Renombrado de gestorTurno para claridad
       // ILanzadorModulo lanzadorRegistroConsulta1 = lanzadorRegistroConsulta;

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
        this.lanzadorRegistroConsulta = new LanzadorRegistroConsulta(consultaService, escritorio);

        // --- CONFIGURACIÓN DEL MENÚ ---

        JMenuBar menuBar = new JMenuBar();
        JMenu menuTurnos = new JMenu("Gestión de turnos");



        itemRegistroTurno = new JMenuItem("Registro de Turno");
        itemGestionTurnos = new JMenuItem("Gestión de Turnos (Consulta/Mod.)");
        JMenu menuConsultas = new JMenu("Gestión consulta /Historia clínica");
        itemRegistroConsulta = new JMenuItem("Registro de Consultas");

        itemRegistroTurno.addActionListener(this);
        itemRegistroTurno.setActionCommand("ABRIR_REGISTRO_TURNO");

        itemGestionTurnos.addActionListener(this);
        itemGestionTurnos.setActionCommand("ABRIR_GESTION_TURNOS");

        itemRegistroConsulta.addActionListener(this);
        itemRegistroConsulta.setActionCommand("ABRIR_REGISTRO_CONSULTA");

        menuTurnos.add(itemRegistroTurno);
        menuTurnos.addSeparator();
        menuTurnos.add(itemGestionTurnos);

        menuConsultas.add(itemRegistroConsulta);

        menuBar.add(menuTurnos);
        menuBar.add(menuConsultas);

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

        } else if (comando.equals("ABRIR_REGISTRO_CONSULTA")) {
            this.lanzadorRegistroConsulta.lanzar();
            //JOptionPane.showMessageDialog(this, "Abriendo Gestión de Clientes...", "Módulo", JOptionPane.INFORMATION_MESSAGE);
            // Aquí se usaría un lanzador: this.lanzadorClientes.lanzar();
        }
    }

}
