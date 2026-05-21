package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Kaleb
 */
public class AsistenciaDTO {

    private String idAsistenciaDTO;
    private EmpleadoDTO empleado;
    private LocalDateTime fechaHoraRegistro;

    public AsistenciaDTO() {
    }

    public AsistenciaDTO(String idAsistenciaDTO, EmpleadoDTO empleado, LocalDateTime fechaHoraRegistro) {
        this.idAsistenciaDTO = idAsistenciaDTO;
        this.empleado = empleado;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public AsistenciaDTO(EmpleadoDTO empleado, LocalDateTime fechaHoraRegistro) {
        this.empleado = empleado;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public String getIdAsistenciaDTO() {
        return idAsistenciaDTO;
    }

    public void setIdAsistenciaDTO(String idAsistencia) {
        this.idAsistenciaDTO = idAsistencia;
    }

    public EmpleadoDTO getEmpleadoDTO() {
        return empleado;
    }

    public void setEmpleadoDTO(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}
