/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import dtos.ENUMS.RegimenFiscalDTO;
/**
 *
 * @author aaron
 */
public class PerfilFiscalDTO {
    
    private String rfc;
    private String nombre;
    private String codigoPostal;
    private RegimenFiscalDTO regimenFiscal;
    private String correo;

    public PerfilFiscalDTO() {
    }

    public PerfilFiscalDTO(String rfc, String nombre, String codigoPostal, RegimenFiscalDTO regimenFiscal, String correo) {
       
        this.rfc = rfc;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.regimenFiscal = regimenFiscal;
        this.correo = correo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public RegimenFiscalDTO getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(RegimenFiscalDTO regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}
