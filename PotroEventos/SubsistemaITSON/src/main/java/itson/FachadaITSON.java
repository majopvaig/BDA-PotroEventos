/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson;

import dtos.UsuarioITSONDTO;
import dtos.UsuarioInstitucionalDTO;

/**
 *
 * @author maria
 */
public class FachadaITSON extends ControlITSON implements IITSON {
    
    @Override
    public boolean validarUsuarioITSON(UsuarioInstitucionalDTO usuario){
        if(!super.validarObjetoUsuario(usuario)){
            return false;
        }
        UsuarioITSONDTO resultado = super.buscarUsuario(usuario);
        return resultado != null;
    }
    
}
