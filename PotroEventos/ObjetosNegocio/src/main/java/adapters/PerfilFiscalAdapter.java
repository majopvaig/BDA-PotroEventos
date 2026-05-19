/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import Entitys.ENUMS.RegimenFiscal;
import dtos.PerfilFiscalDTO;
import Entitys.PerfilFiscal;
import dtos.ENUMS.RegimenFiscalDTO;
/**
 *
 * @author aaron
 */
public class PerfilFiscalAdapter {
    
    public static PerfilFiscalDTO convertirADTO(PerfilFiscal perfil){
        if(perfil == null){
            return null;
        }
        
        PerfilFiscalDTO dto = new PerfilFiscalDTO();
        
        dto.setRfc(perfil.getRfc());
        dto.setNombre(perfil.getNombre());
        dto.setCorreo(perfil.getCorreo());
        dto.setCodigoPostal(perfil.getCodigoPostal());
        dto.setRegimenFiscal(perfil.getRegimenFiscal() != null ? RegimenFiscalDTO.valueOf(perfil.getRegimenFiscal().name()) : null);
        return dto;
    }
    
    public static PerfilFiscal convertirADominio(PerfilFiscalDTO perfilDTO){
        if(perfilDTO == null){
            return null;
        }
        
        PerfilFiscal dominio = new PerfilFiscal();
        
        dominio.setRfc(perfilDTO.getRfc());
        dominio.setNombre(perfilDTO.getNombre());
        dominio.setCorreo(perfilDTO.getCorreo());
        dominio.setCodigoPostal(perfilDTO.getCodigoPostal());
        dominio.setRegimenFiscal(perfilDTO.getRegimenFiscal() != null ? RegimenFiscal.valueOf(perfilDTO.getRegimenFiscal().name()) : null);
        return dominio;
    }
}
