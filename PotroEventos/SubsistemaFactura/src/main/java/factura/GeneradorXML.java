/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;

import dtos.FacturaDTO;
import excepciones.FacturaException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author aaron
 */

//Clase especializada en la generación de XML para CFDI 4.0
public class GeneradorXML {
    
   
    private static final DateTimeFormatter XML_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static GeneradorXML instance;
    
    private GeneradorXML() {}
    
    public static GeneradorXML getInstance() {
        if (instance == null) {
            instance = new GeneradorXML();
        }
        return instance;
    }
    
    /**
     * Genera el XML del CFDI a partir de un FacturaDTO
     * @param factura Datos de la factura
     * @return XML como String
     * @throws FacturaException Si hay error en la generación
     */
    public String generarXML(FacturaDTO factura) throws FacturaException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.newDocument();
            
            // Calcular impuestos
            double subtotal = calcularSubtotal(factura);
            double iva = calcularIVA(factura);
            
            // Construir documento
            org.w3c.dom.Element comprobante = crearComprobante(doc, factura, subtotal, iva);
            doc.appendChild(comprobante);
            
            crearEmisor(doc, comprobante, factura);
            crearReceptor(doc, comprobante, factura);
            crearConceptos(doc, comprobante, factura, subtotal, iva);
            crearImpuestos(doc, comprobante, factura, iva);
            
            return convertirDocumentoAString(doc);
            
        } catch (Exception e) {
            throw new FacturaException("Error al generar XML de factura: " + e.getMessage());
        }
    }
    
    private double calcularSubtotal(FacturaDTO factura) {
    // El subtotal es el total SIN IVA
    return factura.getTotal() / 1.16;
}

private double calcularIVA(FacturaDTO factura) {
    double subtotal = calcularSubtotal(factura);
    return factura.getTotal() - subtotal;
}
    
    private Element crearComprobante(Document doc, FacturaDTO factura, 
                                                double subtotal, double iva) {
     org.w3c.dom.Element comprobante = doc.createElementNS("http://www.sat.gob.mx/cfd/4", "cfdi:Comprobante");
     comprobante.setAttribute("xmlns:cfdi", "http://www.sat.gob.mx/cfd/4");
     comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
     comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd");
     comprobante.setAttribute("Version", "4.0");
     comprobante.setAttribute("Serie", "A");
     comprobante.setAttribute("Folio", String.valueOf(System.currentTimeMillis()));
     comprobante.setAttribute("Fecha", factura.getFechaCompra().format(XML_DATE_FORMAT));
     comprobante.setAttribute("Sello", "");
     comprobante.setAttribute("FormaPago", factura.getMetodoPago() != null ? factura.getMetodoPago() : "01");
     comprobante.setAttribute("NoCertificado", "");
     comprobante.setAttribute("Certificado", "");
     comprobante.setAttribute("SubTotal", String.format("%.2f", subtotal));
     comprobante.setAttribute("Moneda", factura.getMoneda() != null ? factura.getMoneda() : "MXN");
     comprobante.setAttribute("Total", String.format("%.2f", factura.getTotal()));
     comprobante.setAttribute("TipoDeComprobante", "I");
     comprobante.setAttribute("Exportacion", "01");
     comprobante.setAttribute("MetodoPago", "PUE");
     comprobante.setAttribute("LugarExpedicion", factura.getPerfil().getCodigoPostal());
     return comprobante;
 }
    
    private void crearEmisor(Document doc, Element comprobante, FacturaDTO factura) {
        org.w3c.dom.Element emisor = doc.createElement("cfdi:Emisor");
        emisor.setAttribute("Rfc", factura.getPerfil().getRfc());
        emisor.setAttribute("Nombre", factura.getPerfil().getNombre());
        emisor.setAttribute("RegimenFiscal", factura.getPerfil().getRegimenFiscal().getCodigo());
        comprobante.appendChild(emisor);
    }
    
    private void crearReceptor(Document doc, Element comprobante, FacturaDTO factura) {
        Element receptor = doc.createElement("cfdi:Receptor");
        receptor.setAttribute("Rfc", "XAXX010101000");
        receptor.setAttribute("Nombre", "PUBLICO GENERAL");
        receptor.setAttribute("DomicilioFiscalReceptor", factura.getPerfil().getCodigoPostal());
        receptor.setAttribute("RegimenFiscalReceptor", "616");
        receptor.setAttribute("UsoCFDI", factura.getUsoCfdi().getCodigo());
        comprobante.appendChild(receptor);
    }
    
    private void crearConceptos(Document doc, Element comprobante, 
                              FacturaDTO factura, double subtotal, double iva) {
        Element conceptos = doc.createElement("cfdi:Conceptos");

        org.w3c.dom.Element concepto = doc.createElement("cfdi:Concepto");
        concepto.setAttribute("ClaveProdServ", "84111500");
        concepto.setAttribute("Cantidad", "1");
        concepto.setAttribute("ClaveUnidad", "E48");
        concepto.setAttribute("Unidad", "Servicio");
        concepto.setAttribute("Descripcion", "Boleto de evento");
        concepto.setAttribute("ValorUnitario", String.format("%.2f", subtotal));
        concepto.setAttribute("Importe", String.format("%.2f", subtotal));
        concepto.setAttribute("ObjetoImp", "02");

        // Impuestos del concepto
        org.w3c.dom.Element impuestosConcepto = doc.createElement("cfdi:Impuestos");
        org.w3c.dom.Element trasladosConcepto = doc.createElement("cfdi:Traslados");

        org.w3c.dom.Element traslado = doc.createElement("cfdi:Traslado");
        traslado.setAttribute("Base", String.format("%.2f", subtotal));
        traslado.setAttribute("Impuesto", "002");
        traslado.setAttribute("TipoFactor", "Tasa");
        traslado.setAttribute("TasaOCuota", "0.160000");
        traslado.setAttribute("Importe", String.format("%.2f", iva));

        trasladosConcepto.appendChild(traslado);
        impuestosConcepto.appendChild(trasladosConcepto);
        concepto.appendChild(impuestosConcepto);
        conceptos.appendChild(concepto);
        comprobante.appendChild(conceptos);
    }
    
    private void crearImpuestos(Document doc, Element comprobante, 
                                  FacturaDTO factura, double iva) {
        Element impuestos = doc.createElement("cfdi:Impuestos");
        impuestos.setAttribute("TotalImpuestosTrasladados", String.format("%.2f", iva));
        
        org.w3c.dom.Element traslados = doc.createElement("cfdi:Traslados");
        org.w3c.dom.Element trasladoGeneral = doc.createElement("cfdi:Traslado");
        trasladoGeneral.setAttribute("Impuesto", "002");
        trasladoGeneral.setAttribute("TipoFactor", "Tasa");
        trasladoGeneral.setAttribute("TasaOCuota", "0.160000");
        trasladoGeneral.setAttribute("Importe", String.format("%.2f", iva));
        
        traslados.appendChild(trasladoGeneral);
        impuestos.appendChild(traslados);
        comprobante.appendChild(impuestos);
    }
    
    private String convertirDocumentoAString(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        
        return writer.toString();
    }
}
