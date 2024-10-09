package LoginPage;
import ConfirmLogin.*;
import StudentPage.StudentPageHandler;
import AdminPage.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AdminPage.AdminPageHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login_Button_Controller {

	@FXML
    private Label titleLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField Username_textField,Reg_Username_TextField;
    @FXML
    private PasswordField Password_textField,Reg_Password_TextField,Reg_confirmPassword_TextField;
    @FXML
    private Label feedbackLabel;
    
     String password,username;
    
    // Programmatic password confirmation field
    private PasswordField confirmPasswordField = new PasswordField();
    
    private boolean isPasswordConfirmed = false;

    private DataBaseHelper dataBase = new DataBaseHelper();
   
    public void Login() throws SQLException {
    	
       checkLogin();
       
    }
    
    public void Register() throws SQLException
    {
    	dataBase.connectToDatabase();
    	username = Reg_Username_TextField.getText();
        password = Reg_Password_TextField.getText();
    	String confirmPassword = Reg_confirmPassword_TextField.getText();
    	
    	
    	
    	if(!password.equals(confirmPassword))
    	{
    		System.out.println("Passwords do not match!");
    		return;
    	}
    	if(dataBase.isValidUser(username, password))
    	{
    		System.out.println("User already exists!. Login!");
    		return;
    	}
    	
    	LoadConfirmLogin();
    	dataBase.closeConnection();
    }
   
    private void LoadConfirmLogin() {
        try {
            // Load the FXML file for the Confirm Login scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmLogin/FinishInput.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the FXML
            LoginConfirmController loginController = loader.getController();

            // Pass the username and password to the controller
            loginController.setUsernameAndPassword(username, password);

            // Initialize and display the new Confirm Login scene
            Stage stage = (Stage) titleLabel.getScene().getWindow();
            Scene confirmLoginScene = new Scene(root);

            // Add the CSS file to the scene
            confirmLoginScene.getStylesheets().add(getClass().getResource("/ConfirmLogin/applicationLoginFinish.css").toExternalForm());

            // Set the scene and show the stage
            stage.setScene(confirmLoginScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method that checks login and password 2nd entry
    
    
    public void checkLogin() throws SQLException
    {
    	dataBase.connectToDatabase();
    	dataBase.PrintUserTables();
    	username = Username_textField.getText();
        password = Password_textField.getText();
        
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        
        try (PreparedStatement pstmt = dataBase.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet resultSet = pstmt.executeQuery();
            
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                navigateToPageBasedOnRole(role);
            } else {
                // Username or password is incorrect
                feedbackLabel.setText("Invalid username or password");
                Alert alert = new Alert(AlertType.ERROR, "Invalid username or password");
                alert.showAndWait();
            }
        }
        dataBase.closeConnection();
    }
   
  private void navigateToPageBasedOnRole(String role) {
		if("Admin".equals(role)) {
			Stage stage = (Stage) titleLabel.getScene().getWindow();
            AdminPageHandler.initializeAdminPage(stage);
		}else if("Student".equals(role))
		{
			Stage stage = (Stage) titleLabel.getScene().getWindow();
            StudentPageHandler.initializeStudentPage(stage);
		}
		
	}
  private void LoadAdminPage() {
      try {
          // Load the FXML file for the Confirm Login scene
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage/GUI.fxml"));
          Parent root = loader.load();

          // Get the controller associated with the FXML
           AdminPageController loginController = loader.getController();

          
          // Initialize and display the new Confirm Login scene
          Stage stage = (Stage) titleLabel.getScene().getWindow();
          Scene confirmLoginScene = new Scene(root);

          // Add the CSS file to the scene
          confirmLoginScene.getStylesheets().add(getClass().getResource("/AdminPage/application.css").toExternalForm());

          // Set the scene and show the stage
          stage.setScene(confirmLoginScene);
          stage.show();

      } catch (IOException e) {
          e.printStackTrace();
      }
  }
	//checks if the email is already in data base
  	private boolean IsUserNameExists(String username) throws SQLException{
  		String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
  		try(PreparedStatement pstmt = dataBase.getConnection().prepareStatement(query))
  				{
  					pstmt.setString(1, username);
  					ResultSet resultSet = pstmt.executeQuery();
  					if(resultSet.next())
  					{
  						return resultSet.getInt("count") > 0;
  					}
  				}
  				return false;
  			}

   
}