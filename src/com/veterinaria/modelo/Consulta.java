package com.veterinaria.modelo;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Consulta {

    private int idConsulta;
    private int idMascota;
    private int idPropietario;
    private int idTipoPractica;
    private Date fechaConsulta;
    private Time hora;
    private String resultadoEstudio;
    private String diagnostico;
    private String pronostico;
    private String tratamiento;

    public Consulta() {
    }

    public Consulta(int idMascota, int idPropietario, int idTipoPractica, Date fechaConsulta,
                    Time hora, String resultadoEstudio, String diagnostico, String pronostico,
                    String tratamiento) {

        this.idMascota = idMascota;
        this.idPropietario = idPropietario;
        this.idTipoPractica = idTipoPractica;
        this.fechaConsulta = fechaConsulta;
        this.hora = hora;
        this.resultadoEstudio = resultadoEstudio;
        this.diagnostico = diagnostico;
        this.pronostico = pronostico;
        this.tratamiento = tratamiento;
    }

    public Consulta(int idConsulta, java.sql.Date fechaConsulta, Time hora, String mascota, String propietario, String practica) {
        this.idConsulta=idConsulta;
        this.fechaConsulta = fechaConsulta;
        this.hora = hora;
    }

    public Consulta(int idConsulta, java.sql.Date fechaConsulta, Time hora, String mascota, String propietario, String practica, String resultadoEstudio, String diagnostico, String pronostico, String tratamiento) {
    this.diagnostico=diagnostico;
    this.resultadoEstudio=resultadoEstudio;
    this.pronostico=pronostico;
    this.tratamiento=tratamiento;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;

        return idConsulta == consulta.idConsulta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConsulta);
    }



    // --- Getters y Setters necesarios para el flujo de guardado ---


    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public int getIdPropietario() { return idPropietario; }
    public void setIdPropietario(int idPropietario) { this.idPropietario = idPropietario; }

    public int getIdTipoPractica() { return idTipoPractica; }
    public void setIdTipoPractica(int idTipoPractica) { this.idTipoPractica = idTipoPractica; }

    public Date getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(Date fechaConsulta) { this.fechaConsulta = fechaConsulta; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public String getResultadoEstudio() { return resultadoEstudio; }
    public void setResultadoEstudio(String resultadoEstudio) { this.resultadoEstudio = resultadoEstudio; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getPronostico() { return pronostico; }
    public void setPronostico(String pronostico) { this.pronostico = pronostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", idMascota=" + idMascota +
                ", idPropietario=" + idPropietario +
                ", idTipoPractica=" + idTipoPractica +
                ", fechaConsulta=" + fechaConsulta +
                ", hora=" + hora +
                ", resultadoEstudio='" + resultadoEstudio + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", pronostico='" + pronostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                '}';
    }
}
