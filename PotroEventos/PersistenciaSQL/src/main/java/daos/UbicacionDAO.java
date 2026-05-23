/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.ENUMS.TipoUbicacionP;
import Entitys.Seccion;
import Entitys.Ubicacion;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.IUbicacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author maria
 */
public class UbicacionDAO implements IUbicacionDAO {
    
    private static UbicacionDAO instance;
    
    private static final Logger LOG = Logger.getLogger(UbicacionDAO.class.getName());
    
    private UbicacionDAO(){}
    
    public static UbicacionDAO getInstancia(){
        if(instance == null){
            instance = new UbicacionDAO();
        }
        return instance;
    }

    @Override
    public Ubicacion consultarUbicacion(String idUbicacion) throws PersistenciaException {
        if(idUbicacion ==  null || idUbicacion.isEmpty() || idUbicacion.isBlank()){
            throw new PersistenciaException("Proporcione el ID de la ubicación a buscar.");
        }
        String comando = """
                         select u.id as id_ubicacion, u.nombre as nombre_ubicacion, u.tipo as tipo_ubicacion, u.capacidad as capacidad_ubicacion, 
                         s.id as id_seccion, s.nombre as nombre_seccion, s.capacidad as capacidad_seccion, s.precio_base as precio_seccion
                         from ubicaciones u
                         left join secciones s on s.id_ubicacion = u.id
                         where u.id = ?
                         """;
        Long idLong = IdAdapter.stringALong(idUbicacion);
        try(Connection con = ConexionBD.crearConexion(); PreparedStatement ps = con.prepareStatement(comando)){
            ps.setLong(1, idLong);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    LOG.log(Level.WARNING, "No se encontró una ubicación con dicho ID: {0}", idLong);
                    throw new PersistenciaException("No hay registros con el ID proporcionado.");
                }
                return extraerUbicacion(rs);
            }
        } catch(SQLException ex){
            LOG.log(Level.SEVERE, "Error de SQL al consultar la ubicación.", ex);
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    private Ubicacion extraerUbicacion(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        Ubicacion ubicacion = null;
        while (rs.next()) {
            if (ubicacion == null) {
                ubicacion = new Ubicacion();
                ubicacion.setIdUbicacion(IdAdapter.LongAString(rs.getLong("id_ubicacion")));
                ubicacion.setNombre(rs.getString("nombre_ubicacion"));
                String tipoStr = rs.getString("tipo_ubicacion");
                if (tipoStr != null) {
                    ubicacion.setTipo(TipoUbicacionP.valueOf(tipoStr));
                }
                ubicacion.setCapacidad(rs.getInt("capacidad_ubicacion"));
                ubicacion.setSecciones(new ArrayList<>());
            }
            long idSeccion = rs.getLong("id_seccion");
            if (!rs.wasNull()) {
                Seccion seccion = new Seccion();
                seccion.setIdSeccion(IdAdapter.LongAString(idSeccion));
                seccion.setNombre(rs.getString("nombre_seccion"));
                seccion.setCapacidad(rs.getInt("capacidad_seccion"));
                double precio = rs.getDouble("precio_seccion");
                seccion.setPrecioBase((long) precio);
                ubicacion.getSecciones().add(seccion);
            }
        }
        return ubicacion;
    }
    
}
