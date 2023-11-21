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
import static models.EmployeesDao.rol_user;
import models.Suppliers;
import models.SuppliersDao;
import views.SystemView;

/**
 *
 * @author luisr
 */
public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private SystemView views;
    String rol = rol_user; // para que el rol

    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, SystemView views) {
        this.supplier = supplier;
        this.supplierDao = supplierDao;
        this.views = views;
        //Botón de registrar proveedor
        this.views.btnRegisterSupplier.addActionListener(this);
        //Botón de modificar proveedores
        this.views.btnUpdateSupplier.addActionListener(this);
        this.views.suppliersTable.addMouseListener(this);
        this.views.txtSearchSupplier.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegisterSupplier) {
            if (views.txtSupplierName.getText().equals("")
                    || views.txtSupplierDescription.getText().equals("")
                    || views.txtSupplierAddress.getText().equals("")
                    || views.txtSupplierTelephone.getText().equals("")
                    || views.txtSupplierEmail.getText().equals("")
                    || views.cmbSupplierCity.getSelectedItem().toString().equals("")) { // no esta el id porque es autoincremntable
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //Realizar inserción
                supplier.setName(views.txtSupplierName.getText().trim());
                supplier.setDescription(views.txtSupplierDescription.getText().trim());
                supplier.setAddress(views.txtSupplierAddress.getText().trim());
                supplier.setTelephone(views.txtSupplierTelephone.getText().trim());
                supplier.setEmail(views.txtSupplierEmail.getText().trim());
                supplier.setCity(views.cmbSupplierCity.getSelectedItem().toString());

                if (supplierDao.registerSupplierQuery(supplier)) {
                    cleanTable();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor registrado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el proveedor");
                }
            }
        } else if (e.getSource() == views.btnUpdateSupplier) {
            if (views.txtSupplierId.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txtSupplierName.getText().equals("")
                        || views.txtSupplierAddress.getText().equals("")
                        || views.txtSupplierTelephone.getText().equals("")
                        || views.txtSupplierEmail.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    supplier.setName(views.txtSupplierName.getText().trim());
                    supplier.setDescription(views.txtSupplierDescription.getText().trim());
                    supplier.setAddress(views.txtSupplierAddress.getText().trim());
                    supplier.setTelephone(views.txtSupplierTelephone.getText().trim());
                    supplier.setEmail(views.txtSupplierEmail.getText().trim());
                    supplier.setCity(views.cmbSupplierCity.getSelectedItem().toString());
                    supplier.setId(Integer.parseInt(views.txtSupplierId.getText()));
                    
                    if (supplierDao.updateSupplierQuery(supplier)) {
                        cleanTable();
                        cleanFields();
                        listAllSuppliers();
                        JOptionPane.showMessageDialog(null, "Datos del Proveedor modificados con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un erro al modificar los datos del Proveedor");
                    }
                }
            }
        }
    }

    //Listar Proveedores
    public void listAllSuppliers() {
        if (rol.equals("Administrador")) {
            List<Suppliers> list = supplierDao.listSuppliersQuery(views.txtSearchSupplier.getText());
            model = (DefaultTableModel) views.suppliersTable.getModel(); //// casteamos con el ide //model = views.EmployeesTable.getModel();
            Object[] row = new Object[7]; // 7 poruq tengo 7 columnas
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
            views.suppliersTable.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.suppliersTable) {
            int row = views.suppliersTable.rowAtPoint(e.getPoint());
            views.txtSupplierId.setText(views.suppliersTable.getValueAt(row, 0).toString());
            views.txtSupplierName.setText(views.suppliersTable.getValueAt(row, 1).toString());
            views.txtSupplierDescription.setText(views.suppliersTable.getValueAt(row, 2).toString());
            views.txtSupplierAddress.setText(views.suppliersTable.getValueAt(row, 3).toString());
            views.txtSupplierTelephone.setText(views.suppliersTable.getValueAt(row, 4).toString());
            views.txtSupplierEmail.setText(views.suppliersTable.getValueAt(row, 5).toString());
            views.cmbSupplierCity.setSelectedItem(views.suppliersTable.getValueAt(row, 6).toString());
            //Deshabilitar btn
            views.btnRegisterSupplier.setEnabled(false);
            views.txtSupplierId.setEditable(false);
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
        if (e.getSource() == views.txtSearchSupplier) {
            //Limpiar tabla
            cleanTable();
            //listar proveedor
            listAllSuppliers();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txtSupplierId.setText("");
        views.txtSupplierId.setEditable(true);
        views.txtSupplierName.setText("");
        views.txtSupplierDescription.setText("");
        views.txtSupplierAddress.setText("");
        views.txtSupplierTelephone.setText("");
        views.txtSupplierEmail.setText("");
        views.cmbSupplierCity.setSelectedIndex(0);
    }

}
