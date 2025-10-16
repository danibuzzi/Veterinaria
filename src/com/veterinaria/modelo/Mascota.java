package com.veterinaria.modelo;
import java.util.Objects;
import java.time.LocalDate;

public class Mascota {
    private int idMascota;
    private int idPropietario;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String especie;
    private String sexo;
    private String seniasParticulares;
    private boolean activa;

    public Mascota() {}

    // Constructor completo
    public Mascota(int idMascota, int idPropietario, String nombre, LocalDate fechaNacimiento, String especie, String sexo, String seniasParticulares, boolean activa) {
        this.idMascota = idMascota;
        this.idPropietario = idPropietario;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.especie = especie;
        this.sexo = sexo;
        this.seniasParticulares = seniasParticulares;
        this.activa = activa;
    }

    // Getters esenciales para la lógica
    public int getIdMascota() { return idMascota; }
    public int getIdPropietario() { return idPropietario; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSeniasParticulares() {
        return seniasParticulares;
    }

    public void setSeniasParticulares(String seniasParticulares) {
        this.seniasParticulares = seniasParticulares;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // Setters (esenciales, deben estar completos en su versión final)
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }
    public void setIdPropietario(int idPropietario) { this.idPropietario = idPropietario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        // CRÍTICO: SOLO SE COMPARA EL ID
        return idMascota == mascota.idMascota;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMascota);
    }

    @Override
    public String toString() {
        return nombre + " (" + especie + ")";
    }
}