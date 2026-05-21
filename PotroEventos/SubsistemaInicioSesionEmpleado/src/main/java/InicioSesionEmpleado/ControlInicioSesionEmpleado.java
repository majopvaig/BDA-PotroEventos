package InicioSesionEmpleado;

import dtos.EmpleadoDTO;
import excepciones.EmpleadoException;
import excepciones.NegocioException;
import interfaces.IEmpleadoBO;
import java.util.logging.Logger;
import objetosNegocio.EmpleadoBO;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Control encargado de gestionar el inicio de sesión de empleados.
 *
 * <p>
 * Esta clase coordina la validación de datos y la autenticación de empleados
 * dentro del sistema.
 * </p>
 *
 * <p>
 * Implementa el patrón Singleton para garantizar una única instancia durante la
 * ejecución de la aplicación.
 * </p>
 *
 * @author Brian Sandoval - 262741
 */
public class ControlInicioSesionEmpleado {

    /**
     * Instancia única del control.
     */
    private static ControlInicioSesionEmpleado instancia;

    /**
     * Logger del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ControlInicioSesionEmpleado.class.getName());

    /**
     * Objeto de negocio de empleados.
     */
    private final IEmpleadoBO empleadoBO = EmpleadoBO.getInstance();

    private EmpleadoDTO empleadoSesion;

    /**
     * Constructor privado.
     */
    private ControlInicioSesionEmpleado() {

    }

    /**
     * Obtiene la instancia única del control.
     *
     * @return Instancia de {@link ControlInicioSesionEmpleado}.
     */
    public static ControlInicioSesionEmpleado getInstance() {

        if (instancia == null) {
            instancia = new ControlInicioSesionEmpleado();
        }

        return instancia;
    }

    /**
     * Obtiene un empleado utilizando sus credenciales.
     *
     * <p>
     * Antes de realizar la consulta, el método valida y formatea la información
     * del empleado.
     * </p>
     *
     * @param empleadoDTO Datos del empleado.
     *
     * @return Empleado autenticado.
     *
     * @throws EmpleadoException Se lanza cuando:
     * <ul>
     * <li>Los datos son inválidos.</li>
     * <li>El empleado no existe.</li>
     * <li>Ocurre un error durante la operación.</li>
     * </ul>
     */
    protected EmpleadoDTO obtenerEmpleado(EmpleadoDTO empleadoDTO) throws EmpleadoException {

        EmpleadoDTO empleado;

        if (!validarEmpleado(empleadoDTO)) {
            throw new EmpleadoException("Los datos del empleado son inválidos.");
        }

        try {
            empleado = empleadoBO.obtenerEmpleado(empleadoDTO);
        } catch (NegocioException ex) {
            throw new EmpleadoException(ex.getMessage());
        }

        if (empleado == null) {
            throw new EmpleadoException("El empleado no existe.");
        }

        boolean coincide = BCrypt.checkpw(empleadoDTO.getContrasenia(), empleado.getContrasenia());

        if (!coincide) {
            throw new EmpleadoException("Contraseña incorrecta.");
        }

        empleado.setContrasenia(null);
        setEmpleadoSesion(empleado);
        LOG.info(() -> "Empleado: " + empleado.getIdEmpleado());

        return empleado;
    }

    /**
     * Valida y formatea la información del empleado.
     *
     * <p>
     * Este método realiza las siguientes validaciones:
     * </p>
     *
     * <ul>
     * <li>Verifica que el empleado no sea nulo.</li>
     * <li>Valida que el correo no esté vacío.</li>
     * <li>Valida que la contraseña no esté vacía.</li>
     * <li>Aplica trim al correo y contraseña.</li>
     * <li>Convierte el correo a minúsculas.</li>
     * </ul>
     *
     * @param empleadoDTO Empleado a validar.
     *
     * @return {@code true} si los datos son válidos, {@code false} en caso
     * contrario.
     */
    private boolean validarEmpleado(EmpleadoDTO empleadoDTO) {

        if (empleadoDTO == null) {

            LOG.warning("El empleado recibido es nulo.");

            return false;
        }

        if (empleadoDTO.getCorreo() == null || empleadoDTO.getCorreo().isBlank()) {

            LOG.warning("El correo del empleado es inválido.");

            return false;
        }

        if (empleadoDTO.getContrasenia() == null || empleadoDTO.getContrasenia().isBlank()) {

            LOG.warning("La contraseña del empleado es inválida.");

            return false;
        }

        empleadoDTO.setCorreo(empleadoDTO.getCorreo().trim().toLowerCase());

        empleadoDTO.setContrasenia(empleadoDTO.getContrasenia().trim());

        return true;
    }

    /**
     * Obtiene el empleado autenticado actualmente en el sistema.
     *
     * <p>
     * Este método retorna la información del empleado que inició sesión y que
     * se encuentra almacenado en memoria durante la ejecución de la aplicación.
     * </p>
     *
     * @return Empleado autenticado actualmente o {@code null} si no existe una
     * sesión activa.
     */
    protected EmpleadoDTO getEmpleadoSesion() {
        return empleadoSesion;
    }

    /**
     * Establece el empleado autenticado actual del sistema.
     *
     * <p>
     * Este método almacena en memoria el empleado que inició sesión para que
     * pueda ser utilizado posteriormente en distintos módulos del sistema, como
     * la revisión de boletos y registro de asistencias.
     * </p>
     *
     * @param empleadoDTO Empleado autenticado que será almacenado en sesión.
     */
    private void setEmpleadoSesion(EmpleadoDTO empleadoDTO) {
        this.empleadoSesion = empleadoDTO;
    }

    /**
     * Establece el empleado autenticado actual del sistema a nulo.
     *
     * <p>
     * Este método cierra la sesión del empleado.
     * </p>
     */
    protected void cerrarSesion() {
        this.empleadoSesion = null;
    }

}
