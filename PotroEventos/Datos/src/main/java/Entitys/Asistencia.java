package Entitys;

import java.time.LocalDateTime;

/**
 *
 * @author Kaleb
 */
public class Asistencia {

    private String idAsistencia;
    private Empleado empleado;
    private LocalDateTime fechaHoraRegistro;

    public Asistencia() {
    }

    public Asistencia(String idAsistencia, Empleado empleado, LocalDateTime fechaHoraRegistro) {
        this.idAsistencia = idAsistencia;
        this.empleado = empleado;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Asistencia(Empleado empleado, LocalDateTime fechaHoraRegistro) {
        this.empleado = empleado;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public String getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(String idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}
