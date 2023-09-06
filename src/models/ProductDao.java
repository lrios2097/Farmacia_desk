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
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author luisr
 */
public class ProductDao {
    
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL(); // instancio la conexion
    Connection conn;
    PreparedStatement pst; //Consultas
    ResultSet rs; //Obtener datos de la consulta
    
    //Registrar Producto
    public boolean registerProductQuery(Products product){
        String query = "INSERT INTO products(code, name, description, unit_price, created, updated, category_id)"
                + "VALUES(?, ?, ? ,? ,? ,? ,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareCall(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto");
            return false;
        }
    }
    
    //Listar productos
    public List listProductQuery(String value){
        List<Products> list_products = new ArrayList();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca " +
            "WHERE pro.category_id = ca.id";
        String query_search_product = "SELET pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca"+
                 "ON pro.category_id = ca.id WHERE pro.name LIKE '%" + value + "%'";
        
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_product);
                rs = pst.executeQuery();
            }
            
            while (rs.next()){
                Products product = new Products();
                product.setId(rs.getInt("id")); //"" son de la query
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);         
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_products;
    }
    
    //Modificar Producto
    public boolean updateProductQuery(Products product){
        String query = "UPDATE products set code = ?, name = ?, description = ?, unit_price = ?, updated = ?, category_id = ?" +
                "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareCall(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategory_id());
            pst.setInt(7, product.getId());
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del producto");
            return false;
        }
    }
    
    //Eliminar Producto
    public boolean deleteProductQuery(int id){
        
        String query_delete_product = "DELETE * FROM productss WHERE id=" + id; //'value'
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_delete_product);
            rs = pst.executeQuery();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar un producto que no tenga relación en la tabla");
        }
        return false;
    }
    
    // Buscar producto
    public Products searchProduct(int id){
        String query = "SELET pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca " +
                 "ON pro.category_id = ca.id WHERE id = ?";
        Products product = new Products();
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()){ //Usamos if porque solo es un registro
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
                                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
       return product;
    }
    
    //Buscar producto por codigo
    public Products searchCodeProduct(int code){
        String query = "SELET pro.id pro.name FROM products pro WHERE code = ?"; // solo el id y nombre
        Products product = new Products();
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);
            rs = pst.executeQuery();
            
            if (rs.next()){ //Usamos if porque solo es un registro
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                
                                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
       return product;
    }
    
    //Traer la cantidad de productos
    public Products searchId(int id){
        String query = "SELECT pro.product_quantity FROM products pro WHERE pro.id = ?";
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                product.setProduct_quantity(rs.getInt("product_quantity"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
    //Actualizar stock
    public boolean updateStockQuery(int amount, int product_id){
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt (1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
    }
}
