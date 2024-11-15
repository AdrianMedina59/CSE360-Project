/**
 * <p> Main class for admin scene. </p>
 * 
 * <p> Description: Initiates the Admin scene and gets all the resources set up including fxml file and css file.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package admin_Instructor;
import java.io.IOException;

import ConfirmLogin.*;	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class AdminInstructorHandler extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Admin_Instructor_Page.fxml"));
            Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		launch(args);
	}
    
  
}
