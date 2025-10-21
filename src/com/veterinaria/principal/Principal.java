package com.veterinaria.principal;

/*import javax.swing.*;

import com.veterinaria.modelo.Conexion;
import com.veterinaria.modelo.GestorTurno3;
import com.veterinaria.modelo.TurnoDAO3;
import com.veterinaria.vista.*;

import java.sql.SQLException;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TurnoDAO3 turnoDAO = new TurnoDAO3();
                // 1. INICIALIZAR EL MODELO
               // ModeloTurnos modelo = new ModeloTurnos();

                // 2. INICIALIZAR LA VISTA
                //VentanaPrincipal vista = new VentanaPrincipal();

                // 1. CREACIÓN DEL MODELO DE LÓGICA (GestorTurno)
                GestorTurno3 gestorTurno = new GestorTurno3(turnoDAO);

                // 2. CREACIÓN DE LA VISTA PRINCIPAL (Inyección de Dependencias)
                VentanaPrincipal2 vistaPrincipal = new VentanaPrincipal2(gestorTurno);

                // 3. INICIALIZAR Y CONECTAR EL CONTROLADOR (El ensamblaje MVC)
                //ControladorPrincipal controlador = new ControladorPrincipal(modelo, vista);

                // El controlador ya está conectado a los menús.

                // 4. MOSTRAR LA VISTA PRINCIPAL
                vistaPrincipal.setVisible(true);

            }
        });
    }
}*/

import com.veterinaria.modelo.FactoriaServicios; // Importar la nueva clase
import com.veterinaria.vista.VentanaPrincipal2;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // ✅ 1. Inicialización ÚNICA de la Factoría de Servicios
                    FactoriaServicios servicios = new FactoriaServicios();

                    // 2. CREACIÓN DE LA VISTA PRINCIPAL (Inyección de la Factoría)
                    VentanaPrincipal2 vistaPrincipal = new VentanaPrincipal2(
                            servicios.getGestorTurno(), servicios.getGestorGestionTurnos(),
                            servicios.getConsultaService(),servicios.getHistoriaClinicaService()
                    );

                    // 3. MOSTRAR LA VISTA PRINCIPAL
                    vistaPrincipal.setVisible(true);

                } catch (Exception e) {
                    // 🛑 MANEJO DE ERRORES: Muestra una ventana si falla la inicialización
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            null,
                            "La aplicación no pudo iniciar debido a un error crítico del sistema.\n" +
                                    "Por favor, verifique la conexión a la base de datos o contacte a soporte técnico.\n" +
                                    "Detalle técnico: " + e.getMessage(), // Incluye el mensaje técnico para el soporte
                            "ERROR CRÍTICO AL INICIAR",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}
