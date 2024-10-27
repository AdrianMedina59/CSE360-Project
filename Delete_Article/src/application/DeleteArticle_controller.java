package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteArticle_controller

{
	private AnchorPane scenePane;
	
	public void DeleteArticle()
	{
		
		
		Stage stage;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Are you sure you like to delete this article");
		alert.setContentText("Hit Ok to delete the article, hit cancel to not delete the article!!");
				
		if(alert.showAndWait().get() == ButtonType.OK)
		{
			
			
		}
		
		
	}
	
	public void Exit()
	{
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Are you sure you like to exit this screen");
		alert.setContentText("Hit Ok to exit, hit cancel to stay here!!");
		
		if(alert.showAndWait().get() == ButtonType.OK)
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage/Login.fxml"));
			
		}
		
		
	}
	
	
		
}






