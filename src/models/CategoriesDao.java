
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp; //agrego de forma manual
import java.sql.SQLException; //manual
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author luisr
 */
public class CategoriesDao {
        
        //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL(); // instancio la conexion
    Connection conn;
    PreparedStatement pst; //Consultas
    ResultSet rs; //Obtener datos de la consulta
    
    //Registrar Categoria
    public boolean registerCategoryQuery(Categories category){
        String query = "INSERT INTO categories (name, created, updated) VALUES(?,?,?,)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la categoria");
            return false;
        }
    }
    
    //Listar Categorias
    public List listCategoriesQuery(String value){
        List<Categories> list_categories = new ArrayList();
        String query ="SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE %'"+value+"%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            while(rs.next()){
                Categories category = new Categories(); //instancio un nuevo category
                category.setId(rs.getInt("id")); //agrego los datos del rs a mi nuevo category
                category.setName(rs.getString("name"));
                list_categories.add(category); // agrego ese category a mi array que instancie al inicio
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_categories;
        
    }
    
    //Modificar Categorias
    public boolean updateCategoryQuery(Categories category){
        String query = "UPDATE SET name =?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos de la categoría");
            return false;
        }
        
    }
    
    //Eliminar Categoria
    public boolean deleteCategoryQuery(int id){
        String query ="DELETE FROM categories WHERE id =" + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar una Categoría que tenga relación con otra tabla");
            return false;
        }
    }
    
}
