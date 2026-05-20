package interfaces;

import Entitys.Empleado;
import excepciones.PersistenciaException;

/**
 * Interfaz que define las operaciones de persistencia relacionadas con los
 * empleados.
 *
 * <p>
 * Esta interfaz establece los métodos que deben implementar las clases
 * encargadas de interactuar con el sistema de almacenamiento de empleados,
 * independientemente de la tecnología de persistencia utilizada.
 * </p>
 *
 * <p>
 * Su propósito es desacoplar la lógica de negocio de los detalles específicos
 * de acceso a datos, permitiendo una arquitectura más flexible y mantenible.
 * </p>
 *
 * @author Brian Sandoval - 262741
 */
public interface IEmpleadoDAO {

    /**
     * Obtiene un empleado registrado en el sistema utilizando la información
     * proporcionada.
     *
     * <p>
     * Generalmente este método es utilizado para validar las credenciales de
     * acceso de un empleado, realizando una búsqueda basada en el correo y la
     * contraseña.
     * </p>
     *
     * @param empleado Objeto que contiene los datos necesarios para buscar al
     * empleado, como el correo y la contraseña.
     *
     * @return Un objeto {@link Empleado} correspondiente al empleado encontrado
     * en el sistema, o {@code null} si no existe coincidencia.
     *
     * @throws PersistenciaException Se lanza cuando ocurre un error durante el
     * acceso o consulta de los datos.
     */
    Empleado obtenerEmpleado(Empleado empleado) throws PersistenciaException;

}
