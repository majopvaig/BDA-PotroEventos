package entidadesmongo;

import entidadesresumenmongo.UsuarioResumenMongo;
import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class ReservacionMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private double total;
    private BoletoMongoEntidad boleto;
    private PagoMongoEntidad pago;
    private UsuarioResumenMongo usuario;
    private LocalDateTime fechaRegistro;
    private String estado;
    
    private DevolucionMongoEntidad devolucion;
    private ObjectId factura;

    public ReservacionMongoEntidad() {
    }

    public ReservacionMongoEntidad(double total, BoletoMongoEntidad boleto, PagoMongoEntidad pago, UsuarioResumenMongo usuario, LocalDateTime fechaRegistro, String estado, DevolucionMongoEntidad devolucion) {
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.devolucion = devolucion;
    }

    public ReservacionMongoEntidad(ObjectId id, double total, BoletoMongoEntidad boleto, PagoMongoEntidad pago, UsuarioResumenMongo usuario, LocalDateTime fechaRegistro, String estado, DevolucionMongoEntidad devolucion) {
        this.id = id;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.devolucion = devolucion;
    }

    public ReservacionMongoEntidad(ObjectId id, double total, BoletoMongoEntidad boleto, PagoMongoEntidad pago, UsuarioResumenMongo usuario, LocalDateTime fechaRegistro, String estado, ObjectId factura) {
        this.id = id;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.factura = factura;
    }

    public ReservacionMongoEntidad(ObjectId id, double total, BoletoMongoEntidad boleto, PagoMongoEntidad pago, UsuarioResumenMongo usuario, LocalDateTime fechaRegistro, String estado, DevolucionMongoEntidad devolucion, ObjectId factura) {
        this.id = id;
        this.total = total;
        this.boleto = boleto;
        this.pago = pago;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.devolucion = devolucion;
        this.factura = factura;
    }

    public ObjectId getFactura() {
        return factura;
    }

    public void setFactura(ObjectId factura) {
        this.factura = factura;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public BoletoMongoEntidad getBoleto() {
        return boleto;
    }

    public void setBoleto(BoletoMongoEntidad boleto) {
        this.boleto = boleto;
    }

    public PagoMongoEntidad getPago() {
        return pago;
    }

    public void setPago(PagoMongoEntidad pago) {
        this.pago = pago;
    }

    public UsuarioResumenMongo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumenMongo usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DevolucionMongoEntidad getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(DevolucionMongoEntidad devolucion) {
        this.devolucion = devolucion;
    }

    @Override
    public String toString() {
        return "ReservacionMongoEntidad{" 
                + "id=" + getIdComoTexto() 
                + ", total=" + total 
                + ", boleto=" + boleto 
                + ", cobro=" + pago
                + ", usuario=" + usuario 
                + ", fechaRegistro=" + fechaRegistro 
                + ", estado=" + estado 
                + ", devolucion=" + devolucion
                + ", factura=" + factura
                + '}';
    }
    
}