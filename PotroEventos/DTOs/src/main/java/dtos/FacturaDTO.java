/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import dtos.ENUMS.UsoCfdiDTO;
import java.time.LocalDateTime;

/**
 *
 * @author aaron
 */
public class FacturaDTO {
    
    private PerfilFiscalDTO perfil;
    private UsoCfdiDTO usoCfdi;
    private Double total;
    private String moneda;
    private String metodoPago;
    private LocalDateTime fechaCompra;
    private LocalDateTime fechaTimbrado;
    private String xmlTimbrado;
    private String uuid;           // Para mostrar en el PDF
    
    private String idReservacion; // no encotnre otra forma hadajd
    
    
    public FacturaDTO() {
    }

    public FacturaDTO(PerfilFiscalDTO perfil, UsoCfdiDTO usoCfdi, Double total, String moneda, String metodoPago, LocalDateTime fechaCompra, LocalDateTime fechaTimbrado, String xmlTimbrado, String uuid, String idReservacion) {
        this.perfil = perfil;
        this.usoCfdi = usoCfdi;
        this.total = total;
        this.moneda = moneda;
        this.metodoPago = metodoPago;
        this.fechaCompra = fechaCompra;
        this.fechaTimbrado = fechaTimbrado;
        this.xmlTimbrado = xmlTimbrado;
        this.uuid = uuid;
        this.idReservacion = idReservacion;
    }

    public String getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(String idReservacion) {
        this.idReservacion = idReservacion;
    }

    

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    
    
    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getXmlTimbrado() {
        return xmlTimbrado;
    }

    public void setXmlTimbrado(String xmlTimbrado) {
        this.xmlTimbrado = xmlTimbrado;
    }



    public PerfilFiscalDTO getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilFiscalDTO perfil) {
        this.perfil = perfil;
    }

    public UsoCfdiDTO getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(UsoCfdiDTO usoCfdi) {
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


    
}

