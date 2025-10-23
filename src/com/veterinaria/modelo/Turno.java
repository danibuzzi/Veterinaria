package com.veterinaria.modelo;



public class Turno {
    private int idTurno;
    private int idTipoConsulta;
    private int idPropietario;
    private int idMascota;
    private String fechaTurno;
    private String hora;

    public Turno(int idTurno, int idTipoConsulta, int idPropietario, int idMascota, String fechaTurno, String hora) {
        this.idTurno = idTurno;
        this.idTipoConsulta = idTipoConsulta;
        this.idPropietario = idPropietario;
        this.idMascota = idMascota;
        this.fechaTurno = fechaTurno;
        this.hora = hora;
    }
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

    public int getIdTurno() {
        return idTurno;
    }
}