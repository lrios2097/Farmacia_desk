/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author luisr
 */
public class PurchasesDao {
    
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL(); // instancio la conexion
    Connection conn;
    PreparedStatement pst; //Consultas
    ResultSet rs; //Obtener datos de la consulta
    
    //Registar compra
    public boolean registerPurchaseQuery(int supplier_id, int employee_id, double total){
        String query = "INSERT INTO purchases (spplier_id, employee_id, total, created)"
                + "VALUES(?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst=conn.prepareStatement(query);
            pst.setInt(1, supplier_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insetar la compra");
            return false;
        }
    }
    
    //Registrar detalles de la compra
    public boolean registerPurchaseDetailQuery(int purchase_id, double purchase_price, int purchase_amount,
                                                double purchase_subtotal, int product_id){
            
        String query = "INSERT INTO purchase_details (purchase_id, purchase_price, purchase_amount, "
        + "purchase_subtotal, purchase_date, purchase_id VALUES (?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, purchase_id);
            pst.setDouble(2, purchase_price);
            pst.setInt(3, purchase_amount);
            pst.setDouble(4, purchase_subtotal);
            pst.setTimestamp(5, datetime);
            pst.setInt(6,product_id);
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar los detalles de la compra ");
            return false;
        }
    
    }
    
    //Optener ID de la compra
    public int purchaseId(){
        int id = 0; //para el if
        String query = "SELECT MAX(id) FROM purchases"; // para encontrar el id mayor
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id"); // asigno el valor del maximo id en mi var id
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return id;
    }
    
}
