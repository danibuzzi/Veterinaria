package com.veterinaria.modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Capa de Servicio para manejar la lógica de negocio del registro de Propietarios.
 * Incluye todas las validaciones requeridas.
 */
public class RegistroPropietarioService {

    private final PropietarioDAO propietarioDAO;

    // Patrón de expresión regular para la validación de email (básico)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    public RegistroPropietarioService(PropietarioDAO propietarioDAO) {
        this.propietarioDAO = propietarioDAO;
    }

    /**
     * Realiza todas las validaciones de negocio y persiste el Propietario.
     * @param propietario El objeto Propietario a validar y guardar.
     * @throws IllegalArgumentException Si alguna validación falla.
     */
    public void registrarPropietario(Propietario propietario) throws IllegalArgumentException, RuntimeException {

        // 1. Validar que todos los campos obligatorios estén presentes
        validarCamposObligatorios(propietario);

        // 2. Validar formato de Email
        if (!esEmailValido(propietario.getEmail())) {
            throw new IllegalArgumentException("El formato del campo Email es incorrecto.");
        }

        // 3. Validar DNI único (Consulta a la BD)
        if (propietarioDAO.existeDni(propietario.getDni())) {
            throw new IllegalArgumentException("El DNI ingresado ya se encuentra registrado en el sistema.");
        }

        // 4. Validar Fecha de Nacimiento
        if (!esFechaNacimientoValida(propietario.getFechaNacimiento())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser posterior a la fecha actual.");
        }

        // 5. Persistir el Propietario
        try {
            propietarioDAO.guardar(propietario);
        } catch (RuntimeException e) {
            // Re-lanzar la excepción de la capa DAO con un mensaje amigable
            throw new RuntimeException("Fallo al intentar guardar el propietario. Verifique la conexión a la base de datos.", e);
        }
    }

    /**
     * Verifica que el email tenga un formato válido.
     */
    private boolean esEmailValido(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * Verifica que la fecha de nacimiento no sea posterior a la fecha actual.
     */
    private boolean esFechaNacimientoValida(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            // Esto ya debería ser cubierto por validarCamposObligatorios si es requerido
            return false;
        }
        return fechaNacimiento.isBefore(LocalDate.now()) || fechaNacimiento.isEqual(LocalDate.now());
    }

    /**
     * Verifica que todos los campos requeridos estén llenos.
     * Lanza una excepción con el nombre del campo faltante.
     */
    private void validarCamposObligatorios(Propietario p) throws IllegalArgumentException {
        // En tu diseño, todos los campos parecen ser obligatorios.

        if (p.getDni() == null || p.getDni().isEmpty()) throw new IllegalArgumentException("El campo DNI es obligatorio.");
        if (p.getApellido() == null || p.getApellido().isEmpty()) throw new IllegalArgumentException("El campo Apellidos es obligatorio.");
        if (p.getNombre() == null || p.getNombre().isEmpty()) throw new IllegalArgumentException("El campo Nombres es obligatorio.");
        if (p.getFechaNacimiento() == null) throw new IllegalArgumentException("El campo Fecha de Nacimiento es obligatorio.");
        if (p.getDireccion() == null || p.getDireccion().isEmpty()) throw new IllegalArgumentException("El campo Dirección es obligatorio.");
        if (p.getTelefono() == null || p.getTelefono().isEmpty()) throw new IllegalArgumentException("El campo Teléfono es obligatorio.");
        if (p.getEmail() == null || p.getEmail().isEmpty()) throw new IllegalArgumentException("El campo Email es obligatorio.");
        if (p.getPais() == null || p.getPais().isEmpty()) throw new IllegalArgumentException("El campo País es obligatorio.");
        if (p.getCiudad() == null || p.getCiudad().isEmpty()) throw new IllegalArgumentException("El campo Ciudad es obligatorio.");
    }
}