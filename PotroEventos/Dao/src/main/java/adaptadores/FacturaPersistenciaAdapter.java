/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.ENUMS.UsoCfdi;
import Entitys.Factura;
import entidadesmongo.FacturaMongoEntidad;
import entidadesresumenmongo.FacturaResumenMongo;
import entidadesresumenmongo.PerfilFiscalResumenMongo;
import excepciones.PersistenciaException;

/**
 *
 * @author aaron
 */
public class FacturaPersistenciaAdapter {
    
    
    
    public static FacturaMongoEntidad convertirAMongo(Factura dominio)throws PersistenciaException{
        if(dominio == null){
            return null;
        }
        
        FacturaMongoEntidad mongo = new FacturaMongoEntidad();
        
        // Convertir PerfilFiscal a PerfilFiscalResumenMongo
        if (dominio.getPerfil() != null) {
            PerfilFiscalResumenMongo perfilResumen = new PerfilFiscalResumenMongo();
            perfilResumen.setRfc(dominio.getPerfil().getRfc());
            perfilResumen.setNombre(dominio.getPerfil().getNombre());
            perfilResumen.setCodigoPostal(dominio.getPerfil().getCodigoPostal());
            perfilResumen.setRegimenFiscal(dominio.getPerfil().getRegimenFiscal().getCodigo());
            perfilResumen.setCorreo(dominio.getPerfil().getCorreo());
            mongo.setPerfilFiscal(perfilResumen);
        }
        
        // Convertir UsoCfdi Enum a String
        if (dominio.getUsoCfdi() != null) {
            mongo.setUsoCfdi(dominio.getUsoCfdi().getCodigo());
        }
        
        mongo.setTotalPagado(dominio.getTotal());
        mongo.setMoneda(dominio.getMoneda());
        mongo.setFechaReserva(dominio.getFechaCompra());
        mongo.setFechaTimbrado(dominio.getFechaTimbrado());
        mongo.setTimbrado(dominio.getXmlTimbrado());  // El XML timbrado
        
        mongo.setUuid(dominio.getUuid());
        
        
        return mongo;
    }
    
    public static Factura convertirADominio(FacturaMongoEntidad mongo) throws PersistenciaException{
        
        if (mongo == null) {
            return null;
        }
        
        Factura dominio = new Factura();
        
        // Convertir PerfilFiscalResumenMongo a PerfilFiscal
        if (mongo.getPerfilFiscal() != null) {
            Entitys.PerfilFiscal perfil = new Entitys.PerfilFiscal();
            perfil.setRfc(mongo.getPerfilFiscal().getRfc());
            perfil.setNombre(mongo.getPerfilFiscal().getNombre());
            perfil.setCodigoPostal(mongo.getPerfilFiscal().getCodigoPostal());
            perfil.setCorreo(mongo.getPerfilFiscal().getCorreo());
            
            // Convertir String a Enum RegimenFiscal
            if (mongo.getPerfilFiscal().getRegimenFiscal() != null) {
                perfil.setRegimenFiscal(Entitys.ENUMS.RegimenFiscal.valueOf(
                    mongo.getPerfilFiscal().getRegimenFiscal()
                ));
            }
            dominio.setPerfil(perfil);
        }
        
        // Convertir String a UsoCfdi Enum
        if (mongo.getUsoCfdi() != null) {
            dominio.setUsoCfdi(UsoCfdi.valueOf(mongo.getUsoCfdi()));
        }
        
        dominio.setTotal(mongo.getTotalPagado());
        dominio.setMoneda(mongo.getMoneda());
        dominio.setFechaCompra(mongo.getFechaReserva());
        dominio.setFechaTimbrado(mongo.getFechaTimbrado());
        dominio.setXmlTimbrado(mongo.getTimbrado());
        dominio.setUuid(mongo.getUuid());
        
        return dominio;
    }
}
