/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entitys.PerfilFiscal;
import excepciones.PersistenciaException;

/**
 *
 * @author maria
 */
public interface IPerfilFiscal {

    boolean guardarPerfilFiscal(PerfilFiscal perfil, String idUsuario) throws PersistenciaException;
}
