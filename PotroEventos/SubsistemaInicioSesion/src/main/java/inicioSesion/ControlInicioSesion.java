package inicioSesion;

import dtos.UsuarioDTO;
/**
 * Clase controlador de inicio de sesion que gestiona el control interno de todo
 * lo que tiene que ver con la gestion de inicio de sesion
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
import dtos.LoginDTO;
import dtos.RegistroUsuarioDTO;
import excepciones.InicioSesionException;
import excepciones.NegocioException;
import interfaces.IUsuarioBO;
import objetosNegocio.UsuarioBO;
import org.mindrot.jbcrypt.BCrypt;

public class ControlInicioSesion {

    private static ControlInicioSesion instance;

    private final IUsuarioBO usuarioBO = UsuarioBO.getInstance();

    private ControlInicioSesion() {
    }

    public static ControlInicioSesion getIntance() {
        if (instance == null) {
            instance = new ControlInicioSesion();
        }
        return instance;
    }

    protected UsuarioDTO iniciarSesion(LoginDTO login) throws InicioSesionException {
        try {
            RegistroUsuarioDTO registroRecuperado = usuarioBO.iniciarSesion(login);
            if (registroRecuperado == null) {
                return null;
            }

            if (!BCrypt.checkpw(login.getContrasenia(), registroRecuperado.getContrasenia())) {
                throw new InicioSesionException("La contraseña o el correo es incorrecto.");
            }

            return usuarioBO.obtenerUsuarioPorCorreo(registroRecuperado.getCorreo());

        } catch (NegocioException ex) {
            throw new InicioSesionException(ex.getMessage());
        }
    }

    protected boolean verificarUsuario(LoginDTO login) {
        if (login.getCorreo().isEmpty() || login.getContrasenia().isEmpty()) {
            return false;
        }
        return true;
    }

    protected UsuarioDTO registrarUsuario(RegistroUsuarioDTO usuario) throws InicioSesionException {
        usuario.setContrasenia(BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt()));
        try {
            return usuarioBO.guardarUsuario(usuario);
        } catch (NegocioException ex) {
            throw new InicioSesionException(ex.getMessage());
        }
    }
}
