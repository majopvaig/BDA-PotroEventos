/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesresumenmongo;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class AsientoEventoResumenMongo {
    
    private ObjectId idAsientoEvento;
    private String fila;
    private int numero;
    private String nombreSeccion;

    public AsientoEventoResumenMongo() {
    }

    public AsientoEventoResumenMongo(ObjectId idAsientoEvento, String fila, int numero, String nombreSeccion) {
        this.idAsientoEvento = idAsientoEvento;
        this.fila = fila;
        this.numero = numero;
        this.nombreSeccion = nombreSeccion;
    }

    public ObjectId getIdAsientoEvento() {
        return idAsientoEvento;
    }

    public void setIdAsientoEvento(ObjectId idAsientoEvento) {
        this.idAsientoEvento = idAsientoEvento;
    }
    
    @BsonIgnore
    public String getIdComoTexto(){
        if(idAsientoEvento == null){
            return null;
        }
        return idAsientoEvento.toHexString();
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

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }
    
}
