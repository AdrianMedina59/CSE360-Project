package Article;

import java.io.IOException;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import ConfirmLogin.*;
import java.sql.Connection;


public class Delete_ArticleController 

{
	@FXML
	private TextField Article_title; // JavaFX TextField to delete the article title
	
	@FXML
	private AnchorPane scenePane;
	@FXML
	private DataBaseHelper dataBase = new DataBaseHelper();
	
	
	public void DeleteArticle() throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete_article.fxml"));
		Parent remove = loader.load();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Are you sure you like to delete this article");
		alert.setContentText("Hit Ok to delete the article, hit cancel to not delete the article!!");
				
		if(alert.showAndWait().get() == ButtonType.OK)
		{
			//if(dataBase.getArticleByName(titleFromDb))
			//{
				
		//	}
			
			
			
			
			
		}
		
		
	}
	

}
