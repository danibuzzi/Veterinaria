package com.veterinaria.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPracticaDAO {

    // 🛑 Sentencias SQL
    private static final String SQL_SELECT_BY_ID =
            "SELECT idTipoPractica, descripcion FROM TipoPractica WHERE idTipoPractica = ?";
    private static final String SQL_SELECT_ALL =
            "SELECT idTipoPractica, descripcion FROM TipoPractica ORDER BY descripcion";

    public TipoPracticaDAO() {}

    /**
     * Obtiene un TipoPractica por su ID (crucial para mostrar el detalle).
     * @param idTipoPractica El ID de la práctica a buscar.
     * @return El objeto TipoPractica o null si no se encuentra.
     */
    public TipoPractica obtenerPorId(int idTipoPractica) {
        TipoPractica tipoPractica = null;

        try (Connection conn = Conexion.conectar(); // Usar el método de tu clase Conexion
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idTipoPractica);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tipoPractica = new TipoPractica();
                    tipoPractica.setIdTipoPractica(rs.getInt("idTipoPractica"));
                    tipoPractica.setDescipcion(rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al obtener TipoPractica por ID: " + e.getMessage());
            throw new RuntimeException("Fallo al obtener Tipo de Práctica por ID.", e);
        }
        return tipoPractica;
    }

    /**
     * Lista todos los Tipos de Práctica disponibles (útil para ComboBoxes).
     * @return Una lista de objetos TipoPractica.
     */
    public List<TipoPractica> listarTodos() {
        List<TipoPractica> tipos = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoPractica tipo = new TipoPractica();
                tipo.setIdTipoPractica(rs.getInt("idTipoPractica"));
                tipo.setDescipcion(rs.getString("descripcion"));
                tipos.add(tipo);
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al listar Tipos de Práctica: " + e.getMessage());
            throw new RuntimeException("Fallo al listar Tipos de Práctica.", e);
        }
        return tipos;
    }
}
