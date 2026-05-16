/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author aaron
 */
public class ControlFactura {
    
    // --- Singleton del control ---
    private static ControlFactura instance;
    
    // --- Contructor privado para asegurar encapsulamiento ---
    private ControlFactura(){
    }
    
    // --- Get para obtener la instancia de la clase (singleton) ---
    private ControlFactura getInstance(){
        if(instance == null){
            this.instance = new ControlFactura();
        }
        return instance;
    } 
    
    
}
