/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesmongo;

import entidadesresumenmongo.AsientoEventoResumenMongo;
import entidadesresumenmongo.EventoResumenMongo;

/**
 *
 * @author maria
 */
public class BoletoMongoEntidad {
    
    private String codigoQR;
    private String estado;
    private EventoResumenMongo evento;
    private AsientoEventoResumenMongo asiento;
    private String token;

    public BoletoMongoEntidad() {
    }

    public BoletoMongoEntidad(String codigoQR, String estado, EventoResumenMongo evento, AsientoEventoResumenMongo asiento, String token) {
        this.codigoQR = codigoQR;
        this.estado = estado;
        this.evento = evento;
        this.asiento = asiento;
        this.token = token;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EventoResumenMongo getEvento() {
        return evento;
    }

    public void setEvento(EventoResumenMongo evento) {
        this.evento = evento;
    }

    public AsientoEventoResumenMongo getAsiento() {
        return asiento;
    }

    public void setAsiento(AsientoEventoResumenMongo asiento) {
        this.asiento = asiento;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
