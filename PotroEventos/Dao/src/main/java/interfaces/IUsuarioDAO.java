/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.PerfilFiscal;
import Entitys.Usuario;
import excepciones.PersistenciaException;

/**
 *
 * @author aaron
 */
public interface IUsuarioDAO {

    Usuario obtenerPorId(String idUsuario) throws PersistenciaException;
    
    Usuario obtenerUsuario(Usuario usuario) throws PersistenciaException;

    Usuario guardarUsuario(Usuario usuario) throws PersistenciaException;
    
    Usuario obtenerPorCorreo(String correo) throws PersistenciaException;
    
    boolean restarCreditos(String idUsuario, Integer cantidad) throws PersistenciaException;
    
    boolean aumentarCreditos(String idUsuario, Integer cantidad) throws PersistenciaException;
    
    //caso factura
    boolean guardarPerfilFiscal(PerfilFiscal perfil, String idUsuario)throws PersistenciaException;
}
