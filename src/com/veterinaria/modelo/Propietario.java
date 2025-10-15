package com.veterinaria.modelo;
import java.time.LocalDate;

public class Propietario {
    private int idPropietario;
    private String dni;

    public String getDni() {
        return dni;
    }

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String pais;
    private String ciudad;

    public Propietario() {}

    // Constructor completo
    public Propietario(int idPropietario, String dni, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String pais, String ciudad) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
    }

    // Getters esenciales para la lógica
    public int getIdPropietario() { return idPropietario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }

    // Setters (esenciales, deben estar completos en su versión final)
    public void setIdPropietario(int idPropietario) { this.idPropietario = idPropietario; }
    public void setDni(String dni) { this.dni = dni; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return apellido + ", " + nombre;
    }
}
