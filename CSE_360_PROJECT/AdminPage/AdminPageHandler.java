/**
 * <p> Main class for admin scene. </p>
 * 
 * <p> Description: Initiates the Admin scene and gets all the resources set up including fxml file and css file.</p>
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
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class AdminPageHandler {

    // This method will initialize the admin page using the current stage
    public static void initializeAdminPage(Stage stage) {
        try {
            // Load the FXML file for the admin page
            Parent root = FXMLLoader.load(AdminPageHandler.class.getResource("GUI.fxml"));
            
            // Create a new scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(AdminPageHandler.class.getResource("application.css").toExternalForm());
           
            // Set the new scene on the existing stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String DeleteArticleTest(String title) throws SQLException
    {
    	DataBaseHelper database = new DataBaseHelper();
    	database.connectToDatabase();
    	if(title == "") 
    	{
    		return "Title cannot be empty";
    	}
    	if(database.getArticle(title) == "Article title not found.") 
    	{
    		return "Article title not found";
    	}
    	database.deleteArticle(title);
    	return "Article has been Deleted";
    	
    }
    
    
    
    
    public static String changeRoleTest(String username,String role) throws SQLException
    {
    	DataBaseHelper database = new DataBaseHelper();
    	database.connectToDatabase();
    	if(username == "")
    	{
    		return "Username cannot be empty";
    	}
    	//checking if the username is valid first
    	if(database.getUser(username) == "User not found.") {
    		database.closeConnection();
    		return "Username is not found in database!";
    	}
    	//checking if the role is not null
    	 if (role == "") {
    		database.closeConnection();
			return "Role cannot be empty";
		}
			database.updateUserRole(username, role);
			database.closeConnection();
	    	return "Role has been changed";
    }
    
    
}
