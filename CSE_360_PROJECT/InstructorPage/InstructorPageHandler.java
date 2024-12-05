
/**
 * <p> Main class for Instructor page. </p>
 * 
 * <p> Description: The class loads all the FXML files and css files used for scene.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */
package InstructorPage;
	
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class InstructorPageHandler extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Instructor.fxml"));			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public static String addStudentTest(int userId, int classId) throws SQLException {
	    DataBaseHelper database = new DataBaseHelper();
	    database.connectToDatabase();
	    
	    if (userId <= 0) {
	        database.closeConnection();
	        return "Student id must be an ID";
	    }
	    if (classId <= 0) {
	        database.closeConnection();
	        return "Class ID must be a class";
	    }


	    try {
	        database.enrollStudentInClass(userId, classId);
	        database.closeConnection();
	        return "Student enrolled successfully";
	    } catch (SQLException e) {
	        database.closeConnection();
	        return "Error while enrolling student: " + e.getMessage();
	    }
	}
	public static String removeStudentTest(int studentId, int classId) throws SQLException {
	    DataBaseHelper database = new DataBaseHelper();
	    database.connectToDatabase();

	    if (studentId <= 0) {
	        database.closeConnection();
	        return "Student id must be an ID";
	    }
	    if (classId <= 0) {
	        database.closeConnection();
	        return "Class ID must be a class";
	    }

	    try {
	        database.removeStudentFromClass(studentId, classId);
	        database.closeConnection();
	        return "Student removed successfully";
	    } catch (SQLException e) {
	        database.closeConnection();
	        return "Error while removing student: " + e.getMessage();
	    }
	}
	
	public static String DeleteHelpArticleTest(String title) throws Exception {
	    DataBaseHelper database = new DataBaseHelper();
	    database.connectToDatabase();

	    // Check if the title is empty
	    if (title.equals("")) { // Use .equals() for string comparison
	        return "Title cannot be empty";
	    }

	    try {
	        // Check if the article exists in the database
	        if (database.getHelpArticleByName(database.getConnection(), title) == null) 
	        { // Check for null
	            return "Article title not found";
	        }

	        // Delete the article
	        database.deleteHelpArticle(title);
	        return "Article has been Deleted";
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "An error occurred while deleting the article: " + e.getMessage();
	    }
	}


}
