/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Devolucion;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IDevolucionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class DevolucionDAO implements IDevolucionDAO {

    private static DevolucionDAO instance;
    
    private static final Logger LOG = Logger.getLogger(DevolucionDAO.class.getName());
    
    private DevolucionDAO(){}
    
    public static DevolucionDAO getInstancia(){
        if(instance == null){
            instance = new DevolucionDAO();
        }
        return instance;
    }
    
    @Override
    public boolean agregarDevolucion(Devolucion devolucion, String idReservacion) throws PersistenciaException {
        if(devolucion == null || idReservacion == null || idReservacion.isEmpty() || idReservacion.isBlank()){
            throw new PersistenciaException("Ingrese todos lo datos necesarios.");
        }
        String comando = """
                         insert into devoluciones (motivo, tipo, comentarios, id_reservacion) values (?, ?, ?, ?)
                         """;
        Long idLong = IdAdapter.stringALong(idReservacion);
        try(Connection conn = ConexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, devolucion.getMotivo().name());
            ps.setString(2, devolucion.getTipo().name());
            ps.setString(3, devolucion.getDescripcion());
            ps.setLong(4, idLong);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas == 0){
                LOG.log(Level.WARNING, "No se pudo agregar la devolución.");
                throw new PersistenciaException("No se pudo agrgar la devolución.");
            }
            return true;
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar la ubicación.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
}
