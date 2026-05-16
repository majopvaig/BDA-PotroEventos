/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Usuario;
import entidadesmongo.UsuarioMongoEntidad;
import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class UsuarioPersistenciaAdapter {
    
    public static UsuarioMongoEntidad convertirAMongo(Usuario dominio) throws PersistenciaException {
        if(dominio == null){
            return null;
        }
        
        UsuarioMongoEntidad mongo = new UsuarioMongoEntidad();
        
        mongo.setId(convertirStringAObjectId(dominio.getIdUsuario()));
        mongo.setNombre(dominio.getNombre());
        mongo.setApellidoPaterno(dominio.getApellidoPaterno());
        if(dominio.getApellidoMaterno() != null){
            mongo.setApellidoMaterno(dominio.getApellidoMaterno());
        }
        mongo.setCorreo(dominio.getCorreo());
        mongo.setContrasenia(dominio.getContrasenia());
        
        return mongo;
    }
    
    public static Usuario convertirADominio(UsuarioMongoEntidad mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Usuario dominio = new Usuario();
        
        dominio.setIdUsuario(mongo.getIdComoTexto());
        dominio.setNombre(mongo.getNombre());
        dominio.setApellidoPaterno(mongo.getApellidoPaterno());
        dominio.setApellidoMaterno(mongo.getApellidoMaterno());
        dominio.setCorreo(mongo.getCorreo());
        dominio.setContrasenia(mongo.getContrasenia());
        
        return dominio;
    }
    
    public static Usuario convertirADominio(Document mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Usuario dominio = new Usuario();
        
        dominio.setIdUsuario(mongo.getObjectId("_id").toHexString());
        dominio.setNombre(mongo.getString("nombre"));
        dominio.setApellidoPaterno(mongo.getString("apellidoPaterno"));
        dominio.setApellidoMaterno(mongo.getString("apellidoMaterno"));
        dominio.setCorreo(mongo.getString("correo"));
        dominio.setContrasenia(mongo.getString("contrasenia"));
        
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
