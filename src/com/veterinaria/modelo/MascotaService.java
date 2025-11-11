package com.veterinaria.modelo;


// Asumiendo que PropietarioDAO está en el mismo paquete 'modelo'
import java.time.LocalDate;
import java.util.List;

public class MascotaService {

    private final MascotaDAO mascotaDAO;
    private final PropietarioDAO propietarioDAO; // Usaremos el DAO de Propietarios

    public MascotaService(PropietarioDAO propietarioDAO, MascotaDAO mascotaDAO) {
        this.mascotaDAO = new MascotaDAO();
        this.propietarioDAO = new PropietarioDAO();
    }


    /*public MascotaService() {
        this.mascotaDAO = new MascotaDAO();
        this.propietarioDAO = new PropietarioDAO();
    }*/

    // --- Métodos de Listado (para la vista) ---

    public List<Propietario> listarPropietariosActivos() {
        try {
            // Delega la llamada a PropietarioDAO
            return propietarioDAO.listarTodosActivos();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error de sistema al listar propietarios.", e);
        }
    }

    // --- Métodos de Negocio (Registro de Mascota) ---

    public void registrarNuevaMascota(Mascota mascota) {

        // 1. Validación de Negocio (mínima, el Controlador debe hacer la mayoría)
        if (mascota.getIdPropietario() <= 0) {
            throw new IllegalArgumentException("Error: ID de Propietario inválido.");
        }
        if (mascota.getFechaNacimiento() == null || mascota.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Error: La fecha de nacimiento no puede ser nula ni futura.");
        }
        // ... otras validaciones de negocio ...

        // 2. Llamada al DAO
        try {
           mascotaDAO.insertar(mascota);
        } catch (RuntimeException e) {
            // Relanza la excepción del DAO para que el Controlador la maneje
            throw new RuntimeException("Error al intentar guardar la mascota en la base de datos.", e);
        }
    }

    //PAra consulta modfiacion y eliminacion


    public List<Mascota> listarMascotasActivasPorPropietario(int idPropietario) {
        return mascotaDAO.listarActivasPorPropietario(idPropietario);
    }

    // --- NUEVOS MÉTODOS para Gestión (Modificación/Eliminación) ---

    public Mascota obtenerMascotaPorId(int idMascota) {
        if (idMascota <= 0) {
            throw new IllegalArgumentException("ID de Mascota inválido.");
        }
        return mascotaDAO.obtenerPorId(idMascota);
    }

    public void actualizarMascota(Mascota mascota) {
        // Validación de Negocio para la Modificación
        if (mascota.getIdMascota() <= 0) {
            throw new IllegalArgumentException("Error: ID de Mascota a modificar inválido.");
        }
        if (mascota.getFechaNacimiento() == null || mascota.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Error: La fecha de nacimiento no puede ser nula ni futura.");
        }
        // ... otras validaciones de negocio ...

        mascotaDAO.actualizar(mascota);
    }

    public void eliminarMascotaLogico(int idMascota) {
        if (idMascota <= 0) {
            throw new IllegalArgumentException("ID de Mascota para eliminación inválido.");
        }
        // 🛑 Lógica de Negocio: Verificar si tiene turnos o consultas pendientes si aplica...
        // Aquí asumimos que la eliminación lógica es directa.
        mascotaDAO.eliminarLogico(idMascota);
    }


}
