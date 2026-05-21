/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import Entitys.PerfilFiscal;
import adapters.PerfilFiscalAdapter;
import daos.UsuarioDAO;
import dtos.PerfilFiscalDTO;
import fabricas.FabricaFacturaBO;
import fabricas.IFabricaFacturaBO;
import interfaces.IPerfilFiscalBO;
import interfaces.IUsuarioDAO;

/**
 *
 * @author aaron
 */
public class PerfilFiscalBO implements IPerfilFiscalBO{

    // porque con el usuario lo asociare jeje
    private IUsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    
    private static PerfilFiscalBO instance;

    private PerfilFiscalBO() {
    }
    
    public static PerfilFiscalBO getInstance(){
        if(instance == null){
            instance = new PerfilFiscalBO();
        }
        return instance;
    }
    
    
    @Override
    public boolean guardarPerfilFiscal(PerfilFiscalDTO guardar, String idUsuario) {
        
        PerfilFiscal perfilGuardar = PerfilFiscalAdapter.convertirADominio(guardar);
        
        return usuarioDAO.guardarPerfilFiscal(perfilGuardar, idUsuario);
    }


    
    
}
