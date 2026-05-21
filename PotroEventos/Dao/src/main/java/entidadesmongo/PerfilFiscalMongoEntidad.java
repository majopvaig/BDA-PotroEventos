/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesmongo;


import org.bson.codecs.pojo.annotations.BsonId;

import org.bson.types.ObjectId;
/**
 *
 * @author aaron
 * 
 * Representa una entidad que contiene exclusivamente los atributos que yo le proporciono.
 * Util cuando quiero saber solo ciertos campos y dejar otros de lado.
 */
public class PerfilFiscalMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private String rfc;
    private String nombre;
    private String codigoPostal;
    private String regimenFiscal;
    private String correo;
    
    // --- Constructores ---

    public PerfilFiscalMongoEntidad() {
    }

    public PerfilFiscalMongoEntidad(String rfc, String nombre, String codigoPostal, String regimenFiscal, String correo) {
        this.rfc = rfc;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.regimenFiscal = regimenFiscal;
        this.correo = correo;
    }

    public PerfilFiscalMongoEntidad(ObjectId id, String rfc, String nombre, String codigoPostal, String regimenFiscal, String correo) {
        this.id = id;
        this.rfc = rfc;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.regimenFiscal = regimenFiscal;
        this.correo = correo;
    }

    // --- getters y setters ---
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PerfilFiscalMongoEntidad{" +
                "id=" + id + 
                ", rfc=" + rfc + 
                ", nombre=" + nombre + 
                ", codigoPostal=" + codigoPostal + 
                ", regimenFiscal=" + regimenFiscal + 
                ", correo=" + correo + '}';
    }
    
    
}
