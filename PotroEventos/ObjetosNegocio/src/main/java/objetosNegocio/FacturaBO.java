/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import adapters.FacturaAdapter;
import daos.FacturaDAO;
import daos.ReservacionDAO;
import dtos.FacturaDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import fabricas.FabricaFacturaBO;
import fabricas.IFabricaFacturaBO;
import interfaces.IFacturaBO;
import interfaces.IFacturaDAO;
import interfaces.IReservacionDAO;

/**
 *
 * @author aaron
 */
public class FacturaBO implements IFacturaBO{
    
    private static FacturaBO instance;
    private IFabricaFacturaBO  fabricaFactura = FabricaFacturaBO.getInstance();
    
    private IFacturaDAO dao = FacturaDAO.getInstance();
    private IReservacionDAO reservaDao = ReservacionDAO.getInstance();
    
    private FacturaBO() {
    }
    
    public static FacturaBO getInstance(){
        if(instance == null){
            instance = new FacturaBO();
        }
        return instance;
    }

    @Override
    public boolean guardarFactura(FacturaDTO factura) throws NegocioException{
        try {
            String idFactura = dao.guardarFactura(FacturaAdapter.dtoAEntidad(factura));
            if(idFactura == null){
                throw new NegocioException("No se pudo guardar la factura.");
            }
            return reservaDao.asociarFactura(factura.getIdReservacion(), idFactura);

        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @Override
    public FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva) throws NegocioException {
        return fabricaFactura.crearFactura(perfil, reserva);
    }
    
    
}
