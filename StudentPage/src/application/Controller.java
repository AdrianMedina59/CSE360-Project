package application;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Controller 
{
	@FXML
	private Button studentLogout;
	@FXML
	private AnchorPane InstructorPage;
	
	public void logout(ActionEvent event)
	{
		
	Stage stage;
	
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Logout");
	alert.setHeaderText("Would you like to logout");
	alert.setContentText("Do you want to save before you logout");
			
	if(alert.showAndWait().get() == ButtonType.OK)
	{
		stage = (Stage) InstructorPage.getScene().getWindow();
		stage.close();
	}
	
	
	}


}
