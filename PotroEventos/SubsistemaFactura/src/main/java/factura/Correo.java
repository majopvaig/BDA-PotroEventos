/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author aaron
 */
public class Correo {
     public static void enviarCorreo(String destinatario, JasperPrint jasper) throws Exception {
        
        // Convierte el el jasper en pdf como un arreglo de bytes 
        byte[] pdf = JasperExportManager.exportReportToPdf(jasper);
        
        // lo empaqueta como un datasource, es como darle identidad a los bytes como un pdf
        ByteArrayDataSource dataSource =
        new ByteArrayDataSource(pdf, "application/pdf"); // es lo que dice q es pdf

        MimeBodyPart adjunto = new MimeBodyPart(); // esta cosa como que se difive por partes

        adjunto.setDataHandler(new DataHandler(dataSource)); // mete el pdf al adjunto
        adjunto.setFileName("potroFactura.pdf");
        final String remitente = "potroeventos@gmail.com"; 
        final String password = "xsnl tehn oeed latj";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com"); // ptrotocolo del correo
        props.put("mail.smtp.port", "587"); // puerto del mismo tls
        props.put("mail.smtp.auth", "true"); // autenticacion 
        props.put("mail.smtp.starttls.enable", "true"); // activa el cifrado

        Session session = Session.getInstance(props,  // crea la sesion y se autentifica
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                } // son las credenciales de la persona
            }
        );

        Message message = new MimeMessage(session); // crea el mensaje q se enviara

        message.setFrom(new InternetAddress(remitente)); // quien envia vaya
        message.setRecipients( 
            Message.RecipientType.TO,
            InternetAddress.parse(destinatario) // quienr ecibe el mensaje
        );

        // Titulo del correo 
        message.setSubject("Comprobante de facturación. PotroEventos");

        // Parte del texto. Es el cuerpo que da contexto
        MimeBodyPart texto = new MimeBodyPart();
        texto.setText("Reservación facturada en potroEventos. Gracias por formar parte de nuestro equipo.");

        // Combinar, junta todas las partes asi como los brainrots de roblox
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(texto);
        multipart.addBodyPart(adjunto);

        message.setContent(multipart);

        // se envía el correo
        Transport.send(message);
    }
}
