package dtos;

import dtos.ENUMS.EstadoBoletoDTO;

/**
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public class BoletoDTO {

    private String idBoleto;
    private String codigoQR;
    private Double precio;
    private EstadoBoletoDTO estadoBoleto;
    private EventoDTO evento;
    private AsientoEventoDTO asiento;
    private String token;
    private AsistenciaDTO asistenciaDTO;

    public BoletoDTO() {
    }

    public BoletoDTO(String idBoleto, String codigoQR, Double precio, EstadoBoletoDTO estadoBoleto, EventoDTO evento, AsientoEventoDTO asiento, String token, AsistenciaDTO asistenciaDTO) {
        this.idBoleto = idBoleto;
        this.codigoQR = codigoQR;
        this.precio = precio;
        this.estadoBoleto = estadoBoleto;
        this.evento = evento;
        this.asiento = asiento;
        this.token = token;
        this.asistenciaDTO = asistenciaDTO;
    }

    public BoletoDTO(String codigoQR, Double precio, EstadoBoletoDTO estadoBoleto, EventoDTO evento, AsientoEventoDTO asiento, String token, AsistenciaDTO asistenciaDTO) {
        this.codigoQR = codigoQR;
        this.precio = precio;
        this.estadoBoleto = estadoBoleto;
        this.evento = evento;
        this.asiento = asiento;
        this.token = token;
        this.asistenciaDTO = asistenciaDTO;
    }

    public BoletoDTO(String codigoQR, Double precio, EstadoBoletoDTO estadoBoleto, EventoDTO evento, AsientoEventoDTO asiento, String token) {
        this.codigoQR = codigoQR;
        this.precio = precio;
        this.estadoBoleto = estadoBoleto;
        this.evento = evento;
        this.asiento = asiento;
        this.token = token;
    }

    public String getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(String idBoleto) {
        this.idBoleto = idBoleto;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public EstadoBoletoDTO getEstadoBoleto() {
        return estadoBoleto;
    }

    public void setEstadoBoleto(EstadoBoletoDTO estadoBoleto) {
        this.estadoBoleto = estadoBoleto;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public AsientoEventoDTO getAsiento() {
        return asiento;
    }

    public void setAsiento(AsientoEventoDTO asiento) {
        this.asiento = asiento;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AsistenciaDTO getAsistenciaDTO() {
        return asistenciaDTO;
    }

    public void setAsistenciaDTO(AsistenciaDTO asistenciaDTO) {
        this.asistenciaDTO = asistenciaDTO;
    }

}
