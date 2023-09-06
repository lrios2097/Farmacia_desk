/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author luisr
 */
public class SuppliersDao {
    
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL(); // instancio la conexion
    Connection conn;
    PreparedStatement pst; //Consultas
    ResultSet rs; //Obtener datos de la consulta
    
    //Registrar Proveedor
    public boolean registerSupplierQuery(Suppliers supplier){
        String query = "INSERT INTO suppliers (name, description, address, telephone, email, city, created, updated)"
                + "VALUES(?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor");
            return false;
        }
    }
    
    //Listar Proveedores
    public List listSuppliersQuery(String value){
        // creamos la variable List
        List<Suppliers> list_suppliers = new ArrayList();
        String query = "SELECT * FROM suppliers";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value+ "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }
            //cada vez que se haga una busqueda se creara una lista distinta
            while(rs.next()){
                Suppliers supplier = new Suppliers(); // creo un solo supplier
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("description"));
                supplier.setName(rs.getString("address"));
                supplier.setName(rs.getString("telephone"));
                supplier.setName(rs.getString("email"));
                supplier.setName(rs.getString("city"));
                list_suppliers.add(supplier);      
            }
               
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_suppliers;
        
    }
    
    // Modificar poveedor
    public boolean updateSupplierQuery(Suppliers supplier){
        String query = "UPDATE SET name = ?, description = ?, address = ?, telephone = ?, email = ?, city = ?, updated = ?,"
                + "WHERE id= ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del proveedor");
            return false;
        }
    }
    
    //Eliminar Proveedor
    public boolean deleteSupplierQuery(int id){
        String query = "DELETE FROM suppliers WHERE id = " + id;
        try {
            conn = cn.getConnection(); // llamar a la conexion
            pst = conn.prepareCall(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar un proveedor que tiene relación con otra tabla");
            return false;
        }
    }
    
}
