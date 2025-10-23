package com.veterinaria.modelo;

import com.veterinaria.modelo.TurnoDAO3;
import com.veterinaria.modelo.Turno;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase que maneja la lógica de negocio para la gestión de turnos.
 */
public class GestorGestionTurnos {

    private  TurnoDAO3 turnoDAO;
    private PropietarioDAO propietario;
    private MascotaDAO mascota;
    private TipoConsultaDAO tipoConsulta;
    private FactoriaServicios factoria;


    //
    public GestorGestionTurnos(TurnoDAO3 turnoDAO, PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO, TurnoDAO3 turnoDAO1, PropietarioDAO propietario, MascotaDAO mascota, TipoConsultaDAO tipoConsulta, FactoriaServicios factoria) {
        this.turnoDAO = turnoDAO;
        this.propietario = propietario;
        this.mascota = mascota;
        this.tipoConsulta = tipoConsulta;
        this.factoria = factoria;
    }

    //Para que lanzador obtenga la factoria
    public FactoriaServicios getFactoria() {
        return factoria;
    }

    public GestorGestionTurnos(TurnoDAO3 turnoDAO, PropietarioDAO propietario, MascotaDAO mascota, TipoConsultaDAO tipoConsulta, FactoriaServicios factoria) {
        this.propietario = propietario;
        this.mascota = mascota;
        this.tipoConsulta = tipoConsulta;
        this.factoria = factoria;
        this.turnoDAO = new TurnoDAO3();
    }

    // ----------------------------------------------------
    // 1. GESTIÓN DE CONSULTA (LISTADO PARA LA GRILLA)
    // ----------------------------------------------------

    /**
     * Busca los turnos para una fecha y los convierte en una matriz Object[][]
     * apta para el modelo de la JTable.
     */
    private Object[][] datosGrillaCompleta;

    //  MÉTODO GETTER para que el Controlador pueda acceder a la fila completa de 9
    public Object[] obtenerFilaCompleta(int indiceFila) {
        // Verifica que la matriz exista y que el índice sea válido
        if (datosGrillaCompleta != null && indiceFila >= 0 && indiceFila < datosGrillaCompleta.length) {
            return datosGrillaCompleta[indiceFila];
        }
        return null; // Devuelve null si no hay datos o el índice es inválido
    }

   /* public Object[][] obtenerDatosParaGrilla(String fecha) {
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
    }*/

    public Object[][] obtenerDatosParaGrilla(String fecha) {
        try {
            List<Object[]> listaDatos = turnoDAO.buscarDatosParaGrillaPorFecha(fecha);

            if (listaDatos == null || listaDatos.isEmpty()) {
                this.datosGrillaCompleta = new Object[0][0]; // 🛑 Inicializar a vacío
                return new Object[0][0];
            }

            int filas = listaDatos.size();

            // Obtenemos el número REAL de columnas (que el DAO devuelve, que debe ser 9)
            int columnas = listaDatos.get(0).length;

            Object[][] matrizDatos = new Object[filas][columnas];

            for (int i = 0; i < filas; i++) {
                matrizDatos[i] = listaDatos.get(i);
            }

            // Guardamos la matriz COMPLETA de 9 columnas antes de salir
            this.datosGrillaCompleta = matrizDatos;

            return matrizDatos;

        } catch (SQLException e) {
            System.err.println("Error de SQL al obtener datos para la grilla: " + e.getMessage());
            this.datosGrillaCompleta = new Object[0][0]; // Inicializamos a vacío en caso de error
            return new Object[0][0];
        }
    }


    // ----------------------------------------------------
    // GESTIÓN DE ELIMINACIÓN
    // ----------------------------------------------------

    /**
     * Llama al DAO para eliminar un turno por su ID.
     * retornando true si se eliminó con éxito, false en caso contrario.
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
     * obtenemos el id de turno de la columna de la grilla.
     * y los nuevos datos que fueron capturados del formulario de modificación.
     * @devuelve true si se actualizó con éxito, false en caso contrario.
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

    // ------------------------
    // REGISTRO (CREACIÓN)
    // ------------------------

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



    /*public int obtenerIDPropietarioPorTurnoID(String idTurnoStr) throws SQLException {

        // Llama al DAO correcto
        return new TurnoDAO().obtenerIDPropietarioPorTurnoID(idTurnoStr);
    }*/

    public int obtenerIDPropietarioPorTurnoID(String idTurnoStr) throws SQLException {
        try {
            // 1. CONVERSIÓN SEGURA: Convertir el String recibido a un Integer.
            int idTurno = Integer.parseInt(idTurnoStr);

            // 2. LLAMADA AL DAO
            // 🛑 Nota: He corregido el error de sintaxis (doble 'id' en el retorno).
            return turnoDAO.obtenerIDPropietarioPorTurnoID(idTurnoStr);

        } catch (NumberFormatException e) {
            // Manejo de error si el String no es un número válido
            System.err.println("Error de formato: El ID del turno no es un número válido: " + idTurnoStr);
            throw new IllegalArgumentException("El ID del turno debe ser un número entero válido.", e);
        }
    }

    public int obtenerIDMascotaPorTurnoID(String idTurnoStr) throws SQLException {
        try {
            // CONVERSIÓN SEGURA: Convertir el String recibido a un Integer.
            int idTurno = Integer.parseInt(idTurnoStr);

            // LLAMADA AL DAO

            return turnoDAO.obtenerIDMascotaPorTurnoID(idTurnoStr);

        } catch (NumberFormatException e) {
            // Manejo de error si el String no es un número válido
            System.err.println("Error de formato: El ID del turno no es un número válido: " + idTurnoStr);
            throw new IllegalArgumentException("El ID del turno debe ser un número entero válido.", e);
        }
    }

    public int obtenerIDTipoConsultaPorTurnoID(String idTurnoStr) throws SQLException {
        try {
            //  CONVERSIÓN SEGURA: Convertir el String recibido a un Integer.
            int idTurno = Integer.parseInt(idTurnoStr);

            // LLAMADA AL DAO

            return turnoDAO.obtenerIDTipoConsultaPorTurnoID(idTurnoStr);

        } catch (NumberFormatException e) {
            // Manejo de error si el String no es un número válido
            System.err.println("Error de formato: El ID del turno no es un número válido: " + idTurnoStr);
            throw new IllegalArgumentException("El ID del turno debe ser un número entero válido.", e);
        }
    }





}