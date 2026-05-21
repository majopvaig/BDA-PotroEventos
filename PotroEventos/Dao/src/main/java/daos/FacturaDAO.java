/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Entitys.Factura;
import adaptadores.FacturaPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.InsertOneResult;
import conexion.ConexionMongo;
import entidadesmongo.FacturaMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IFacturaDAO;
import org.bson.types.ObjectId;

/**
 *
 * @author aaron
 */
public class FacturaDAO implements IFacturaDAO{
    private MongoCollection<FacturaMongoEntidad> coleccionFactura;
    private static FacturaDAO instance;
    
    private FacturaDAO() {

        this.coleccionFactura = ConexionMongo.obtenerColeccionFacturas();
    }
    
    public static FacturaDAO getInstance() {
        if (instance == null) {
            instance = new FacturaDAO();
        }
        return instance;
    }
    
    
    /**
     * Guardar factura y retornar el ID generado
     */
    @Override
    public String guardarFactura(Factura factura) throws PersistenciaException {
        try {
            // Convertir dominio a MongoDB dentro del DAO
            FacturaMongoEntidad facturaMongo = FacturaPersistenciaAdapter.convertirAMongo(factura);
            
            InsertOneResult resultado = coleccionFactura.insertOne(facturaMongo);
            if (resultado.getInsertedId() == null) {
                throw new PersistenciaException("Error al guardar factura");
            }
            ObjectId idGenerado = resultado.getInsertedId().asObjectId().getValue();
            facturaMongo.setId(idGenerado);
            return idGenerado.toHexString();
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar factura: " + e.getMessage());
        }
    }
}
