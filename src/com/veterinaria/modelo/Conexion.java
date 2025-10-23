package com.veterinaria.modelo;



import com.veterinaria.modelo.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class Conexion {

        // --- CONFIGURACIÓN DE LA BASE DE DATOS ---

        private static final String URL = "jdbc:mysql://localhost:3306/veterinaria";
        private static final String USUARIO = "root";
        private static final String CLAVE = "1234"; // ¡CAMBIAR!
        private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

        private static Connection conexion = null;

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
                System.err.println("  [ERROR CRÍTICO DE CONEXIÓN] La conexión a la base de datos ha fallado.");
                System.err.println("   Causa: " + e.getMessage());
                System.err.println("   Código SQL: " + e.getSQLState());

                // Relanza la excepción para que el Gestor (capa superior) la intercepte
                // y muestre un mensaje amigable al usuario.
                throw new SQLException("Fallo de Conexión a BD. Verifique que MySQL esté activo y las credenciales sean correctas.", e);
            }
        }

       /* public static Connection obtenerConexion() {
            if (conexion == null) {
                try {
                    Class.forName(JDBC_DRIVER);
                    conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
                    System.out.println("Conexión exitosa a la base de datos.");
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Error: No se encontró el driver JDBC.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error de SQL al conectar: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            return conexion;
        }

        /*public static void cerrarConexion() {
            if (conexion != null) {
                try {
                    conexion.close();
                    conexion = null;
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/

        public static Connection obtenerConexion() throws SQLException {

            // Cargar el Driver
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Error: No se encontró el driver JDBC.", e);
            }

            // SIEMPRE se crea una NUEVA instancia de conexión
            try {
                Connection connection = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("✅ [DEBUG] Conexión a MySQL exitosa.");
                return connection;
            } catch (SQLException e) {
                System.err.println(" [ERROR CRÍTICO] La conexión ha fallado. Causa: " + e.getMessage());
                throw new SQLException("Fallo de Conexión a BD. Verifique el servidor y las credenciales.", e);
            }
        }
    }

