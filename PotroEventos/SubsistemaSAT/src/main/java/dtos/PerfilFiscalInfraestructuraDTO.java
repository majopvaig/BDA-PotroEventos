/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author aaron
 */
public class PerfilFiscalInfraestructuraDTO {
    
    private String rfc;
    private String nombre;
    private String codigoPostal;
    private String regimenFiscal;
    private String correo;

    public PerfilFiscalInfraestructuraDTO() {
    }
    // constructor con un rfc para enviar a la api

    public PerfilFiscalInfraestructuraDTO(String rfc) {
        this.rfc = rfc;
    }
    
    

    public PerfilFiscalInfraestructuraDTO(String rfc, String nombre, String codigoPostal, String regimenFiscal, String correo) {
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

    public String getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
}
