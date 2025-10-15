package com.veterinaria.controlador;

// Archivo: com/veterinaria/controlador/FactoriaLanzadores.java

import com.veterinaria.modelo.GestorTurno3;

import javax.swing.*;

public class FactoriaLanzadores implements IFactoriaLanzadores {

    private final GestorTurno3 gestorTurno;
    private final JDesktopPane escritorio;

    public FactoriaLanzadores(GestorTurno3 gestorTurno, JDesktopPane escritorio) {
        this.gestorTurno = gestorTurno;
        this.escritorio = escritorio;
    }

    @Override
    public ILanzadorModulo getLanzador(String modulo) {
        switch (modulo) {
            case "REGISTRO_TURNO":
                return new LanzadorRegistroTurno(gestorTurno, escritorio);
            case "CONSULTA_FECHA":
                //return new LanzadorGestionTurnos(gestorTurno, escritorio);
            case "GESTION_CLIENTES":
                // 🛑 Si tuvieras un módulo de Clientes, iría aquí:
                // return new LanzadorGestionClientes(gestorCliente, escritorio);
            default:
                throw new IllegalArgumentException("Módulo no reconocido: " + modulo);
        }
    }
}