package com.veterinaria.modelo;


import com.veterinaria.modelo.ConsultaDAO;
import com.veterinaria.modelo.Consulta;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Propietario;
import com.veterinaria.modelo.TipoConsulta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConsultaService {

    private final ConsultaDAO consultaDAO;


    public ConsultaService() {
        this.consultaDAO = new ConsultaDAO();
    }

    // --- Métodos de Listado ---
    public List<Propietario> listarPropietarios() {

        return consultaDAO.listarPropietarios();
    }


    public List<TipoPractica> listarTiposPractica() {
        try {
            // Obtiene la lista, que puede tener duplicados (diferente ID)
            List<TipoPractica> listaOriginal = consultaDAO.listarTiposPractica();

            // FILTRO FORZADO: Usamos HashSet para eliminar duplicados lógicos (mismo ID).
            HashSet<TipoPractica> setUnico = new HashSet<>(listaOriginal);

            // Devuelve la lista sin duplicados
            return new ArrayList<>(setUnico);

        } catch (RuntimeException e) {
            throw e;
        }
    }

    // --- Método de Guardado con Lógica de Negocio ---
    public void guardarNuevaConsulta(Consulta consulta) {

        // Lógica de Validación de Negocio )
        if (consulta.getIdMascota() <= 0 || consulta.getIdPropietario() <= 0 || consulta.getIdTipoPractica() <= 0) {

            throw new IllegalArgumentException("Error de Negocio: ID de Mascota, Propietario o Tipo de Práctica inválido.");
        }

        //  LLAMADA AL DAO CON MANEJO DE ERRORES
        try {

            consultaDAO.insertar(consulta);
        } catch (RuntimeException e) {
            // Relanza la excepción del DAO para que el Controlador la atrape y muestre el JOptionPane.
            throw e;
        }
    }


    // Listado de mascotas por propietario:
    public List<Mascota> listarMascotasPorPropietario(int idPropietario) {
        if (idPropietario <= 0) {
            return List.of();
        }
        try {
            return consultaDAO.listarMascotasPorPropietario(idPropietario); // <--- Llama al DAO
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<Object[]> buscarConsultasResumen(int idPropietario, Integer idMascota, java.sql.Date fechaDesde) {
        try {
            // Delega la llamada al DAO
            return consultaDAO.listarConsultasResumen(idPropietario, idMascota, fechaDesde);
        } catch (RuntimeException e) {
            // Re-lanza cualquier error de BD para que el Controlador lo maneje
            throw e;
        }
    }

}