package adapters;

import Entitys.Empleado;
import dtos.EmpleadoDTO;

/**
 *
 * @author Brian Sandoval - 262741
 */
public class EmpleadoAdapter {

    public static EmpleadoDTO entidadADTO(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        return new EmpleadoDTO(empleado.getIdEmpleado(), empleado.getCorreo(), empleado.getContrasenia());

    }

    public static Empleado dtoAEntidad(EmpleadoDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Empleado(dto.getIdEmpleado(), dto.getCorreo(), dto.getContrasenia());
    }

}
