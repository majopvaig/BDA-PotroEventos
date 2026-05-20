/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Devolucion;
import Entitys.ENUMS.MotivoDevolucionP;
import Entitys.ENUMS.TipoDevolucionP;
import entidadesmongo.DevolucionMongoEntidad;
import excepciones.PersistenciaException;
import java.time.ZoneId;
import org.bson.Document;

/**
 *
 * @author maria
 */
public class DevolucionPersistenciaAdapter {

    public static DevolucionMongoEntidad convertirAMongo(Devolucion dominio) throws PersistenciaException {
        if (dominio == null) {
            return null;
        }

        DevolucionMongoEntidad mongo = new DevolucionMongoEntidad();
        mongo.setMotivo(dominio.getMotivo().name());
        mongo.setDescripcion(dominio.getDescripcion());
        mongo.setFechaHoraDevolucion(dominio.getFechaHoraDevolucion());
        if (dominio.getTipo() != null) {
            mongo.setTipo(dominio.getTipo().name());
        }
        if (dominio.getReembolso() != null) {
            mongo.setReembolso(ReembolsoPersistenciaAdapter.convertirAMongo(dominio.getReembolso()));
        }
        return mongo;
    }
    
    public static Devolucion convertirADominio(Document doc) throws PersistenciaException {
        if(doc == null){
            return null;
        }
        
        Devolucion dominio = new Devolucion();
        dominio.setMotivo(MotivoDevolucionP.valueOf(doc.getString("motivo")));
        dominio.setDescripcion(doc.getString("descripcion"));
        if (doc.getDate("fechaHoraDevolucion") != null) {
            dominio.setFechaHoraDevolucion(doc.getDate("fechaHoraDevolucion")
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }
        
        String tipo = doc.getString("tipo");
        if(tipo != null){
            dominio.setTipo(TipoDevolucionP.valueOf(tipo));
        }
        
        Document reembolso = (Document) doc.get("reembolso");
        if(reembolso != null){
            dominio.setReembolso(ReembolsoPersistenciaAdapter.convertirADominio(reembolso));
        }
        
        return dominio;
    }

    public static Devolucion convertirADominio(DevolucionMongoEntidad mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }

        Devolucion dominio = new Devolucion();
        dominio.setMotivo(MotivoDevolucionP.valueOf(mongo.getMotivo()));
        dominio.setDescripcion(mongo.getDescripcion());
        dominio.setFechaHoraDevolucion(mongo.getFechaHoraDevolucion());
        if (mongo.getTipo() != null) {
            dominio.setTipo(TipoDevolucionP.valueOf(mongo.getTipo()));
        }
        if (mongo.getReembolso() != null) {
            dominio.setReembolso(ReembolsoPersistenciaAdapter.convertirADominio(mongo.getReembolso()));
        }
        return dominio;
    }
}
