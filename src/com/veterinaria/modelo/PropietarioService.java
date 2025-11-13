package com.veterinaria.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class PropietarioService {

    private final PropietarioDAO propietarioDAO;
    private final MascotaDAO mascotaDAO; // Para el chequeo de integridad

    public PropietarioService(PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO) {
        this.propietarioDAO = propietarioDAO;
        this.mascotaDAO = mascotaDAO;
    }

    // --- Métodos de Consulta ---

    public List<Propietario> buscarPropietarios(String textoBusqueda, String tipoBusqueda) {
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto de búsqueda no puede estar vacío.");
        }

        // Validación de solo números si se busca por DNI
        if (tipoBusqueda.equalsIgnoreCase("DNI") && !textoBusqueda.matches("\\d+")) {
            throw new IllegalArgumentException("La búsqueda por DNI debe contener solo números.");
        }

        return propietarioDAO.buscarPropietarios(textoBusqueda.trim(), tipoBusqueda);
    }

    public Propietario obtenerPropietarioPorId(int idPropietario) {
        if (idPropietario <= 0) {
            throw new IllegalArgumentException("ID de Propietario inválido.");
        }
        return propietarioDAO.obtenerPorId(idPropietario);
    }

    // --- Métodos de Modificación ---

    public void actualizarPropietario(Propietario p) {
        // 1. Validaciones de Negocio (Campos obligatorios y formato)
        if (p.getIdPropietario() <= 0) {
            throw new IllegalArgumentException("Error: ID de Propietario a modificar inválido.");
        }
        if (p.getNombre().trim().isEmpty() || p.getApellido().trim().isEmpty() ||
                p.getDireccion().trim().isEmpty() || p.getCiudad().trim().isEmpty() ||
                p.getTelefono().trim().isEmpty() || p.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Todos los campos (excepto DNI que está deshabilitado) son obligatorios.");
        }

        // 2. Validación de Teléfono (solo números)
        if (!p.getTelefono().matches("\\d+")) {
            throw new IllegalArgumentException("Error: El campo Teléfono debe contener solo números.");
        }

        // 3. Validación de Email (Formato)
        if (!validarEmail(p.getEmail())) {
            throw new IllegalArgumentException("Error: El formato del correo electrónico no es válido.");
        }

        // 4. Ejecución de la actualización
        propietarioDAO.actualizar(p);
    }

    private boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // --- Métodos de Eliminación ---

    public void eliminarPropietario(int idPropietario) {
        if (idPropietario <= 0) {
            throw new IllegalArgumentException("ID de Propietario inválido.");
        }

        // 1. Chequeo de Integridad Referencial
        int mascotasActivas = mascotaDAO.contarMascotasActivasPorPropietario(idPropietario);

        if (mascotasActivas > 0) {
            // Se asume que si tiene mascota activa, puede tener turnos o consultas.
            throw new IllegalStateException("El propietario no puede ser eliminado porque tiene " + mascotasActivas + " mascota(s) activa(s) asociada(s).");
        }

        // 2. Ejecución de la eliminación lógica
        propietarioDAO.eliminarPropietario(idPropietario);
    }
}