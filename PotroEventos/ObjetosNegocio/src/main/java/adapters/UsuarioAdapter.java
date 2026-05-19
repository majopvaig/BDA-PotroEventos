/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import Entitys.Usuario;
import Entitys.PerfilFiscal;
import dtos.LoginDTO;
import dtos.PerfilFiscalDTO;
import dtos.RegistroUsuarioDTO;
import dtos.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dayanara Peralta G
 */
public class UsuarioAdapter {
    
    public static UsuarioDTO entidadADTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellidoPaterno(usuario.getApellidoPaterno());
        dto.setApellidoMaterno(usuario.getApellidoMaterno());
        dto.setCorreo(usuario.getCorreo());
        dto.setCreditos(usuario.getCreditos());
        if(usuario.getPerfilFiscal() != null){
            PerfilFiscal perfil = usuario.getPerfilFiscal();
            dto.setPerfil(PerfilFiscalAdapter.convertirADTO(perfil));
        }
        
        return dto;
    }
    
    public static Usuario dtoAEntidad(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCreditos(dto.getCreditos());
        
        if(dto.getPerfil() != null){
            PerfilFiscalDTO perfilDTO = dto.getPerfil();
            usuario.setPerfilFiscal(PerfilFiscalAdapter.convertirADominio(perfilDTO));
        }
        
        return usuario;
    }
    
    public static List<UsuarioDTO> listaDTO(List<Usuario> lista) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for (Usuario user : lista) {
            usuarios.add(entidadADTO(user));
        }
        return usuarios;
    }
    
    public static Usuario dtoRegistroAEntidad(RegistroUsuarioDTO registroDTO){
        if(registroDTO == null){
            return null;
        }
        
        Usuario entidad = new Usuario();
        entidad.setNombre(registroDTO.getNombres());
        entidad.setApellidoPaterno(registroDTO.getApellidoPaterno());
        entidad.setApellidoMaterno(registroDTO.getApellidoMaterno());
        entidad.setCorreo(registroDTO.getCorreo());
        entidad.setContrasenia(registroDTO.getContrasenia());
        
        return entidad;
    }
    
    public static Usuario dtoLoginAEntidad(LoginDTO loginDTO){
        if(loginDTO == null){
            return null;
        }
        
        Usuario entidad = new Usuario();
        entidad.setCorreo(loginDTO.getCorreo());
        entidad.setContrasenia(loginDTO.getContrasenia());
        
        return entidad;
    }
}
