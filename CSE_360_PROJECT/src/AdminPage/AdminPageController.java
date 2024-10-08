package AdminPage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.Node;
import ConfirmLogin.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminPageController 
{
	
	@FXML
	 private Button logoutbutton;
	@FXML
	private AnchorPane scenePane;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void printUsers() throws SQLException
	{
		ConfirmLogin.DataBaseHelper database = new ConfirmLogin.DataBaseHelper(); //data base
		database.PrintUserTables();
	}
	
	
	public void switchbacktoLogin(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
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
		stage = (Stage) scenePane.getScene().getWindow();

		switchbacktoLogin(event);
	}
	
	
	}
	
	public void ListUsers(ActionEvent event) throws SQLException
	{
		printUsers();

	}
	
	public void ResetUser(ActionEvent event)
	{
		
	}
	
	public void InviteUser(ActionEvent event)
	{
		
	}
	
	public void Delete(ActionEvent event)
	{
		
	}
	
	public void AddorRemove(ActionEvent event)
	{
		
	}
	
	
	
	


}
