package application;

import java.io.FileNotFoundException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login_Button_Controller {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField Username_textField;
    @FXML
    private PasswordField Password_textField;
    @FXML
    private Label feedbackLabel;
    
    // Programmatic password confirmation field
    private PasswordField confirmPasswordField = new PasswordField();
    
    private boolean isPasswordConfirmed = false;

    @FXML
    public void Login() {
        if (!isPasswordConfirmed) {
            // First entry, check username and password
            if (Username_textField.getText().isEmpty() && Password_textField.getText().isEmpty()) {
                feedbackLabel.setText("Please enter valid Username and Password.");
                return;
            } else if (Username_textField.getText().isEmpty()) {
                feedbackLabel.setText("Please enter a valid username.");
                return;
            } else if (Password_textField.getText().isEmpty()) {
                feedbackLabel.setText("Please enter a password.");
                return;
            }

            // Change labels to indicate confirmation step
            usernameLabel.setText("Password:");
            passwordLabel.setText("Confirm Password:");
            feedbackLabel.setText("Please reconfirm your password.");
            
            // Replace Username_textField with confirmPasswordField for password confirmation
            confirmPasswordField.setLayoutX(Username_textField.getLayoutX());
            confirmPasswordField.setLayoutY(Username_textField.getLayoutY());
            confirmPasswordField.setPrefWidth(Username_textField.getPrefWidth());
            AnchorPane parent = (AnchorPane) Username_textField.getParent(); // Assumes AnchorPane as parent layout
            parent.getChildren().remove(Username_textField); // Remove the original text field
            parent.getChildren().add(confirmPasswordField); // Add the password field in its place

            // Clear the original password field for the confirm password entry
            Password_textField.clear(); // Clear Password field if needed

            isPasswordConfirmed = true; // Set the flag to indicate password confirmation step
            return;
        }

        // Second entry (confirm password)
        String password = Password_textField.getText(); // This is the original password
        String confirmPassword = confirmPasswordField.getText(); // Now using the confirmPasswordField

        if (password.equals(confirmPassword)) {
            // Passwords match, proceed to the next GUI
            openNextGUI();
        } else {
            // Passwords do not match
            feedbackLabel.setText("Mismatched password.");
            confirmPasswordField.clear(); // Clear the confirm password field for re-entry 
        }
    }

    private void openNextGUI() {
        try {
            URL resource = getClass().getResource("Login.fxml"); // Adjust if needed based on your package structure
            if (resource == null) {
                throw new FileNotFoundException("FXML file not found: Login.fxml");
            }
            AnchorPane root = (AnchorPane) FXMLLoader.load(resource);
            Stage stage = (Stage) Password_textField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "Failed to load the next GUI: " + e.getMessage());
            alert.showAndWait();
        }
    }
}