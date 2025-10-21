package com.veterinaria.modelo;

import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.TipoPractica;
import com.veterinaria.modelo.Consulta;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // --- Sentencias SQL ---
    private static final String SQL_SELECT_PROPIETARIOS = "SELECT idPropietario, nombre, apellido FROM Propietario ORDER BY apellido";
    private static final String SQL_SELECT_MASCOTAS_BY_PROPIETARIO = "SELECT idMascota, nombre, idPropietario FROM Mascota WHERE idPropietario = ?";
    private static final String SQL_SELECT_TIPOS_CONSULTA = "SELECT DISTINCT idTipoPractica, descripcion FROM TipoPractica ORDER BY descripcion";
    private static final String SQL_INSERT_CONSULTA =
            "INSERT INTO Consulta (idMascota, idPropietario, idTipoPractica, fechaConsulta, hora, resultadoEstudio, Diagnostico, Pronostico, Tratamiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // ---------------------------------------------
    // MÉTODOS DE LECTURA (Listar)
    // ---------------------------------------------

    public List<Propietario> listarPropietarios() {
        List<Propietario> propietarios = new ArrayList<>();

        // 🛑 CLAVE: try-with-resources asegura el cierre automático de la conexión
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_PROPIETARIOS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                propietarios.add(new Propietario(rs.getInt("idPropietario"), rs.getString("nombre"), rs.getString("apellido")));
            }
            return propietarios;

        } catch (SQLException e) {
            // 🛑 Envolvemos la SQLException en RuntimeException (Error de BD)
            System.err.println("Error de BD al listar propietarios: " + e.getMessage());
            throw new RuntimeException("Fallo al obtener la lista de propietarios.", e);
        }
    }
    // Archivo: ConsultaDAO.java
    public List<Mascota> listarMascotasPorPropietario(int idPropietario) {
        List<Mascota> mascotas = new ArrayList<>();

        try (Connection conn = Conexion.obtenerConexion(); // Obtiene la conexión FRESCA
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_MASCOTAS_BY_PROPIETARIO)) {

            stmt.setInt(1, idPropietario); // 🛑 Verifique que el índice es '1' y el ID no es 0

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // ... Mapeo correcto de Mascota
                    Mascota mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("idMascota"));
                    mascota.setNombre(rs.getString("nombre")); // Asegúrese que el toString() está en Mascota.java
                    mascotas.add(mascota);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al listar mascotas: " + e.getMessage());
            // 🛑 Propagar como RuntimeException para que el Controlador la atrape
            throw new RuntimeException("Fallo al obtener las mascotas.", e);
        }
        return mascotas;
    }

    public List<TipoPractica> listarTiposPractica() {
        List<TipoPractica> tipos = new ArrayList<>();


        // 🛑 CLAVE: try-with-resources
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_TIPOS_CONSULTA);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoPractica tipo=new TipoPractica();
                tipo.setIdTipoPractica(rs.getInt("idTipoPractica"));
                tipo.setDescipcion(rs.getString("descripcion"));
                /*tipos.add(new TipoPractica(rs.getInt("idTipoPractica"),
                        rs.getString("descripcion")));*/
                tipos.add(tipo);
            }
            return tipos;

        } catch (SQLException e) {
            // 🛑 Envolvemos la SQLException en RuntimeException
            System.err.println("Error de BD al listar tipos de práctica: " + e.getMessage());
            throw new RuntimeException("Fallo al obtener los tipos de práctica.", e);

        }
    }

    // ---------------------------------------------
    // MÉTODO DE ESCRITURA (Insertar)
    // ---------------------------------------------

    public void insertar(Consulta consulta) { // Método es VOID

        // 🛑 CLAVE: try-with-resources
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_CONSULTA)) {

            stmt.setInt(1, consulta.getIdMascota());
            stmt.setInt(2, consulta.getIdPropietario());
            stmt.setInt(3, consulta.getIdTipoPractica());

            // Mapeo de datos
            java.sql.Date sqlDate = new java.sql.Date(consulta.getFechaConsulta().getTime());
            stmt.setDate(4, sqlDate);
            stmt.setTime(5, consulta.getHora());

            // Campos de texto
            stmt.setString(6, consulta.getResultadoEstudio());
            stmt.setString(7, consulta.getDiagnostico());
            stmt.setString(8, consulta.getPronostico());
            stmt.setString(9, consulta.getTratamiento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            // 🛑 Envolvemos la SQLException en RuntimeException
            System.err.println("Error de BD al insertar la consulta: " + e.getMessage());
            throw new RuntimeException("Fallo al registrar la consulta en la base de datos.", e);
        }
    }
//Modifacion detalle

    // 🛑 SQL para el listado RESUMEN (NO incluye los campos de texto largo)
    private static final String SQL_SELECT_CONSULTAS_RESUMEN =
            "SELECT c.idConsulta, c.fechaConsulta, t.descripcion AS Practica, c.diagnostico, c.pronostico, c.tratamiento " +
                    "FROM Consulta c " +
                    "JOIN Mascota m ON c.idMascota = m.idMascota " + // Necesarios para el WHERE
                    "JOIN Propietario p ON c.idPropietario = p.idPropietario " + // Necesarios para el WHERE
                    "JOIN TipoPractica t ON c.idTipoPractica = t.idTipoPractica " +
                    "WHERE p.idPropietario = ? AND c.idMascota = ? AND c.fechaConsulta >= ? " +
                    "ORDER BY c.fechaConsulta DESC";

    // 🛑 SQL para la consulta DETALLE (Incluye TODOS los campos, especialmente los textos largos)
    private static final String SQL_SELECT_DETALLE_CONSULTA =
            "SELECT c.idConsulta, c.fechaConsulta, m.nombre AS Mascota, CONCAT(p.apellido, ', ', p.nombre) AS Propietario, " +
                    "       t.descripcion AS Practica, c.resultadoEstudio, c.diagnostico, c.pronostico, c.tratamiento " +
                    "FROM Consulta c " +
                    "JOIN Mascota m ON c.idMascota = m.idMascota " +
                    "JOIN Propietario p ON c.idPropietario = p.idPropietario " +
                    "JOIN TipoPractica t ON c.idTipoPractica = t.idTipoPractica " +
                    "WHERE c.idConsulta = ?";

    public Object[] consultarDetalle(int idConsulta) {
        Object[] detalle = null;
        try (Connection conn = Conexion.obtenerConexion(); // Asume tu clase de conexión
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_DETALLE_CONSULTA)) {

            stmt.setInt(1, idConsulta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detalle = new Object[]{
                            rs.getInt("idConsulta"),      // 0: ID (int)
                            rs.getDate("fechaConsulta"),  // 1: Fecha (Date)// 2: Hora (Time)
                            rs.getString("Mascota"),      // 3: Mascota (String)
                            rs.getString("Propietario"),  // 4: Propietario (String)
                            rs.getString("Practica"),     // 5: Practica (String)
                            rs.getString("resultadoEstudio"), // 6: Resultado Estudio (String)
                            rs.getString("diagnostico"),  // 7: Diagnóstico (String)
                            rs.getString("pronostico"),   // 8: Pronóstico (String)
                            rs.getString("tratamiento")   // 9: Tratamiento (String)
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al consultar detalle (consultarDetalle): " + e.getMessage());
            throw new RuntimeException("Fallo al obtener el detalle de la consulta.", e);
        }
        return detalle;
    }

    // -------------------------------------------------------------
    // 🛑 Método 2: LISTAR RESUMEN (Devuelve List<Object[]> para la tabla)
    // -------------------------------------------------------------
    public List<Object[]> listarConsultasResumen(int idPropietario,int idMascota, java.sql.Date fecha) {
        List<Object[]> lista = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_CONSULTAS_RESUMEN)) {

            stmt.setInt(1, idPropietario);
            stmt.setInt(2, idMascota); // ⬅️ Segundo Parámetro (idMascota)
            stmt.setDate(3, fecha);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Orden de la tabla: ID | Fecha | Hora | Mascota | Propietario | Práctica
                    Object[] c = new Object[]{
                            rs.getInt("idConsulta"),                   // 0: ID (oculto)
                            sdf.format(rs.getDate("fechaConsulta")),   // 1: Fecha
                            rs.getString("Practica"),                  // 2: Tipo Práctica
                            rs.getString("diagnostico"),               // 3: Diagnóstico Principal
                            rs.getString("pronostico"),                // 4: Pronóstico
                            rs.getString("tratamiento")
                    };
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al listar resumen: " + e.getMessage());
            throw new RuntimeException("Fallo al obtener el listado de consultas.", e);
        }
        return lista;
    }

}