/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.ENUMS.RegimenFiscal;
import Entitys.PerfilFiscal;
import Entitys.Usuario;
import entidadesmongo.UsuarioMongoEntidad;
import entidadesresumenmongo.PerfilFiscalResumenMongo;
import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class UsuarioPersistenciaAdapter {
    
    public static UsuarioMongoEntidad convertirAMongo(Usuario dominio) throws PersistenciaException {
        if (dominio == null) {
            return null;
        }

        UsuarioMongoEntidad mongo = new UsuarioMongoEntidad();

        mongo.setId(convertirStringAObjectId(dominio.getIdUsuario()));
        mongo.setNombre(dominio.getNombre());
        mongo.setApellidoPaterno(dominio.getApellidoPaterno());
        if (dominio.getApellidoMaterno() != null) {
            mongo.setApellidoMaterno(dominio.getApellidoMaterno());
        }
        
        if (dominio.getPerfilFiscal() != null) {
            PerfilFiscalResumenMongo resumen = new PerfilFiscalResumenMongo();
            resumen.setRfc(dominio.getPerfilFiscal().getRfc());
            resumen.setNombre(dominio.getPerfilFiscal().getNombre());
            resumen.setCodigoPostal(dominio.getPerfilFiscal().getCodigoPostal());
            resumen.setRegimenFiscal(dominio.getPerfilFiscal().getRegimenFiscal().getCodigo());
            resumen.setCorreo(dominio.getPerfilFiscal().getCorreo());
            mongo.setPerfilFiscal(resumen);
        }
        
        mongo.setCorreo(dominio.getCorreo());
        mongo.setContrasenia(dominio.getContrasenia());
        
        Integer creditos = dominio.getCreditos();
        if (creditos == null) {
            mongo.setCreditos(0);
        } else {
            mongo.setCreditos(dominio.getCreditos());
        }

        return mongo;
    }
    
    public static Usuario convertirADominio(UsuarioMongoEntidad mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }
        
        Usuario dominio = new Usuario();
        
        dominio.setIdUsuario(mongo.getIdComoTexto());
        dominio.setNombre(mongo.getNombre());
        dominio.setApellidoPaterno(mongo.getApellidoPaterno());
        dominio.setApellidoMaterno(mongo.getApellidoMaterno());
        dominio.setCorreo(mongo.getCorreo());
        dominio.setContrasenia(mongo.getContrasenia());
        dominio.setCreditos(mongo.getCreditos());
        
        if (mongo.getPerfilFiscal() != null) {
            PerfilFiscal perfil = new PerfilFiscal();
            perfil.setRfc(mongo.getPerfilFiscal().getRfc());
            perfil.setNombre(mongo.getPerfilFiscal().getNombre());
            perfil.setCorreo(mongo.getPerfilFiscal().getCorreo());
            perfil.setCodigoPostal(mongo.getPerfilFiscal().getCodigoPostal());
            String regimenStr = mongo.getPerfilFiscal().getRegimenFiscal();
            
            if (regimenStr != null && !regimenStr.isEmpty()) {
                perfil.setRegimenFiscal(RegimenFiscal.valueOf(regimenStr));
            }
        
            dominio.setPerfilFiscal(perfil);
        }
        
        return dominio;
    }
    
    public static Usuario convertirADominio(Document mongo) throws PersistenciaException {
        if (mongo == null) {
            return null;
        }
        
        Usuario dominio = new Usuario();
        
        dominio.setIdUsuario(mongo.getObjectId("_id").toHexString());
        dominio.setNombre(mongo.getString("nombre"));
        dominio.setApellidoPaterno(mongo.getString("apellidoPaterno"));
        dominio.setApellidoMaterno(mongo.getString("apellidoMaterno"));
        dominio.setCorreo(mongo.getString("correo"));
        dominio.setContrasenia(mongo.getString("contrasenia"));
        
        Integer creditos = mongo.getInteger("creditos");
        if (creditos != null) {
            dominio.setCreditos(creditos);
        } else {
            dominio.setCreditos(0);
        }
        
        Document perfilDoc = (Document) mongo.get("perfilFiscal");
        if (perfilDoc != null) {
            PerfilFiscal perfil = new PerfilFiscal();
            perfil.setRfc(perfilDoc.getString("rfc"));
            perfil.setNombre(perfilDoc.getString("nombre"));
            perfil.setCodigoPostal(perfilDoc.getString("codigoPostal"));
            String regimenStr = perfilDoc.getString("regimenFiscal");
            
            if (regimenStr != null && !regimenStr.isEmpty()) {
                perfil.setRegimenFiscal(RegimenFiscal.valueOf(regimenStr));
            }
            
            perfil.setCorreo(perfilDoc.getString("correo"));
            dominio.setPerfilFiscal(perfil);
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