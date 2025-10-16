package com.veterinaria.modelo;



import com.veterinaria.modelo.TipoConsulta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoConsultaDAO {


    /**
     * Obtiene todos los tipos de consulta de la DB.
     */
    public List<TipoConsulta> obtenerTodos() throws SQLException {
        List<TipoConsulta> tipos = new ArrayList<>();
        // ASUMO que tu tabla se llama TipoConsulta
        String sql = "SELECT idTipoConsulta, descripcion FROM TipoConsulta ORDER BY descripcion";

        try (Connection conn = Conexion.conectar(); // Usar tu clase de conexión
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoConsulta tc = new TipoConsulta(
                        rs.getInt("idTipoConsulta"),
                        rs.getString("descripcion")
                );
                tipos.add(tc);
            }
        }
        return tipos;
    }

    /**
     * Obtiene el ID del Tipo de Consulta a partir de su nombre.
     */
    public int obtenerIdPorNombre(String nombre) throws SQLException {
        int id = -1;
        String sql = "SELECT idTipoConsulta FROM TipoConsulta WHERE descripcion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("idTipoConsulta");
                }
            }
        }
        return id;
    }
}