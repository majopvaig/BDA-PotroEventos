/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.ENUMS.TipoUbicacionP;
import Entitys.Ubicacion;
import entidadesmongo.UbicacionMongoEntidad;
import excepciones.PersistenciaException;
import org.bson.Document;

/**
 *
 * @author maria
 */
public class UbicacionPersistenciaAdapter {
    
    public static Ubicacion convertirADominio(Document mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Ubicacion dominio = new Ubicacion();
        
        dominio.setIdUbicacion(mongo.getObjectId("_id").toHexString());
        dominio.setNombre(mongo.getString("nombre"));
        dominio.setCapacidad(mongo.getInteger("capacidad"));
        String tipo = mongo.getString("tipoUbicacion");
        if (tipo != null && !tipo.trim().isEmpty()) {
            dominio.setTipo(TipoUbicacionP.valueOf(tipo));
        } else {
            System.err.println("¡OJO! La ubicación con ID " + mongo.getObjectId("_id") + " no tiene el campo 'tipoUbicacion'."); 
        }
        dominio.setSecciones(SeccionPersistenciaAdapter.convertirDocsADominio(mongo.getList("secciones", Document.class)));
        
        return dominio;
    }
    
     public static Ubicacion convertirADominio(UbicacionMongoEntidad mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Ubicacion dominio = new Ubicacion();
        
        dominio.setIdUbicacion(mongo.getIdComoTexto());
        dominio.setNombre(mongo.getNombre());
        dominio.setCapacidad(mongo.getCapacidad());
        dominio.setTipo(TipoUbicacionP.valueOf(mongo.getTipoUbicacion()));
        dominio.setSecciones(SeccionPersistenciaAdapter.convertirListaADominio(mongo.getSecciones()));
        
        return dominio;
    }
   
}
