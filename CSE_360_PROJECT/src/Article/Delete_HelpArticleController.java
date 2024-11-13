/**
 * <p> Main class for continue to delete article </p>
 * 
 * <p> Description: This will be used to search through the database and delete an article by inputting the title .</p>
 * 
 * <p> Copyright: Mark Loffman © 2024 </p>
 * 
 * @author : Mark Loffman
 * 
 * @version 1.00		2024-10-28
 *  
 */

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
import javafx.stage.Stage;
import ConfirmLogin.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Delete_HelpArticleController 

{
	@FXML
	private TextField Article_title; // JavaFX TextField to delete the article title
	
	@FXML
	private AnchorPane DeleteArticle;
	@FXML
	private DataBaseHelper dataBase = new DataBaseHelper();
	
	
	public void DeleteHelpArticle() throws SQLException 
	{
		String title = Article_title.getText();
			
		dataBase.connectToDatabase();
		
		 Alert alert = new Alert(AlertType.CONFIRMATION);

    	alert.setTitle("Delete");
    	alert.setHeaderText("Would you like to delete the article");
    	alert.setContentText("Hit submit to delete the article");
    			
    	if(alert.showAndWait().get() == ButtonType.OK)
    	{
    		
    		if(dataBase.isValidHelpArticleTitle(title)) 
    		{
                //delete article based on title
              dataBase.deleteHelpArticle(title);
                
            }
    		
    	}
    	
    	closeWindow();
	}
	
	
    void closeWindow()
    {
    	 // Code to close the window
        Stage stage = (Stage) DeleteArticle.getScene().getWindow();
        stage.close();
    }
}
		