/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inicioSesion;

import dtos.LoginDTO;
import dtos.RegistroUsuarioDTO;
import dtos.UsuarioDTO;
import excepciones.InicioSesionException;

/**
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public interface IFachadaInicioSesion {

    //inicia sesion
    public UsuarioDTO iniciarSesion(LoginDTO login) throws InicioSesionException;

    public void cerrarSesion();

    public UsuarioDTO registrarUsuario(RegistroUsuarioDTO usuario) throws InicioSesionException;
}
