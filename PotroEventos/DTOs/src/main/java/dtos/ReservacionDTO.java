/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import dtos.ENUMS.ReservacionEstadoDTO;
import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class ReservacionDTO {

    private String idReservacion;
    private Double total;
    private BoletoDTO boleto;
    private PagoDTO pago;
    private UsuarioDTO usuario;
    private LocalDateTime fechaHora;
    private ReservacionEstadoDTO estado;
    private DevolucionDTO devolucion;

    public ReservacionDTO() {
    }

    public ReservacionDTO(String idReservacion, Double total, BoletoDTO boleto, PagoDTO pago, UsuarioDTO usuario, LocalDateTime fechaHora, ReservacionEstadoDTO estado, DevolucionDTO devolucion) {
        this.idReservacion = idReservacion;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.devolucion = devolucion;
    }

    public ReservacionDTO(Double total, BoletoDTO boleto, PagoDTO pago, UsuarioDTO usuario, LocalDateTime fechaHora, ReservacionEstadoDTO estado, DevolucionDTO devolucion) {
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.devolucion = devolucion;
    }

    public String getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(String idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public BoletoDTO getBoleto() {
        return boleto;
    }

    public void setBoleto(BoletoDTO boleto) {
        this.boleto = boleto;
    }

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public ReservacionEstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(ReservacionEstadoDTO estado) {
        this.estado = estado;
    }

    public DevolucionDTO getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(DevolucionDTO devolucion) {
        this.devolucion = devolucion;
    }
    
}
