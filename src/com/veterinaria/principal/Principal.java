package com.veterinaria.principal;


import com.veterinaria.modelo.FactoriaServicios; // Importamos la factoria de servicios
import com.veterinaria.vista.VentanaPrincipal;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Inicialización ÚNICA de la Factoría de Servicios
                    FactoriaServicios servicios = new FactoriaServicios();

                    // CREACIÓN DE LA VISTA PRINCIPAL (Inyección de la Factoría)
                    VentanaPrincipal vistaPrincipal = new VentanaPrincipal(
                            servicios.getGestorTurno(), servicios.getGestorGestionTurnos(),
                            servicios.getConsultaService(),servicios.getHistoriaClinicaService()
                    );
                    // MOSTRAR LA VISTA PRINCIPAL
                    vistaPrincipal.setVisible(true);
                } catch (Exception e) {
                    // MANEJO DE ERRORES: Muestra una ventana si falla la inicialización
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
