/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesmongo;

import entidadesresumenmongo.PerfilFiscalResumenMongo;
import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;


/**
 *
 * @author aaron
 */
public class FacturaMongoEntidad {
    
    @BsonId
    private ObjectId id;
    
    private PerfilFiscalResumenMongo perfilFiscal;
    private String usoCfdi;
    private Double totalPagado;
    private String moneda;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaTimbrado;
    private String timbrado;
    private String uuid;
    
    // --- Contructores ---
    public FacturaMongoEntidad() {
        
    }

    public FacturaMongoEntidad(ObjectId id, PerfilFiscalResumenMongo perfilFiscal, String usoCfdi, Double totalPagado, String moneda, LocalDateTime fechaReserva, LocalDateTime fechaTimbrado, String timbrado, String uuid) {
        this.id = id;
        this.perfilFiscal = perfilFiscal;
        this.usoCfdi = usoCfdi;
        this.totalPagado = totalPagado;
        this.moneda = moneda;
        this.fechaReserva = fechaReserva;
        this.fechaTimbrado = fechaTimbrado;
        this.timbrado = timbrado;
        this.uuid = uuid;
    }

    public FacturaMongoEntidad(PerfilFiscalResumenMongo perfilFiscal, String usoCfdi, Double totalPagado, String moneda, LocalDateTime fechaReserva, LocalDateTime fechaTimbrado, String timbrado, String uuid) {
        this.perfilFiscal = perfilFiscal;
        this.usoCfdi = usoCfdi;
        this.totalPagado = totalPagado;
        this.moneda = moneda;
        this.fechaReserva = fechaReserva;
        this.fechaTimbrado = fechaTimbrado;
        this.timbrado = timbrado;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    
    

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public PerfilFiscalResumenMongo getPerfilFiscal() {
        return perfilFiscal;
    }

    public void setPerfilFiscal(PerfilFiscalResumenMongo perfilFiscal) {
        this.perfilFiscal = perfilFiscal;
    }

    public String getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(String usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public Double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(Double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaTimbrado() {
        return fechaTimbrado;
    }

    public void setFechaTimbrado(LocalDateTime fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    @Override
    public String toString() {
        return "FacturaMongoEntidad{" + "id=" + id + ", perfilFiscal=" + perfilFiscal + ", usoCfdi=" + usoCfdi + ", totalPagado=" + totalPagado + ", moneda=" + moneda + ", fechaReserva=" + fechaReserva + ", fechaTimbrado=" + fechaTimbrado + ", timbrado=" + timbrado + '}';
    }
    
    
}
