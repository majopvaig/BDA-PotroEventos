/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import Entitys.Categoria;
import Entitys.ENUMS.CategoriaEvento;
import entidadesmongo.CategoriaMongoEntidad;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maria
 */
public class CategoriaPersistenciaAdapter {
    
    public static Categoria convertirADominio(CategoriaMongoEntidad mongo) throws PersistenciaException {
        if(mongo == null){
            return null;
        }
        
        Categoria dominio = new Categoria();
        
        dominio.setId(mongo.getIdComoTexto());
        dominio.setNombre(CategoriaEvento.valueOf(mongo.getNombre()));
        dominio.setUrlImagen(mongo.getUrlImagen());
        
        return dominio;
    }
    
    public static List<Categoria> convertirListaADominio(List<CategoriaMongoEntidad> lista) throws PersistenciaException {
        List<Categoria> categorias = new ArrayList<>();
        
        if(lista == null){
            return categorias;
        }
        
        for(CategoriaMongoEntidad mongo : lista){
            categorias.add(convertirADominio(mongo));
        }
        
        return categorias;
    }

}
