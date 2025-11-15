package com.veterinaria.vista;

import com.veterinaria.controlador.*;
import com.veterinaria.modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private final JDesktopPane escritorio; // ✅ 1. Escritorio para JInternalFrame

    // ✅ 2. Variables para los Lanzadores (Factoría de Controladores)
    private final ILanzadorModulo lanzadorRegistroTurno;
    private final ILanzadorModulo lanzadorGestionTurnos;
    private final ILanzadorModulo lanzadorRegistroConsulta;
    private final ILanzadorModulo lanzadorHistoriaClinica;
    private final ILanzadorModulo lanzadorRegistroMascota;


    private final ILanzadorModulo lanzadorConsultaMascotas;
    private final ILanzadorModulo lanzadorConsultaPropietario;

    private final ILanzadorModulo lanzadorConsultaTurnoPropietario;
    private final ILanzadorModulo lanzadorRegistroPropietario;
    // Componentes del Menú
    // ... (items existentes)



    private JMenuItem itemRegistroTurno;
    private JMenuItem itemGestionTurnos;
    private JMenuItem itemRegistroConsulta;
    private JMenuItem itemHistoriaClinica;
    private  JMenuItem itemRegistroMascota;
    private JMenuItem itemConsultaMascotas;
    private JMenuItem itemConsultaPropietario;
    private JMenuItem itemConsultaTurnoPropietario;
    private JMenuItem itemRegistroPropietario;

    // 1. CONSTRUCTOR
    public VentanaPrincipal(GestorTurno3 gestorRegistro, GestorGestionTurnos gestorGestionTurnos, ConsultaService consultaService
    , HistoriaClinicaService historiaClinicaService, MascotaService mascotaService,
                            PropietarioService propietarioService, TurnoPropietarioService turnoPropietarioService, RegistroPropietarioService registroPropietarioService) {
        super("Veterinaria Los Llanos");
        //this.lanzadorRegistroPropietario = lanzadorRegistroPropietario;
        // this.lanzadorConsultaTurnoPropietario = lanzadorConsultaTurnoPropietario;

        //this.lanzadorRegistroMascota = lanzadorRegistroMascota;
        //private final GestorTurno3 gestorTurno;
        // Renombrado de gestorTurno para claridad
        // ILanzadorModulo lanzadorRegistroConsulta1 = lanzadorRegistroConsulta;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        setLocationRelativeTo(null);
        // --- codigo icono ventana---
        try {
            // Intentar cargar la imagen del recurso (es la forma recomendada)
            Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/imagen/iconovete32.png"));

            // Si la imagen se cargó correctamente, la establecemos.
            if (icon != null) {
                setIconImage(icon);
            }
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo cargar el icono de la ventana. Verifique la ruta del archivo: /img/veterinaria_icon.png");
            e.printStackTrace();
        }
        // ✅ 3. Inicialización del Escritorio y se establece como contenedor principal

        this.escritorio = new JDesktopPane();
        setContentPane(escritorio);

        // ✅ 4. Inicialización de los Lanzadores (Factoría)

        // Instancio los lanzadores
        this.lanzadorRegistroTurno = new LanzadorRegistroTurno(gestorRegistro, escritorio);
        this.lanzadorGestionTurnos = new LanzadorGestionTurnos(gestorGestionTurnos, escritorio);
        this.lanzadorRegistroConsulta = new LanzadorRegistroConsulta(consultaService, escritorio);
        this.lanzadorHistoriaClinica = new LanzadorHistoriaClinica(historiaClinicaService,escritorio);
        this.lanzadorRegistroMascota=new LanzadorRegistroMascota(mascotaService,escritorio);
        this.lanzadorConsultaMascotas =new LanzadorConsultaMascota(mascotaService,escritorio);
        this.lanzadorConsultaPropietario =new LanzadorConsultaPropietario(propietarioService,escritorio);
        this.lanzadorConsultaTurnoPropietario = new LanzadorConsultaTurnoPropietario(turnoPropietarioService, this.escritorio);
        this.lanzadorRegistroPropietario=new LanzadorRegistroPropietario(registroPropietarioService,escritorio);

        // --- CONFIGURACIÓN DEL MENÚ ---

        JMenuBar menuBar = new JMenuBar();
        JMenu menuTurnos = new JMenu("Gestión de turnos (agenda)");



        itemRegistroTurno = new JMenuItem("Registro de Turno");
        itemGestionTurnos = new JMenuItem("Gestión de Turnos (Consulta/Mod.)");
        itemConsultaTurnoPropietario=new JMenuItem("Consulta turnos propietario");


        JMenu menuConsultas = new JMenu("Gestión consulta /Historia clínica");
        itemRegistroConsulta = new JMenuItem("Registro de Consultas");
        itemHistoriaClinica= new JMenuItem("Consulta historias clínicas");


        itemRegistroTurno.addActionListener(this);
        itemRegistroTurno.setActionCommand("ABRIR_REGISTRO_TURNO");

        itemGestionTurnos.addActionListener(this);
        itemGestionTurnos.setActionCommand("ABRIR_GESTION_TURNOS");

        itemConsultaTurnoPropietario.addActionListener(this);
        itemConsultaTurnoPropietario.setActionCommand("ABRIR_CONSULTA_TURNOS_PROPIETARIO");

        itemRegistroConsulta.addActionListener(this);
        itemRegistroConsulta.setActionCommand("ABRIR_REGISTRO_CONSULTA");

        itemHistoriaClinica.addActionListener(this);
        itemHistoriaClinica.setActionCommand("ABRIR_HISTORIA_CLINICA");



        JMenu menuPacientes = new JMenu("Gestión de propietarios/pacientes");

        itemRegistroPropietario=new JMenuItem("Registro de propietario");
        itemRegistroPropietario.addActionListener(this);
        itemRegistroPropietario.setActionCommand("ABRIR_REGISTRO_PROPIETARIO");

        itemConsultaPropietario =new JMenuItem("Consulta modificación propietarios");
        itemConsultaPropietario.addActionListener(this);
        itemConsultaPropietario.setActionCommand("ABRIR_CONSULTA_PROPIETARIO");

        itemRegistroMascota = new JMenuItem("Registro de Mascota");
        itemRegistroMascota.addActionListener(this);
        itemRegistroMascota.setActionCommand("ABRIR_REGISTRO_MASCOTA");


        itemConsultaMascotas = new JMenuItem("Consulta y Modificación de Mascotas");
        itemConsultaMascotas.addActionListener(this);
        itemConsultaMascotas.setActionCommand("ABRIR_CONSULTA_MASCOTAS");

        JMenu menuConfiguracion = new JMenu("Gestión configuraciòn maestra");

        menuTurnos.add(itemRegistroTurno);
        menuTurnos.addSeparator();
        menuTurnos.add(itemGestionTurnos);
        menuTurnos.add(itemConsultaTurnoPropietario);

        menuConsultas.add(itemRegistroConsulta);
        menuConsultas.add(itemHistoriaClinica);

        menuPacientes.add(itemRegistroPropietario);
        menuPacientes.add(itemConsultaPropietario);
        menuPacientes.addSeparator();
        menuPacientes.add(itemRegistroMascota);
        menuPacientes.add(itemConsultaMascotas);

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
        else if (comando.equals("ABRIR_REGISTRO_MASCOTA")) {
            this.lanzadorRegistroMascota.lanzar();
        }

        else if (comando.equals("ABRIR_CONSULTA_MASCOTAS")) {
            this.lanzadorConsultaMascotas.lanzar();
        }

        else if (comando.equals("ABRIR_CONSULTA_PROPIETARIO")) {
            this.lanzadorConsultaPropietario.lanzar();
        }

        else if (comando.equals("ABRIR_CONSULTA_TURNOS_PROPIETARIO")) {
            this.lanzadorConsultaTurnoPropietario.lanzar();
        }
        else if (comando.equals("ABRIR_REGISTRO_PROPIETARIO")) {
            this.lanzadorRegistroPropietario.lanzar();
        }

    }

}