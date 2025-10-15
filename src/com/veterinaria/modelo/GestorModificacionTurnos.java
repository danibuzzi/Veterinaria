package com.veterinaria.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorModificacionTurnos {

    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO;
    private final TurnoDAO turnoDAO;

    public GestorModificacionTurnos(PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO, TurnoDAO turnoDAO) {
        this.propietarioDAO = propietarioDAO;
        this.mascotaDAO = mascotaDAO;
        this.turnoDAO = turnoDAO;
    }

    // --- Carga de Combos (Llama a los DAOs) ---

    public List<Propietario> obtenerTodosLosPropietariosActivos() {
        return propietarioDAO.listarTodosActivos();
    }

    public List<Mascota> obtenerMascotasPorPropietario(int idPropietario) {
        return mascotaDAO.listarActivasPorPropietario(idPropietario);
    }

    public List<String> obtenerTiposDeConsulta() {
        // En un sistema real, esto iría a la BD (OpcionesDAO)
        List<String> tipos = new ArrayList<>();
        tipos.add("Consulta de Control Anual");
        tipos.add("Consulta de Emergencia/Urgencia");
        tipos.add("Vacunación");
        return tipos;
    }

    public List<String> obtenerHorariosDisponibles(LocalDate fecha) {
        // Lógica de validación de horarios
        List<String> horarios = new ArrayList<>();
        horarios.add("08:00");
        horarios.add("08:30");
        horarios.add("09:00");
        horarios.add("09:30");
        return horarios;
    }

    // --- Persistencia ---

    public boolean actualizarTurno(Turno turnoModificado) {
        return turnoDAO.actualizar(turnoModificado);
    }
}
