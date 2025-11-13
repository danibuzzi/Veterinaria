package com.veterinaria.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {


    private static final String SQL_INSERT_MASCOTA =
            "INSERT INTO Mascota (idPropietario, nombre, fechanacimiento, especie, raza, sexo, seniasparticulares, activa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    //  Se actualizan todos los campos editables, se usa idMascota en el WHERE
   // private static final String SQL_UPDATE_MASCOTA =
     //       "UPDATE Mascota SET nombre = ?, fechanacimiento = ?, especie = ?, raza = ?, sexo = ?, seniasparticulares = ? WHERE idMascota = ?";

    private static final String SQL_UPDATE_MASCOTA =
            "UPDATE Mascota SET nombre = ?, fechanacimiento = ?, especie = ?, raza = ?, sexo = ?, seniasparticulares = ?, activa = ? WHERE idMascota = ?";

    // ELIMINACIÓN LÓGICA: Marca como inactiva (activa = false)
    private static final String SQL_DELETE_LOGICO_MASCOTA =
            "UPDATE Mascota SET activa = ? WHERE idMascota = ?";

    // BÚSQUEDA POR ID
    private static final String SQL_SELECT_BY_ID =
            "SELECT idMascota, idPropietario, nombre, fechanacimiento, especie, raza, sexo, seniasparticulares, activa FROM Mascota WHERE idMascota = ?";

    // LISTAR POR PROPIETARIO
    private static final String SQL_SELECT_ACTIVAS_POR_PROPIETARIO =
            "SELECT idMascota, idPropietario, nombre, fechanacimiento, especie, raza, sexo, seniasparticulares, activa FROM Mascota WHERE idPropietario = ? AND activa = TRUE ORDER BY nombre";


    public MascotaDAO() {}

   public void insertar(Mascota mascota) {

        try (Connection conn = Conexion.conectar();
             // Usa RETURN_GENERATED_KEYS para poder obtener el ID generado (opcional)
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT_MASCOTA, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, mascota.getIdPropietario());
            ps.setString(2, mascota.getNombre());
            // Conversión de LocalDate a java.sql.Date
            ps.setDate(3, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(4, mascota.getEspecie());
            ps.setString(5, mascota.getRaza());
            ps.setString(6, mascota.getSexo());
            ps.setString(7, mascota.getSeniasParticulares());
            ps.setBoolean(8, true); // Por defecto, la mascota se crea como activa

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("La inserción de la mascota falló, no se afectaron filas.");
            }

            // Opcional: Asignar el ID generado al objeto
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    mascota.setIdMascota(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de BD al insertar mascota: " + e.getMessage());
            throw new RuntimeException("Fallo al registrar la nueva mascota en el sistema.", e);
        }
    }

    /*public List<Mascota> listarActivasPorPropietario(int idPropietario) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT idMascota, idPropietario, nombre, fechanacimiento, especie, sexo, seniasparticulares " +
                "FROM Mascota WHERE idPropietario = ? ORDER BY nombre";

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
                    m.setSexo(rs.getString("sexo"));
                    m.setSeniasParticulares(rs.getString("seniasparticulares"));
                    mascotas.add(m);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar mascotas por propietario: " + e.getMessage());
        }
        return mascotas;
    }*/

    //Para consulta modfiaciony eliminacion de mascotas


    public Mascota obtenerPorId(int idMascota) {
        Mascota mascota = null;
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("idMascota"));
                    mascota.setIdPropietario(rs.getInt("idPropietario"));
                    mascota.setNombre(rs.getString("nombre"));

                    Date sqlDate = rs.getDate("fechanacimiento");
                    mascota.setFechaNacimiento(sqlDate != null ? sqlDate.toLocalDate() : null);

                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setRaza(rs.getString("raza")); // <--- Se incluye raza
                    mascota.setSexo(rs.getString("sexo"));
                    mascota.setSeniasParticulares(rs.getString("seniasparticulares"));
                    mascota.setActiva(rs.getBoolean("activa"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener Mascota por ID.", e);
        }
        return mascota;
    }

    // Actualizar Mascota (Modificación)
    public void actualizar(Mascota mascota) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_MASCOTA)) {

            int i = 1;
            ps.setString(i++, mascota.getNombre());
            ps.setDate(i++, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(i++, mascota.getEspecie());
            ps.setString(i++, mascota.getRaza());
            ps.setString(i++, mascota.getSexo());
            ps.setString(i++, mascota.getSeniasParticulares());

            //  activa
            ps.setBoolean(i++, mascota.isActiva());
            // ID de la Mascota para el WHERE
           ps.setInt(i++, mascota.getIdMascota());

            //idPropietario
            //ps.setInt(i++, mascota.getIdPropietario());



            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Error: No se encontró la mascota para actualizar.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la mascota.", e);
        }
    }

    // --- NUEVO MÉTODO: Eliminación Lógica ---
    public void eliminarLogico(int idMascota) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE_LOGICO_MASCOTA)) {

            ps.setBoolean(1, false); // activa = false
            ps.setInt(2, idMascota);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Error: No se encontró la mascota para eliminar.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la eliminación lógica de la mascota.", e);
        }
    }

    public List<Mascota> listarActivasPorPropietario(int idPropietario) {
        // ... (Implementación, asegurando que se lea la 'raza' del ResultSet) ...
        List<Mascota> mascotas = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ACTIVAS_POR_PROPIETARIO)) {

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
                    m.setRaza(rs.getString("raza")); // <--- Se incluye raza
                    m.setSexo(rs.getString("sexo"));
                    m.setSeniasParticulares(rs.getString("seniasparticulares"));
                    m.setActiva(rs.getBoolean("activa"));
                    mascotas.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mascotas por propietario.", e);
        }
        return mascotas;
    }

    //Agregado para integridad

    // Chequeo de integridad: cuenta mascotas activas asociadas a un propietario.
    public int contarMascotasActivasPorPropietario(int idPropietario) {
        String sql = "SELECT COUNT(*) FROM Mascota WHERE idPropietario = ?";
        int count=0;
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar mascotas activas del propietario: " + e.getMessage(), e);
        }
        return count;
    }


}
