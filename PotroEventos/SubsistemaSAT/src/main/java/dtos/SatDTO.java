/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author aaron
 */
public class SatDTO {
    private String uuid;           // Identificador único del timbrado
    private String selloDigital;   // Sello digital generado
    private String estatus;        // TIMBRADO_EXITOSO, ERROR, etc.
    private String xmlTimbrado;    // XML ya timbrado con el sello y UUID
    private String fechaTimbrado;  // Fecha y hora del timbrado
    
    // Constructor por defecto
    public SatDTO() {
    }
    
    // Constructor con todos los campos
    public SatDTO(String uuid, String selloDigital, String estatus, 
                  String xmlTimbrado, String fechaTimbrado) {
        this.uuid = uuid;
        this.selloDigital = selloDigital;
        this.estatus = estatus;
        this.xmlTimbrado = xmlTimbrado;
        this.fechaTimbrado = fechaTimbrado;
    }
    
    // Getters y Setters
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getSelloDigital() {
        return selloDigital;
    }
    
    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }
    
    public String getEstatus() {
        return estatus;
    }
    
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public String getXmlTimbrado() {
        return xmlTimbrado;
    }
    
    public void setXmlTimbrado(String xmlTimbrado) {
        this.xmlTimbrado = xmlTimbrado;
    }
    
    public String getFechaTimbrado() {
        return fechaTimbrado;
    }
    
    public void setFechaTimbrado(String fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }
    
    @Override
    public String toString() {
        return "SatDTO{" +
                "uuid='" + uuid + '\'' +
                ", estatus='" + estatus + '\'' +
                ", fechaTimbrado=" + fechaTimbrado +
                '}';
    }
}
