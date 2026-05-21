/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sat;

import dtos.PerfilFiscalInfraestructuraDTO;
import dtos.SatDTO;
import excepciones.SatException;

/**
 *
 * @author aaron
 */
public class FachadaSAT implements IComunicacionSAT{
    private static FachadaSAT instance;
    private ControlSAT control = ControlSAT.getInstance();
    
        public static FachadaSAT getInstance(){
        if(instance == null){
            instance = new FachadaSAT();
        }
        return instance;
    }
    @Override
    public PerfilFiscalInfraestructuraDTO obtenerPerfilFiscal(String rfc) throws SatException {
        return control.obtenerPerfilFiscal(rfc);
    }

    @Override
    public SatDTO timbrarXml(String xml, String rfc) throws SatException {
        return control.timbrarXml(xml, rfc);
    }
    
}
