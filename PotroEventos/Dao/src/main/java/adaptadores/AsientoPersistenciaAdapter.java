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
    
    private static IUbicacionDAO ubicacionDAO = UbicacionDAO.getInstance();
    
    public static AsientoMongoEntidad convertirAMongo(Asiento dominio) throws PersistenciaException {
        if(dominio == null){
            return null;
        }
        
        AsientoMongoEntidad mongo = new AsientoMongoEntidad();
        
        mongo.setId(convertirStringAObjectId(dominio.getIdAsiento()));
        mongo.setFila(dominio.getFila());
        mongo.setNumero(dominio.getNumero());
        mongo.setUbicacion(convertirStringAObjectId(dominio.getUbicacion().getIdUbicacion()));
        mongo.setSeccion(convertirStringAObjectId(dominio.getSeccion().getIdSeccion()));
        
        return mongo;
    }
    
    public static Asiento convertirADominio(Document mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }

        Asiento dominio = new Asiento();

        dominio.setIdAsiento(mongo.getIdComoTexto());
        dominio.setFila(mongo.getFila());
        dominio.setNumero(mongo.getNumero());

        String ubiId = mongo.getUbicacionComoTexto();
        if (ubiId != null && !ubiId.isBlank()) {
            try {
                Ubicacion u = ubicacionDAO.consultarPorID(ubiId);
                if (u != null) {
                    dominio.setUbicacion(u);
                }
            } catch (PersistenciaException e) {
                System.err.println("Error al consultar ubicación: " + e.getMessage());
            }
        }

        String secId = mongo.getSeccionComoTexto();
        if (secId != null && !secId.isBlank() && ubiId != null && !ubiId.isBlank()) {
            try {
                Seccion s = ubicacionDAO.buscarSeccionPorId(ubiId, secId);
                if (s != null) {
                    dominio.setSeccion(s);
                }
            } catch (PersistenciaException e) {
                System.err.println("Error al consultar sección: " + e.getMessage());
            }
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
