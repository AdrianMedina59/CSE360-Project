package admin_Instructor;

import java.sql.SQLException;
import java.util.List;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class RemoveClassController {

	
	@FXML
	private ChoiceBox<String> removeClass_ChoiceBox;
	@FXML
    private DataBaseHelper dataBase = new DataBaseHelper();
	
	public void initialize() throws SQLException {
		dataBase.connectToDatabase();
        
		
		//populating the class choice box with class names
		List<String> classNames = dataBase.getClassNames();
		removeClass_ChoiceBox.getItems().addAll(classNames);
		dataBase.closeConnection();
	}
	
	public void removeClass() throws SQLException
	{
		
	    
	    
		dataBase.connectToDatabase();
		String selectedClass = removeClass_ChoiceBox.getValue();
		int classId = dataBase.getClassIdByName(selectedClass);
		 if (selectedClass == null) {
	            System.out.println("Please select a class.");
	            return;
	        }
		 dataBase.removeClass(classId);
		 System.out.println("Student successfully removed from the class.");
		 dataBase.closeConnection();
		// Close the current window
	        Stage stage = (Stage) removeClass_ChoiceBox.getScene().getWindow();
	        stage.close();
	}
}
