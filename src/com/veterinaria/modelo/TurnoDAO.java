
// Archivo: com/vetapp/modelo/dao/TurnoDAO.java
package com.veterinaria.modelo;

// Archivo: com/vetapp/modelo/dao/TurnoDAO.java


import com.veterinaria.modelo.Conexion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import  java.util.Date;

import static java.sql.DriverManager.getConnection;

public class TurnoDAO {

    private static final String INSERT_SQL =
            "INSERT INTO turno (idTipoConsulta, idPropietario, idMascota, fechaturno, hora) " +
                    "VALUES (?, ?, ?, ?, ?)";

    public boolean guardar(Turno turno) throws SQLException {
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, turno.getIdTipoConsulta());
            ps.setInt(2, turno.getIdPropietario());
            ps.setInt(3, turno.getIdMascota());
            ps.setString(4, turno.getFechaTurno());
            ps.setString(5, turno.getHora());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw e;
        }
    }

    // --- CARGA DE NOMBRES PARA COMBOBOX ---

    // Incluye el DNI en el nombre para evitar duplicados.
    public List<String> obtenerNombresPropietarios() throws SQLException {
        List<String> nombres = new ArrayList<>();
        nombres.add("--- Seleccione Propietario ---");

        final String SQL = "SELECT CONCAT(nombre, ' ', apellido, ' (DNI: ', dni, ')') AS nombre_unico FROM propietario";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre_unico"));
            }
        }
        return nombres;
    }

    public List<String> obtenerNombresMascotasPorPropietario(int idPropietario) throws SQLException {
        List<String> nombres = new ArrayList<>();
        nombres.add("--- Seleccione Mascota ---");
        final String SQL = "SELECT nombre FROM mascota WHERE idPropietario = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, idPropietario);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    nombres.add(rs.getString("nombre"));
                }
            }
        }
        return nombres;
    }

    public List<String> obtenerNombresTiposConsulta() throws SQLException {
        List<String> nombres = new ArrayList<>();
        nombres.add("--- Seleccione Tipo de Consulta ---");
        final String SQL = "SELECT descripcion FROM tipoconsulta";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("descripcion"));
            }
        }
        return nombres;
    }

    // --- BÚSQUEDA DE ID (Traducción de String a Int al GUARDAR) ---

    // Usa la cadena única (Nombre + DNI)
    public int obtenerIdPropietarioPorNombre(String cadenaUnica) throws SQLException {
        final String SQL = "SELECT idPropietario FROM propietario WHERE CONCAT(nombre, ' ', apellido, ' (DNI: ', dni, ')') = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, cadenaUnica);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) { return rs.getInt("idPropietario"); }
            }
        }
        return 0;
    }

    // Requiere el ID de Propietario para ser precisa.
    public int obtenerIdMascotaPorNombre(String nombreMascota, int idPropietario) throws SQLException {
        final String SQL = "SELECT idMascota FROM mascota WHERE nombre = ? AND idPropietario = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, nombreMascota);
            ps.setInt(2, idPropietario);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) { return rs.getInt("idMascota"); }
            }
        }
        return 0;
    }

    public int obtenerIdTipoConsultaPorNombre(String descripcion) throws SQLException {
        final String SQL = "SELECT idTipoConsulta FROM tipoconsulta WHERE descripcion = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, descripcion);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) { return rs.getInt("idTipoConsulta"); }
            }
        }
        return 0;
    }

    // Archivo: TurnoDAO.java (Debe ser añadido a su clase)

    public List<String> obtenerTiposConsulta() throws SQLException {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT nombre FROM tipoconsulta ORDER BY nombre"; // Asumiendo que existe una tabla 'tipo_consulta'

        // Asumiendo que su clase TurnoDAO tiene un método para obtener la conexión
        try (Connection conn = getConnection(sql);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Añadir una opción predeterminada al inicio
            tipos.add("--- Seleccione Tipo de Consulta ---");

            while (rs.next()) {
                tipos.add(rs.getString("nombre"));
            }
        }
        return tipos;
    }

    /**
     * Consulta la base de datos y devuelve un Set de Strings (HH:mm) con las horas de inicio de turnos ocupados para una fecha.
     */
    public Set<String> obtenerHorasInicioOcupadas(Date fecha) throws SQLException {
        Set<String> horasOcupadas = new HashSet<>();
        String formatoFecha = "yyyy-MM-dd";
        String fechaStr = new SimpleDateFormat(formatoFecha).format(fecha);

        // 🛑 QUERY: Debe traer solo la hora de inicio del turno.
        // AJUSTA ESTA FUNCIÓN SEGÚN TU BASE DE DATOS (MySQL: DATE_FORMAT(hora, '%H:%i'), SQL Server: FORMAT(hora, 'HH:mm'))
        String sql = "SELECT DATE_FORMAT(hora, '%H:%i') AS hora_inicio FROM Turnos WHERE fecha = ?";

        // Asumiendo que existe un método obtenerConexion()
        try (Connection conexion =  getConnection(sql);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, fechaStr);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    horasOcupadas.add(rs.getString("hora_inicio"));
                }
            }
        }
        return horasOcupadas;
    }

    public boolean verificarDisponibilidad(String fecha, String hora) throws SQLException {

        // Consulta SQL que cuenta el número de turnos con esa fecha y hora.
        String sql = "SELECT COUNT(*) FROM turno WHERE fechaturno = ? AND hora = ?";
        int count = 0;

        // 🛑 DEBES ADAPTAR ESTE BLOQUE TRY-WITH-RESOURCES A TU LÓGICA DE CONEXIÓN A LA BD
        try (Connection conn =  Conexion.conectar(); // Asume que tienes un método obtenerConexion()
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fecha); // Pasa la fecha (ej: '2025-10-16')
            ps.setString(2, hora);   // Pasa la hora (ej: '15:00:00')

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // Obtiene el valor de COUNT(*)
                }
            }
        }

        // Si count es mayor que 0, significa que el turno YA EXISTE (fue guardado por el primer clic).
        return count > 0;
}
}