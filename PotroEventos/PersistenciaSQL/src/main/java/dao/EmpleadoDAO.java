/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Empleado;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IEmpleadoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class EmpleadoDAO implements IEmpleadoDAO {
    
    private static EmpleadoDAO instance;
    
    private static final Logger LOG = Logger.getLogger(EmpleadoDAO.class.getName());
    
    private EmpleadoDAO(){}
    
    public static EmpleadoDAO getInstancia(){
        if(instance == null){
            instance = new EmpleadoDAO();
        }
        return instance;
    }

    @Override
    public Empleado obtenerEmpleado(Empleado empleado) throws PersistenciaException {
        if(empleado == null || empleado.getCorreo() == null || empleado.getCorreo().isEmpty() || empleado.getCorreo().isBlank()){
            throw new PersistenciaException("Se requiere un correo para buscar al empleado.");
        }
        String comandoSQL = """
                            select id, correo, contrasenia 
                            from empleados
                            where correo = ?
                            """;
        try(Connection conn = ConexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(comandoSQL)){
            ps.setString(1, empleado.getCorreo());
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró un empleado con dicho correo");
                    throw new PersistenciaException("No hay registros con el correo proporcionado.");
                }
                return new Empleado(
                        IdAdapter.LongAString(rs.getLong("id")), 
                        rs.getString("correo")
                );
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar el empleado.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
}
