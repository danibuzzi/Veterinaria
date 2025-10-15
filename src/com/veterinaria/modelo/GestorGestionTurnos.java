package com.veterinaria.modelo;

import com.veterinaria.modelo.TurnoDAO3;
import com.veterinaria.modelo.Turno;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase que maneja la lógica de negocio para la gestión de turnos.
 * Actúa como intermediario entre el Controlador y el TurnoDAO3.
 */
public class GestorGestionTurnos {

    private final TurnoDAO3 turnoDAO;

    public GestorGestionTurnos(TurnoDAO3 turnoDAO) {
        this.turnoDAO = new TurnoDAO3();
    }

    // ----------------------------------------------------
    // 1. GESTIÓN DE CONSULTA (LISTADO PARA LA GRILLA)
    // ----------------------------------------------------

    /**
     * Busca los turnos para una fecha y los convierte en una matriz Object[][]
     * apta para el modelo de la JTable.
     * @param fecha La fecha para la cual buscar turnos (formato "yyyy-MM-dd").
     * @return Una matriz Object[][] con los datos de la grilla (incluye el ID en la Columna 0).
     */
    public Object[][] obtenerDatosParaGrilla(String fecha) {
        try {
            // El DAO devuelve una lista de arrays genéricos List<Object[]>
            List<Object[]> listaDatos = turnoDAO.buscarDatosParaGrillaPorFecha(fecha);

            if (listaDatos == null || listaDatos.isEmpty()) {
                return new Object[0][0]; // Devuelve matriz vacía si no hay resultados
            }

            // Convertir la List<Object[]> en una matriz Object[][]
            int filas = listaDatos.size();
            // Asume 6 columnas como se definió en el DAO
            int columnas = listaDatos.get(0).length;

            Object[][] matrizDatos = new Object[filas][columnas];

            for (int i = 0; i < filas; i++) {
                matrizDatos[i] = listaDatos.get(i);
            }

            return matrizDatos;

        } catch (SQLException e) {
            System.err.println("Error de SQL al obtener datos para la grilla: " + e.getMessage());
            // En un sistema real, lanzarías una excepción de negocio aquí
            return new Object[0][0];
        }
    }

    // ----------------------------------------------------
    // 2. GESTIÓN DE ELIMINACIÓN
    // ----------------------------------------------------

    /**
     * Llama al DAO para eliminar un turno por su ID.
     * @param idTurno El ID del turno a eliminar, obtenido de la Columna 0 de la grilla.
     * @return true si se eliminó con éxito, false en caso contrario.
     */
    public boolean eliminarTurno(int idTurno) {
        try {
            return turnoDAO.eliminarTurnoPorId(idTurno);
        } catch (SQLException e) {
            System.err.println("Error de SQL al eliminar turno con ID " + idTurno + ": " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------
    // 3. GESTIÓN DE MODIFICACIÓN
    // ----------------------------------------------------

    /**
     * Llama al DAO para actualizar los datos de un turno.
     * @param idTurno El ID del turno a modificar, obtenido de la Columna 0 de la grilla.
     * @param datosNuevos Objeto Turno con los nuevos datos (fecha, hora, tipoConsulta)
     * que fueron capturados del formulario de modificación.
     * @return true si se actualizó con éxito, false en caso contrario.
     */
    public boolean modificarTurno(int idTurno, Turno datosNuevos) {
        try {
            // Se le pasa el ID por separado y el objeto 'Turno' con los datos actualizados.
            return turnoDAO.actualizarTurno(idTurno, datosNuevos);
        } catch (SQLException e) {
            System.err.println("Error de SQL al modificar turno con ID " + idTurno + ": " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------
    // 4. REGISTRO (CREACIÓN) - SI ES NECESARIO DESDE AQUÍ
    // ----------------------------------------------------

    /**
     * Llama al DAO para registrar un nuevo turno (si la gestión de registro pasa por aquí).
     */
    public boolean registrarTurno(Turno turno) {
        try {
            return turnoDAO.guardar(turno);
        } catch (SQLException e) {
            System.err.println("Error al registrar turno: " + e.getMessage());
            return false;
        }
    }
}