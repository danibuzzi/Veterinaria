package com.veterinaria.modelo;



// Clase que encapsula los datos del formulario (INSERT)
public class Turno {

    private final int idTipoConsulta;
    private final int idPropietario;
    private final int idMascota;
    private final String fechaTurno;
    private final String hora;

    public Turno(int idTipoConsulta, int idPropietario, int idMascota, String fechaTurno, String hora) {
        this.idTipoConsulta = idTipoConsulta;
        this.idPropietario = idPropietario;
        this.idMascota = idMascota;
        this.fechaTurno = fechaTurno;
        this.hora = hora;
    }

    // Getters necesarios para que el Gestor acceda a los datos
    public int getIdTipoConsulta() { return idTipoConsulta; }
    public int getIdPropietario() { return idPropietario; }
    public int getIdMascota() { return idMascota; }
    public String getFechaTurno() { return fechaTurno; }
    public String getHora() { return hora; }
}