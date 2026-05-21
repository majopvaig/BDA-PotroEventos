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

    public RegistroUsuarioDTO iniciarSesion(LoginDTO sesion) throws NegocioException;

    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) throws NegocioException;

    public boolean restarCreditos(Integer cantidad, String idUsuario) throws NegocioException;

    public boolean aumentarCreditos(Integer cantidad, String idUsuario) throws NegocioException;

}
