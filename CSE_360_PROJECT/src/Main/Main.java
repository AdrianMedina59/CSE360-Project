package Main;
import ConfirmLogin.*;



public class Main{
    public static void main(String[] args) {
    
    	
        ConfirmLoginHandler confirmLogin = new ConfirmLoginHandler();
        
        confirmLogin.InitalizeConfirmLogin(args);  // Calls the method to launch the JavaFX login screen
    }
}
