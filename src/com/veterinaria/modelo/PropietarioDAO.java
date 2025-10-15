package com.veterinaria.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Asumimos que existe la clase 'Conexion' con el método estático obtenerConexion()
public class PropietarioDAO {

    public PropietarioDAO() {}

    public List<Propietario> listarTodosActivos() {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = "SELECT idPropietario, dni, nombre, apellido, fechanacimiento, direccion, pais, ciudad FROM Propietario WHERE activo = TRUE ORDER BY apellido, nombre";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Propietario p = new Propietario();
                p.setIdPropietario(rs.getInt("idPropietario"));
                p.setDni(rs.getString("dni"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                Date sqlDate = rs.getDate("fechanacimiento");
                p.setFechaNacimiento(sqlDate != null ? sqlDate.toLocalDate() : null);
                p.setDireccion(rs.getString("direccion"));
                p.setPais(rs.getString("pais"));
                p.setCiudad(rs.getString("ciudad"));
                propietarios.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar propietarios activos: " + e.getMessage());
        }
        return propietarios;
    }
}