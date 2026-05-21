/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zeroBounce;

import excepciones.ZeroBounceException;

/**
 *
 * @author aaron
 */
public class FachadaZero implements IZeroBounce{
    
    private ControlZero  control = ControlZero.getInstance();

    private static FachadaZero instance;
    
    private FachadaZero() {
    }
    
    public static FachadaZero getInstance(){
        if(instance == null){
            instance = new FachadaZero();
        }
        return instance;
    }
    @Override
    public boolean validarCorreo(String email) throws ZeroBounceException{
        return control.verificarCorreo(email);
    }
}
