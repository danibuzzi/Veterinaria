/*package com.veterinaria.modelo;

// Aquí importarías librerías (DAOs) de todos los módulos:
// import com.veterinaria.modelo.TurnoDAO3;
// import com.veterinaria.modelo.PacienteDAO;

public class FactoriaServicios {

    // ✅ Gestores (Modelos) inicializados una sola vez
    private final GestorTurno3 gestorTurno;
    private final GestorGestionTurnos gestorGestionTurnos; // Ejemplo de otro módulo

    public FactoriaServicios() {
        // --- 1. Inicialización del Módulo Turnos ---
        TurnoDAO3 turnoDAO = new TurnoDAO3(); // Uso de librería/DAO
        this.gestorTurno = new GestorTurno3(turnoDAO);
        this.gestorGestionTurnos=new GestorGestionTurnos(turnoDAO);

    }

    // Métodos públicos para acceder a los gestores:

    public GestorTurno3 getGestorTurno() {
        return gestorTurno;
    }

    public GestorGestionTurnos getGestorGestionTurnos() {
        return gestorGestionTurnos;
    }
}*/

/*
package com.veterinaria.modelo;


import com.veterinaria.controlador.ControladorGestionTurnos;
import com.veterinaria.vista.VentanaGestionTurnos;
// Importar todos los DAOs necesarios
// Si no existen, reemplaza con los nombres de tus clases reales.
// import com.veterinaria.modelo.TurnoDAO3;
// import com.veterinaria.modelo.PropietarioDAO;
// import com.veterinaria.modelo.MascotaDAO;
// import com.veterinaria.modelo.TipoConsultaDAO;

public class FactoriaServicios {

    // --- 1. DECLARACIÓN DE TODOS LOS DAOs ---
    private final TurnoDAO3 turnoDAO;
    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO;
    private final TipoConsultaDAO tipoConsultaDAO;

    // --- 2. DECLARACIÓN DE GESTORES ---
    private final GestorTurno3 gestorTurno;
    private final GestorGestionTurnos gestorGestionTurnos;
    // El gestorModificacionTurnos NO debe ser un campo aquí si solo se usa en el Controlador.
    // Lo borramos para evitar confusiones.

    public FactoriaServicios() {
        // A. CREACIÓN DE TODOS LOS DAOs
        this.turnoDAO = new TurnoDAO3();
        this.propietarioDAO = new PropietarioDAO();
        this.mascotaDAO = new MascotaDAO();
        this.tipoConsultaDAO = new TipoConsultaDAO();

        // B. CREACIÓN DE GESTORES (RESPEANDO LAS FIRMAS)

        // 1. GestorTurno3 (Asume 1 DAO)
        this.gestorTurno = new GestorTurno3(turnoDAO);

        // 2. GestorGestionTurnos (Asume 3 DAOs, basado en image_a41426.png)
        // **CORRECCIÓN CLAVE:** Si tu GestorGestionTurnos es quien transporta la Factoría (la 'cápsula'), debe recibir 4 parámetros.
        this.gestorGestionTurnos = new GestorGestionTurnos(
                turnoDAO,
                propietarioDAO,
                mascotaDAO,
                tipoConsultaDAO,
                this // 🛑 4º parámetro: Referencia a la propia Factoría (this)
        );


    }

    // --- MÉTODOS PÚBLICOS (GETTERS) ---

    public GestorTurno3 getGestorTurno() {
        return gestorTurno;
    }

    public GestorGestionTurnos getGestorGestionTurnos() {
        return gestorGestionTurnos;
    }

    // --- MÉTODO ENSAMBLADOR DEL CONTROLADOR (LA SOLUCIÓN A LA INYECCIÓN) ---
    public ControladorGestionTurnos crearControladorGestionTurnos(VentanaGestionTurnos vista) {

        // 🛑 Ensamblamos el Controlador con los 6 parámetros que necesita
        return new ControladorGestionTurnos(
                // 1. GestorGestionTurnos: Ya inicializado arriba.
                gestorGestionTurnos,

                // 2. Vista
                vista,

                // 3, 4, 5, 6. Los 4 DAOs que el Controlador usa para crear el GestorModificacionTurnos
                turnoDAO,
                propietarioDAO,
                mascotaDAO,
                tipoConsultaDAO
        );
    }
}*/

package com.veterinaria.modelo;

import com.veterinaria.controlador.ControladorGestionTurnos;
import com.veterinaria.vista.VentanaGestionTurnos;



public class FactoriaServicios {

    // --- DECLARACIÓN DE TODOS LOS DAOs ---

    private final TurnoDAO3 turnoDAO;
    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO;
    private final TipoConsultaDAO tipoConsultaDAO;
    private final TipoPracticaDAO tipoPracticaDAO;
    private final ConsultaService consultaService;
    private final ConsultaDAO consultaDAO;
    private final HistoriaClinicaService historiaClinicaService;

    // --- 2. DECLARACIÓN DE GESTORES ---

    private final GestorTurno3 gestorTurno;          // 🛑 Para Registro (
    private final GestorGestionTurnos gestorGestionTurnos; // Para Gestión

    public FactoriaServicios() {
        this.consultaService = new ConsultaService();
        // A. CREACIÓN DE TODOS LOS DAOs
        this.consultaDAO=new ConsultaDAO();
        this.turnoDAO = new TurnoDAO3();
        this.propietarioDAO = new PropietarioDAO();
        this.mascotaDAO = new MascotaDAO();
        this.tipoConsultaDAO = new TipoConsultaDAO();
        this.tipoPracticaDAO=new TipoPracticaDAO();
        this.historiaClinicaService = new HistoriaClinicaService(consultaDAO,
                propietarioDAO,mascotaDAO,tipoPracticaDAO);

        // CREACIÓN DE GESTORES (Inyección de dependencias)

        // GESTOR DE REGISTRO DE TURNOS

        this.gestorTurno = new GestorTurno3(turnoDAO);

        // GESTOR DE GESTIÓN DE TURNOS

        this.gestorGestionTurnos = new GestorGestionTurnos(
                turnoDAO,
                propietarioDAO,
                mascotaDAO,
                tipoConsultaDAO,
                this // referencia a la factoria
        );
    }

    // --- MÉTODOS PÚBLICOS (GETTERS) ---

    // Lo usa VentanaPrincipal2 para Registro
    public GestorTurno3 getGestorTurno() {
        return gestorTurno;
    }

    // Lo usa VentanaPrincipal2 para Gestión
    public GestorGestionTurnos getGestorGestionTurnos() {
        return gestorGestionTurnos;
    }

    // --- MÉTODO ENSAMBLADOR DEL CONTROLADOR DE GESTIÓN (PARA EL LANZADOR) ---
    // Este método es usado por LanzadorGestionTurnos.lanzar()
    public ControladorGestionTurnos crearControladorGestionTurnos(VentanaGestionTurnos vista) {

        // Ensambla el Controlador con los 6 argumentos necesarios
        return new ControladorGestionTurnos(
                // 1. GestorGestionTurnos: Ya inicializado arriba.
                gestorGestionTurnos,
                vista,
                turnoDAO,
                propietarioDAO,
                mascotaDAO,
                tipoConsultaDAO
        );
    }

    public ConsultaService getConsultaService() {
        return consultaService;
    }

    public HistoriaClinicaService getHistoriaClinicaService() { // ⬅️ Asegúrate de que exista
        return historiaClinicaService;
    }

}
