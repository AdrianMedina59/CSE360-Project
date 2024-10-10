
/**
 * <p> functionality class for login page. </p>
 * 
 * <p> Description: The class loads houses all the functionality for the login class. Uses the confirm code controller for communication</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */
package LoginPage;

import java.awt.Button;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfirmCodeController {
	@FXML
	private AnchorPane ConfirmWindow;
	 @FXML
	private ListView<String> CodesList;
	private DataBaseHelper dataBase = new DataBaseHelper();
	
	
	public void initialize() {
		dataBase = new DataBaseHelper();
        try {
        	dataBase.connectToDatabase();  // Connect to the database
            loadPasscodes();  // Load passcodes into the ListView
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	 // Method to load passcodes from the database and display them
    private void loadPasscodes() {
        try {
            ResultSet resultSet = dataBase.getPasscodes();  // Get all passcodes
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                String passcode = resultSet.getString("passcode");
                CodesList.getItems().add("Category: " + category + ", Passcode: " + passcode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	void closeWindow()
    {
    	 // Code to close the window
        Stage stage = (Stage) ConfirmWindow.getScene().getWindow();
        stage.close();
    }
}
