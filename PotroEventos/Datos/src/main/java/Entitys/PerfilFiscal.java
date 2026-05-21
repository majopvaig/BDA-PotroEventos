/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entitys;

import Entitys.ENUMS.RegimenFiscal;

/**
 *
 * @author aaron
 */
public class PerfilFiscal {
    
    // --- atributos ---
    private String rfc;
    private String nombre;
    private String codigoPostal;
    private RegimenFiscal regimenFiscal;
    private String correo;

    // --- constructores ---
    
    public PerfilFiscal() {
    }

    public PerfilFiscal(String rfc, String nombre, String codigoPostal, RegimenFiscal regimenFiscal, String correo) {
        this.rfc = rfc;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.regimenFiscal = regimenFiscal;
        this.correo = correo;
    }


    // --- getters y setters ---

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
    
    public RegimenFiscal getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(RegimenFiscal regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
}
