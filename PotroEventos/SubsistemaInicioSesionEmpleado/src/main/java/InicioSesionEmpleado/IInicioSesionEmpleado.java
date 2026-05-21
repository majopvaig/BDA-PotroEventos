package InicioSesionEmpleado;

import dtos.EmpleadoDTO;
import excepciones.EmpleadoException;

/**
 *
 * @author Brian Sandoval - 262741
 */
public interface IInicioSesionEmpleado {

    EmpleadoDTO obtenerEmpleado(EmpleadoDTO empleado) throws EmpleadoException;

    EmpleadoDTO getEmpleadoSesion();
    
    void cerrarSesion();
}
