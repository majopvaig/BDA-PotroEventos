/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import dtos.ENUMS.MotivoDevolucionN;
import dtos.ENUMS.TipoDevolucionN;
import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class DevolucionDTO {
    
    private MotivoDevolucionN motivo;
    private String descripcion;
    private TipoDevolucionN tipo;
    private LocalDateTime fechaHoraDevolucion;
    private ReembolsoDTO reembolso;

    public DevolucionDTO() {
    }

    public DevolucionDTO(MotivoDevolucionN motivo, String descripcion, TipoDevolucionN tipo, LocalDateTime fechaHoraDevolucion, ReembolsoDTO reembolso) {
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaHoraDevolucion = fechaHoraDevolucion;
        this.reembolso = reembolso;
    }

    public MotivoDevolucionN getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoDevolucionN motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoDevolucionN getTipo() {
        return tipo;
    }

    public void setTipo(TipoDevolucionN tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHoraDevolucion() {
        return fechaHoraDevolucion;
    }

    public void setFechaHoraDevolucion(LocalDateTime fechaHoraDevolucion) {
        this.fechaHoraDevolucion = fechaHoraDevolucion;
    }

    public ReembolsoDTO getReembolso() {
        return reembolso;
    }

    public void setReembolso(ReembolsoDTO reembolso) {
        this.reembolso = reembolso;
    }
}
