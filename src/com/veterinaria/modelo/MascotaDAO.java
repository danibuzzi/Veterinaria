package com.veterinaria.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    public MascotaDAO() {}

    public List<Mascota> listarActivasPorPropietario(int idPropietario) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT idMascota, idPropietario, nombre, fechanacimiento, especie, sexo, seniasparticulares, activa " +
                "FROM Mascota WHERE idPropietario = ? AND activa = TRUE ORDER BY nombre";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota m = new Mascota();
                    m.setIdMascota(rs.getInt("idMascota"));
                    m.setIdPropietario(rs.getInt("idPropietario"));
                    m.setNombre(rs.getString("nombre"));

                    Date sqlDate = rs.getDate("fechanacimiento");
                    m.setFechaNacimiento(sqlDate != null ? sqlDate.toLocalDate() : null);

                    m.setEspecie(rs.getString("especie"));
                    m.set.setSexo(rs.getString("sexo"));
                    m.setSeniasParticulares(rs.getString("seniasparticulares"));
                    m.setActiva(rs.getBoolean("activa"));
                    mascotas.add(m);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar mascotas por propietario: " + e.getMessage());
        }
        return mascotas;
    }
}
