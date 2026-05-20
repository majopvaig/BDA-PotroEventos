package adapters;

import Entitys.Asistencia;
import Entitys.Empleado;
import dtos.AsistenciaDTO;
import dtos.EmpleadoDTO;

/**
 *
 * @author Kaleb
 */
public class AsistenciaAdapter {

    public static AsistenciaDTO convertirAsistenciaDTO(Asistencia entidad) {
        if (entidad == null) {
            return null;
        }

        return new AsistenciaDTO(entidad.getIdAsistencia(), new EmpleadoDTO(entidad.getEmpleado().getIdEmpleado(), entidad.getEmpleado().getCorreo(), entidad.getEmpleado().getContrasenia()), entidad.getFechaHoraRegistro());
    }

    public static Asistencia convertirAsistenciaEntidad(AsistenciaDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Asistencia(dto.getIdAsistenciaDTO(), new Empleado(dto.getEmpleadoDTO().getIdEmpleado(), dto.getEmpleadoDTO().getCorreo(), dto.getEmpleadoDTO().getContrasenia()), dto.getFechaHoraRegistro());
    }
}
