package application;
import java.io.IOException;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class StudentpageController 
{
	@FXML
	private Button studentlogout;
	@FXML
	private AnchorPane InstructorPage;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchbacktoLogin(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/logigexample.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene (root);
		stage.setScene(scene);
		stage.show();
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
		stage = (Stage) InstructorPage.getScene().getWindow();
		switchbacktoLogin(event);
	}
	
	
	}


}
