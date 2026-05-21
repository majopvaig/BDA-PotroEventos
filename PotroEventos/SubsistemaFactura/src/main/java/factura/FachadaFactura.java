/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;

import dtos.FacturaDTO;
import dtos.PagoDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import dtos.SatDTO;
import excepciones.FacturaException;
import java.time.LocalDateTime;
import net.sf.jasperreports.engine.JasperPrint;

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

    @Override
    public FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva) throws FacturaException {
        try{
            //  --- Validaciones ---
            if(perfil == null){
                throw new FacturaException("Proporcione un perfil fiscal.");
            }
            if(reserva == null){
                throw new FacturaException("Proporcione un pago válido.");
            }
            if(!control.validarFechaCompra(reserva.getPago().getFechaOperacion())){
                throw new FacturaException("La fecha de la compra supera los 30 dias.");
            }
            FacturaDTO factura = control.crearFactura(perfil, reserva);
            
            if(factura == null){
                throw new FacturaException("Error al crear factura.");
            }
            return factura;
        }catch(FacturaException fe){
            throw new FacturaException(fe.getMessage());
        }
    }

    @Override
    public boolean generarFactura(FacturaDTO factura) throws FacturaException {
        if(!control.validarExistenciaCorreo(factura.getPerfil().getCorreo())){
            throw new FacturaException("Correo inexistente.");
        }
        String xml = control.generarXml(factura);
        if(!control.validarXML(xml)){
            throw new FacturaException("Error al generar xml.");
        }
        
        // mandar a timbrar xml
        SatDTO respuesta = control.timbrarXml(xml, factura.getPerfil().getRfc());
        if(!control.validarTimbrado(respuesta)){
            throw new FacturaException("Error al timbrar.");
        }
        
        // settear los 2 valores nuevos jeje
        factura.setXmlTimbrado(respuesta.getXmlTimbrado());
        factura.setUuid(respuesta.getUuid());
        if (respuesta.getFechaTimbrado() != null && !respuesta.getFechaTimbrado().isBlank()) {
            factura.setFechaTimbrado(LocalDateTime.parse(respuesta.getFechaTimbrado()));
        } else {
            factura.setFechaTimbrado(LocalDateTime.now()); // Fallback
        }
        
        // validar el pdf que se creo
        JasperPrint pdfGenerado = control.generarPDF(factura);
        if(!control.validarPdf(pdfGenerado)){
            throw new FacturaException("Error al crear el pdf");
        }
        control.guardarFactura(factura);
        control.enviarCorreo(factura.getPerfil().getCorreo(), pdfGenerado);
        // regresar
        return true;
    }
    
    
    private boolean guardarPerfilFiscal(PerfilFiscalDTO guardar, String idUsuario) throws FacturaException{
        return control.guardarPerfil(guardar, idUsuario);
    }
}
