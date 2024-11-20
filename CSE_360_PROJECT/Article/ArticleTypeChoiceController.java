package Article;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArticleTypeChoiceController {

	private String role,username;

	public void setRole(String role) {
		// TODO Auto-generated method stub
		this.role = role;
	}

	public void setName(String username) {
		// TODO Auto-generated method stub
		this.username = username;
	}
	
	//function to create an article
		public void createGeneralArticle(ActionEvent event) 
		{
		    try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/createArticle.fxml")); // Assuming it's in the same package
		        Parent articleRoot = loader.load();
		        
		        ArticleController articleController = loader.getController();
		        articleController.setRole("Instructor");
		        articleController.setName(username);
		        
		        Stage articleStage = new Stage();
		        articleStage.setTitle("Article Choice");
		        articleStage.setScene(new Scene(articleRoot));
		        articleStage.show();
		    } catch (IOException e) {
		        e.printStackTrace(); // Print stack trace for debugging
		    }
		}
	
		// Function in order to create a help article
		public void createHelpArticle(ActionEvent event) {
			try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/CreateHelp.fxml")); // Assuming it's in the same package
		        Parent helpRoot = loader.load();
		        helpArticleController helpArticleController = loader.getController();
		        helpArticleController.setRole("Instructor");
		        helpArticleController.setName(username);
		        
		        Stage articleStage = new Stage();
		        articleStage.setTitle("Create Article");
		        articleStage.setScene(new Scene(helpRoot));
		        articleStage.show();
			 } catch (IOException e) {
			        e.printStackTrace(); // Print stack trace for debugging
			    }
		}
		
		public void createSpecialArticle(ActionEvent event) throws SQLException {
			try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/CreateSpecialArticle.fxml")); // Assuming it's in the same package
		        Parent helpRoot = loader.load();
		        SpecialArticleController articleController = loader.getController();
		        articleController.setRole("Instructor");
		        System.out.println(username);
		        articleController.setName(username);
		        
		        Stage articleStage = new Stage();
		        articleStage.setTitle("Create Article");
		        articleStage.setScene(new Scene(helpRoot));
		        articleStage.show();
			 } catch (IOException e) {
			        e.printStackTrace(); // Print stack trace for debugging
			    }
		}
	

	
	
	
}
