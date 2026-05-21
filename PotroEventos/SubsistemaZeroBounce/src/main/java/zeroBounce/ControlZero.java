/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zeroBounce;

import conexion.Conexion;
import excepciones.ZeroBounceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aaron
 */
public class ControlZero {
    
    private static ControlZero instance;
    private final Conexion conexion = new Conexion();
    
    private ControlZero() {
    }
    
    
    public static ControlZero getInstance(){
        if(instance == null){
            instance = new ControlZero();
        }
        return instance;
    }
    
    protected boolean verificarCorreo(String correo) throws ZeroBounceException{
        try {
            String respuesta = this.conexion.ejecutarValidacion(correo);
            
            String status = extraerValor(respuesta, "status");
            
            // regresa verdadero si es valido
            return "valid".equals(status);
            
        } catch (ZeroBounceException ex) {
            throw new ZeroBounceException(ex.getMessage());
        }
    }
    
    private String extraerValor(String json, String llave) {
        Pattern pattern = Pattern.compile("\"" + llave + "\":\\s*\"?([^\",\\}]+)\"?");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1).replace("\"", "").trim();
        }
        return "unknown"; // si no se encuentra
    }
    
}
