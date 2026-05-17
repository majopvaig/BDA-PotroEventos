/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Asiento;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author maria
 */
public class AsientoPersistenciaAdapter {
    
    public static Asiento convertirADominio(Document mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }

        Asiento dominio = new Asiento();

        dominio.setIdAsiento(mongo.getObjectId("_id").toHexString());
        dominio.setFila(mongo.getString("fila"));
        dominio.setNumero(mongo.getInteger("numero"));

        Document ubicacion = (Document) mongo.get("ubicacion");
        if(ubicacion != null){
            dominio.setUbicacion(UbicacionPersistenciaAdapter.convertirADominio(ubicacion));
        }
        Document seccion = (Document) mongo.get("seccion");
        if(seccion != null){
            dominio.setSeccion(SeccionPersistenciaAdapter.convertirADominio(seccion));
        }
            return dominio;
    }
    
    public static List<Asiento> convertirDocumentosADominio(List<Document> lista) throws PersistenciaException {
        List<Asiento> asientos = new ArrayList<>();
        
        if(lista == null){
            return asientos;
        }
        
        for(Document mongo : lista){
            asientos.add(convertirADominio(mongo));
        }
        
        return asientos;
    }
   
}
