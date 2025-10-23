package com.veterinaria.modelo;

import java.util.Objects;

public class TipoPractica {
    private int idTipoPractica;
    private String descripcion;

    // Constructor vacío
    public TipoPractica() {
    }

    // Constructor completo
    public TipoPractica(int idTipoPractica, String descripcion) {
        this.idTipoPractica = idTipoPractica;
        this.descripcion = descripcion;
    }

    // --- Getters y Setters ---


    public int getIdTipoPractica() {
        return idTipoPractica;
    }

    public void setIdTipoPractica(int idTipoPractica) {
        this.idTipoPractica = idTipoPractica;
    }

    public String getDescipcion() {
        return descripcion;
    }

    public void setDescipcion(String descipcion) {
        this.descripcion = descipcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoPractica tipo = (TipoPractica) o;

        return idTipoPractica == tipo.idTipoPractica;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoPractica);
    }
    @Override
    public String toString() {
        return descripcion;
    }
}
