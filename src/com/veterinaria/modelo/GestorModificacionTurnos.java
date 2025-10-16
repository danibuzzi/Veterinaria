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




// En tu Gestor (ej. GestorModificacionTurnos)

    public List<String> obtenerHorariosDisponibles(LocalDate fecha) throws Exception {

        // 🛑 1. CONVERSIÓN DE LA FECHA (para compatibilidad con el DAO antiguo)
        // Esto evita el error de Date/LocalDate
        java.util.Date dateConvertida = java.sql.Date.valueOf(fecha);

        // 2. Obtener las horas OCUPADAS (DAO)
        List<String> horasOcupadas;

        try {
            // 🛑 3. CRÍTICO: Recibir el resultado como Set<String>
            // Esto soluciona el error que el IDE te da en la línea de la llamada.
            Set<String> horasOcupadasSet = turnoDAO.obtenerHorasInicioOcupadas(dateConvertida);

            // 4. Convertir el Set a List para usar el resto de la lógica (removeAll, etc.)
            horasOcupadas = new ArrayList<>(horasOcupadasSet);

        } catch (SQLException e) {
            throw new Exception("Error al obtener horas ocupadas desde DB.", e);
        }

        // 5. Generar TODAS las horas posibles (con paso de 30 minutos)
        List<String> todasLasHoras = new ArrayList<>();
        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fin = LocalTime.of(19, 30);


        LocalTime horaActual = inicio;
        while ((horaActual.isBefore(fin) || horaActual.equals(fin))) {
            todasLasHoras.add(horaActual.format(DateTimeFormatter.ofPattern("HH:mm")));
            horaActual = horaActual.plusMinutes(30);
        }

        // 6. Filtrar las DISPONIBLES
        todasLasHoras.removeAll(horasOcupadas);
        System.out.println("horas ocuapdas "+horasOcupadas);
        System.out.println("todas las horas "+todasLasHoras );
        return todasLasHoras;
    }
    // --- Persistencia ---

    public boolean actualizarTurno(Turno turnoModificado) {
        // 1. Obtener el ID del turno que se está modificando
        int idTurno = turnoModificado.getIdTurno();

        // 2. Comprobar que el ID no es 0 (para evitar errores en el DAO)
        if (idTurno <= 0) {
            System.err.println("Error: El ID del turno a modificar es inválido (<= 0).");
            return false;
        }

        try {
            // 3. LLAMAR AL DAO PARA EJECUTAR EL UPDATE
            // ASUMIMOS que el DAO tiene el método: public boolean actualizarTurno(int id, Turno t)
            return turnoDAO.actualizarTurno(idTurno, turnoModificado);

        } catch (SQLException e) {
            // 🛑 CRÍTICO: Imprimir el error de la base de datos para depuración
            System.err.println("❌ ERROR SQL REAL al modificar turno:");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            return false; // El Gestor devuelve false, lo que dispara el mensaje de error en la Vista
        } catch (Exception e) {
            // Capturar otros posibles errores (ej. NullPointerException)
            System.err.println("❌ OTRO ERROR al modificar turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Busca el ID numérico (int) del Tipo de Consulta a partir de su nombre (String),
     * consultando directamente la base de datos.
     * @param nombreConsulta El nombre del tipo de consulta (Ej: "Vacunación").
     * @return El ID numérico (idTipoConsulta).
     * @throws Exception Si ocurre un error SQL o si el tipo de consulta no existe.
     */
    public int obtenerIdTipoConsulta(String nombreConsulta) throws Exception {
        int idTipoConsulta = -1;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // ***************************************************************
            // ¡IMPORTANTE! Reemplaza esto con tu llamada REAL a la conexión:
            // Ejemplo: conn = ConexionBD.getConnection();
            conn = null; // DEBES REEMPLAZAR ESTO con tu método de conexión.

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
