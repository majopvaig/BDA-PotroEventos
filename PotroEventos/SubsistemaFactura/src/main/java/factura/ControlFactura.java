/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;

import adapter.PerfilFiscalInfraestructuraAdapter;
import dtos.PerfilFiscalDTO;
import dtos.UsuarioDTO;
import excepciones.FacturaException;
import excepciones.NegocioException;
import excepciones.SatException;
import interfaces.IPerfilFiscalBO;
import interfaces.IReservacionBO;
import interfaces.IUsuarioBO;
import objetosNegocio.ReservacionBO;
import objetosNegocio.UsuarioBO;
import sat.FachadaSAT;
import sat.IComunicacionSAT;
import objetosNegocio.PerfilFiscalBO;
/**
 *
 * @author aaron
 */
public class ControlFactura {
    
    // --- Singleton del control ---
    private  static ControlFactura instance;
    
    // --- Bo de Reservacion ---
    private IReservacionBO controlReservacion = ReservacionBO.getInstance();
    // --- Bo de usuario ---
    private IUsuarioBO controlUsuario = UsuarioBO.getInstance();
    // --- infraestructura sat ---
    private IComunicacionSAT controlSAT = FachadaSAT.getInstance();
    
    // --- BO de perfilFiscal ---
    private IPerfilFiscalBO controlPerfil = PerfilFiscalBO.getInstance();
    
    // --- Contructor privado para asegurar encapsulamiento ---
    private ControlFactura(){
    }
    
    // --- Get para obtener la instancia de la clase (singleton) ---
    public  static ControlFactura getInstance(){
        if(instance == null){
            instance = new ControlFactura();
        }
        return instance;
    } 
    
    // --- Métodos ---
    
    protected boolean buscarFactura(String idReservacion) throws FacturaException{
        try{
            return controlReservacion.obtenerReservacion(idReservacion);
        }catch(NegocioException fe){
            throw new FacturaException(fe.getMessage());
        }
    }
    
    protected PerfilFiscalDTO obtenerPerfil(String idUsuario)throws FacturaException{
        try {
            UsuarioDTO usuario = controlUsuario.obtenerUsuarioPorId(idUsuario);
            return usuario.getPerfil();
        } catch (NegocioException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    protected PerfilFiscalDTO BuscarPerfil(String rfc)throws FacturaException{
        try {
            // recibes el de infra
            PerfilFiscalDTO perfil = PerfilFiscalInfraestructuraAdapter.convertirInfraestructuraANegocio(controlSAT.obtenerPerfilFiscal(rfc));
                //verificas el null. Pendiente
                
                    // conviertes a negocio
            return perfil;
        } catch (SatException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    
    protected boolean guardarPerfil(PerfilFiscalDTO guardar, String idUsuario){
         return controlPerfil.guardarPerfilFiscal(guardar, idUsuario);
    }
}
