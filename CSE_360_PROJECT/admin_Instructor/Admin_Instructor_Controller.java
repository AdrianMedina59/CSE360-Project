package admin_Instructor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sound.midi.VoiceStatus;

import Article.ArticleController;
import Article.ArticleListController;
import Article.Delete_ArticleController;
import Article.Delete_HelpArticleController;
import Article.helpArticleController;
import ConfirmLogin.DataBaseHelper;
import LoginPage.Login_Button_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Admin_Instructor_Controller 
{
	@FXML
    private Button CreateArticlesButton;
	@FXML
    private Button listClassesButton;
	@FXML
	private Button ListArticlesButton,listStudentsButton;
	@FXML
	private Button Logout;
	@FXML
	private AnchorPane AdminInsPage;
	@FXML
	private Label TitleLabel, UserLabel;  // Make sure UserLabel is defined here
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String username;
	private String groupName;
	@FXML
	private Label GroupLabel;
	private DataBaseHelper dataBase = new DataBaseHelper();

	
	public void SetGroupLabel(String username) {
		GroupLabel.setText(username);
	}
	
	public void SetUserLabel(String username) {
		UserLabel.setText(username);
	}
	
	public void setUserName(String username)
	{
		this.username = username;
	}
	public String getUserName()
	{
		return username;
	}
	
	public void setListButtonName(String name) {
		this.listClassesButton.setText("List " + name + " classes");
	}
	
	public void setListStudentButtonName(String name) {
		this.listStudentsButton.setText("List " + name + " students");
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
	
	public void ListStudents() throws IOException, SQLException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ListStudents.fxml"));
	    Parent listArticleRoot = loader.load();

		ListStudentsController listStudentsController = loader.getController();

		
		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
			// Debug: Print the entire ResultSet for debugging purposes
	        System.out.println("Loading classes from the database...");
            // Execute SQL query to get all students based on department
	        listStudentsController.setUsername(username);
	        ResultSet rs = dataBase.getStudentsByDepartment(dataBase.getGroupIdByAdminInstructor(getUserName()));
            
           
            listStudentsController.loadStudentsData(rs);

            // Pass the resultSet to the UserListController to load the data
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close the database connection
            dataBase.closeConnection();
        }

        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene articleListScene = new Scene(listArticleRoot);
        newStage.setTitle("Class List");
        newStage.setScene(articleListScene);
        newStage.show();
	}
	
	
	public void printStudentsByDepartment() {
	    try {
	    	DataBaseHelper dataBaseHelper = new DataBaseHelper();
	    	dataBaseHelper.connectToDatabase();
	        ResultSet rs = dataBaseHelper.getStudentsByDepartment(dataBaseHelper.getGroupIdByAdminInstructor(getUserName()));
	        
	        System.out.println("=== Students in Department ID: " + dataBaseHelper.getGroupIdByAdminInstructor(getUserName()) + " ===");
	        
	        while (rs.next()) {
	            // Check and match the column names with your SQL query
	            int studentId = rs.getInt("studentId"); // Ensure "studentId" matches the query alias
	            String studentName = rs.getString("studentName"); // Ensure alias matches here too
	            String enrolledClasses = rs.getString("enrolledClasses"); // Same for this column

	            System.out.println("Student ID: " + studentId);
	            System.out.println("Student Name: " + studentName);
	            System.out.println("Enrolled Classes: " + enrolledClasses);
	            System.out.println("----------------------------------");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching or printing students by department: " + e.getMessage());
	    }
	}
	public void ListClasses() throws SQLException, IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ListClasses.fxml"));
	    Parent listArticleRoot = loader.load();

	    
		ListClassesController listClassesController = loader.getController();

		DataBaseHelper dataBase = new DataBaseHelper();
		dataBase.connectToDatabase();
		try {
			// Debug: Print the entire ResultSet for debugging purposes
	        System.out.println("Loading classes from the database...");
            // Execute SQL query to get all users from the database
	        listClassesController.setUsername(this.username);
            ResultSet resultSet = dataBase.getClassesByDepartment(dataBase.getGroupIdByAdminInstructor(username));
            
            printStudentsByDepartment();
            listClassesController.loadClassData(resultSet);

            // Pass the resultSet to the UserListController to load the data
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close the database connection
            dataBase.closeConnection();
        }

        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene articleListScene = new Scene(listArticleRoot);
        newStage.setTitle("Class List");
        newStage.setScene(articleListScene);
        newStage.show();
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
	
	public void createArticle(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/CreateArticle.fxml")); // Assuming it's in the same package
	        Parent articleRoot = loader.load();
	        
	        ArticleController articleController = loader.getController();
	        articleController.setRole("Admin Instructor");
	        articleController.setName(username);
	        
	        Stage articleStage = new Stage();
	        articleStage.setTitle("Create Article");
	        articleStage.setScene(new Scene(articleRoot));
	        articleStage.show();
	    } catch (IOException e) {
	        e.printStackTrace(); // Print stack trace for debugging
	    }
	}
	
	
	public void AddClass_Button() {
		
		try
	    {
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("addClass.fxml")); 
	        Parent studentRoot = loader.load();
	        
	        AddClassController addClassController = loader.getController();
	        addClassController.setAdminInstructorController(this);
	        Stage articleStage = new Stage();
	        articleStage.setTitle("Add Class");
	        articleStage.setScene(new Scene(studentRoot));
	        articleStage.show();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace(); // Print stack trace for debugging
	    }
		
		
	}
	
	public void RemoveStudent_Button()
	{
		  try
		    {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeStudent.fxml")); 
		        Parent studentRoot = loader.load();
		        removeStudentController removeStudentController = loader.getController();
		        removeStudentController.setUsername(this.username);
				
		        Stage articleStage = new Stage();
		        articleStage.setTitle("Add Student to class");
		        articleStage.setScene(new Scene(studentRoot));
		        articleStage.show();
		    }
		    catch (IOException e)
		    {
		        e.printStackTrace(); // Print stack trace for debugging
		    }
	}
	
	public void removeClass()
	{
		 try
		    {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("RemoveClass.fxml")); 
		        Parent studentRoot = loader.load();
		        RemoveClassController removeClassController = loader.getController();
		        removeClassController.setUsername(this.username);
				
		        Stage articleStage = new Stage();
		        articleStage.setTitle("Add Student to class");
		        articleStage.setScene(new Scene(studentRoot));
		        articleStage.show();
		    }
		    catch (IOException e)
		    {
		        e.printStackTrace(); // Print stack trace for debugging
		    }
	}
	
	public void AddStudent_Button(ActionEvent event) throws IOException
	{
	    try
	    {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("addStudentoClass.fxml")); 
	        Parent studentRoot = loader.load();
	        Addstudent_toclass Addstudent_toclass = loader.getController();
	        
	        	Addstudent_toclass.setUsername(this.username);
	       
			
	        Stage articleStage = new Stage();
	        articleStage.setTitle("Add Student to class");
	        articleStage.setScene(new Scene(studentRoot));
	        articleStage.show();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace(); // Print stack trace for debugging
	    }
	}
	public void restore(ActionEvent event) {
		 try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/RestoreArticles.fxml")); 
		        Parent RestoreRoot = loader.load();
		     
		        
		        Stage newStage = new Stage();
		        newStage.setTitle("Restore");
		        newStage.setScene(new Scene(RestoreRoot));
		        newStage.show();
		    } catch (IOException e) {
		        e.printStackTrace(); 
		    }
	}
	@FXML
	public void handleBackupArticles() {
	    try {
	        dataBase.connectToDatabase(); 
	        dataBase.backupArticles(); 
	        dataBase.closeConnection(); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void createHelpArticle(ActionEvent event)
	{
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/CreateHelp.fxml")); // Assuming it's in the same package
	        Parent helpRoot = loader.load();
	        helpArticleController helpArticleController = loader.getController();
	        helpArticleController.setRole("Admin Instructor");
	        helpArticleController.setName(username);
	        
	        Stage articleStage = new Stage();
	        articleStage.setTitle("Create Article");
	        articleStage.setScene(new Scene(helpRoot));
	        articleStage.show();
		 } catch (IOException e) {
		        e.printStackTrace(); // Print stack trace for debugging
		    }



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

	
	
	
	
}