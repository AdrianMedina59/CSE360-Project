package InstructorPage;

import java.sql.SQLException;
import java.util.List;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AddStudentController {

	//the choice boxes
	@FXML
	private ChoiceBox<String> class_ChoiceBox,student_ChoiceBox;
	private String username;
	private boolean isChoiceBoxInitialized = false;
	private DataBaseHelper dataBase = new DataBaseHelper();
	
	
	public void setUsername(String username) throws SQLException//
    {
    	this.username = username;
        System.out.println("Child Controller Username: " + username);
        
        // Populate choice boxes if they haven't been initialized yet
        try {
			if (!isChoiceBoxInitialized) {
			    populateChoiceBoxes();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	 public void initialize() throws SQLException {//
	        System.out.println("initialize() called.");
	        // Check if username is already set
	        if (username != null) {
	            populateChoiceBoxes();
	        }
	    }
	 public void populateChoiceBoxes() throws SQLException
	 {
		dataBase.connectToDatabase(); 
		// Populating the class choice box with class names
        List<String> classNames = dataBase.getClassesFromInstructor(username);
        class_ChoiceBox.getItems().addAll(classNames);
        
     // Populating the student choice box with student names
        List<String> studentNames = dataBase.getStudentNames();
        student_ChoiceBox.getItems().addAll(studentNames);
	 }
	
	 //function to add students to selected class
	 public void AddStudentToClass() throws SQLException
	    {
	    	dataBase.connectToDatabase();
	    	//getting values form the choice boxes
	    	String selectedClass = class_ChoiceBox.getValue();
	        String selectedStudent = student_ChoiceBox.getValue();
	    	
	        if (selectedClass == null || selectedStudent == null) {
	            System.out.println("Please select both a class and a student.");
	            return;
	        }
	        
	        // Retrieve the classId and userId for the selected names
	        int classId = dataBase.getClassIdByName(selectedClass);
	        int userId = dataBase.getUserIdByName(selectedStudent);
	        
	        if (classId == -1 || userId == -1) {
	            System.out.println("Could not find class or student in the database.");
	            return;
	        }
	        
	     // Enroll the student in the class
	        dataBase.enrollStudentInClass(userId, classId);
	        System.out.println("Student successfully enrolled in the class.");
	        dataBase.printClassStudentsTable();
	        dataBase.closeConnection();
	    }
}
