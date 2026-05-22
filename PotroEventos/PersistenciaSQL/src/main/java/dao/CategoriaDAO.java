package dao;

import Entitys.Categoria;
import Entitys.ENUMS.CategoriaEvento;
import adapters.IdAdapter;
import conexion.ConexionBD;
import excepciones.PersistenciaException;
import interfaces.ICategoriaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements ICategoriaDAO {

    private static CategoriaDAO instance;

    private CategoriaDAO() {
    }

    public static CategoriaDAO getInstance(){
        if(instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

    @Override
    public Categoria obtenerPorId(String idReserva) throws PersistenciaException {
        if(idReserva == null){
            throw new PersistenciaException("El idReserva no existe.");
        }
        // transformar el tipo de dato
        Long idConvertido = IdAdapter.stringALong(idReserva);

        String sql = """
                    Select idReserva, urlImagen, nombre
                    from categorias 
                    where idCategoria = ?
                    """;

        try(Connection con = ConexionBD.crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            // --- asignar parámetro ---
            ps.setLong(1, idConvertido);
            ResultSet resultado = ps.executeQuery();

            // --- validar si hay resultados ---
            if(resultado.next()){
                // --- Creacion de objeto ---
                Categoria categoria = new Categoria();
                // --- Extraccion de datos ---
                categoria.setId(IdAdapter.LongAString(resultado.getLong("idReserva")));
                categoria.setUrlImagen(resultado.getString("urlImagen"));
                categoria.setNombre(CategoriaEvento.valueOf(resultado.getString("nombre")));
                return categoria;
            }
            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al establecer conexión.");
        }
    }

    @Override
    public List<Categoria> consultarCategorias() throws PersistenciaException {
        // --- crear query ---
        String sql =
                """
                Select idReserva, urlImagen, nombre
                from categorias
                """;
        //  --- Crear conexion ---
        try(Connection con = ConexionBD.crearConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ){
            //  --- crear lista ---
            List<Categoria> lista = new ArrayList<>();
            //  --- ejecutar query ---
            ResultSet result = ps.executeQuery();
            //  --- meter cada resultado a la lista ---
            while(result.next()){
                // --- convertir a categoria ---
                Categoria categoria = new Categoria();

                categoria.setId(IdAdapter.LongAString(result.getLong("idReserva")));
                categoria.setUrlImagen(result.getString("urlImagen"));
                categoria.setNombre(CategoriaEvento.valueOf(result.getString("nombre")));

                lista.add(categoria);
            }
            return lista;
        }catch(SQLException e){
            throw new PersistenciaException("Error en la busqueda de categorias.");
        }
    }
}
