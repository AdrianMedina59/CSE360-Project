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
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    	//root is set to the .fxml file which houses the buttons and text fields
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(root,Color.GOLD);
        //setting the login css 
        loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}