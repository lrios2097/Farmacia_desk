
package main;

import views.loginView;

/**
 *
 * @author luisr
 */
public class main {
    public static void main(String[] args) {
        //instanciar el login para que se ejecute primero
        loginView login = new loginView();
        login.setVisible(true);
    }
    
}
