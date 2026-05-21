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
public interface IZeroBounce {
    boolean validarCorreo(String email) throws ZeroBounceException;
}
