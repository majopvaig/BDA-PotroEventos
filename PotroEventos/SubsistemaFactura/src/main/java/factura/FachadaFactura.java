/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;

import dtos.PerfilFiscalDTO;
import dtos.PerfilFiscalInfraestructuraDTO;
import dtos.UsuarioDTO;
import excepciones.FacturaException;

/**
 *
 * @author aaron
 */
public class FachadaFactura implements IFactura{
    
    private  ControlFactura control = ControlFactura.getInstance();
    
    
    @Override
    public boolean obtenerFactura(String idReservacion)throws FacturaException{
        try {
            return control.buscarFactura(idReservacion);
        } catch (FacturaException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }

    @Override
    public PerfilFiscalDTO buscarPerfil(String idUsuario) throws FacturaException {
        try{
            return control.obtenerPerfil(idUsuario);
        }catch(FacturaException ex){
            throw new FacturaException(ex.getMessage());
        }
    }

    @Override
    public PerfilFiscalDTO buscarPerfilFiscal(String rfc, String idUsuario) throws FacturaException {
        try{
            // obtener objeto
            PerfilFiscalDTO  busqueda = control.BuscarPerfil(rfc);
            // validaciones
            if(busqueda == null){
                throw new FacturaException("Perfil no pudo encontrado");
            }
            
            // guardar perfil
            if(!guardarPerfilFiscal(busqueda, idUsuario)){
                throw new FacturaException("Hubo un fallo al asociar su perfilFiscal.");
            }
            
            return busqueda;
        }catch(FacturaException fe){
            throw new FacturaException(fe.getMessage());
        }
    }
    
    private boolean guardarPerfilFiscal(PerfilFiscalDTO guardar, String idUsuario){
        return control.guardarPerfil(guardar, idUsuario);
    }
}
