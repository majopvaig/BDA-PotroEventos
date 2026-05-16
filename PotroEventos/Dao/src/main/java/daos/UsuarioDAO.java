package daos;

import Entitys.Usuario;
import adaptadores.UsuarioPersistenciaAdapter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.InsertOneResult;
import conexion.ConexionMongo;
import entidadesmongo.UsuarioMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IUsuarioDAO;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

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

            if (BCrypt.checkpw(usuario.getContrasenia(), resultado.getContrasenia())) {
                return UsuarioPersistenciaAdapter.convertirADominio(resultado);
            }

            return null;
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
            usuario.setContrasenia(BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt()));
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
            UsuarioMongoEntidad seccion = coleccionUsuarios
                    .find(eq("_id", new ObjectId(idUsuario)))
                    .first();
            return UsuarioPersistenciaAdapter.convertirADominio(seccion);
        } catch (MongoException me) {
            throw new PersistenciaException("No fue posible obtener al usuario.");
        }
    }

}
