/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entitys.Asiento;
import Entitys.Seccion;
import Entitys.Ubicacion;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IAsientoDAO;
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
 * @author Dayanara Peralta G
 */
public class AsientoDAO implements IAsientoDAO{
    private static AsientoDAO instancia;

    private AsientoDAO() {
    }

    public static AsientoDAO getInstance() {
        if (instancia == null) {
            instancia = new AsientoDAO();
        }
        return instancia;
    }

    @Override
    public List<Asiento> consultarAsientos() throws PersistenciaException {
        List<Asiento> listaAsientos = new ArrayList<>();
        String sql = "Select id, fila, numero, ubicacion, seccion from asientos";
        
        try (Connection conex = ConexionBD.crearConexion();
             PreparedStatement ps = conex.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Asiento asiento = new Asiento();
                
                Long idBD = rs.getLong("id");
                asiento.setIdAsiento(IdAdapter.LongAString(idBD));
                asiento.setFila(rs.getString("fila"));
                asiento.setNumero(rs.getInt("numero"));
                
                Long idUbicacionBD = rs.getLong("ubicacion_id");
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(IdAdapter.LongAString(idUbicacionBD));
                asiento.setUbicacion(ubicacion);
                
                Long idSeccionBD = rs.getLong("seccion_id");
                Seccion seccion = new Seccion();
                seccion.setIdSeccion(IdAdapter.LongAString(idSeccionBD));
                asiento.setSeccion(seccion);
                
                listaAsientos.add(asiento);
            }
            
            return listaAsientos;
            
        } catch (SQLException ex) {
            Logger.getLogger(AsientoDAO.class.getName()).log(Level.SEVERE, "Error al consultar la lista", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public Asiento consultarPorID(String idAsiento) throws PersistenciaException {
        if(idAsiento == null){
            throw new PersistenciaException("El id no existe.");
        }
        
        Long id = IdAdapter.stringALong(idAsiento);
        
        String sql = "Select id, fila, numero, ubicacion, seccion from asientos where id = ?";
        
        try(Connection conex = ConexionBD.crearConexion();
                PreparedStatement ps = conex.prepareStatement(sql)){
            ps.setLong(1, id);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Asiento asiento = new Asiento();
                    Long idBD = rs.getLong("id");
                    asiento.setIdAsiento(IdAdapter.LongAString(idBD));
                    asiento.setFila(rs.getString("fila"));
                    asiento.setNumero(rs.getInt("numero"));
                    
                    Long idUbicacionBD = rs.getLong("ubicacion_id");
                    Ubicacion ubicacion = new Ubicacion();
                    ubicacion.setIdUbicacion(IdAdapter.LongAString(idUbicacionBD));
                    asiento.setUbicacion(ubicacion);
                    
                    Long idSeccionBD = rs.getLong("seccion_id");
                    Seccion seccion = new Seccion();
                    seccion.setIdSeccion(IdAdapter.LongAString(idSeccionBD));
                    asiento.setSeccion(seccion);
                    
                    return asiento;
                }
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AsientoDAO.class.getName()).log(Level.SEVERE, "Error al consultar por ID", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }

}
