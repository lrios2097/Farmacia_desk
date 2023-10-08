/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import views.SystemView;

/**
 *
 * @author luisr
 */
public class EmployeesController implements ActionListener , MouseListener, KeyListener{ //Implementamos el ActionsListener, el mouse listener es para la tabla de list employees
    //El Keylistener es para saber que esta escribiendo la persona en el buscador
    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    //Rol
    String rol = rol_user; // Se importa el rol_user de la class EmployeeDao
    DefaultTableModel model = new DefaultTableModel(); // para poder interactuar con la tabla de buscar employee

    public EmployeesController(Employees employee, EmployeesDao employeeDao, SystemView views) {
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.views = views;
        //boton de registar Empleados
        this.views.btnRegisterEmployee.addActionListener(this);
        //boton de modificar empleados
        this.views.btnUpdateEmployee.addActionListener(this);
        //boton de eliminar empleado
        this.views.btnDeleteEmployee.addActionListener(this);
        //boton a cancelar
        this.views.btnCancellEmployee.addActionListener(this);
        //boton de cambiar contraseña
        this.views.btnModifyData.addActionListener(this);
        //colocar label en escucha
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.EmployeesTable.addMouseListener(this); // esto es para la tabla, mostrar lista de empleados
        this.views.txtSearchEmployee.addKeyListener(this); // para que funcione el buscar
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btnRegisterEmployee){
            //verificar si los campos estan vacios
            if(views.txtEmployeeId.getText().equals("")
                    || views.txtEmployeeFullName.getText().equals("")
                    || views.txtEmployeeUserName.getText().equals("")
                    || views.txtEmployeeAddress.getText().equals("")
                    || views.txtEmployeeTelephone.getText().equals("")
                    || views.txtEmployeeEmail.getText().equals("")
                    || views.cmbEmployeeRol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txtEmployeePassword.getPassword()).equals("")){
                
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                //Realizar la insercion
                employee.setId(Integer.parseInt(views.txtEmployeeId.getText().trim()));
                employee.setFull_name(views.txtEmployeeFullName.getText().trim());
                employee.setUsername(views.txtEmployeeUserName.getText().trim());
                employee.setAddress(views.txtEmployeeAddress.getText().trim());
                employee.setTelephone(views.txtEmployeeTelephone.getText().trim());
                employee.setEmail(views.txtEmployeeEmail.getText().trim());
                employee.setPassword(String.valueOf(views.txtEmployeePassword.getPassword()));
                employee.setRol(views.cmbEmployeeRol.getSelectedItem().toString());
                
                if(employeeDao.registerEmployeeQuery(employee)){
                    cleanTable();
                    cleanFields();
                    listAllEmployess();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al empleado");
                }
            }     
        }else if(e.getSource() == views.btnUpdateEmployee){
                    if(views.txtEmployeeId.equals("")){
                        JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
                    }else{
                        //verificar si los campos estan vacios
                        if(views.txtEmployeeId.getText().equals("")
                                || views.txtEmployeeFullName.getText().equals("")
                                || views.cmbEmployeeRol.getSelectedItem().toString().equals("")){
                            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                        }else{
                            //enviar info que el usuario ha escrito
                            employee.setId(Integer.parseInt(views.txtEmployeeId.getText().trim()));
                            employee.setFull_name(views.txtEmployeeFullName.getText().trim());
                            employee.setUsername(views.txtEmployeeUserName.getText().trim());
                            employee.setAddress(views.txtEmployeeAddress.getText().trim());
                            employee.setTelephone(views.txtEmployeeTelephone.getText().trim());
                            employee.setEmail(views.txtEmployeeEmail.getText().trim());
                            employee.setPassword(String.valueOf(views.txtEmployeePassword.getPassword()));
                            employee.setRol(views.cmbEmployeeRol.getSelectedItem().toString());
                            
                            if(employeeDao.updateEmployeeQuery(employee)){
                                cleanTable();
                                cleanFields();
                                listAllEmployess();
                                views.btnRegisterEmployee.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Datos del empleado modificados con éxito");
                            }else{
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el empleado");
                            }
                        }
                    }
        }else if (e.getSource() == views.btnDeleteEmployee){
            int row = views.EmployeesTable.getSelectedRow(); // para seleccionar el elemento a eliminar de la tabla
            
            if(row == -1){ // el usuario no ha seleccionado nada en la tabla
                JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminar");
            }else if (views.EmployeesTable.getValueAt(row,0).equals(id_user)){//Es la var de employeesDao, tenemos que importar para que no salga error
                JOptionPane.showMessageDialog(null, "No puede eliminar al usuario autenticado");
            }else{
                int id = Integer.parseInt(views.EmployeesTable.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Desea eliminar a este empleado ?"); //confirm dialog para darle en aceptar
                
                    if(question == 0 && employeeDao.deleteEmployeeQUery(id) !=false){
                        cleanTable();
                        cleanFields();
                        views.btnRegisterEmployee.setEnabled(true);
                        views.txtEmployeePassword.setEnabled(true);
                        listAllEmployess();
                        JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
                    }
            }
        } else if(e.getSource() == views.btnCancellEmployee){
            cleanFields();
            views.btnRegisterEmployee.setEnabled(true);
            views.txtEmployeePassword.setEnabled(true);
            views.txtEmployeeId.setEnabled(true);
        } else if (e.getSource() == views.btnModifyData){ //e de actionEvent, estoy validando si se hizo click
            //Recolectar info de las cajas password
            String password = String.valueOf(views.txtPasswordModify.getPassword());
            String confirm_password = String.valueOf(views.txtPasswordModifyConfirm.getPassword());
            // Verificar que las cajas de texto no esten vacias
            if(!password.equals("") && !confirm_password.equals("")){
                // Verificar que las contraseñas sean iguales
                if(password.equals(confirm_password)){
                    employee.setPassword(String.valueOf(views.txtPasswordModify.getPassword()));
                    
                    if(employeeDao.UpdateEmployeePassword(employee) !=false){
                    JOptionPane.showMessageDialog(null, "Contraseña modificada con éxito");
                    }else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña");    
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }
            
        }
    
    }
    //Listar todos los empleados
    public void listAllEmployess(){
        if(rol.equals("Administrador")){
            List<Employees> list = employeeDao.listEmployeesQuery(views.txtSearchEmployee.getText());
            model = (DefaultTableModel) views.EmployeesTable.getModel(); //// casteamos con el ide //model = views.EmployeesTable.getModel();
            Object[] row = new Object[7]; // 7 poruq tengo 7 columnas
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();  
                model.addRow(row);
            }
        } 
    }

    @Override
    public void mouseClicked(MouseEvent e) { //Borramos los comentarios de todos los metodos de la interfaz
        if (e.getSource() == views.EmployeesTable) {
            int row = views.EmployeesTable.rowAtPoint(e.getPoint()); // para saber en que fila se hizo clic
            views.txtEmployeeId.setText(views.EmployeesTable.getValueAt(row, 0).toString());
            views.txtEmployeeFullName.setText(views.EmployeesTable.getValueAt(row,1).toString());
            views.txtEmployeeUserName.setText(views.EmployeesTable.getValueAt(row,2).toString());
            views.txtEmployeeAddress.setText(views.EmployeesTable.getValueAt(row,3).toString());
            views.txtEmployeeTelephone.setText(views.EmployeesTable.getValueAt(row,4).toString());
            views.txtEmployeeEmail.setText(views.EmployeesTable.getValueAt(row,5).toString());
            views.cmbEmployeeRol.setSelectedItem(views.EmployeesTable.getValueAt(row,6).toString());
            
            //Deshabilitar
            views.txtEmployeeId.setEditable(false);
            views.txtEmployeePassword.setEnabled(false);
            views.btnRegisterEmployee.setEnabled(false); 
        }else if (e.getSource() == views.jLabelEmployees){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(4); 
                cleanTable();
                cleanFields();
                listAllEmployess();
            }else{
                views.jTabbedPane1.setEnabledAt(4, false);
                views.jLabelEmployees.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
            }
            
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) { // para escuchar el buscador
        if(e.getSource() == views.txtSearchEmployee){ // si hacemos clic en el buscador
            cleanTable(); // antes de 
            listAllEmployess();
        }
    }
    
    //Limpiar campos
    public void cleanFields(){
        views.txtEmployeeId.setText("");
        views.txtEmployeeId.setEditable(true);
        views.txtEmployeeFullName.setText("");
        views.txtEmployeeUserName.setText("");
        views.txtEmployeeAddress.setText("");
        views.txtEmployeeTelephone.setText("");
        views.txtEmployeeEmail.setText("");
        views.txtEmployeePassword.setText("");
        views.cmbEmployeeRol.setSelectedItem(0);
        
    }
    
    public void cleanTable(){
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i= i-1;
        }
    }
    
}
