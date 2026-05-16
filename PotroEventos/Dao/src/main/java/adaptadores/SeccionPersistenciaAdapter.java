/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Seccion;
import entidadesmongo.SeccionMongoEntidad;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author maria
 */
public class SeccionPersistenciaAdapter {
    
    public static Seccion convertirADominio(Document mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Seccion dominio = new Seccion();
        
        dominio.setIdSeccion(mongo.getObjectId("_id").toHexString());
        dominio.setNombre(mongo.getString("nombre"));
        dominio.setCapacidad(mongo.getInteger("capacidad"));
        Number precioBaseNum = (Number) mongo.get("precioBase");
        if (precioBaseNum != null) {
            dominio.setPrecioBase(precioBaseNum.longValue());
        }
        
        return dominio;
    }
    
    public static Seccion convertirADominio(SeccionMongoEntidad mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Seccion dominio = new Seccion();
        
        dominio.setIdSeccion(mongo.getIdComoTexto());
        dominio.setNombre(mongo.getNombre());
        dominio.setCapacidad(mongo.getCapacidad());
        dominio.setPrecioBase(mongo.getPrecioBase());
        
        return dominio;
    }
    
    public static List<Seccion> convertirDocsADominio(List<Document> lista) throws PersistenciaException {
        List<Seccion> secciones = new ArrayList<>();
        
        if(lista == null){
            return secciones;
        }
        
        for(Document documento : lista){
            secciones.add(convertirADominio(documento));
        }
        
        return secciones;
    }
    
    public static List<Seccion> convertirListaADominio(List<SeccionMongoEntidad> lista) throws PersistenciaException {
        List<Seccion> secciones = new ArrayList<>();
        
        if(lista == null){
            return secciones;
        }
        
        for(SeccionMongoEntidad mongo : lista){
            secciones.add(convertirADominio(mongo));
        }
        
        return secciones;
    }
   
}
