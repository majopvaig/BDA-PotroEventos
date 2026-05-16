/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inicioSesion;

import dtos.LoginDTO;
import dtos.RegistroUsuarioDTO;
import dtos.UsuarioDTO;
import excepciones.InicioSesionException;
import excepciones.NegocioException;

/**
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public class InicioSesionFachada implements IFachadaInicioSesion {

    // para usar el singleton
    private static InicioSesionFachada instance;

    private final ControlInicioSesion control = ControlInicioSesion.getIntance();

    private InicioSesionFachada() {
    }

    public static InicioSesionFachada getInstance() {
        if (instance == null) {
            instance = new InicioSesionFachada();
        }
        return instance;
    }

    @Override
    public UsuarioDTO iniciarSesion(LoginDTO login) throws InicioSesionException {
        try {
            return control.iniciarSesion(login);
        } catch (NegocioException ex) {
            throw new InicioSesionException(ex.getMessage());
        }
    }

    @Override
    public void cerrarSesion() {
        control.eliminarSesion();
    }

    @Override
    public UsuarioDTO registrarUsuario(RegistroUsuarioDTO usuario) throws InicioSesionException {
        try {
            return control.registrarUsuario(usuario);
        } catch (NegocioException ex) {
            throw new InicioSesionException(ex.getMessage());
        }
    }

}
