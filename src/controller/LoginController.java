/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import views.LoginView;
import views.SystemView;

/**
 *
 * @author luisr
 */
public class LoginController implements ActionListener {

    private Employees employees;
    private EmployeesDao employees_dao;
    private LoginView login_view;

    public LoginController(Employees employees, EmployeesDao employees_dao, LoginView login_view) {
        this.employees = employees;
        this.employees_dao = employees_dao;
        this.login_view = login_view;
        this.login_view.btnIngresar.addActionListener(this);
    }

    //sirve para escuchar cuando se realiza una accion en un elemento de la interfaz
    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtener los datos de la vista
        String user = login_view.txtUserName.getText().trim(); //.trim() es para eliminar espacion
        String pass = String.valueOf(login_view.txtPassword.getPassword());

        if (e.getSource() == login_view.btnIngresar) {
            System.out.println("Botón Ingresar presionado."); 
            // validar que los campos no esten vacios 
            if (!user.equals("") || !pass.equals("")) {
                System.out.println("Campos no están vacíos.");
                //pasar los parametros al metodo login
                employees = employees_dao.loginQuery(user, pass);
                //Verificar la existencia del usuario
                System.out.println("Valor de getUsername(): " + employees.getUsername());
                if (employees.getUsername() != null){ 
                    System.out.println("Usuario autenticado.");
                    if (employees.getRol().equals("Administrador")) {
                        System.out.println("Usuario autenticado.");
                        SystemView admin = new SystemView(); //Systemview es la clase de views que a la vez es tratada como un obj
                        admin.setVisible(true);
                    } else {
                        SystemView aux = new SystemView(); //Systemview es la clase de views que a la vez es tratada como un obj, en este caso es si tenemos 2 ventanas una de admin y otra de aux
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
                }

            }else {
                JOptionPane.showMessageDialog(null, "Los campos están vacíos");
            }

        }

    }

}
