/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sat;

import com.google.gson.Gson;
import conexion.ConexionSAT;
import dtos.PerfilFiscalInfraestructuraDTO;
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
    
    
}
