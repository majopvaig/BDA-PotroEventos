package entidadesmongo;

import entidadesresumenmongo.AsientoEventoResumenMongo;
import entidadesresumenmongo.EventoResumenMongo;

/**
 * Entidad de MongoDB que representa un Boleto dentro de una Reservación.
 * Estructurada fielmente según el modelo lógico del sistema.
 *
 * @author Brian Sandoval - 262741
 */
public class BoletoMongoEntidad {

    private String id;

    private String estado;
    private EventoResumenMongo evento;

    private AsientoEventoResumenMongo asiento;

    private String token;
    private double precio;
    private AsistenciaMongoEntidad asistencia;
    private String codigoQR;

    /**
     * Constructor vacío requerido por el POJO Codec de MongoDB
     */
    public BoletoMongoEntidad() {
    }

    /**
     * Constructor completo con todos los atributos del modelo
     *
     * @param id
     */
    public BoletoMongoEntidad(String id, String estado, EventoResumenMongo evento, AsientoEventoResumenMongo asiento, String token, double precio, AsistenciaMongoEntidad asistencia, String codigoQR) {
        this.id = id;
        this.estado = estado;
        this.evento = evento;
        this.asiento = asiento;
        this.token = token;
        this.precio = precio;
        this.asistencia = asistencia;
        this.codigoQR = codigoQR;
    }

    public BoletoMongoEntidad(String estado, EventoResumenMongo evento, AsientoEventoResumenMongo asiento, String token, double precio, AsistenciaMongoEntidad asistencia, String codigoQR) {
        this.estado = estado;
        this.evento = evento;
        this.asiento = asiento;
        this.token = token;
        this.precio = precio;
        this.asistencia = asistencia;
        this.codigoQR = codigoQR;
    }

    // Getters y Setters con anotaciones para asegurar el mapeo exacto en MongoDB
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public AsistenciaMongoEntidad getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(AsistenciaMongoEntidad asistencia) {
        this.asistencia = asistencia;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    @Override
    public String toString() {
        return "BoletoMongoEntidad{"
                + "id='" + id + '\''
                + ", estado='" + estado + '\''
                + ", token='" + token + '\''
                + ", precio=" + precio
                + ", codigoQR='" + codigoQR + '\''
                + '}';
    }
}
