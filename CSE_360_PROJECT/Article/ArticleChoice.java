package Article;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArticleChoice {

	 private ArticleListController articleListController;
	 private String username;
	
	 
	 public ArticleChoice()
	 {
		 
	 }
	 
	 public ArticleChoice(ArticleListController articleListController) {
	        this.articleListController = articleListController;
	    }
	
	  public void displayInstructorArticles() throws SQLException, IOException {
	        ListArtilces("Instructor");
	    }
	  
	  public void displayAdminArticles() throws SQLException, IOException {
	        ListArtilces("Admin");
	    }
	  
	 
	  
	  public void displaySpecialArticles() throws SQLException, IOException
	  {
		  ListSpecialArticles(username);
	  }
	  
	  public void setUsername(String username)
	    {
	    	this.username = username;
	    }
	  
	  public void searchArticlesDisplay() throws SQLException, IOException
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/SearchArticles.fxml"));
		    Parent listArticleRoot = loader.load();

			SearchArticlesController searchArticlesController = loader.getController();
			
			

	        // Set up the new stage and scene for the user list
	        Stage newStage = new Stage();
	        Scene articleListScene = new Scene(listArticleRoot);
	        newStage.setTitle("Article Search");
	        newStage.setScene(articleListScene);
	        newStage.show();
	    }
	  
	  public void ListSpecialArticles(String role) throws SQLException, IOException
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/SpecialArticleList.fxml"));
		    Parent listArticleRoot = loader.load();

		    SpecialArticleListController specialArticlelistController = loader.getController();
		    specialArticlelistController.setUsername(username);
			

	        // Set up the new stage and scene for the user list
	        Stage newStage = new Stage();
	        Scene articleListScene = new Scene(listArticleRoot);
	        newStage.setTitle("Article List");
	        newStage.setScene(articleListScene);
	        newStage.show();
	    }
	  
	
	  public void ListArtilces(String role) throws SQLException, IOException
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/ArticleList.fxml"));
		    Parent listArticleRoot = loader.load();

			ArticleListController articlelistController = loader.getController();
			
			DataBaseHelper dataBase = new DataBaseHelper();
			dataBase.connectToDatabase();
			try {
				if(role == "Instructor")
				{
		            // Pass the resultSet to the UserListController to load the data
		            articlelistController.loadInstructorArticles();
				}
				else if(role == "Admin")
				{
					articlelistController.loadAdminArticles();
				}
	            

	           
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
