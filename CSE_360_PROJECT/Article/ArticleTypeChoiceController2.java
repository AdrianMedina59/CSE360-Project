package Article;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArticleTypeChoiceController2 {

	private String role,username;

	public void setRole(String role) {
		// TODO Auto-generated method stub
		this.role = role;
	}

	public void setName(String username) {
		// TODO Auto-generated method stub
		this.username = username;
	}
	
	public void ListGeneralArticles(ActionEvent event) throws SQLException, IOException
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
	            //Try SQL query to get all users from the database
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
		

	
	
	
}
