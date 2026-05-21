/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;

import adapter.PerfilFiscalInfraestructuraAdapter;
import dtos.FacturaDTO;
import dtos.PagoDTO;
import dtos.PerfilFiscalDTO;
import dtos.ReservacionDTO;
import dtos.SatDTO;
import dtos.UsuarioDTO;
import excepciones.FacturaException;
import excepciones.NegocioException;
import excepciones.SatException;
import excepciones.ZeroBounceException;
import interfaces.IFacturaBO;
import interfaces.IPerfilFiscalBO;
import interfaces.IReservacionBO;
import interfaces.IUsuarioBO;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import objetosNegocio.FacturaBO;
import objetosNegocio.ReservacionBO;
import objetosNegocio.UsuarioBO;
import sat.FachadaSAT;
import sat.IComunicacionSAT;
import objetosNegocio.PerfilFiscalBO;
import zeroBounce.FachadaZero;
import zeroBounce.IZeroBounce;
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
    private IPerfilFiscalBO PerfilBO = PerfilFiscalBO.getInstance();
    // --- BO de Factura ---
    private IFacturaBO facturaBO = FacturaBO.getInstance();
    // --- Api zero bounce ---
    private IZeroBounce controlCorreo = FachadaZero.getInstance();
    // --- Generador de xmls ---
    private GeneradorXML generador = GeneradorXML.getInstance();
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
    
    /**
     *  Método que busca si una reserva ya tiene una factura asociada
     * @param idReservacion
     * @return
     * @throws FacturaException 
     */
    protected boolean buscarFactura(String idReservacion) throws FacturaException{
        try{
            return controlReservacion.obtenerReservacion(idReservacion);
        }catch(NegocioException fe){
            throw new FacturaException(fe.getMessage());
        }
    }
    
    /**
     * 
     * @param idUsuario
     * @return
     * @throws FacturaException 
     */
    protected PerfilFiscalDTO obtenerPerfil(String idUsuario)throws FacturaException{
        try {
            UsuarioDTO usuario = controlUsuario.obtenerUsuarioPorId(idUsuario);
            return usuario.getPerfil();
        } catch (NegocioException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    /**
     * Busca un perfil Fiscal en la api
     * @param rfc
     * @return
     * @throws FacturaException 
     */
    protected PerfilFiscalDTO BuscarPerfil(String rfc)throws FacturaException{
        try {
            // recibes el de infra
            PerfilFiscalDTO perfil = PerfilFiscalInfraestructuraAdapter.convertirInfraestructuraANegocio(controlSAT.obtenerPerfilFiscal(rfc));
                
            // conviertes a negocio
            return perfil;
        } catch (SatException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    /**
     * Crea una factura a partir de 2 datos fundamentales
     * @param perfil
     * @param reserva
     * @return
     * @throws FacturaException 
     */
    protected FacturaDTO crearFactura(PerfilFiscalDTO perfil, ReservacionDTO reserva) throws FacturaException{
        try {
            return facturaBO.crearFactura(perfil, reserva);
        } catch (NegocioException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    /**
     * Creacion de xml
     * @param factura
     * @return
     * @throws FacturaException 
     */
    protected String generarXml(FacturaDTO factura) throws FacturaException{
        String xmlGenerado = generador.generarXML(factura);

        if(xmlGenerado == null){
           throw new FacturaException("Error al generar xml");
        }
        return xmlGenerado;
    }
    
    /**
     * timrbado del xml
     * @param xml
     * @param rfc
     * @return
     * @throws FacturaException 
     */
    protected SatDTO timbrarXml(String xml, String rfc) throws FacturaException{
        try {
            if(xml == null){
                throw new FacturaException("xml vacio.");
            }
            return controlSAT.timbrarXml(xml, rfc);
        } catch (SatException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    /**
     *  Validar xml generado
     * @param xml
     * @return 
     */
    protected boolean validarXML(String xml){
        if(xml != null){
            return true;
        }
        return false;
    }
    
    /**
     * Validar el xml timbrado
     * @param respuesta
     * @return 
     */
    protected boolean validarTimbrado(SatDTO respuesta){
        if(respuesta == null){
            return false;
        }
        if(respuesta.getXmlTimbrado() == null){
            return false;
        }
        if(respuesta.getUuid() == null){
            return false;
        }
        return true;
    }
    
    /**
     * Generar el pdf a partir de la info de la factura
     * @param factura la factura
     * @return el jasper que sera pdf
     * @throws FacturaException 
     */
    protected JasperPrint generarPDF(FacturaDTO factura) throws FacturaException{
        Map<String, Object> params = new HashMap<>();

            // Datos generales del Timbrado
            params.put("uuid", factura.getUuid());
            params.put("fechaTimbrado", factura.getFechaTimbrado().format(DateTimeFormatter.ISO_DATE).toString());
            params.put("fechaCompra", factura.getFechaCompra().format(DateTimeFormatter.ISO_DATE).toString());
            params.put("total", factura.getTotal());
            params.put("moneda", factura.getMoneda());
            params.put("metodoPago", factura.getMetodoPago());
            params.put("usoCfdi", factura.getUsoCfdi().getDescripcion());

            //  Datos de la empresa, todos son harcodeados jajaja
            params.put("emisorRfc", "PEV120520AA1"); 
            params.put("emisorNombre", "POTROEVENTOS S.A. DE C.V.");
            params.put("emisorRegimen", "601 - General de Ley Personas Morales");
            params.put("emisorCodigoPostal", "85000"); 
            params.put("emisorCorreo", "potroeventos@gmail.com");

            // de la persona que hizo la factura
            params.put("receptorRfc", factura.getPerfil().getRfc());
            params.put("receptorNombre", factura.getPerfil().getNombre().toUpperCase());
            params.put("receptorRegimen", factura.getPerfil().getRegimenFiscal().getDescripcion());
            params.put("receptorCodigoPostal", factura.getPerfil().getCodigoPostal());

            try {
                // Generar el reporte en memoria
                InputStream reporteStream = getClass().getResourceAsStream("/Factura.jasper");
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporteStream, params, new JREmptyDataSource());
                return jasperPrint;
            } catch (JRException ex) {
                throw new FacturaException("Error al generar pdf: " + ex.getMessage());
            }
    }
    
    /**
     * Enviar el pdf al correoxd
     * @param correo el destino al que se enviará
     * @param jasper el jasper que se enviarrá por correo
     */
    protected void enviarCorreo(String correo, JasperPrint jasper) throws FacturaException{
        try {
            Correo.enviarCorreo(correo, jasper);
        } catch (Exception ex) {
           throw new FacturaException(ex.getMessage());
        }
    }
    
    protected boolean validarFechaCompra(LocalDateTime fechaCompra){
        
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime fechaLimite = fechaCompra.plusDays(30);
        
        return !hoy.isBefore(fechaCompra) && !hoy.isAfter(fechaLimite);
    }
    /**
     * Valida que el pdf haya sido creado exitosamente
     * @param jasper false si no se creó, true si sí
     * @return 
     */
    protected boolean validarPdf(JasperPrint jasper){
        if(jasper == null){
            return false;
        }
        return true;
    }
    
    /**
     * Guarda la factura en la base de datos
     * @param factura 
     * @return true si todo bien, false si no
     * @throws FacturaException 
     */
    protected boolean guardarFactura(FacturaDTO factura) throws FacturaException{
        try {
            return facturaBO.guardarFactura(factura);
        } catch (NegocioException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    
    /**
     * 
     * @param correo
     * @return
     * @throws FacturaException 
     */
    protected boolean validarExistenciaCorreo(String correo) throws FacturaException{
        try {
            if(correo == null){
                throw new FacturaException("Correo nulo.");
            }
            return controlCorreo.validarCorreo(correo);
        } catch (ZeroBounceException ex) {
            throw new FacturaException(ex.getMessage());
        }
    }
    /**
     * Guarda un perfil fiscal en la bd, es un auxiliar interno que llama la fachada.
     * @param guardar
     * @param idUsuario
     * @return 
     */
    protected boolean guardarPerfil(PerfilFiscalDTO guardar, String idUsuario) throws FacturaException {
        try {
            return PerfilBO.guardarPerfilFiscal(guardar, idUsuario);
        } catch (NegocioException ex) {
           throw new FacturaException(ex.getMessage());
        }
    }
    
    
}
