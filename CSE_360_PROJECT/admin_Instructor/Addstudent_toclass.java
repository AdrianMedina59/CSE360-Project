package admin_Instructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Addstudent_toclass

{
	@FXML
	private ChoiceBox<String> class_ChoiceBox,student_ChoiceBox;
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
	 public String getUsername() {
		 return this.username;
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
	            class_ChoiceBox.getItems().addAll(classNames);

	            // Populating the student choice box with student names
	            List<String> studentNames = dataBase.getStudentNames();
	            student_ChoiceBox.getItems().addAll(studentNames);

	            dataBase.closeConnection();
	            isChoiceBoxInitialized = true; // Mark initialization as complete
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
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
    
    public void removeStudent()
    {
    	
    }

   
}
