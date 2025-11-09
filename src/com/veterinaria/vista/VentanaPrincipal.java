package com.veterinaria.vista;

import com.veterinaria.controlador.*;
import com.veterinaria.modelo.ConsultaService;
import com.veterinaria.modelo.GestorGestionTurnos;
import com.veterinaria.modelo.GestorTurno3;
import com.veterinaria.modelo.HistoriaClinicaService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private final JDesktopPane escritorio; // ✅ 1. Escritorio para JInternalFrame

    // ✅ 2. Variables para los Lanzadores (Factoría de Controladores)
    private final ILanzadorModulo lanzadorRegistroTurno;
    private final ILanzadorModulo lanzadorGestionTurnos;
    private final ILanzadorModulo lanzadorRegistroConsulta;
    private final ILanzadorModulo lanzadorHistoriaClinica;


    private JMenuItem itemRegistroTurno;
    private JMenuItem itemGestionTurnos;
    private JMenuItem itemRegistroConsulta;
    private JMenuItem itemHistoriaClinica;

    // 1. CONSTRUCTOR
    public VentanaPrincipal(GestorTurno3 gestorRegistro, GestorGestionTurnos gestorGestionTurnos, ConsultaService consultaService
    ,HistoriaClinicaService historiaClinicaService) {
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

        // Instancio los lanzadores
        this.lanzadorRegistroTurno = new LanzadorRegistroTurno(gestorRegistro, escritorio);
        this.lanzadorGestionTurnos = new LanzadorGestionTurnos(gestorGestionTurnos, escritorio);
        this.lanzadorRegistroConsulta = new LanzadorRegistroConsulta(consultaService, escritorio);
        this.lanzadorHistoriaClinica = new LanzadorHistoriaClinica(historiaClinicaService,escritorio);
        // --- CONFIGURACIÓN DEL MENÚ ---

        JMenuBar menuBar = new JMenuBar();
        JMenu menuTurnos = new JMenu("Gestión de turnos (agenda)");



        itemRegistroTurno = new JMenuItem("Registro de Turno");
        itemGestionTurnos = new JMenuItem("Gestión de Turnos (Consulta/Mod.)");
        JMenu menuConsultas = new JMenu("Gestión consulta /Historia clínica");
        itemRegistroConsulta = new JMenuItem("Registro de Consultas");
        itemHistoriaClinica= new JMenuItem("Consulta historias clínicas");


        itemRegistroTurno.addActionListener(this);
        itemRegistroTurno.setActionCommand("ABRIR_REGISTRO_TURNO");

        itemGestionTurnos.addActionListener(this);
        itemGestionTurnos.setActionCommand("ABRIR_GESTION_TURNOS");

        itemRegistroConsulta.addActionListener(this);
        itemRegistroConsulta.setActionCommand("ABRIR_REGISTRO_CONSULTA");

        itemHistoriaClinica.addActionListener(this);
        itemHistoriaClinica.setActionCommand("ABRIR_HISTORIA_CLINICA");


        JMenu menuPacientes = new JMenu("Gestión de propietarios/pacientes");
        JMenu menuConfiguracion = new JMenu("Gestión configuraciòn maestra");

        menuTurnos.add(itemRegistroTurno);
        menuTurnos.addSeparator();
        menuTurnos.add(itemGestionTurnos);

        menuConsultas.add(itemRegistroConsulta);
        menuConsultas.add(itemHistoriaClinica);

        menuBar.add(menuTurnos);
        menuBar.add(menuConsultas);
        menuBar.add(menuPacientes);
        menuBar.add(menuConfiguracion);

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

        } else if (comando.equals("ABRIR_HISTORIA_CLINICA")) {
            // ✅ Delegación limpia al Lanzador
            this.lanzadorHistoriaClinica.lanzar();
        }
    }

}