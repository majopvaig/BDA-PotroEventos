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
public class AsientoMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private String fila;
    private int numero;
    private ObjectId ubicacion;
    private ObjectId seccion;

    public AsientoMongoEntidad() {
    }

    public AsientoMongoEntidad(String fila, int numero, ObjectId seccion) {
        this.fila = fila;
        this.numero = numero;
        this.seccion = seccion;
    }

    public AsientoMongoEntidad(ObjectId id, String fila, int numero, ObjectId ubicacion, ObjectId seccion) {
        this.id = id;
        this.fila = fila;
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.seccion = seccion;
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

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public ObjectId getUbicacion(){
        return ubicacion;
    }
    
    public void setUbicacion(ObjectId ubicacion){
        this.ubicacion = ubicacion;
    }
    
    @BsonIgnore
    public String getUbicacionComoTexto(){
        if(ubicacion == null){
            return null;
        }
        return ubicacion.toHexString();
    }

    public ObjectId getSeccion() {
        return seccion;
    }

    public void setSeccion(ObjectId seccion) {
        this.seccion = seccion;
    }
    
    @BsonIgnore
    public String getSeccionComoTexto(){
        if(seccion == null){
            return null;
        }
        return seccion.toHexString();
    }

    @Override
    public String toString() {
        return "AsientoMongoEntidad{" 
                + "id=" + getIdComoTexto() 
                + ", fila=" + fila 
                + ", numero=" + numero 
                + ", ubicacion=" + getUbicacionComoTexto()
                + ", seccion=" + getSeccionComoTexto() 
                + '}';
    }
    
}
    