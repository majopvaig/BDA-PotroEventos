package daos;

import Entitys.PerfilFiscal;
import Entitys.Usuario;
import adaptadores.UsuarioPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Updates.inc;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import conexion.ConexionMongo;
import entidadesmongo.UsuarioMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IUsuarioDAO;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author aaron
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static UsuarioDAO instance;

    private MongoCollection<UsuarioMongoEntidad> coleccionUsuarios = ConexionMongo.obtenerColeccionUsuarios();

    private UsuarioDAO() {
        coleccionUsuarios.createIndex(new Document("correo", 1), new IndexOptions().unique(true));
    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

     @Override
    public Usuario obtenerUsuario(Usuario usuario) throws PersistenciaException {
        if (usuario == null || usuario.getCorreo() == null) {
            throw new PersistenciaException("El correo del usuario es requerido.");
        }

        try {

            UsuarioMongoEntidad resultado = coleccionUsuarios.find(eq("correo", usuario.getCorreo())).first();

            if (resultado == null) {
                return null;
            }

            return UsuarioPersistenciaAdapter.convertirADominio(resultado);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar el usuario en la base de datos.");
        }
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) throws PersistenciaException {
        if (usuario == null) {
            throw new PersistenciaException("El usuario no puede ser null.");
        }
        try {
            UsuarioMongoEntidad u = UsuarioPersistenciaAdapter.convertirAMongo(usuario);
            InsertOneResult resultado = this.coleccionUsuarios.insertOne(u);

            if (resultado.getInsertedId() == null) {
                throw new PersistenciaException("Error al guardar al usuario");
            }

            ObjectId idGenerado = resultado.getInsertedId().asObjectId().getValue();

            u.setId(idGenerado);

            return UsuarioPersistenciaAdapter.convertirADominio(u);

        } catch (MongoException e) {
            if (e.getCode() == 11000) {
                throw new PersistenciaException("El correo '" + usuario.getCorreo() + "' ya está registrado.");
            }
            throw new PersistenciaException("No fue posible guardar al usuario");
        }
    }

    @Override
    public Usuario obtenerPorId(String idUsuario) throws PersistenciaException {
        try {
            UsuarioMongoEntidad u = coleccionUsuarios
                    .find(eq("_id", new ObjectId(idUsuario)))
                    .first();
            return UsuarioPersistenciaAdapter.convertirADominio(u);
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible obtener al usuario.");
        }
    }

    @Override
    public Usuario obtenerPorCorreo(String correo) throws PersistenciaException {
        try {
            UsuarioMongoEntidad u = coleccionUsuarios
                    .find(eq("correo", correo))
                    .first();
            return UsuarioPersistenciaAdapter.convertirADominio(u);
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible recuperar dicho usuario.");
        }
    }

    @Override
    public boolean restarCreditos(String idUsuario, Integer cantidad) throws PersistenciaException {
        try {
            Bson filtro = Filters.and(
                    Filters.eq("_id", new ObjectId(idUsuario)),
                    Filters.gte("creditos", cantidad));

            Bson actualizacion = inc("creditos", -cantidad);

            UpdateResult resultado = coleccionUsuarios.updateOne(filtro, actualizacion);

            return resultado.getModifiedCount() > 0;
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible restar los créditos al usuario");
        }
    }

    @Override
    public boolean aumentarCreditos(String idUsuario, Integer cantidad) throws PersistenciaException {
        try {
            Bson filtro = Filters.eq("_id", new ObjectId(idUsuario));

            Bson actualizacion = inc("creditos", +cantidad);

            UpdateResult resultado = coleccionUsuarios.updateOne(filtro, actualizacion);

            return resultado.getModifiedCount() > 0;
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible agregar los créditos al usuario");
        }
    }
    
    @Override
    public boolean guardarPerfilFiscal(PerfilFiscal perfil, String idUsuario) {
        try{
            if(perfil == null){
                throw new MongoException("No puede almacenarse un campo vacío.");
            }
            
            UpdateResult resultado = coleccionUsuarios
                    .updateOne(Filters.eq("_id", new ObjectId(idUsuario))
                            , Updates.set("perfilFiscal", perfil));
            
            if(resultado == null){
                return false;
            }
            
            return true;
        }catch(MongoException me){
            throw new MongoException("Error al guardar perfil fiscal.");
        }
        
    }
}
