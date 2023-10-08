/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author luisr
 */
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Esta clase se ponene todos los metodos permiten la conexion de java con mysql
public class EmployeesDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL(); // instancio la conexion
    Connection conn;
    PreparedStatement pst; //Consultas
    ResultSet rs; //Obtener datos de la consulta
    
    //Variables para enviar datos entre interfaces, todo menos el password
    public static int id_user = 0; // estan en static para poder enviar datos entre interfaces
    public static String full_name_user = "";
    public static String username_user = "";
    public static String addres_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";
    
    //Método Login pero de la clase Employees
    public Employees loginQuery(String user, String password){
        String query = "SELECT * FROM employees WHERE username=? AND password=?";
        Employees employee = new Employees();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query); // copn confundir con preparedStatement
            //Enviar parámetros
            pst.setString(1, user); // los signos de ?
            pst.setString(2, password); // ?
            rs = pst.executeQuery();
            
            if(rs.next()){ // si existe
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                employee.setFull_name(rs.getString("full_name"));
                System.out.println("ID del usuario: " + id_user);
                full_name_user = employee.getFull_name();
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();
                employee.setAddress(rs.getString("address"));
                addres_user = employee.getAddress();
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener al empleado " + e);
            e.printStackTrace();
        }
        return employee;
    }
    
    //Registrar Empleado
    public boolean registerEmployeeQuery(Employees employee){
        String query = "INSERT INTO employees(id, full_name, username, address, telephone, email, password, rol, created,"+
                "updated) VALUES(?,?,?,?,?,?,?,?,?,?)";
        // esto es para created y updated
        Timestamp datetime = new Timestamp(new Date().getTime()); //UsamosTomestamp de SQL, no olvidar que se necesita importar manualmente las de sql, revisar arriba
        
        try {
            conn = cn.getConnection();
            pst= conn.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado " +e);
            return false;
        }
    }
    //Listar Empleado
    public List listEmployeesQuery (String value) {
        List<Employees> list_employees = new ArrayList();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query_search_employees = "SELECT *FROM employees WHERE id LIKE '%"+ value + "%'";
        
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_employees);
                rs = pst.executeQuery();
            }
        // va a recorrer siempre que hayan registros
        while(rs.next()){
            Employees employee = new Employees();
            employee.setId(rs.getInt("id"));
            employee.setFull_name(rs.getString("full_name"));
            employee.setUsername(rs.getString("username"));
            employee.setAddress(rs.getString("address"));
            employee.setTelephone(rs.getString("telephone"));
            employee.setEmail(rs.getString("email"));
            employee.setRol(rs.getString("rol"));
            list_employees.add(employee); // agregamos cada 1 de los empleados a la lisa 
        }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }
    
    //ModificarEmpleado
    public boolean updateEmployeeQuery(Employees employee){
        String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ?"
                + "where ID=?";
        // esto es para created y updated
        Timestamp datetime = new Timestamp(new Date().getTime()); //UsamosTomestamp de SQL, no olvidar que se necesita importar manualmente las de sql, revisar arriba
        
        try {
            conn = cn.getConnection();
            pst= conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, employee.getId());
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado " +e);
            return false;
        }
    }
    
    //Eliminar Empleado
    public boolean deleteEmployeeQUery(int id){
        String query = "DELETE FROM employees WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar un empleado que tenga relacion con otra tanla" + e);
            return false;
        }
    }
    
    //Cambiar contraseña
    public boolean UpdateEmployeePassword(Employees employee){
        String query = "UPDATE employees SET password = ? WHERE username = '" + username_user + "'"; // username_user, lo llenamos en el metodo login, para usarlo luego
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un erro en el cambio de contraseña" + e);
            return false;
        }
    }
    
}
