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
public class ReembolsoMongoEntidad {

    private String idOperacion;
    private LocalDateTime fechaOperacion;
    private double importe;
    private String metodoPago;

    public ReembolsoMongoEntidad() {
    }

    public ReembolsoMongoEntidad(String idOperacion, LocalDateTime fechaOperacion, double importe, String metodoPago) {
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

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
