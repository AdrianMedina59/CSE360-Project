package AdminPage;
import javafx.event.ActionEvent;
import LoginPage.*;
import java.io.IOException;

import java.sql.ResultSet;

import java.sql.SQLException;
import javafx.scene.Node;
import ConfirmLogin.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminPageController 
{
	
	@FXML
	 private Button logoutbutton;
	@FXML
	private AnchorPane scenePane;
	@FXML
    private Label TitleLabel;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	

	public void printUsers() throws SQLException
	{
		ConfirmLogin.DataBaseHelper database = new ConfirmLogin.DataBaseHelper(); //data base
		database.PrintUserTables();
	}
	
	
	 private void loadLoginPage() {
	        try {
	            // Load the FXML file for the Confirm Login scene
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage/Login.fxml"));
	            Parent root = loader.load();

	            // Get the controller associated with the FXML
	            Login_Button_Controller loginController = loader.getController();

	            // Pass the username and password to the controller
	           

	            
				// Initialize and display the new Confirm Login scene
	            Stage stage = (Stage) TitleLabel.getScene().getWindow();
	            Scene confirmLoginScene = new Scene(root);

	            // Add the CSS file to the scene
	            confirmLoginScene.getStylesheets().add(getClass().getResource("/LoginPage/application.css").toExternalForm());

	            // Set the scene and show the stage
	            stage.setScene(confirmLoginScene);
	            stage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	public void logout(ActionEvent event) throws IOException
	{

	
	Stage stage;
	
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Logout");
	alert.setHeaderText("Would you like to logout");
	alert.setContentText("Hit Ok to log out. You will return back to login!!");
			
	if(alert.showAndWait().get() == ButtonType.OK)
	{
		stage = (Stage) scenePane.getScene().getWindow();

		loadLoginPage();
	}
	
	
	}
	

	public void ListUsers(ActionEvent event) throws SQLException, IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersList.fxml"));
		Parent listUserRoot = loader.load();
		UserListController userListController = loader.getController();
		
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
            // Execute SQL query to get all users from the database
            ResultSet resultSet = dataBase.getUsers(); // Assuming this method fetches the ResultSet for all users

            // Pass the resultSet to the UserListController to load the data
            userListController.loadUserData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close the database connection
            dataBase.closeConnection();
        }

        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene userListScene = new Scene(listUserRoot);
        newStage.setTitle("User List");
        newStage.setScene(userListScene);
        newStage.show();
    }


	

	
	public void ResetUser(ActionEvent event)
	{
		
	}
	
	public void InviteUser(ActionEvent event)
	{
		
	}
	
	public void Delete(ActionEvent event)
	{
		
	}
	
	public void AddorRemove(ActionEvent event)
	{
		
	}
	
	
	
	


}