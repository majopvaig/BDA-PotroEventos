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
public class SeccionMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private String nombre;
    private Integer capacidad;
    private Long precioBase;

    public SeccionMongoEntidad() {
        this.id = new ObjectId();
    }

    public SeccionMongoEntidad(String nombre, Integer capacidad, Long precioBase) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
    }

    public SeccionMongoEntidad(ObjectId id, String nombre, Integer capacidad, Long precioBase) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Long getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Long precioBase) {
        this.precioBase = precioBase;
    }

    @Override
    public String toString() {
        return "SeccionMongoEntidad{" 
                + "id=" + getIdComoTexto() 
                + ", nombre=" + nombre 
                + ", capacidad=" + capacidad 
                + ", precioBase=" + precioBase 
                + '}';
    }
    
}
