/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapter;

import dtos.ENUMS.RegimenFiscalDTO;
import dtos.PerfilFiscalDTO;
import dtos.PerfilFiscalInfraestructuraDTO;

/**
 *
 * @author aaron
 */
public class PerfilFiscalInfraestructuraAdapter {
    
    
    // Convierte un dto de infraestructura a negocio
    public static PerfilFiscalDTO convertirInfraestructuraANegocio(PerfilFiscalInfraestructuraDTO perfil){
       if(perfil == null){
           return null;
       }
        // crear objeto
        PerfilFiscalDTO perfilN = new PerfilFiscalDTO();
        
        // seteos
        perfilN.setRfc(perfil.getRfc());
        perfilN.setNombre(perfil.getNombre());
        
        // el codigo del regimen fiscal
        String codigo = perfil.getRegimenFiscal();
        RegimenFiscalDTO regimen = null;
        
        for(RegimenFiscalDTO r : RegimenFiscalDTO.values()){
            if(r.getCodigo().equals(codigo)){
                regimen = r;
                break;
            }
        }
        perfilN.setRegimenFiscal(regimen);
        perfilN.setCorreo(perfil.getCorreo());
        perfilN.setCodigoPostal(perfil.getCodigoPostal());
        return perfilN;
    }
}
