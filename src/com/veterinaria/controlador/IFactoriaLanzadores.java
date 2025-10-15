package com.veterinaria.controlador;

// Archivo: com/veterinaria/controlador/IFactoriaLanzadores.java

public interface IFactoriaLanzadores {

    // Método para obtener el Lanzador que abrirá la ventana principal de ese módulo
    ILanzadorModulo getLanzador(String modulo);
}