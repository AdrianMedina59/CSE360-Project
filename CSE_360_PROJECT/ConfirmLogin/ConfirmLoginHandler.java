/**
 * <p> Main class for continue login. </p>
 * 
 * <p> Description: Initiates the login scene and gets all the resources set up including fxml file and css file.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */
package ConfirmLogin;

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

public class ConfirmLoginHandler {
    
	   // This method will initialize the confirm login page using the current stage
    public static void initializeConfirmLogin(Stage stage) {
        try {
            // Load the FXML file for the  page
            Parent root = FXMLLoader.load(ConfirmLoginHandler.class.getResource("FinishInput.fxml"));

            // Create a new scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(ConfirmLoginHandler.class.getResource("applicationLoginFinish.css").toExternalForm());

            // Set the new scene on the existing stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
