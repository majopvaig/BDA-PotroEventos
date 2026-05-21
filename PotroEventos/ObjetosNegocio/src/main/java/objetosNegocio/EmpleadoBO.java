package objetosNegocio;

import adapters.EmpleadoAdapter;
import daos.EmpleadoDAO;
import dtos.EmpleadoDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IEmpleadoBO;
import interfaces.IEmpleadoDAO;
import java.util.logging.Logger;

/**
 * Objeto de negocio encargado de gestionar las operaciones relacionadas con
 * empleados.
 *
 * <p>
 * Esta clase contiene la lógica de negocio necesaria para validar y consultar
 * empleados dentro del sistema.
 * </p>
 *
 * <p>
 * Implementa el patrón Singleton para garantizar una única instancia durante la
 * ejecución de la aplicación.
 * </p>
 *
 * @author Kaleb
 */
public class EmpleadoBO implements IEmpleadoBO {

    /**
     * Instancia única del BO.
     */
    private static EmpleadoBO instancia;

    /**
     * Logger del sistema.
     */
    private static final Logger LOG = Logger.getLogger(EmpleadoBO.class.getName());

    /**
     * DAO encargado de la persistencia de empleados.
     */
    private final IEmpleadoDAO empleadoDAO = EmpleadoDAO.getInstance();

    /**
     * Constructor privado.
     */
    private EmpleadoBO() {

    }

    /**
     * Obtiene la instancia única del BO.
     *
     * @return Instancia de {@link EmpleadoBO}.
     */
    public static EmpleadoBO getInstance() {

        if (instancia == null) {
            instancia = new EmpleadoBO();
        }

        return instancia;
    }

    /**
     * Obtiene un empleado utilizando la información proporcionada.
     *
     * @param empleadoDTO Información del empleado.
     *
     * @return Empleado encontrado.
     *
     * @throws NegocioException Se lanza cuando los datos son inválidos o ocurre
     * un error en persistencia.
     */
    @Override
    public EmpleadoDTO obtenerEmpleado(EmpleadoDTO empleadoDTO) throws NegocioException {

        EmpleadoDTO empleado;

        if (!validarDatos(empleadoDTO)) {
            throw new NegocioException("La información del empleado es inválida.");
        }

        try {

            LOG.info(() -> "Consultando empleado con correo: " + empleadoDTO.getCorreo());

            empleado = EmpleadoAdapter.entidadADTO(empleadoDAO.obtenerEmpleado(EmpleadoAdapter.dtoAEntidad(empleadoDTO)));

        } catch (PersistenciaException ex) {

            LOG.severe(() -> "Error al obtener empleado: " + ex.getMessage());

            throw new NegocioException(ex.getMessage());
        }

        return empleado;
    }

    /**
     * Valida la información de un empleado.
     *
     * @param empleadoDTO Empleado a validar.
     *
     * @return {@code true} si los datos son válidos, {@code false} en caso
     * contrario.
     */
    private boolean validarDatos(EmpleadoDTO empleadoDTO) {

        if (empleadoDTO == null) {

            LOG.warning("El empleado recibido es nulo.");

            return false;
        }

        if (empleadoDTO.getCorreo() == null
                || empleadoDTO.getCorreo().isBlank()) {

            LOG.warning("El correo del empleado es inválido.");

            return false;
        }

        if (empleadoDTO.getContrasenia() == null
                || empleadoDTO.getContrasenia().isBlank()) {

            LOG.warning("La contraseña del empleado es inválida.");

            return false;
        }

        return true;
    }

}
