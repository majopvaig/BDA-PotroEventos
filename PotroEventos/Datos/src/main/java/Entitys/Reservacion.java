/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entitys;

import Entitys.ENUMS.ReservacionEstado;
import java.time.LocalDateTime;

/**
 *
 * @author maria
 * @author Aaron Burciaga (Facturación Integrada)
 */
public class Reservacion {
    
    private String idReservacion;
    private Double total;
    private Boleto boleto;
    private Pago pago;
    private Usuario usuario;
    private LocalDateTime fechaHora;
    private ReservacionEstado estado;
    
    // Caso de uso: Devolución (Compañeros)
    private Devolucion devolucion;
    
    // Caso de uso: Factura (Aaron)
    private String idFactura;
        
    public Reservacion() {
    }

    // Constructor Base + Devolución (Original de compañeros)
    public Reservacion(String idReservacion, Double total, Boleto boleto, Pago pago, Usuario usuario, LocalDateTime fechaHora, ReservacionEstado estado, Devolucion devolucion) {
        this.idReservacion = idReservacion;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.devolucion = devolucion;
    }

    // Constructor sin ID + Devolución (Original de compañeros)
    public Reservacion(Double total, Boleto boleto, Pago pago, Usuario usuario, LocalDateTime fechaHora, ReservacionEstado estado, Devolucion devolucion) {
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.devolucion = devolucion;
    }

    // Constructor especial para tu caso individual de Factura
    public Reservacion(String idReservacion, Double total, Boleto boleto, Pago pago, Usuario usuario, LocalDateTime fechaHora, ReservacionEstado estado, String idFactura) {
        this.idReservacion = idReservacion;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idFactura = idFactura;
    }
    
    // Constructor Maestro (Incluye TODO: Devolución + Factura por si se necesita)
    public Reservacion(String idReservacion, Double total, Boleto boleto, Pago pago, Usuario usuario, LocalDateTime fechaHora, ReservacionEstado estado, Devolucion devolucion, String idFactura) {
        this.idReservacion = idReservacion;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.devolucion = devolucion;
        this.idFactura = idFactura;
    }

    // --- GETTERS Y SETTERS DE TU CASO (FACTURA) ---
    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }
    
    // --- GETTERS Y SETTERS DEL CASO DE DEVOLUCIÓN ---
    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }
    
    // --- GETTERS Y SETTERS BASE ---
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

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public ReservacionEstado getEstado() {
        return estado;
    }

    public void setEstado(ReservacionEstado estado) {
        this.estado = estado;
    }
}