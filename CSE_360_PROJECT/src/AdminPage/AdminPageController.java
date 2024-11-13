/**
 * <p> Main class for Admin page. </p>
 * 
 * <p> Description: Initiates the Admin scene is the main functionality of the code.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package AdminPage;

import ConfirmLogin.*;
import javafx.event.ActionEvent;
import LoginPage.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import Article.ArticleController;
import Article.Delete_ArticleController;
import Article.Delete_HelpArticleController;
import Article.helpArticleController;
import Article.ArticleListController;
import Article.helpArticleController;
import Article.hArticleListController;
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
	@FXML
	private Button restoreDeletedButton;
	
	private DataBaseHelper dataBase = new DataBaseHelper();
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Timeline deletePasscodeTimeline;
	private String userName;
	
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
	
	public void createArticle(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/CreateArticle.fxml")); // Assuming it's in the same package
	        Parent articleRoot = loader.load();
	        
	        ArticleController articleController = loader.getController();
	        articleController.setRole("Admin");
	        articleController.setName(userName);
	        
	        Stage articleStage = new Stage();
	        articleStage.setTitle("Create Article");
	        articleStage.setScene(new Scene(articleRoot));
	        articleStage.show();
	    } catch (IOException e) {
	        e.printStackTrace(); // Print stack trace for debugging
	    }
	}
	
	public void createHelpArticle(ActionEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/CreateHelp.fxml")); // Assuming it's in the same package
	        Parent helpRoot = loader.load();
	        helpArticleController helpArticleController = loader.getController();
	        helpArticleController.setRole("Admin");
	        helpArticleController.setName(userName);
	        
	        Stage articleStage = new Stage();
	        articleStage.setTitle("Create Article");
	        articleStage.setScene(new Scene(helpRoot));
	        articleStage.show();
		 } catch (IOException e) {
		        e.printStackTrace(); // Print stack trace for debugging
		    }



		}
	

	public void ListArtices(ActionEvent event) throws SQLException, IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/ArticleList.fxml"));
	    Parent listArticleRoot = loader.load();

		ArticleListController articlelistController = loader.getController();
		
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
            // Execute SQL query to get all users from the database
            ResultSet resultSet = dataBase.getArticles(); // Assuming this method fetches the ResultSet for all articles

            // Pass the resultSet to the UserListController to load the data
            articlelistController.loadArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close the database connection
            dataBase.closeConnection();
        }

        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene articleListScene = new Scene(listArticleRoot);
        newStage.setTitle("Article List");
        newStage.setScene(articleListScene);
        newStage.show();
    }
	
	public void ListHelpArticles(ActionEvent event) throws SQLException, IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/HelpArticleList.fxml"));
	    Parent listArticleRoot = loader.load();

		hArticleListController harticlelistController = loader.getController();
		
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
            // Execute SQL query to get all users from the database
            ResultSet resultSet = dataBase.getHelpArticles(); // Assuming this method fetches the ResultSet for all articles

            // Pass the resultSet to the UserListController to load the data
            harticlelistController.loadHelpArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close the database connection
            dataBase.closeConnection();
        }

        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene articleListScene = new Scene(listArticleRoot);
        newStage.setTitle("Help Article List");
        newStage.setScene(articleListScene);
        newStage.show();
	}
	
	public void Article_delete(ActionEvent event) throws IOException, SQLException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/Delete_article.fxml"));
		Parent deletearticleroot = loader.load();
		Delete_ArticleController DeleteArticleController = loader.getController();
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		Stage newStage = new Stage();
		Scene RemoveArticle= new Scene(deletearticleroot);
		newStage.setTitle("Remove Article");
		newStage.setScene(RemoveArticle);
		newStage.show();

  }

	public void HelpArticle_delete(ActionEvent event) throws IOException, SQLException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/DeleteHelpArticle.fxml"));
		Parent deleteHelpRoot = loader.load();
		Delete_HelpArticleController DeleteHelpArticleController = loader.getController();
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		Stage newStage = new Stage();
		Scene RemoveArticle= new Scene(deleteHelpRoot);
		newStage.setTitle("Remove Article");
		newStage.setScene(RemoveArticle);
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
	
	//Sends a reset password code for user to use to reset forgotten password
	public void ResetUser(ActionEvent event) throws SQLException
	{
		String resetCode = generateRandomString(5);
		dataBase.connectToDatabase();
		 try {
	            // Insert the new invite code
	            dataBase.insertPasscode("reset", resetCode);

	            
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

	
	public void ChangeRoles(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeRoles.fxml"));
		Parent Remove = loader.load();
		ChangeRolesController changeRolesController = loader.getController();
		
        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene RemoveScene = new Scene(Remove);
        newStage.setTitle("Remove User");
        newStage.setScene(RemoveScene);
        newStage.show();
	}


	public void setUserName(String username) {
		this.userName = username;
		
		
		
	}
	@FXML
	public void restoreDeletedArticles(ActionEvent event) {
	    // Confirm restoration action
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Restore Help Articles");
	    alert.setHeaderText("Restore Deleted Help Articles");
	    alert.setContentText("Are you sure you want to restore all deleted articles?");

	    if (alert.showAndWait().get() == ButtonType.OK) {
	        try {
	            // Connect to the database
	            dataBase.connectToDatabase();  // Use the already existing connection from dataBase
	            
	            // Create SQL query to restore deleted articles
	            String restoreQuery = "UPDATE help_articles SET deleted = false WHERE deleted = true";
	            
	            // Create a statement to execute the query
	            Statement statement = dataBase.getConnection().createStatement();
	            statement.executeUpdate(restoreQuery);  // Executes the query
	            
	            // Inform the user that the articles were restored
	            showInfoAlert("Success", "Articles Restored", "All deleted articles have been restored.");
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Show error message if something goes wrong
	            showErrorAlert("Failed to Restore Articles", "There was an error restoring the articles.");
	        } finally {
	            // Ensure the connection is closed after the operation
	            dataBase.closeConnection();
	        }
	    }
	}

	// Utility method to show success alerts
	private void showInfoAlert(String title, String header, String content) {
	    Alert successAlert = new Alert(AlertType.INFORMATION);
	    successAlert.setTitle(title);
	    successAlert.setHeaderText(header);
	    successAlert.setContentText(content);
	    successAlert.showAndWait();
	}

	// Utility method to show error alerts
	private void showErrorAlert(String header, String content) {
	    Alert errorAlert = new Alert(AlertType.ERROR);
	    errorAlert.setTitle("Error");
	    errorAlert.setHeaderText(header);
	    errorAlert.setContentText(content);
	    errorAlert.showAndWait();
	}

	
	
	
	
	


}