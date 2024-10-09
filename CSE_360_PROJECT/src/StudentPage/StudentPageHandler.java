package StudentPage;
	
import java.io.IOException;

import AdminPage.AdminPageHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class StudentPageHandler  {
	
	public static void initializeStudentPage(Stage stage) {
        try {
            // Load the FXML file for the admin page
            Parent root = FXMLLoader.load(StudentPageHandler.class.getResource("Student.fxml"));

            // Create a new scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(StudentPageHandler.class.getResource("application.css").toExternalForm());

            // Set the new scene on the existing stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
