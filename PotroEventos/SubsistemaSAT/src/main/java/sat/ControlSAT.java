/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sat;

import com.google.gson.Gson;
import conexion.ConexionSAT;
import dtos.*;
import excepciones.SatException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author aaron
 */
public class ControlSAT {
    
    public static ControlSAT instance;
    
    // Objeto de conexion
    private ConexionSAT conexion = ConexionSAT.getInstance();
    
    private final HttpClient httpClient;
    private final Gson gson;
    private final String baseUrl;
    
    private ControlSAT() {
        
        this.conexion = ConexionSAT.getInstance();
        this.httpClient = conexion.getHttpClient();
        this.gson = conexion.getGson();
        this.baseUrl = conexion.getBaseUrl();
        
    }
    
    public static ControlSAT getInstance(){
        if(instance == null){
            instance = new ControlSAT();
        }
        return instance;
    }

    protected PerfilFiscalInfraestructuraDTO obtenerPerfilFiscal(String rfc) throws SatException{
        try{
            
            // --- Contruir peticion get ---

            HttpRequest httpRequest = HttpRequest.newBuilder() // arma la peticion paso a paso
                    .uri(URI.create(this.baseUrl + "/perfil/" + rfc)) // define la url a la que se dirige
                    .GET() // metodo solo de busqueda
                    .build(); // envia el objeto creado y configurado

            //  --- Envio de petición ---
            
            HttpResponse<String> response = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers // envia la peticion construida
                            .ofString());   // indica que la respuesta la recibamos en texto 
            
            // --- Procesar respuesta ---
            
            if(response.statusCode() == 200){
                return (gson.fromJson(response.body(), PerfilFiscalInfraestructuraDTO.class));
            }else if(response.statusCode() == 404){
                return null;
            }else {
                throw new SatException("Error de busqueda de perfil: " + response.statusCode());
            }
                
        }catch(Exception e){
            throw new SatException("Error al establecer conexion: " + e.getMessage());
        }
    }
    
    protected SatDTO timbrarXml(String xml, String rfc) throws SatException {
        try {

            // --- Construir objeto con los datos de la solicitud ---
            TimbradoRequest request = new TimbradoRequest();
            request.setRfcEmisor(rfc);
            request.setXmlPlano(xml);

            // --- Convertir objeto a JSON ---
            String jsonRequest = gson.toJson(request);

            // --- Construir peticion POST ---
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseUrl + "/timbrar"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            // --- Enviar petición ---
            HttpResponse<String> response = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // --- Procesar respuesta ---
            if (response.statusCode() == 200) {
                // ✅ Retornar SatDTO completo
                return gson.fromJson(response.body(), SatDTO.class);
            } else {
                throw new SatException("Error al timbrar: " + response.statusCode() + " - " + response.body());
            }

        } catch (Exception e) {
            throw new SatException("Error al establecer conexion con API SAT: " + e.getMessage());
        }
    }
}
