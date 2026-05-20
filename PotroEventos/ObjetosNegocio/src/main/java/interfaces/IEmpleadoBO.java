package interfaces;

import dtos.EmpleadoDTO;
import excepciones.NegocioException;

/**
 * Interfaz que define las operaciones de negocio relacionadas con empleados.
 *
 * <p>
 * Esta interfaz permite realizar validaciones y consultas relacionadas con
 * empleados dentro del sistema.
 * </p>
 *
 * @author Kaleb
 */
public interface IEmpleadoBO {

    /**
     * Obtiene un empleado a partir de sus datos de autenticación o búsqueda.
     *
     * <p>
     * Este método se utiliza principalmente para validar la existencia de un
     * empleado dentro del sistema.
     * </p>
     *
     * @param empleadoDTO DTO con la información del empleado.
     *
     * @return Empleado encontrado.
     *
     * @throws NegocioException Se lanza cuando:
     * <ul>
     * <li>El empleado recibido es nulo.</li>
     * <li>La información es inválida.</li>
     * <li>Ocurre un error durante la operación.</li>
     * </ul>
     */
    EmpleadoDTO obtenerEmpleado(EmpleadoDTO empleadoDTO) throws NegocioException;

}
