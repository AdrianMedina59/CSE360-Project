package StudentPage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import Messages.MessageController;
import Messages.MessageListController;
import Article.ArticleChoice;
import Article.ArticleListController;
import Article.SearchArticlesController;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML
	private Button adminInstructorButton;
	@FXML
	private Button yourInstructorButton;
	@FXML
	private TextField messageInput;

	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String userName;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(); 

	
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
	 private void loadSearchArticlesPage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/ArticleChoice.fxml"));
	    Parent listArticleRoot = loader.load();

		ArticleChoice articleChoice = loader.getController();
		System.out.print("System page controller name passed: " + userName);
		articleChoice.setUsername(userName);
		
		
		

        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene articleListScene = new Scene(listArticleRoot);
        newStage.setTitle("Article Search");
        newStage.setScene(articleListScene);
        newStage.show();
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
	
	public void HelpPage(ActionEvent event) throws IOException {

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
	    Parent root = loader.load(); 
	    MessageController messageController = loader.getController();
	    messageController.setUsername(userName);
	    messageController.setSender(this.userName);
	    messageController.setReceiver("Admin Instructor");  
	    Stage messageStage = new Stage();
	    messageStage.setTitle("Help");
	    messageStage.setScene(new Scene(root));
	    messageStage.show();
	}
	
	public void MessageList(ActionEvent event) throws SQLException, IOException{
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Messages/MessagesList.fxml"));
	    Parent MessageRoot = loader.load();
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
 
            ResultSet resultSet = dataBase.getMessages(); 
        MessageListController.loadMessageData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            dataBase.closeConnection();
        }

        Stage newStage = new Stage();
        Scene articleListScene = new Scene(MessageRoot);
        newStage.setTitle("Messages");
        newStage.setScene(articleListScene);
        newStage.show();


	}

	
   


}
