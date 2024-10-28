package StudentPage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Article.ArticleListController;
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

/**
 * <p> functionality class for Student page page. </p>
 * 
 * <p> Description: The class loads houses all the functionality for the student page.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */
public class StudentpageController 
{
	@FXML
	private Button studentlogout;
	@FXML
    private Button searchButton; 

	@FXML
	private AnchorPane InstructorPage;
	@FXML
	private Label TitleLabel,UserLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String userName;
	
	
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
	@FXML
	 private void loadSearchArticlesPage() {
	        try {
	            // Load the FXML file for the new scene
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchArticles.fxml"));
	            AnchorPane newPage = loader.load();

	            // Create a new scene and set it to the current stage
	            Scene scene = new Scene(newPage);
	            Stage stage = (Stage) searchButton.getScene().getWindow(); // Get the current stage
	            stage.setScene(scene); // Switch to the new scene
	            stage.show(); // Show the new scene
	        } catch (IOException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	    }


	public void setUserName(String username) {
		this.userName = username;
		
	}
	
	public void ListArtilces(ActionEvent event) throws SQLException, IOException
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


}
