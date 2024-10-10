package AdminPage;

import ConfirmLogin.*;
import javafx.event.ActionEvent;
import LoginPage.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javafx.scene.Node;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class AdminPageController 
{
	
	@FXML
	 private Button logoutbutton;
	@FXML
	private AnchorPane scenePane;
	@FXML
    private Label TitleLabel;
	@FXML
	private Label UserLabel;
	
	private DataBaseHelper dataBase = new DataBaseHelper();
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Timeline deletePasscodeTimeline;
	
	//sets the user label
	public void SetUserLabel(String username) {
		UserLabel.setText(username);
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

	//generates a random code for invite and reset
	private String generateRandomString(int length) {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            otp.append((char) ('0' + random.nextInt(10))); // Generate random digits
        }
        return otp.toString();
    }

	//methods invites send an invite code to the main login
	public void InviteUser(ActionEvent event) throws SQLException
	{
		String inviteCode = generateRandomString(5);
		dataBase.connectToDatabase();

        try {
            // Insert the new invite code
            dataBase.insertPasscode("invite", inviteCode);

            
            // Start the deletion process if it's not already started
            if (deletePasscodeTimeline == null) {
                startDeletePasscodeTimeline();
            }
            
            // Print the current passcodes for debugging
            dataBase.printPasscodes();
        } finally {
            dataBase.closeConnection();
        }
    }
	
	
	 // Starts a timeline to delete passcodes every 10 seconds
    private void startDeletePasscodeTimeline() {
        deletePasscodeTimeline = new Timeline(new KeyFrame(Duration.seconds(300), event -> {
			try {
				deleteOnePasscode();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));
        deletePasscodeTimeline.setCycleCount(Timeline.INDEFINITE);
        deletePasscodeTimeline.play();
    }
    
 // Deletes one passcode from the database
    private void deleteOnePasscode() throws SQLException {
        dataBase.connectToDatabase();
        try {
            // Attempt to delete one expired passcode
            dataBase.deleteOnePasscode(); // Implement this method in your DataBaseHelper
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.closeConnection();
        }
    }
	
	public void ResetUser(ActionEvent event)
	{
		
	}
	
	
	public void Delete(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Remove.fxml"));
		Parent Remove = loader.load();
		RemoveController RemoveController = loader.getController();
		
        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene RemoveScene = new Scene(Remove);
        newStage.setTitle("Remove User");
        newStage.setScene(RemoveScene);
        newStage.show();
	}

	
	public void AddorRemove(ActionEvent event)
	{
		
	}
	
	
	
	


}