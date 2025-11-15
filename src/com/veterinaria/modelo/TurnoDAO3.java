package com.veterinaria.modelo;



import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class TurnoDAO3 {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public TurnoDAO3() {

    }

    // Consulta de inserción de turno
    private static final String INSERT_SQL =
            "INSERT INTO turno (idTipoConsulta, idPropietario, idMascota, fechaturno, hora) " +
                    "VALUES (?, ?, ?, ?, ?)";


    // ----------------------------------------------------
    // MÉTODOS CRUD y DE GESTIÓN de turno
    // ----------------------------------------------------

    public boolean guardar(Turno turno) throws SQLException {
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, turno.getIdTipoConsulta());
            ps.setInt(2, turno.getIdPropietario());
            ps.setInt(3, turno.getIdMascota());
            ps.setString(4, turno.getFechaTurno());
            ps.setString(5, turno.getHora());

            return ps.executeUpdate() > 0;
        }
    }


    //Obtencion de las horas ocupadas
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
                    // 1. Obtener la hora. Puede venir como "8:30" o "10:00"
                    String horaSinFormato = rs.getString("hora_hhmm");

                    //  Normalizamos el formato de hora a HH:mm

                    if (horaSinFormato != null && horaSinFormato.length() == 4) {
                        horaSinFormato = "0" + horaSinFormato; // Convierte "8:30" a "08:30"
                    }

                    // Añadimos la hora normalizada
                    horasOcupadas.add(horaSinFormato);
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

        String nombreColumnaReal = "descripcion";

        String sql = "SELECT " + nombreColumnaReal + " FROM tipoconsulta ORDER BY " + nombreColumnaReal;

        try (Connection conexion = Conexion.conectar(); // Usando tu método conectar()
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        String sql = "SELECT CONCAT(apellido, ', ',nombre) as nombre FROM propietario ORDER BY apellido";
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

        String sql = " SELECT t.idTurno, t.hora, " +
                "tc.descripcion AS TipoConsultaNombre, " +
                "CONCAT(p.apellido, ', ', p.nombre) AS NombrePropietario, " +
                "m.nombre AS NombreMascota, t.fechaturno, " +
                "t.idPropietario, t.idMascota, t.idTipoConsulta " +
                "FROM turno t " +
                "JOIN tipoconsulta tc ON t.idTipoConsulta = tc.idTipoConsulta " +
                "JOIN propietario p ON t.idPropietario = p.idPropietario " +
                "JOIN mascota m ON t.idMascota = m.idMascota " +
                "WHERE t.fechaturno = ?";


        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fecha);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    // Creamos el array genérico para una fila de la grilla (6 columnas)
                    Object[] fila = new Object[9];

                    // ID del Turno (CLAVE para la gestión, columna oculta o primera en la tabla)
                    fila[0] = rs.getInt("idTurno");

                    //  Hora
                    fila[1] = rs.getString("hora");

                    // Tipo de Consulta
                    fila[2] = rs.getString("TipoConsultaNombre");

                    // Propietario
                    fila[3] = rs.getString("NombrePropietario");

                    // 6. Mascota
                    fila[4] = rs.getString("NombreMascota");
                    fila[5] = rs.getString("fechaturno");

                    //AÑADIMOS LOS IDs NUMÉRICOS (Índices 6, 7, 8)
                    fila[6] = rs.getInt("idPropietario"); // El ID de la tabla Propietario
                    fila[7] = rs.getInt("idMascota");     // El ID de la tabla Mascota
                    fila[8] = rs.getInt("idTipoConsulta"); // El ID de la tabla TipoConsulta
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

    // Actualziacion del turno  Recibe el ID por separado y el objeto 'Turno' con los nuevos datos.
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

    public int obtenerIDPropietarioPorTurnoID(String idTurnoStr) throws SQLException {
        String sql = "SELECT idPropietario FROM turno WHERE idTurno = ?";
        // Convertir el String a int para pasarlo al PreparedStatement
        int idTurno = Integer.parseInt(idTurnoStr);

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idPropietario");
                } else {
                    // Manejamos el caso si no se encuentra el turno
                    return 0; // O lanzar una excepción específica si lo prefieres
                }
            }
        }
    }

    public int obtenerIDMascotaPorTurnoID(String idTurnoStr) throws SQLException {
        String sql = "SELECT idMascota FROM turno WHERE idTurno = ?";
        int idTurno = Integer.parseInt(idTurnoStr);
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idMascota");
                } else {
                    // Manejar el caso si no se encuentra el turno
                    return 0; // O lanzar una excepción específica si lo prefieres
                }
            }
        }
    }

    public int obtenerIDTipoConsultaPorTurnoID(String idTurnoStr) throws SQLException {
        String sql = "SELECT idTipoConsulta FROM turno WHERE idTurno = ?";
        int idTurno = Integer.parseInt(idTurnoStr);
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idTipoConsulta");
                } else {
                    // Manejar el caso si no se encuentra el turno
                    return 0; // O lanzar una excepción específica si lo prefieres
                }
            }
        }
    }
    //Metodos para consulta turnos propietario


           /* Obtiene una lista de todos los propietarios para el ComboBox.
             El formato es: ID;Nombre Apellido
     */
    public List<String> obtenerPropietariosParaCombo() throws SQLException {
        List<String> propietarios = new ArrayList<>();
        // ASUMO una tabla 'propietario' con columnas 'idPropietario', 'nombre', 'apellido'
        String sql = "SELECT idPropietario, nombre, apellido FROM propietario ORDER BY apellido, nombre";

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("idPropietario");
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                // Formato clave: ID;Nombre Apellido
                propietarios.add(id + ";" + nombreCompleto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener propietarios para combo: " + e.getMessage());
            throw e;
        }
        return propietarios;
    }

    /**
     * Consulta los turnos de un propietario, uniendo Mascota y TipoConsulta.
     * Devuelve directamente Object[] para el TableModel.
     * Columnas: [Fecha Formateada, Hora, Tipo Consulta, Mascota]
     */
    public List<Object[]> obtenerTurnosPorPropietario(int idPropietario, String fechaDesde) throws SQLException {
        List<Object[]> turnos = new ArrayList<>();

        // La condición de filtro incluye el idPropietario Y la fechaDesde
        String sql = "SELECT " +
                "t.fechaTurno, " +
                "t.hora, " +
                "tc.nombre AS nombreTipoConsulta, " +
                "m.nombre AS nombreMascota " +
                "FROM turno t " +
                "JOIN mascota m ON t.idMascota = m.idMascota " +
                "JOIN tipoconsulta tc ON t.idTipoConsulta = tc.idTipoConsulta " +
                "WHERE t.idPropietario = ? AND t.fechaTurno >= ? " + // <-- DOBLE FILTRO
                "ORDER BY t.fechaTurno ASC, t.hora ASC"; // Ordenamos ascendentemente para ver los más viejos primero

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPropietario);
            ps.setString(2, fechaDesde); // Se asigna la fecha

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 1. Obtener los datos
                    Date fecha = rs.getDate("fechaTurno");
                    String hora = rs.getString("hora");
                    String tipo = rs.getString("nombreTipoConsulta");
                    String mascota = rs.getString("nombreMascota");

                    // 2. Formatear la fecha
                    String fechaFormateada = (fecha != null) ? sdf.format(fecha) : "";

                    // 3. Crear el Object[]
                    Object[] fila = {
                            fechaFormateada,
                            hora,
                            tipo,
                            mascota
                    };

                    turnos.add(fila);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar turnos por propietario y fecha: " + e.getMessage());
            throw e;
        }
        return turnos;
    }
}



