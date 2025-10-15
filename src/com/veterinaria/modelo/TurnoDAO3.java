package com.veterinaria.modelo;


// Archivo: com/veterinaria/modelo/TurnoDAO3.java

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

// Se asume que tienes una clase Conexion con un método conectar() estático.
// 🛑 IMPORTANTE: Asegúrate de que tienes un import y acceso a tu clase de Conexión.
// import com.veterinaria.modelo.Conexion;

public class TurnoDAO3 { // 🛑 Nombre de la clase corregido

    public TurnoDAO3() {
        // Inicialización de la base de datos, si es que no requiere parámetros externos
    }

    // Consulta de inserción
    private static final String INSERT_SQL =
            "INSERT INTO turno (idTipoConsulta, idPropietario, idMascota, fechaturno, hora) " +
                    "VALUES (?, ?, ?, ?, ?)";

    // Método simulado de conexión (ADAPTAR A TU CÓDIGO)
   /* private Connection getConnection() throws SQLException {
        // Aquí debe ir la llamada a tu clase de conexión real:
        // return Conexion.conectar();
        throw new UnsupportedOperationException("El método getConnection() debe ser implementado con la lógica de tu clase Conexion.");
    }*/

    // ----------------------------------------------------
    // MÉTODOS CRUD y DE GESTIÓN
    // ----------------------------------------------------

    public boolean guardar(Turno turno) throws SQLException {
        try (Connection con =Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, turno.getIdTipoConsulta());
            ps.setInt(2, turno.getIdPropietario());
            ps.setInt(3, turno.getIdMascota());
            ps.setString(4, turno.getFechaTurno());
            ps.setString(5, turno.getHora());

            return ps.executeUpdate() > 0;
        }
    }

    /** Obtiene todas las horas de inicio ya reservadas para una fecha dada. */
    public Set<String> obtenerHorasInicioOcupadas(Date fecha) throws SQLException {
        Set<String> horasOcupadas = new HashSet<>();
        SimpleDateFormat formatoFechaSQL = new SimpleDateFormat("yyyy-MM-dd");
        String fechaStr = formatoFechaSQL.format(fecha);

        // La consulta obtiene solo la hora y minuto (HH:MM)
        String sql = "SELECT SUBSTR(hora, 1, 5) AS hora_hhmm FROM turno WHERE fechaturno = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, fechaStr);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    horasOcupadas.add(rs.getString("hora_hhmm"));
                }
            }
        }
        return horasOcupadas;
    }

    // ----------------------------------------------------
    // MÉTODOS DE BÚSQUEDA DE IDS Y NOMBRES
    // ----------------------------------------------------

    public int obtenerIdPropietario(String nombrePropietario) throws SQLException {
        String sql = "SELECT idPropietario FROM propietario WHERE nombre = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombrePropietario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("idPropietario");
            }
        }
        return -1;
    }

    public int obtenerIdTipoConsulta(String nombreTipo) throws SQLException {
        String sql = "SELECT idTipoConsulta FROM tipoconsulta WHERE descripcion = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreTipo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("idTipoConsulta");
            }
        }
        return -1;
    }

    public int obtenerIdMascota(String nombreMascota) throws SQLException {
        String sql = "SELECT idMascota FROM mascota WHERE nombre = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreMascota);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("idMascota");
            }
        }
        return -1;
    }

    // ----------------------------------------------------
    // MÉTODOS DE CARGA DE COMBOBOX
    // ----------------------------------------------------

    public List<String> obtenerNombresTiposConsulta() throws SQLException {
        List<String> nombres = new ArrayList<>();

        // 🛑 CORRECCIÓN: Usamos 'descripcion' en lugar de 'nombre' en la consulta SQL.
        String nombreColumnaReal = "descripcion";

        // Asumo que la tabla se llama 'tipo_consulta'
        String sql = "SELECT " + nombreColumnaReal + " FROM tipoconsulta ORDER BY " + nombreColumnaReal;

        try (Connection conexion = Conexion.conectar(); // Usando tu método conectar()
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // 🛑 CRUCIAL: Usamos 'descripcion' para obtener el String del ResultSet
            while (rs.next()) {
                nombres.add(rs.getString(nombreColumnaReal));
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL al cargar Tipos de Consulta: " + e.getMessage());
            throw e;
        }

        return nombres;
    }

    public List<String> obtenerNombresPropietarios() throws SQLException {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM propietario ORDER BY nombre";
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) nombres.add(rs.getString("nombre"));
        }
        return nombres;
    }

    public List<String> obtenerNombresMascotasPorPropietario(int idPropietario) throws SQLException {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM mascota WHERE idPropietario = ? ORDER BY nombre";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPropietario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) nombres.add(rs.getString("nombre"));
            }
        }
        return nombres;


    }

    public List<Object[]> buscarDatosParaGrillaPorFecha(String fecha) throws SQLException {
        List<Object[]> datosGrilla = new ArrayList<>();

        // El SQL debe obtener el ID del Turno (t.idTurno) + los nombres/descripciones para la grilla.
        String sql =   "SELECT t.idTurno, t.hora, " +
                "tc.descripcion AS TipoConsultaNombre, " +
                "CONCAT(p.apellido, ', ', p.nombre) AS NombrePropietario, " +
                "m.nombre AS NombreMascota ,t.fechaturno " +
                "FROM turno t " +
                "JOIN tipoconsulta tc ON t.idTipoConsulta = tc.idTipoConsulta " +
                "JOIN propietario p ON t.idPropietario = p.idPropietario " +
                "JOIN mascota m ON t.idMascota = m.idMascota " +
                "WHERE t.fechaturno = ?";


                /*/"SELECT t.idTurno, t.hora, " +
                "tc.descripcion AS TipoConsultaNombre, " + // Nombre del Tipo
                "CONCAT(p.apellido,', ',p.nombre) AS NombrePropietario, " + // Nombre del Propietario
                "m.nombre AS NombreMascota ,t.fechaturno" + // Nombre de la Mascota
                "FROM turno t " +
                "JOIN tipoconsulta tc ON t.idTipoConsulta = tc.idTipoConsulta " +
                "JOIN propietario p ON t.idPropietario = p.idPropietario " +
                "JOIN mascota m ON t.idMascota = m.idMascota " +
                "WHERE t.fechaturno = ?";*/

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fecha);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    // 🛑 Creamos el array genérico para una fila de la grilla (6 columnas)
                    Object[] fila = new Object[6];

                    // 1. ID del Turno (CLAVE para la gestión, columna oculta o primera en la tabla)
                    fila[0] = rs.getInt("idTurno");

                    // 2. Hora
                    fila[1] = rs.getString("hora");

                    // 4. Tipo de Consulta (Nombre)
                    fila[2] = rs.getString("TipoConsultaNombre");

                    // 5. Propietario (Nombre)
                    fila[3] = rs.getString("NombrePropietario");

                    // 6. Mascota (Nombre)
                    fila[4] = rs.getString("NombreMascota");

                    datosGrilla.add(fila);
                }
            }
        }
        return datosGrilla;
    }

    public boolean eliminarTurnoPorId(int idTurno) throws SQLException {
        String sql = "DELETE FROM turno WHERE idTurno = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            return ps.executeUpdate() > 0;
        }
    }

    // 🛑 NOTA: Recibe el ID por separado y el objeto 'Turno' con los nuevos datos.
    public boolean actualizarTurno(int idTurno, Turno turno) throws SQLException {

        // Actualiza los campos modificables (usamos los getters de tu objeto Turno)
        String sql = "UPDATE turno SET idTipoConsulta=?, fechaturno=?, hora=? WHERE idTurno = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Seteamos los nuevos valores del objeto Turno
            ps.setInt(1, turno.getIdTipoConsulta());
            ps.setString(2, turno.getFechaTurno());
            ps.setString(3, turno.getHora());

            // Seteamos el ID del turno para la cláusula WHERE
            ps.setInt(4, idTurno);

            return ps.executeUpdate() > 0;
        }
    }
}