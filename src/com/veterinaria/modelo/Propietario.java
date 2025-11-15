package com.veterinaria.modelo;
import java.time.LocalDate;
import java.util.Objects;

public class Propietario {
    private int idPropietario;
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String pais;
    private String ciudad;
    private String telefono;
    private String email;

    public Propietario(int idPropietario, String nombre, String apellido
                   ) {
        this.idPropietario = idPropietario;
        this.nombre = nombre;
        this.apellido = apellido;

    }

    public Propietario(int idPropietario, String nombre, String apellido,String dni
    ) {
        this.idPropietario = idPropietario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni=dni;

    }

    public Propietario(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String pais, String ciudad
                       ,String telefono,String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.telefono=telefono;
        this.email=email;

    }




    public Propietario() {}

    // Constructor completo
   public Propietario(int idPropietario, String dni, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String pais, String ciudad
            ,String telefono,String email) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.telefono=telefono;
        this.email=email;
    }

    // Getters esenciales para la lógica
    public int getIdPropietario() { return idPropietario; }
    public String getDni() {
        return dni;
    }
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propietario prop = (Propietario) o;
        // CRÍTICO: SOLO SE COMPARA EL ID
        return idPropietario == prop.idPropietario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPropietario);
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
        String dniDisplay = (dni != null && !dni.isEmpty()) ? "  " + dni : "";
        return apellido + ", " + nombre +"-"+" "+dniDisplay;
    }


}

