/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.ENUMS.RegimenFiscal;
import org.bson.Document;
import Entitys.PerfilFiscal;
import entidadesmongo.PerfilFiscalMongoEntidad;
/**
 *
 * @author aaron
 * 
 */
public class PerfilFiscalPersistenciaAdapter {
    
    
    /**
     *  Adaptador de un documento (contiene la informacion cruda que se extrae de la bd)
     * 
     *  
     * @param mongo es el objeto documento
     * @return el dominio de perfil fiscal
     */
    public static PerfilFiscal convertirADominio(Document mongo){
        if(mongo == null){
            return null;
        }
        // --- creacion de la entidad ---
        PerfilFiscal dominio = new PerfilFiscal();
        
        // ---  seteo de valores extraidos desde el documento, ojo,
        //      debe coincidir con el atributo de la bd ---
        
        // convertir regimen fiscal a texto
        String regimenStr = mongo.getString("regimenFiscal");
        
        
        dominio.setNombre(mongo.getString("nombre"));
        dominio.setRegimenFiscal(RegimenFiscal.valueOf(regimenStr));
        dominio.setRfc(mongo.getString("rfc"));
        dominio.setCodigoPostal(mongo.getString("codigoPostal"));
        dominio.setCorreo(mongo.getString("correo"));
        
        return dominio;
    }
    
    /**
     *  Convierte de una entidad mongo a perfil fiscal. La entidadMongo representa
     *  la informacion que quiero mostrar, no todo el registro completo.
     * @param mongo
     * @return 
     */
    public static PerfilFiscal convertirADominio(PerfilFiscalMongoEntidad mongo){
        if(mongo == null){
            return null;
        }
        
        // --- creaciond del objeto ---
        PerfilFiscal dominio = new PerfilFiscal();
        
        //  Convertir String a Enum
        String regimenStr = mongo.getRegimenFiscal();
        if (regimenStr != null) {
            dominio.setRegimenFiscal(RegimenFiscal.valueOf(regimenStr));
        }     
        
        // --- seteo de valores ---
        dominio.setNombre(mongo.getNombre());
        dominio.setRfc(mongo.getRfc());
        dominio.setCodigoPostal(mongo.getCodigoPostal());
        dominio.setCorreo(mongo.getCorreo());
        
        return dominio;
    }
}
