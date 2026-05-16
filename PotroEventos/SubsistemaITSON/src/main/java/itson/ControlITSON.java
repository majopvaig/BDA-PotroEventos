/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson;

import adaptadores.UsuarioInstitucionalAdapter;
import adapter.UsuarioITSONAdapter;
import dao.UsuarioITSONDAO;
import dtos.UsuarioITSONDTO;
import dtos.UsuarioInstitucionalDTO;
import excepciones.ITSONException;

/**
 *
 * @author maria
 */
public class ControlITSON {
    
    private UsuarioITSONDAO usuarioItsonDAO = UsuarioITSONDAO.getInstance();
    
    protected boolean validarObjetoUsuario(UsuarioInstitucionalDTO usuario){
        if(usuario == null){
            return false;
        }
        
        if(usuario.getIdITSON() == null){
            return false;
        }
        
        if(usuario.getClaveITSON() == null){
            return false;
        }
        
        return true;
    }
    
    protected UsuarioITSONDTO buscarUsuario(UsuarioInstitucionalDTO usuario){
        try{
            UsuarioITSONDTO u = UsuarioInstitucionalAdapter.dtoAInfraestructura(usuario);
            return UsuarioITSONAdapter.entidadADTO(usuarioItsonDAO.consultarUsuario(UsuarioITSONAdapter.dtoAEntidad(u)));
        } catch(ITSONException e){
            return null;
        }
    }
    
}
