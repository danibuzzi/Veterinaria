package com.veterinaria.modelo;



import com.veterinaria.modelo.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class Conexion {

        // --- CONFIGURACIÓN DE LA BASE DE DATOS ---
        private static final String URL = "jdbc:mysql://localhost:3306/veterinaria";
        private static final String USUARIO = "root";
        private static final String CLAVE = "1234"; // ¡CAMBIAR!

        /**
         * Establece la conexión a la base de datos MySQL.
         * @return Objeto Connection activo.
         * @throws SQLException Si la conexión falla (servidor apagado, credenciales erróneas, driver no encontrado, etc.).
         */
        public static Connection conectar() throws SQLException {
            Connection connection = null;
            try {
                // Intenta establecer la conexión
                connection = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("✅ [DEBUG] Conexión a MySQL exitosa.");
                return connection;

            } catch (SQLException e) {
                // Manejo y diagnóstico de errores en la consola del IDE
                System.err.println("❌ [ERROR CRÍTICO DE CONEXIÓN] La conexión a la base de datos ha fallado.");
                System.err.println("   Causa: " + e.getMessage());
                System.err.println("   Código SQL: " + e.getSQLState());

                // Relanza la excepción para que el Gestor (capa superior) la intercepte
                // y muestre un mensaje amigable al usuario.
                throw new SQLException("Fallo de Conexión a BD. Verifique que MySQL esté activo y las credenciales sean correctas.", e);
            }
        }
    }

