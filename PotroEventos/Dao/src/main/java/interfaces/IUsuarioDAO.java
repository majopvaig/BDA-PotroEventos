/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.Usuario;
import excepciones.PersistenciaException;
import Entitys.PerfilFiscal;
/**
 *
 * @author aaron
 */
public interface IUsuarioDAO {

    Usuario obtenerPorId(String idUsuario) throws PersistenciaException;
    
    Usuario obtenerUsuario(Usuario usuario) throws PersistenciaException;

    Usuario guardarUsuario(Usuario usuario) throws PersistenciaException;
    
    //caso factura
    boolean guardarPerfilFiscal(PerfilFiscal perfil, String idUsuario);
    
}
