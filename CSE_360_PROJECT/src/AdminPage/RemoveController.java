/**
 * <p> Main class for continue login. </p>
 * 
 * <p> Description: The class is the main functionality of the change roles functionality.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package AdminPage;

import ConfirmLogin.*;

import java.io.Closeable;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button; // Use JavaFX Button
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;  // Use JavaFX Label
import javafx.scene.control.TextField; // Use JavaFX TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RemoveController {
    @FXML
    private AnchorPane RemoveAnchor;
    @FXML
    private Button SubmitButton; // JavaFX Button
    @FXML
    private TextField RemoveTextfield; // JavaFX TextField
    @FXML
    private Label RemoveTitle; // JavaFX Label

    private DataBaseHelper dataBase = new DataBaseHelper();

    public void DeleteUser() throws SQLException {
        String username = RemoveTextfield.getText();
        dataBase.connectToDatabase();
        Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Delete");
    	alert.setHeaderText("Would You like to delete user?");
    	alert.setContentText("Hit submit to delete user");
    			
    	if(alert.showAndWait().get() == ButtonType.OK)
    	{
    		
    		if(dataBase.getUser(username).equals(username) && !dataBase.getRole(username).equals("Admin")) {
                //delete user
                dataBase.deleteUser(username);
                
            }
    		
    	}
    	closeWindow();
        
    }
    
    void closeWindow()
    {
    	 // Code to close the window
        Stage stage = (Stage) RemoveAnchor.getScene().getWindow();
        stage.close();
    }
}