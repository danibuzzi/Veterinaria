package com.veterinaria.modelo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class HistoriaClinicaService {

    private final ConsultaDAO consultaDAO;
    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO;
    private final TipoPracticaDAO tipoPracticaDAO;

    // Inyección de dependencias
    public HistoriaClinicaService(ConsultaDAO consultaDAO, PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO,TipoPracticaDAO tipoPracticaDAO) {
        this.consultaDAO = consultaDAO;
        this.propietarioDAO = propietarioDAO;
        this.mascotaDAO = mascotaDAO;
        this.tipoPracticaDAO=tipoPracticaDAO;
    }

    /**
     * Lista todos los propietarios activos.
     */
    public List<Propietario> listarPropietarios() {
        // Llama al metodo del DAO de Propietario
        return propietarioDAO.listarTodosActivos();
    }

    /**
     * Lista las mascotas activas que pertenecen a un propietario.
     */
    public List<Mascota> listarMascotasPorPropietario(int idPropietario) {
        // Llama al metodo del DAO de Mascota
        return mascotaDAO.listarActivasPorPropietario(idPropietario);
    }

    /**
     * Obtiene el listado de consultas (resumen) para un propietario.
     * @retorna Una lista de Object[] con el resumen de las consultas.
     */
    /*public List<Object[]> listarConsultasResumen(int idPropietario) {
        // Llama al método modificado en ConsultaDAO que devuelve List<Object[]>
        return consultaDAO.listarConsultasResumen(idPropietario);
    }*/

    public List<Object[]> buscarConsultasResumen(int idPropietario, Integer idMascota, java.sql.Date fechaDesde) {
        // Llama al método del DAO con el nombre MODIFICADO (listarConsultasResumen)
        return consultaDAO.listarConsultasResumen(idPropietario, idMascota, fechaDesde);
    }

    /**
     * Obtiene todos los detalles de una consulta específica, incluyendo nombres/descripciones.
     * @returna Un Object[] con todos los campos.
     */
    public Object[] consultarDetalle(int idConsulta) {
        // Llama al método modificado en ConsultaDAO que devuelve Object[]
        return consultaDAO.consultarDetalle(idConsulta);
    }
}