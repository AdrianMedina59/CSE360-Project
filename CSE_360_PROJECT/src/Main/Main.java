package Main;
import ConfirmLogin.*;
import LoginPage.*;


public class Main{
    public static void main(String[] args) {
    
    	LoginHandler loginPage = new LoginHandler();
    	loginPage.main(args);
    	
//        ConfirmLoginHandler confirmLogin = new ConfirmLoginHandler();
//        
//        confirmLogin.InitalizeConfirmLogin(args);  // Calls the method to launch the JavaFX login screen
    	
    }
}
