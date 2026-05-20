/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesmongo;

import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class DevolucionMongoEntidad {

    private String motivo;
    private String descripcion;
    private String tipo;
    private LocalDateTime fechaHoraDevolucion;
    private ReembolsoMongoEntidad reembolso;

    public DevolucionMongoEntidad() {
    }

    public DevolucionMongoEntidad(String motivo, String descripcion, String tipo, LocalDateTime fechaHoraDevolucion, ReembolsoMongoEntidad reembolso) {
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaHoraDevolucion = fechaHoraDevolucion;
        this.reembolso = reembolso;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHoraDevolucion() {
        return fechaHoraDevolucion;
    }

    public void setFechaHoraDevolucion(LocalDateTime fechaHoraDevolucion) {
        this.fechaHoraDevolucion = fechaHoraDevolucion;
    }

    public ReembolsoMongoEntidad getReembolso() {
        return reembolso;
    }

    public void setReembolso(ReembolsoMongoEntidad reembolso) {
        this.reembolso = reembolso;
    }
}
