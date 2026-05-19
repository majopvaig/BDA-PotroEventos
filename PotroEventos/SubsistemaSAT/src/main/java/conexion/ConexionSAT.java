/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import com.google.gson.Gson;
import excepciones.SatException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Properties;

/**
 *
 * @author aaron
 */
public class ConexionSAT {
    
    // Iniciar Conexion. Es el fellback
    private final String url_api = "http://localhost:65000/api/sat";
    
    // Es el que se encarga de cominicarse/transportar informacion a la api
    private final HttpClient httpClient; 
    
    // Libreria de google que convierte de json objetos java
    private Gson gson; 
    
    // Almacena direccion de la base de datos a la api del sat
    private static String baseUrl;
    
    private static ConexionSAT instance;

    private ConexionSAT(){
        
        // configurar desde el archivo
        try {
            configurar();
        } catch (SatException e) {
            this.baseUrl = url_api; // Usar URL por defecto
        }
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))  // es el tiempo máximo que esperará conexión
                .build();
        
        this.gson = new Gson();
        
    }
    
    // Singleton
    public static ConexionSAT getInstance(){
        if(instance == null){
            instance = new ConexionSAT();
        }
        return instance;
    }
    
    private void configurar() throws SatException{
        try(InputStream entrada = 
                getClass()  // obtiene la clase actual
                .getClassLoader() // obtiene el cargador de clase
                .getResourceAsStream("sat.properties")){ // este ultimo busca el archivo
            
            Properties propiedades = new Properties();
            propiedades.load(entrada);
            this.baseUrl = propiedades.getProperty("sat.api.url", url_api);
            
        }catch(Exception e){
            this.baseUrl = url_api; // si no se hace esto, se podría quedar colgado el sistema
            throw new SatException("Error al establecer conexion con SAT.");
        }
    }

    // --- getters y setters ---
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        ConexionSAT.baseUrl = baseUrl;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
    
    public HttpClient getHttpClient(){
        return this.httpClient;
    }
    
}
