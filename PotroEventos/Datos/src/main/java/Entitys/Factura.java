/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entitys;

import Entitys.ENUMS.UsoCfdi;
import java.time.LocalDateTime;

/**
 *
 * @author aaron
 */
public class Factura {
    
    private PerfilFiscal perfil;
    private UsoCfdi usoCfdi;
    private Double total;
    private String moneda;
    private String metodoPago;
    private LocalDateTime fechaCompra;
    private LocalDateTime fechaTimbrado;
    private String xmlTimbrado;
    private String uuid;           // Para mostrar en el PDF
    
    public Factura() {
    }

    
    
    public Factura(PerfilFiscal perfil, UsoCfdi usoCfdi, Double total, String moneda, String metodoPago, LocalDateTime fechaCompra, LocalDateTime fechaTimbrado, String xmlTimbrado, String uuid) {
        this.perfil = perfil;
        this.usoCfdi = usoCfdi;
        this.total = total;
        this.moneda = moneda;
        this.metodoPago = metodoPago;
        this.fechaCompra = fechaCompra;
        this.fechaTimbrado = fechaTimbrado;
        this.xmlTimbrado = xmlTimbrado;
        this.uuid = uuid;
    }

    public PerfilFiscal getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilFiscal perfil) {
        this.perfil = perfil;
    }

    public UsoCfdi getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(UsoCfdi usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public LocalDateTime getFechaTimbrado() {
        return fechaTimbrado;
    }

    public void setFechaTimbrado(LocalDateTime fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }

    public String getXmlTimbrado() {
        return xmlTimbrado;
    }

    public void setXmlTimbrado(String xmlTimbrado) {
        this.xmlTimbrado = xmlTimbrado;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    
    
}
