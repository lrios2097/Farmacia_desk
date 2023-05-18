/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.systemView;

/**
 *
 * @author luisr
 */
public class settingsController implements MouseListener{
    
    private systemView views;
    //Constructor de esta clase
    public settingsController(systemView views){
        this.views = views;
        this.views.jLabelProduct.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelShop.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployers.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);   
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //si la persona esta sobre el menu, cambiara de color
        if (e.getSource() == views.jLabelProduct) {
            views.jPanelProduct.setBackground(new Color(152, 202, 63));
        }else if (e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelShop){
            views.jPanelShop.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelEmployers){
            views.jPanelEmployers.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelReports){
            views.jPanelReports1.setBackground(new Color (152, 202, 63));
        }else if (e.getSource() == views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color (152, 202, 63));
        }
                
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.jLabelProduct) {
            views.jPanelProduct.setBackground(new Color(18, 45, 61));
        }else if (e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelShop){
            views.jPanelShop.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelEmployers){
            views.jPanelEmployers.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelReports){
            views.jPanelReports1.setBackground(new Color (18, 45, 61));
        }else if (e.getSource() == views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color (18, 45, 61));
        }
    }
    
}
