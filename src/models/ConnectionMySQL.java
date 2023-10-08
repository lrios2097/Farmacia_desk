/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author luisr
 */
import java.sql.Connection; // Importamos para que salga error de la Connection
import java.sql.DriverManager; //de fotma manual
import java.sql.SQLException; //de forma manual
public class ConnectionMySQL {
    private String database_name= "pharmacy_database";
    private String user = "root";
    private String password = "123456";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null;
    
    public Connection getConnection(){
        try {
            //Obtener valor del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Obtener Conexion
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Ha ocurrido un ClassNotFoundException: " + e.getMessage());
        }catch(SQLException e){
            System.err.println("Ha ocurrido un SQLException: " + e.getMessage());
        }
        return conn;
    }
}
