/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Reembolso;
import entidadesmongo.ReembolsoMongoEntidad;
import excepciones.PersistenciaException;
import java.time.ZoneId;
import org.bson.Document;

/**
 *
 * @author maria
 */
public class ReembolsoPersistenciaAdapter {

    public static ReembolsoMongoEntidad convertirAMongo(Reembolso dominio) throws PersistenciaException {
        if (dominio == null) {
            return null;
        }

        return new ReembolsoMongoEntidad(
                dominio.getIdOperacion(),
                dominio.getFechaOperacion(),
                dominio.getImporte(),
                dominio.getMetodoPago());
    }
    
    public static Reembolso convertirADominio(Document doc) throws PersistenciaException {
        if(doc == null){
            return null;
        }
        
        Reembolso r = new Reembolso();
        r.setIdOperacion(doc.getString("idOperacion"));
        r.setImporte(doc.getDouble("importe"));
        if (doc.getDate("fechaOperacion") != null) {
            r.setFechaOperacion(doc.getDate("fechaOperacion")
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }
        r.setMetodoPago(doc.getString("metodoPago"));
        return r;
    }

    public static Reembolso convertirADominio(ReembolsoMongoEntidad mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }

        return new Reembolso(
                mongo.getIdOperacion(),
                mongo.getFechaOperacion(),
                mongo.getImporte(),
                mongo.getMetodoPago());
    }

}
