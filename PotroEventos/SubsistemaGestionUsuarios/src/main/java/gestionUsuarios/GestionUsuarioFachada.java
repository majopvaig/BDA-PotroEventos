/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionUsuarios;

import dtos.UsuarioDTO;
import dtos.ReservacionDTO;
import excepciones.GestionUsuarioException;
import java.util.List;

/**
 * Fachada del subsistema, se encarga de comunicarse con el controlador.
 *
 * @author Aaron Burciaga - 262788
 * @author Brian Sandoval - 262741
 * @author Dayanara Peralta - 262695
 * @author María Valdez - 262775
 */
public class GestionUsuarioFachada implements IGestionUsuariosFachada {

    private final ControlGestionUsuarios control;

    public GestionUsuarioFachada() {
        this.control = new ControlGestionUsuarios();
    }

    // --- Método que regresa el usuario con la sesion activa --- 
    @Override
    public UsuarioDTO obtenerUsuarioActivo() throws GestionUsuarioException {
        return control.getUsuarioActivo();
    }

    @Override
    public List<ReservacionDTO> obtenerReservacionesUsuario(String idUsuario) throws GestionUsuarioException {
        return control.obtenerReservacionUsuario(idUsuario);
    }

    @Override
    public boolean asociarUsuario(UsuarioDTO usuario) throws GestionUsuarioException {
        if(control.asociarUsuario(usuario) != null){
            return true;
        }
        throw new GestionUsuarioException("No se pudo asociar el usuario a la sesión.");
    }
}
