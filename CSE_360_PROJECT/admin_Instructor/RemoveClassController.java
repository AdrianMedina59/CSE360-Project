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
	private String username;
    private boolean isChoiceBoxInitialized = false;
    
    
    public void setUsername(String username)
    {
    	this.username = username;
        System.out.println("Child Controller Username: " + username);
        
        // Populate choice boxes if they haven't been initialized yet
        if (!isChoiceBoxInitialized) {
            populateChoiceBoxes();
        }

    }
    public void initialize() {
        System.out.println("initialize() called.");
        // Check if username is already set
        if (username != null) {
            populateChoiceBoxes();
        }
    }
	
	 // Populate choice boxes based on the username
    private void populateChoiceBoxes() {
        try {
            if (username == null) {
                System.out.println("Null username! Cannot populate choice boxes.");
                return;
            }

            dataBase.connectToDatabase();

            // Populating the class choice box with class names
            List<String> classNames = dataBase.getClassNamesByDepartment(dataBase.getGroupIdByAdminInstructor(username));
            System.out.println("Classes in Department " + dataBase.getGroupIdByAdminInstructor(username) + ":");
            removeClass_ChoiceBox.getItems().addAll(classNames);


            dataBase.closeConnection();
            isChoiceBoxInitialized = true; // Mark initialization as complete
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
