/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package excepciones;

/**
 *
 * @author aaron
 */
public class FacturaException extends Exception {

    /**
     * Creates a new instance of <code>FacturaException</code> without detail
     * message.
     */
    public FacturaException() {
    }

    /**
     * Constructs an instance of <code>FacturaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FacturaException(String msg) {
        super(msg);
    }
}
