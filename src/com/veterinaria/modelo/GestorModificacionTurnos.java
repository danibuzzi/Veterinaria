package com.veterinaria.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GestorModificacionTurnos {

    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO;
    private final TurnoDAO3 turnoDAO;
    private final TipoConsultaDAO tipoConsultaDAO; // Campo


    public GestorModificacionTurnos(TurnoDAO3 turnoDAO, PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO, TipoConsultaDAO tipoConsultaDAO) {
        this.turnoDAO = turnoDAO;
        this.propietarioDAO = propietarioDAO;
        this.mascotaDAO = mascotaDAO;
        this.tipoConsultaDAO = tipoConsultaDAO;
    }

    // --- Carga de Combos (Llama a los DAOs) ---

    public List<Propietario> obtenerTodosLosPropietariosActivos() {
        return propietarioDAO.listarTodosActivos();
    }

    public List<Mascota> obtenerMascotasPorPropietario(int idPropietario) {
        return mascotaDAO.listarActivasPorPropietario(idPropietario);
    }

    public List<TipoConsulta> obtenerTiposDeConsulta() throws SQLException {
     return tipoConsultaDAO.obtenerTodos();

    }




// Obtenecion de los horarios disponibles

    public List<String> obtenerHorariosDisponibles(LocalDate fecha) throws Exception {

        //  CONVERSIÓN DE LA FECHA

        java.util.Date dateConvertida = java.sql.Date.valueOf(fecha);

        // Obtener las horas OCUPADAS (DAO)
        List<String> horasOcupadas;

        try {
            //  Recibir el resultado como Set<String>

            Set<String> horasOcupadasSet = turnoDAO.obtenerHorasInicioOcupadas(dateConvertida);

            // Convertir el Set a List para usar el resto de la lógica
            horasOcupadas = new ArrayList<>(horasOcupadasSet);

        } catch (SQLException e) {
            throw new Exception("Error al obtener horas ocupadas desde DB.", e);
        }

        // Generar TODAS las horas posibles (a intervalos de 30 minutos)
        List<String> todasLasHoras = new ArrayList<>();
        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fin = LocalTime.of(19, 30);


        LocalTime horaActual = inicio;
        while ((horaActual.isBefore(fin) || horaActual.equals(fin))) {
            todasLasHoras.add(horaActual.format(DateTimeFormatter.ofPattern("HH:mm")));
            horaActual = horaActual.plusMinutes(30);
        }

        // Filtrar las horas DISPONIBLES
        todasLasHoras.removeAll(horasOcupadas);
        System.out.println("horas ocuapdas "+horasOcupadas);
        System.out.println("todas las horas "+todasLasHoras );
        return todasLasHoras;
    }
    // --- Actualizacion de datos del turno ---

    public boolean actualizarTurno(Turno turnoModificado) {
        // Obtener el ID del turno que se está modificando
        int idTurno = turnoModificado.getIdTurno();

        // Comprobar que el ID no es 0 (para evitar errores en el DAO)
        if (idTurno <= 0) {
            System.err.println("Error: El ID del turno a modificar es inválido (<= 0).");
            return false;
        }

        try {
            // LLAMAR AL DAO PARA EJECUTAR EL UPDATE

            return turnoDAO.actualizarTurno(idTurno, turnoModificado);

        } catch (SQLException e) {
            // Imprimir el error de la base de datos para depuración
            System.err.println(" ERROR SQL REAL al modificar turno:");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            return false; // El Gestor devuelve false, lo que dispara el mensaje de error en la Vista
        } catch (Exception e) {
            // Capturar otros posibles errores
            System.err.println(" OTRO ERROR al modificar turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Busca el ID numérico (int) del Tipo de Consulta a partir de su nombre (String),
     * consultando directamente la base de datos..
     * lanza  Exception Si ocurre un error SQL o si el tipo de consulta no existe.
     */
    public int obtenerIdTipoConsulta(String nombreConsulta) throws Exception {
        int idTipoConsulta = -1;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //Llamada al metodo de conexion con la base de datos
            conn = null;

            if (conn == null) {
                throw new SQLException("No se pudo establecer la conexión a la base de datos.");
            }
            // ***************************************************************

            String sql = "SELECT idTipoConsulta FROM TipoConsulta WHERE descipcion = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreConsulta);

            rs = stmt.executeQuery();

            if (rs.next()) {
                idTipoConsulta = rs.getInt("idTipoConsulta");
            } else {
                // Lanza una excepción si el nombre de la consulta no existe.
                throw new Exception("Tipo de consulta '" + nombreConsulta + "' no encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Lanza una excepción con un mensaje claro de error de DB
            throw new Exception("Error de base de datos al buscar ID de consulta: " + e.getMessage());
        } finally {
            // Aseguramos el cierre de recursos, obligatorios en un entorno real.
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idTipoConsulta;
    }

}
