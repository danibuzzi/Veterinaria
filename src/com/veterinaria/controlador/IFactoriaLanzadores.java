package com.veterinaria.controlador;


public interface IFactoriaLanzadores {

    // Método para obtener el Lanzador que abrirá la ventana principal de ese módulo
    ILanzadorModulo getLanzador(String modulo);
}