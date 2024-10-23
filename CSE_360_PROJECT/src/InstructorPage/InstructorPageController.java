
/**
 * <p> functionality class for Instructor page. </p>
 * 
 * <p> Description: The class loads houses all the functionality for the instructor class.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */
package InstructorPage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import LoginPage.Login_Button_Controller;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class InstructorPageController 
{

	@FXML
	private Button InstructorLoout;
	@FXML
	private AnchorPane InstructorPage;
	@FXML
	private Label TitleLabel, UserLabel;  // Make sure UserLabel is defined here
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void SetUserLabel(String username) {
		UserLabel.setText(username);
	}
	
	
	public void switchbacktoLogin(ActionEvent event) throws IOException
	{
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
		switchbacktoLogin(event);
	}
	
	
	}
	
	
	public void ListArticles(ActionEvent event) throws SQLException, IOException //everyone can list artciles depending what info
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersList.fxml"));
		Parent articleListRoot = loader.load();
		
		//make an  ArticleCOntroller 
		
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
            // Execute SQL query to get all users from the database
            ResultSet resultSet = dataBase.getUsers(); //instead of users we need a getArticles()

            // Pass the resultSet to the UserListController to load the data
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close the database connection
            dataBase.closeConnection();
        }

		//set up the new Stage and scenee for listing articles
		Stage newStage = new Stage();
		Scene ListArticleScene = new Scene(articleListRoot);
		newStage.setTitle("List Articles");
		newStage.setScene(ListArticleScene);
		newStage.show();
		
		
		
		
	}
	private void deleteArticle(ActionEvent event) throws SQLException, IOException //admin can delete article
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersList.fxml"));
		Parent deleteRoot = loader.load();
		
	//make an articleController
		
		//Set up the new stage and scene for the delete article
		Stage newStage = new Stage();
		Scene deleteArticleScene = new Scene(deleteRoot);
		newStage.setTitle("Delete Article");
		newStage.setScene(deleteArticleScene);
		newStage.show();
		
	}
	
	public void createArticle(ActionEvent event) throws SQLException, IOException //admin intrstuctor and user create
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersList.fxml"));
		Parent articleRoot = loader.load();
		
	//make an articleCOntroller
		
		
        // Set up the new stage and scene for the create Article
		Stage newStage = new Stage();
		Scene articleListScene = new Scene(articleRoot);
		newStage.setTitle("Create Article");
	    newStage.setScene(articleListScene);
	    newStage.show();

	}
	
	
	
	
}
