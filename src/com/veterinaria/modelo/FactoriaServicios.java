package com.veterinaria.modelo;

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

        // --- 2. Inicialización del Módulo Pacientes (Ejemplo) ---
        // PacienteDAO pacienteDAO = new PacienteDAO();
        // this.gestorPaciente = new GestorPaciente(pacienteDAO);
    }

    // Métodos públicos para acceder a los gestores:

    public GestorTurno3 getGestorTurno() {
        return gestorTurno;
    }

    public GestorGestionTurnos getGestorGestionTurnos() {
        return gestorGestionTurnos;
    }
}