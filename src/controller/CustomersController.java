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
import models.Customers;
import models.CustomersDao;
import views.SystemView;

/**
 *
 * @author luisr
 */
public class CustomersController implements ActionListener, MouseListener, KeyListener{
    private Customers customers;
    private CustomersDao customersDao;
    private SystemView views;
    
    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers customers, CustomersDao customersDao, SystemView views) {
        this.customers = customers;
        this.customersDao = customersDao;
        this.views = views;
        //Boton de registrar cliente
        this.views.btnRegisterCustomer.addActionListener(this);
        //Boton de modificar cliente
        this.views.btnUpdateCustomer.addActionListener(this);
        //Boton de eliminar cliente
        this.views.btnDeleteCustomer.addActionListener(this);
        //Boton de cancelar
        this.views.btnCancelCustomer.addActionListener(this);
        //Buscador
        this.views.txtSearchCustomer.addKeyListener(this);
        //Jlabel de la parte lateral izquierda de la views
        this.views.jLabelCustomers.addMouseListener(this);
        //tabla click
        this.views.CustomersTable.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Aqui programamos el controller
        if (e.getSource()== views.btnRegisterCustomer){
            if(views.txtCustomerId.getText().equals("")
                    || views.txtCustomerFullName.getText().equals("")
                    || views.txtCustomerAddress.getText().equals("")
                    || views.txtCustomerTelephone.getText().equals("")
                    || views.txtCustomerEmail.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                customers.setId(Integer.parseInt(views.txtCustomerId.getText().trim()));
                customers.setFull_name(views.txtCustomerFullName.getText().trim());
                customers.setAddress(views.txtCustomerAddress.getText().trim());
                customers.setTelephone(views.txtCustomerTelephone.getText().trim());
                customers.setEmail(views.txtCustomerEmail.getText().trim());
                
                if(customersDao.registerCustomersQuery(customers)){
                    cleanTable();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente registrado con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el Cliente");
                }
            }
        } else if (e.getSource() == views.btnUpdateCustomer){
            if(views.txtCustomerId.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar");
            }else{
                if(views.txtCustomerId.getText().equals("") ||
                   views.txtCustomerFullName.getText().equals("") ||
                   views.txtCustomerAddress.getText().equals("") ||
                   views.txtCustomerTelephone.getText().equals("") ||
                   views.txtCustomerEmail.getText().equals("")){
                   JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                }else{
                   customers.setId(Integer.parseInt(views.txtCustomerId.getText().trim()));
                    customers.setFull_name(views.txtCustomerFullName.getText().trim());
                    customers.setAddress(views.txtCustomerAddress.getText().trim());
                    customers.setTelephone(views.txtCustomerTelephone.getText().trim());
                    customers.setEmail(views.txtCustomerEmail.getText().trim()); 
                    
                    if(customersDao.updateCustomersQuery(customers)){
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        JOptionPane.showMessageDialog(null, "Datos del cliente modificados con éxito");
                    }else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del cliente");
                    }
                }
            }
        }else if(e.getSource() == views.btnDeleteCustomer){
            int row = views.CustomersTable.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente para eliminar");
            }else{
                int id = Integer.parseInt(views.CustomersTable.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar a este Cliente ?"); //confirm dialog para darle en aceptar
                
                    if(question == 0 && customersDao.deleteCustomerQuery(id) !=false){
                        cleanTable();
                        cleanFields();
                        views.btnRegisterCustomer.setEnabled(true);
                        
                        listAllCustomers();
                        JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito");
                    }
            }
            
        } else if (e.getSource() == views.btnCancelCustomer){
            views.btnRegisterCategory.setEnabled(true);
            cleanFields();
        }
    }
    
    //Listar Customers
    public void listAllCustomers(){
        List<Customers> list = customersDao.listCustomerQuery(views.txtSearchCustomer.getText());
        model = (DefaultTableModel) views.CustomersTable.getModel();
        
        Object[] row = new Object[5]; //columnas 5
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }
        views.CustomersTable.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.CustomersTable) {
            int row = views.CustomersTable.rowAtPoint(e.getPoint()); // para tener las coordenadas del customer seleccionado en la tabla
            views.txtCustomerId.setText(views.CustomersTable.getValueAt(row, 0).toString());
            views.txtCustomerFullName.setText(views.CustomersTable.getValueAt(row, 1).toString());
            views.txtCustomerAddress.setText(views.CustomersTable.getValueAt(row, 2).toString());
            views.txtCustomerTelephone.setText(views.CustomersTable.getValueAt(row, 3).toString());
            views.txtCustomerEmail.setText(views.CustomersTable.getValueAt(row, 4).toString());
            //Deshabilitar botones
            views.btnRegisterCustomer.setEnabled(false);
            views.txtCustomerId.setEditable(false);
        }else if (e.getSource() == views.jLabelCustomers){
            views.jTabbedPane1.setSelectedIndex(3);
            cleanTable();
            cleanFields();
            listAllCustomers();
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
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtSearchCustomer) {
            //limpiarTabla
            cleanTable();
            //Listar clientes
            listAllCustomers();
        }
    }
    
    public void cleanTable(){
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i= i-1;
        }
    }
    
    public void cleanFields(){
        views.txtCustomerId.setText("");
        views.txtCustomerId.setEditable(true);
        views.txtCustomerFullName.setText("");
        views.txtCustomerAddress.setText("");
        views.txtCustomerTelephone.setText("");
        views.txtCustomerEmail.setText("");
    }
    
    
}
