/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Asiento;
import Entitys.AsientoEvento;
import Entitys.Boleto;
import Entitys.ENUMS.EstadoBoleto;
import entidadesmongo.BoletoMongoEntidad;
import entidadesresumenmongo.AsientoEventoResumenMongo;
import entidadesresumenmongo.EventoResumenMongo;
import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class BoletoPersistenciaAdapter {
    
    public static BoletoMongoEntidad convertirAMongo(Boleto dominio) throws PersistenciaException {
        if (dominio == null) {
            return null;
        }
        
        BoletoMongoEntidad mongo = new BoletoMongoEntidad();
        
        mongo.setCodigoQR(dominio.getCodigoQR());
        mongo.setEstado(dominio.getEstadoBoleto().name());
        mongo.setToken(dominio.getToken());
        
        EventoResumenMongo e = new EventoResumenMongo(
                convertirStringAObjectId(dominio.getEvento().getIdEvento()),
                dominio.getEvento().getNombreEvento(),
                dominio.getEvento().getFechaHora());
        
        mongo.setEvento(e);
        
        if (dominio.getAsiento() != null) {
            AsientoEventoResumenMongo a = new AsientoEventoResumenMongo(
                    convertirStringAObjectId(dominio.getAsiento().getIdAsientoEvento()),
                    dominio.getAsiento().getAsiento().getFila(),
                    dominio.getAsiento().getAsiento().getNumero(),
                    dominio.getAsiento().getAsiento().getSeccion().getNombre());            
            
            mongo.setAsiento(a);
        } else {
            mongo.setAsiento(null);
        }
        
        return mongo;
    }
    
    public static Boleto convertirADominio(Document mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }

        Boleto dominio = new Boleto();

        dominio.setCodigoQR(mongo.getString("codigoQR"));
        dominio.setEstadoBoleto(EstadoBoleto.valueOf(mongo.getString("estado")));
        dominio.setToken(mongo.getString("token"));

        Document evento = (Document) mongo.get("evento");
        if (evento != null) {
            dominio.setEvento(EventoPersistenciaAdapter.convertirADominio(evento));
        }

        Document asiento = (Document) mongo.get("asiento");
        if (asiento != null) {
            Asiento a = new Asiento();
            a.setFila(asiento.getString("fila"));
            a.setNumero(asiento.getInteger("numero"));

            AsientoEvento ae = new AsientoEvento();
            ae.setIdAsientoEvento(asiento.getObjectId("idAsientoEvento").toHexString());
            ae.setAsiento(a);

            dominio.setAsiento(ae);
        }

        return dominio;
    }
    
    private static ObjectId convertirStringAObjectId(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            return null;
        }
        if (!ObjectId.isValid(id)) {
            throw new PersistenciaException(
                    "El id recibido no tiene formato válido de ObjectId."
            );
        }
        return new ObjectId(id);
    }
}
