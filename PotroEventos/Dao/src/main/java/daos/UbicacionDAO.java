/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.Seccion;
import Entitys.Ubicacion;
import adaptadores.UbicacionPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import conexion.ConexionMongo;
import entidadesmongo.UbicacionMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IUbicacionDAO;
import org.bson.types.ObjectId;

/**
 *
 * @author maria
 */
public class UbicacionDAO implements IUbicacionDAO {

    private final MongoCollection<UbicacionMongoEntidad> coleccionUbicaciones;
    private static UbicacionDAO instance;
    
    private UbicacionDAO(){
        coleccionUbicaciones = ConexionMongo.obtenerColeccionUbicacionea();
    }
    
    public static UbicacionDAO getInstance(){
        if(instance == null){
            instance = new UbicacionDAO();
        }
        return instance;
    }
    
    @Override
    public Ubicacion consultarPorID(String idUbicacion) throws PersistenciaException {
        if (idUbicacion == null || idUbicacion.trim().isEmpty()) {
            return null;
        }
        try {
            UbicacionMongoEntidad seccion = coleccionUbicaciones
                    .find(eq("_id", new ObjectId(idUbicacion)))
                    .first();
            return UbicacionPersistenciaAdapter.convertirADominio(seccion);
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible obtener la sección.");
        }
    }

    @Override
    public Seccion buscarSeccionPorId(String idUbicacion, String idSeccion) throws PersistenciaException {
        try {
            Ubicacion ubicacion = consultarPorID(idUbicacion);
            if (ubicacion == null || ubicacion.getSecciones() == null) {
                return null;
            }

            return ubicacion.getSecciones().stream()
                    .filter(s -> s.getIdSeccion().equals(idSeccion))
                    .findFirst()
                    .orElse(null);
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible obtener la sección.");
        }
    }
    
}
