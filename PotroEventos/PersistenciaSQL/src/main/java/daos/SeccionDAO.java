/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.Seccion;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.ISeccionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class SeccionDAO implements ISeccionDAO {
    
    private static SeccionDAO instance;
    
    private static final Logger LOG = Logger.getLogger(SeccionDAO.class.getName());
    
    private SeccionDAO(){}
    
    public static SeccionDAO getInstance(){
        if(instance == null){
            instance = new SeccionDAO();
        }
        return instance;
    }

    @Override
    public Seccion buscarSeccionPorId(String idSeccion) throws PersistenciaException {
        if(idSeccion == null || idSeccion.isEmpty() || idSeccion.isBlank()){
            throw new PersistenciaException("Se requiere el ID de la sección.");
        }
        String comando = """
                         select id, nombre, capacidad, precio_base from secciones where id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idSeccion);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró una sección con dicho ID: {0}", idLong);
                    throw new PersistenciaException("No hay registros con el ID proporcionado.");
                }
                return extraerSeccion(rs);
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar la sección.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public List<Seccion> buscarPorEvento(String idEvento) throws PersistenciaException {
        String sql = """
                     select id_seccion, nombre, capacidad, precio_base 
                     from secciones where id_evento = ?""";
        Long idL = IdAdapter.stringALong(idEvento);
        try(Connection con = ConexionBD.crearConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setLong(1, idL);
            try(ResultSet rs = ps.executeQuery()){
                List<Seccion> secciones = new ArrayList<>();
                while(rs.next()){
                    Seccion seccion = new Seccion();
                    seccion.setIdSeccion(IdAdapter.LongAString(idL));
                    seccion.setNombre(rs.getString("nombre"));
                    seccion.setCapacidad(rs.getInt("capacidad"));
                    seccion.setPrecioBase(rs.getLong("precio_base"));
                    secciones.add(seccion);
                }
                return secciones;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No fue posible obtener las secciones para el evento: " + idEvento, ex);
        }
    }
    
    private Seccion extraerSeccion(ResultSet rs) throws SQLException {
        if(rs == null){
            return null;
        }
        Seccion s = new Seccion();
        s.setIdSeccion(IdAdapter.LongAString(rs.getLong("id")));
        s.setNombre(rs.getString("nombre"));
        s.setCapacidad(rs.getInt("capacidad"));
        Double precio = rs.getDouble("precio_base");
        s.setPrecioBase(precio.longValue());
        return s;
    }
    
}
