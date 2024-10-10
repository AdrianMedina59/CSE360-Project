
/**
 * <p> Main Class. </p>
 * 
 * <p> Description: This class starts the application.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */
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
