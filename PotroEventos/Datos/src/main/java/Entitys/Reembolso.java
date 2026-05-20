/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entitys;

import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class Reembolso {
    
    private String idOperacion;
    private LocalDateTime fechaOperacion;
    private Double importe;
    private String metodoPago;

    public Reembolso() {
    }

    public Reembolso(String idOperacion, LocalDateTime fechaOperacion, Double importe, String metodoPago) {
        this.idOperacion = idOperacion;
        this.fechaOperacion = fechaOperacion;
        this.importe = importe;
        this.metodoPago = metodoPago;
    }

    public String getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    public LocalDateTime getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDateTime fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
}
