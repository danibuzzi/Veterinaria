package com.veterinaria.controlador;


/**
 * Interfaz que define el contrato para cualquier clase que sea responsable
 * de iniciar un módulo completo (Vista + Controlador).
 */
public interface ILanzadorModulo {

    /**
     * Ejecuta la lógica necesaria para construir e inicializar el módulo.
     */
    void lanzar();
}