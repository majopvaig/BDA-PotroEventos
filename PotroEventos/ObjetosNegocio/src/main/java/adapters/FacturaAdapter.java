/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import Entitys.ENUMS.UsoCfdi;
import Entitys.Factura;
import dtos.ENUMS.UsoCfdiDTO;
import dtos.FacturaDTO;

/**
 *
 * @author aaron
 */
public class FacturaAdapter {
    
    
    public static Factura dtoAEntidad(FacturaDTO factura){
        if(factura == null){
            return null;
        }
        Factura fac = new Factura();
        
        fac.setPerfil(PerfilFiscalAdapter.convertirADominio(factura.getPerfil()));
        
        if (factura.getUsoCfdi() != null) {
            factura.setUsoCfdi(UsoCfdiDTO.valueOf(factura.getUsoCfdi().name()));
        }
        
        fac.setUuid(factura.getUuid());
        fac.setTotal(factura.getTotal());
        fac.setMoneda(factura.getMoneda());
        fac.setMetodoPago(factura.getMetodoPago());
        fac.setFechaCompra(factura.getFechaCompra());
        fac.setFechaTimbrado(factura.getFechaTimbrado());
        fac.setXmlTimbrado(factura.getXmlTimbrado());
        fac.setUuid(factura.getUuid());
        return fac;
    }
    
    public static FacturaDTO entidadADTO(Factura factura){
        if(factura == null){
            return null;
        }
        
        FacturaDTO facturaDTO = new FacturaDTO();
        
        // Convertir PerfilFiscal a PerfilFiscalDTO
        facturaDTO.setPerfil(PerfilFiscalAdapter.convertirADTO(factura.getPerfil()));
        
        // Convertir UsoCfdi Enum a UsoCfdiDTO
        if (factura.getUsoCfdi() != null) {
            facturaDTO.setUsoCfdi(UsoCfdiDTO.valueOf(factura.getUsoCfdi().name()));
        }
        
        facturaDTO.setTotal(factura.getTotal());
        facturaDTO.setMoneda(factura.getMoneda());
        facturaDTO.setMetodoPago(factura.getMetodoPago());
        facturaDTO.setFechaCompra(factura.getFechaCompra());
        facturaDTO.setFechaTimbrado(factura.getFechaTimbrado());
        facturaDTO.setXmlTimbrado(factura.getXmlTimbrado());
        facturaDTO.setUuid(factura.getUuid());
        return facturaDTO;
    }
}
