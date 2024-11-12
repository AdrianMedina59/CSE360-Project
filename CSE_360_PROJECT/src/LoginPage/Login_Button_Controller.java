/**
 * <p> functionality class for Login controller page. </p>
 * 
 * <p> Description: The class loads houses all the functionality for the Login controller.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package LoginPage;
import ConfirmLogin.*;
import InstructorPage.*;
import StudentPage.StudentPageHandler;
import StudentPage.StudentpageController;
import admin_Instructor.Admin_Instructor_Controller;
import AdminPage.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
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
    private TextField Username_textField,Reg_Username_TextField,InviteCode_textField;
    @FXML
    private PasswordField Password_textField,Reg_Password_TextField,Reg_confirmPassword_TextField;
    @FXML
    private Label feedbackLabel;
    
     String password,username,inviteCode;
    
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
        inviteCode = InviteCode_textField.getText();
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
    	if(!dataBase.validPasscode(inviteCode) && dataBase.getNumberOfUsers() > 0)
    	{
    		System.out.println("Not valid invite code!");
    		return;
    	}
    	
    	LoadConfirmLogin();
    	dataBase.closeConnection();
    }
   public void InviteCode() throws IOException
   {
	   FXMLLoader loader = new FXMLLoader(getClass().getResource("confirmCode.fxml"));
		Parent Remove = loader.load();
		ConfirmCodeController controller = loader.getController();
		
       // Set up the new stage and scene for the user list
       Stage newStage = new Stage();
       Scene RemoveScene = new Scene(Remove);
       newStage.setTitle("Invite codes");
       newStage.setScene(RemoveScene);
       newStage.show();
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
    
    public void loadForgot() throws IOException
    {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("forgot.fxml"));
  		Parent Remove = loader.load();
  		ForgotController controller = loader.getController();
  		
         // Set up the new stage and scene for the user list
         Stage newStage = new Stage();
         Scene forgotScene = new Scene(Remove);
         newStage.setTitle("Forgot password");
         newStage.setScene(forgotScene);
         newStage.show();
    }
    
    public void checkLogin() throws SQLException
    {
    	dataBase.connectToDatabase();
    	//debug useage
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
   
    //method used to navigate to the page
  private void navigateToPageBasedOnRole(String role) throws SQLException {
		if("Admin".equals(role)) {
			LoadAdminPage();
		}else if("Student".equals(role))
		{
			loadStudentPage();
		}else if("Instructor".equals(role))
		{
			loadInstructorPage();
		}
		
	}
  
  private void loadInstructorPage() throws SQLException {
	  try {
          // Load the FXML file for the Confirm Login scene
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/InstructorPage/Instructor.fxml"));
          Parent root = loader.load();

          
          // Get the controller associated with the FXML
           InstructorPageController controller = loader.getController();
           dataBase.connectToDatabase();
           controller.SetUserLabel(dataBase.getFirstNameByUsername(username));
           controller.setUserName(dataBase.getFirstNameByUsername(username));
           dataBase.closeConnection();
           
         
          
          
          // Initialize and display the new Confirm Login scene
          Stage stage = (Stage) titleLabel.getScene().getWindow();
          Scene InstructorPage = new Scene(root);

         
          // Set the scene and show the stage
          stage.setScene(InstructorPage);
          stage.show();

      } catch (IOException e) {
          e.printStackTrace();
      }
	  
}

private void loadStudentPage()throws SQLException {
      try {
          // Load the FXML file for the Confirm Login scene
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentPage/Student.fxml"));
          Parent root = loader.load();

          
          // Get the controller associated with the FXML
           StudentpageController loginController = loader.getController();
           dataBase.connectToDatabase();
           loginController.SetUserLabel(dataBase.getFirstNameByUsername(username));
           loginController.setUserName(dataBase.getFirstNameByUsername(username));
           dataBase.closeConnection();
           
         
          
          
          // Initialize and display the new Confirm Login scene
          Stage stage = (Stage) titleLabel.getScene().getWindow();
          Scene Studentpage = new Scene(root);

          // Add the CSS file to the scene
          Studentpage.getStylesheets().add(getClass().getResource("/StudentPage/application.css").toExternalForm());

          // Set the scene and show the stage
          stage.setScene(Studentpage);
          stage.show();

      } catch (IOException e) {
          e.printStackTrace();
      }
	  
  }
  
  private void LoadAdminPage() throws SQLException {
      try {
          // Load the FXML file for the Confirm Login scene
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage/GUI.fxml"));
          Parent root = loader.load();

          
          // Get the controller associated with the FXML
           AdminPageController loginController = loader.getController();
           dataBase.connectToDatabase();
           loginController.SetUserLabel(dataBase.getFirstNameByUsername(username));
           loginController.setUserName(dataBase.getFirstNameByUsername(username));
           
           dataBase.closeConnection();
           
         
          
          
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
  
  private void loadAdminInstructorPage() throws SQLException {
	    try {
	        // Load the FXML file for the Admin Instructor page
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_Instructor/Admin_Instructor_Page.fxml"));
	        Parent root = loader.load();

	        // Get the controller associated with the FXML
	        Admin_Instructor_Controller controller = loader.getController();
	        dataBase.connectToDatabase();
	        
	        // Set the username label (or any other user-specific info)
	        controller.SetUserLabel(dataBase.getFirstNameByUsername(username));  // Assuming `getFirstNameByUsername()` is a method in your `DataBaseHelper`
	        controller.setUserName(dataBase.getFirstNameByUsername(username));
	        
	        dataBase.closeConnection();

	        // Initialize and display the new Admin Instructor page scene
	        Stage stage = (Stage) titleLabel.getScene().getWindow();
	        Scene adminInstructorScene = new Scene(root);

	        // Add the CSS file to the scene
	        adminInstructorScene.getStylesheets().add(getClass().getResource("/admin_Instructor/application.css").toExternalForm());

	        // Set the scene and show the stage
	        stage.setScene(adminInstructorScene);
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