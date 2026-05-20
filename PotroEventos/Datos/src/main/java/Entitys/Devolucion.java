/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entitys;

import Entitys.ENUMS.MotivoDevolucionP;
import Entitys.ENUMS.TipoDevolucionP;
import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class Devolucion {
    
    private MotivoDevolucionP motivo;
    private String descripcion;
    private TipoDevolucionP tipo;
    private LocalDateTime fechaHoraDevolucion;
    private Reembolso reembolso;

    public Devolucion() {
    }

    public Devolucion(MotivoDevolucionP motivo, String descripcion, TipoDevolucionP tipo, LocalDateTime fechaHoraDevolucion, Reembolso reembolso) {
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaHoraDevolucion = fechaHoraDevolucion;
        this.reembolso = reembolso;
    }

    public MotivoDevolucionP getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoDevolucionP motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoDevolucionP getTipo() {
        return tipo;
    }

    public void setTipo(TipoDevolucionP tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHoraDevolucion() {
        return fechaHoraDevolucion;
    }

    public void setFechaHoraDevolucion(LocalDateTime fechaHoraDevolucion) {
        this.fechaHoraDevolucion = fechaHoraDevolucion;
    }

    public Reembolso getReembolso() {
        return reembolso;
    }

    public void setReembolso(Reembolso reembolso) {
        this.reembolso = reembolso;
    }
}
