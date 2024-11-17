package admin_Instructor;

import java.sql.SQLException;
import java.util.List;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class removeStudentController {

	
	@FXML
	private ChoiceBox<String> RemoveStudent_choiceBox,class_ChoiceBox;
	@FXML
    private DataBaseHelper dataBase = new DataBaseHelper();
	

	public void initialize() throws SQLException {
		dataBase.connectToDatabase();
        
		
		//populating the class choice box with class names
		List<String> classNames = dataBase.getClassNames();
		class_ChoiceBox.getItems().addAll(classNames);
		
		 // Clear the student choice box initially (no class selected yet)
        RemoveStudent_choiceBox.getItems().clear();
		
        
        // Add listener to update the student choice box when class is selected
        class_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateStudentChoiceBox(newValue);
            } catch (SQLException e) {
                System.out.println("Error fetching students: " + e.getMessage());
            }
        });

       
	}
      
        private void updateStudentChoiceBox(String selectedClass) throws SQLException {
            // Clear the current items in the student choice box
            RemoveStudent_choiceBox.getItems().clear();

            if (selectedClass != null) {
                // Get the list of students enrolled in the selected class
                List<String> studentsInClass = dataBase.getStudentsInClass(selectedClass);

                // Populate the student choice box with the filtered student list
                RemoveStudent_choiceBox.getItems().addAll(studentsInClass);
            }
        }
	
	
	public void removeStudent() throws SQLException
	{
		//getting values form the choice boxes
    	String selectedClass = class_ChoiceBox.getValue();
        String selectedStudent = RemoveStudent_choiceBox.getValue();
    	
        if (selectedClass == null || selectedStudent == null) {
            System.out.println("Please select both a class and a student.");
            return;
        }
        
        dataBase.connectToDatabase();
        // Retrieve the classId and userId for the selected names
        int classId = dataBase.getClassIdByName(selectedClass);
        int userId = dataBase.getUserIdByName(selectedStudent);
        
        if (classId == -1 || userId == -1) {
            System.out.println("Could not find class or student in the database.");
            return;
        }
        
        dataBase.removeStudentFromClass(userId, classId);
        System.out.println("Student successfully removed from the class.");
        dataBase.printClassStudentsTable();
        dataBase.closeConnection();
	}
}
