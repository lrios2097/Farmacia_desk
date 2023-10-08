
package main;

import views.LoginView;

/**
 *
 * @author luisr
 */
public class main {
    public static void main(String[] args) {
        //instanciar el login para que se ejecute primero
        LoginView login = new LoginView();
        login.setVisible(true);
    }
    
}
