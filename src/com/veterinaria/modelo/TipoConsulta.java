package com.veterinaria.modelo;

import java.util.Objects;

public class TipoConsulta {
    private int idTipoConsulta;
    private String descipcion;

    // Constructor vacío
    public TipoConsulta() {
    }

    // Constructor completo
    public TipoConsulta(int idTipoConsulta, String descripcion) {
        this.idTipoConsulta = idTipoConsulta;
        this.descipcion = descripcion;
    }

    // --- Getters y Setters ---

    public int getIdTipoConsulta() {
        return idTipoConsulta;
    }

    public void setIdTipoConsulta(int idTipoConsulta) {
        this.idTipoConsulta = idTipoConsulta;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       TipoConsulta tipo = (TipoConsulta) o;
        // CRÍTICO: SOLO SE COMPARA EL ID
        return idTipoConsulta == tipo.idTipoConsulta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoConsulta);
    }
    @Override
    public String toString() {
        return descipcion;
    }
}
