/**
 * <p> Main class for Login page. </p>
 * 
 * <p> Description: The class loads all the FXML files and css files used for scene.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package LoginPage; // Replace with the correct package name if needed

import java.io.IOException;

import AdminPage.AdminPageHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginHandler extends Application {
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(root, Color.GOLD);
        loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static String verifyLogin(String username, String password) {
        if (username.isEmpty()) {
            return "Username cannot be empty";
        }
        if (password.isEmpty()) {
            return "Password cannot be empty";
        }
        if (username.length() < 4) {
            return "Username must be at least 4 characters long";
        }
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            return "Username must contain only letters and numbers";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }
        if (!username.equals(VALID_USERNAME) || !password.equals(VALID_PASSWORD)) {
            return "Invalid username or password";
        }
        return "";
    }

    public static void main(String[] args) {
    	
        launch(args);
        
    }
}