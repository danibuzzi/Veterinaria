package com.veterinaria.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PropietarioDAO {

    public PropietarioDAO() {
    }

    public List<Propietario> listarTodosActivos() {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = "SELECT idPropietario, dni, nombre, apellido, fechaNacimiento, direccion, pais, ciudad FROM Propietario ORDER BY apellido, nombre";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Propietario p = new Propietario();
                p.setIdPropietario(rs.getInt("idPropietario"));
                p.setDni(rs.getString("dni"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                Date sqlDate = rs.getDate("fechaNacimiento");
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

    //Agregado para consulta mod propietario

    // Búsqueda por Apellido o DNI (No exacta, "starts with")
    private static final String SQL_BUSCAR_PROPIETARIOS =
            "SELECT idPropietario, dni, nombre, apellido, fechaNacimiento, direccion, pais, ciudad, telefono, email FROM Propietario " +
                    "WHERE %s LIKE ? ORDER BY apellido, nombre";

    private Propietario mapResultSetToPropietario(ResultSet rs) throws SQLException {

        Propietario p = new Propietario();
        p.setIdPropietario(rs.getInt("idPropietario"));
        p.setDni(rs.getString("dni"));
        System.out.println(rs.getString("dni"));
        p.setNombre(rs.getString("nombre"));
        p.setApellido(rs.getString("apellido"));
        Date sqlDate = rs.getDate("fechaNacimiento");
        p.setFechaNacimiento(sqlDate != null ? sqlDate.toLocalDate() : null);
        p.setDireccion(rs.getString("direccion"));
        p.setPais(rs.getString("pais"));
        p.setCiudad(rs.getString("ciudad"));
        p.setTelefono(rs.getString("telefono"));
        p.setEmail(rs.getString("email"));
        //p.setActivo(rs.getBoolean("activa"));
        return p;
    }

    public List<Propietario> buscarPropietarios(String textoBusqueda, String tipoBusqueda) {
        List<Propietario> propietarios = new ArrayList<>();
        String campoBusqueda = tipoBusqueda.equalsIgnoreCase("DNI") ? "dni" : "apellido";
        String sql = String.format(SQL_BUSCAR_PROPIETARIOS, campoBusqueda);

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Uso de LIKE: ?% para búsqueda no exacta (empieza con)
            ps.setString(1, textoBusqueda + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propietarios.add(mapResultSetToPropietario(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar propietarios: " + e.getMessage(), e);
        }
        return propietarios;
    }

    public Propietario obtenerPorId(int idPropietario) {
        Propietario propietario = null;
        String sql = "SELECT idPropietario, dni, nombre, apellido, fechaNacimiento, direccion, pais, ciudad, telefono, email FROM Propietario WHERE idPropietario = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    propietario = mapResultSetToPropietario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener propietario por ID: " + e.getMessage(), e);
        }
        return propietario;
    }

    public void actualizar(Propietario p) {
        String sql = "UPDATE Propietario SET nombre = ?, apellido = ?, fechaNacimiento = ?, direccion = ?, pais = ?, ciudad = ?, telefono = ?, email = ? WHERE idPropietario = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            ps.setString(i++, p.getNombre());
            ps.setString(i++, p.getApellido());

            // Convertir LocalDate a SQL Date
            ps.setDate(i++, p.getFechaNacimiento() != null ? Date.valueOf(p.getFechaNacimiento()) : null);

            ps.setString(i++, p.getDireccion());
            ps.setString(i++, p.getPais());
            ps.setString(i++, p.getCiudad());
            ps.setString(i++, p.getTelefono());
            ps.setString(i++, p.getEmail());
            ps.setInt(i++, p.getIdPropietario()); // WHERE

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el propietario: " + e.getMessage(), e);
        }
    }

    // Eliminación lógica (se usa esta para la integridad referencial)
    public void eliminarPropietario(int idPropietario) {
        String sql = "DELETE FROM Propietario WHERE idPropietario = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPropietario);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                // Esto solo ocurre si el ID no existe
                throw new RuntimeException("No se encontró el propietario con ID " + idPropietario + " para eliminar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el propietario: " + e.getMessage(), e);
        }
    }


}

