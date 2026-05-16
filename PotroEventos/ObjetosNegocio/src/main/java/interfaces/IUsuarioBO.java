package interfaces;

import dtos.LoginDTO;
import dtos.RegistroUsuarioDTO;
import dtos.UsuarioDTO;
import excepciones.NegocioException;

/**
 *
 * @author aaron
 */
public interface IUsuarioBO {

    public UsuarioDTO obtenerUsuario(LoginDTO sesion) throws NegocioException;

    public UsuarioDTO guardarUsuario(RegistroUsuarioDTO usuario) throws NegocioException;
    
    public UsuarioDTO obtenerUsuarioPorId(String idUsuario) throws NegocioException;
}
