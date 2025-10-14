package com.veterinaria.principal;

import javax.swing.*;

import com.veterinaria.modelo.Conexion;
import com.veterinaria.modelo.GestorTurno;
import com.veterinaria.modelo.TurnoDAO;
import com.veterinaria.vista.*;

import java.sql.SQLException;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TurnoDAO turnoDAO = new TurnoDAO();
                // 1. INICIALIZAR EL MODELO
               // ModeloTurnos modelo = new ModeloTurnos();

                // 2. INICIALIZAR LA VISTA
                //VentanaPrincipal vista = new VentanaPrincipal();

                // 1. CREACIÓN DEL MODELO DE LÓGICA (GestorTurno)
                GestorTurno gestorTurno = new GestorTurno(turnoDAO);

                // 2. CREACIÓN DE LA VISTA PRINCIPAL (Inyección de Dependencias)
                VentanaPrincipal vistaPrincipal = new VentanaPrincipal(gestorTurno);

                // 3. INICIALIZAR Y CONECTAR EL CONTROLADOR (El ensamblaje MVC)
                //ControladorPrincipal controlador = new ControladorPrincipal(modelo, vista);

                // El controlador ya está conectado a los menús.

                // 4. MOSTRAR LA VISTA PRINCIPAL
                vistaPrincipal.setVisible(true);

            }
        });
    }
}
