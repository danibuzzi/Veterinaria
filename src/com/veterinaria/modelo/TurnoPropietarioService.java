package com.veterinaria.modelo;
import java.util.Collections;
import java.util.List;

/**
 * Servicio (Gestor) para el módulo de Consulta de Turnos por Propietario.
 * Actúa como intermediario entre el Controlador y los DAOs.
 */
public class TurnoPropietarioService {

    private final PropietarioDAO propietarioDAO;
    private final TurnoDAO3 turnoDAO;

    // ... Constructor ...
    public TurnoPropietarioService(PropietarioDAO propietarioDAO, TurnoDAO3 turnoDAO) {
        this.propietarioDAO = propietarioDAO;
        this.turnoDAO = turnoDAO;
    }

    /**
     * Llama al DAO de Propietario para obtener la lista de propietarios
     * en el formato requerido (ID;Nombre Apellido) para el JComboBox.
     *
     * @return Lista de String con los datos de los propietarios.
     */
    public List<String> listarPropietariosConId() {
        try {
            return propietarioDAO.listarPropietariosConId();
        } catch (Exception e) {
            System.err.println("Error en Service al listar propietarios: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene los turnos de un propietario, filtrando a partir de una fecha.
     *
     * @param idPropietario ID del propietario.
     * @param fechaDesde Fecha de inicio del filtro (formato 'yyyy-MM-dd').
     * @return Lista de Object[] con los datos del turno.
     */
    public List<Object[]> obtenerTurnosPorPropietario(int idPropietario, String fechaDesde) { // <-- ¡NUEVO ARGUMENTO!
        try {
            // Llama al DAO con el nombre y los argumentos exactos
            return turnoDAO.obtenerTurnosPorPropietario(idPropietario, fechaDesde); // <-- Se pasa la fecha
        } catch (Exception e) {
            System.err.println("Error en Service al obtener turnos: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}