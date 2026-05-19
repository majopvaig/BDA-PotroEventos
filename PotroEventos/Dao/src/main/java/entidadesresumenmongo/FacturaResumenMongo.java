/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesresumenmongo;

import java.time.LocalDateTime;

/**
 *
 * @author aaron
 *  Representa solo info que quiero mostrar, en este caso, que almacenare en la bd
 */
public class FacturaResumenMongo {
    
    private PerfilFiscalResumenMongo perfilFiscal;
    private String usoCfdi;
    private String moneda;
    private Double totalPagado;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaHoraTimbrado;
    private String timbrado;

    public FacturaResumenMongo() {
    }

    
    public FacturaResumenMongo(PerfilFiscalResumenMongo perfilFiscal, String usoCfdi, String moneda, Double totalPagado, LocalDateTime fechaReserva, LocalDateTime fechaHoraTimbrado, String timbrado) {
        this.perfilFiscal = perfilFiscal;
        this.usoCfdi = usoCfdi;
        this.moneda = moneda;
        this.totalPagado = totalPagado;
        this.fechaReserva = fechaReserva;
        this.fechaHoraTimbrado = fechaHoraTimbrado;
        this.timbrado = timbrado;
    }

    public PerfilFiscalResumenMongo getPerfilFiscal() {
        return perfilFiscal;
    }

    public void setPerfilFiscal(PerfilFiscalResumenMongo perfilFiscal) {
        this.perfilFiscal = perfilFiscal;
    }

    public String getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(String usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(Double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaHoraTimbrado() {
        return fechaHoraTimbrado;
    }

    public void setFechaHoraTimbrado(LocalDateTime fechaHoraTimbrado) {
        this.fechaHoraTimbrado = fechaHoraTimbrado;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    @Override
    public String toString() {
        return "FacturaResumenMongo{" + "perfilFiscal=" + perfilFiscal + ", usoCfdi=" + usoCfdi + ", moneda=" + moneda + ", totalPagado=" + totalPagado + ", fechaReserva=" + fechaReserva + ", fechaHoraTimbrado=" + fechaHoraTimbrado + ", timbrado=" + timbrado + '}';
    }
    
    

    
}
