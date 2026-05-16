/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesmongo;

import java.util.ArrayList;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class UbicacionMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private String nombre;
    private Integer capacidad;
    private String tipoUbicacion;
    private List<SeccionMongoEntidad> secciones;

    public UbicacionMongoEntidad() {
        secciones = new ArrayList<>();
    }

    public UbicacionMongoEntidad(String nombre, Integer capacidad, String tipoUbicacion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipoUbicacion = tipoUbicacion;
    }
    
    public UbicacionMongoEntidad(String nombre, Integer capacidad, String tipoUbicacion, List<SeccionMongoEntidad> secciones) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipoUbicacion = tipoUbicacion;
        this.secciones = secciones;
    }

    public UbicacionMongoEntidad(ObjectId id, String nombre, Integer capacidad, String tipoUbicacion, List<SeccionMongoEntidad> secciones) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipoUbicacion = tipoUbicacion;
        this.secciones = secciones;
    }

    public ObjectId getId() {
        return id;
    }
    
    @BsonIgnore
    public String getIdComoTexto(){
        if(id == null){
            return null;
        }
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getTipoUbicacion() {
        return tipoUbicacion;
    }

    public void setTipoUbicacion(String tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }

    public List<SeccionMongoEntidad> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<SeccionMongoEntidad> secciones) {
        this.secciones = secciones;
    }

    @Override
    public String toString() {
        return "UbicacionMongoEntidad{" 
                + "id=" + getIdComoTexto() 
                + ", nombre=" + nombre 
                + ", capacidad=" + capacidad 
                + ", tipoUbicacion=" + tipoUbicacion 
                + ", secciones=" + secciones 
                + '}';
    }
    
}
