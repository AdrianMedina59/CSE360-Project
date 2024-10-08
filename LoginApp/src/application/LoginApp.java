package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the layout
        GridPane grid = new GridPane();

        // Create the username label and text field
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        // Create the password label and password field
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        // Create the login button
        Button loginButton = new Button("Login");

        // Create a label for feedback messages
        Label feedbackLabel = new Label();

        // Add elements to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(feedbackLabel, 1, 3); // Add feedback label below the button

        // Set action for the login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (username.isEmpty() && password.isEmpty()) {
                feedbackLabel.setText("Please enter username and password.");
            } else if (username.isEmpty()) {
                feedbackLabel.setText("Please enter username.");
            } else if (password.isEmpty()) {
                feedbackLabel.setText("Please enter password.");
            } else {
                // Both fields have input, create a new blank GUI
                createNewWindow();
            }
        });

        // Create a scene with the grid
        Scene scene = new Scene(grid, 300, 200);

        // Set the stage
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create a new blank GUI window
    private void createNewWindow() {
        Stage newStage = new Stage();
        GridPane newLayout = new GridPane();
        Scene newScene = new Scene(newLayout, 400, 300);
        newStage.setTitle("New Window");
        newStage.setScene(newScene);
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
