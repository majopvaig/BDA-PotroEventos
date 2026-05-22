package interfaces;
import Entitys.Categoria;
import excepciones.PersistenciaException;
import java.util.List;

public interface ICategoriaDAO {

    public Categoria obtenerPorId(String id)throws PersistenciaException;

    public List<Categoria> consultarCategorias() throws PersistenciaException;
}
