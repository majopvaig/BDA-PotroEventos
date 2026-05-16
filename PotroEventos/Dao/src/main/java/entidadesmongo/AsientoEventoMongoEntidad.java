/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesmongo;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class AsientoEventoMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private double precio;
    private String estado;
    private ObjectId asiento;
    private ObjectId evento;
    

    public AsientoEventoMongoEntidad() {
    }

    public AsientoEventoMongoEntidad(double precio, String estado, ObjectId asiento, ObjectId evento) {
        this.precio = precio;
        this.estado = estado;
        this.asiento = asiento;
        this.evento = evento;
    }

    public AsientoEventoMongoEntidad(ObjectId id, double precio, String estado, ObjectId asiento, ObjectId evento) {
        this.id = id;
        this.precio = precio;
        this.estado = estado;
        this.asiento = asiento;
        this.evento = evento;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    @BsonIgnore
    public String getIdComoTexto(){
        if(id == null){
            return null;
        }
        return id.toHexString();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ObjectId getAsiento() {
        return asiento;
    }

    public void setAsiento(ObjectId asiento) {
        this.asiento = asiento;
    }
    
    @BsonIgnore
    public String getAsientoComoTexto(){
        if(asiento == null){
            return null;
        }
        return asiento.toHexString();
    }

    public ObjectId getEvento() {
        return evento;
    }

    public void setEvento(ObjectId evento) {
        this.evento = evento;
    }
    
    @BsonIgnore
    public String getEventoComoTexto(){
        if(evento == null){
            return null;
        }
        return evento.toHexString();
    }

    @Override
    public String toString() {
        return "AsientoEventoMongoEntidad{" 
                + "id=" + getIdComoTexto() 
                + ", precio=" + precio 
                + ", estado=" + estado 
                + ", asiento=" + getAsientoComoTexto() 
                + ", evento=" + getEventoComoTexto() 
                + '}';
    }  
    
}
