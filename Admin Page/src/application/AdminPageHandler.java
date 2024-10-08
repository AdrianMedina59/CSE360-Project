package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class AdminPageHandler {

    // This method will initialize the admin page using the current stage
    public static void initializeAdminPage(Stage stage) {
        try {
            // Load the FXML file for the admin page
            Parent root = FXMLLoader.load(AdminPageHandler.class.getResource("/GUI.fxml"));

            // Create a new scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(AdminPageHandler.class.getResource("/application.css").toExternalForm());

            // Set the new scene on the existing stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}