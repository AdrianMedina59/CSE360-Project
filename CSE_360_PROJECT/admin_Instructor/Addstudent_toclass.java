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

	
	public void initialize() throws SQLException {
		dataBase.connectToDatabase();
		//populating the class choice box with class names
		List<String> classNames = dataBase.getClassNames();
        class_ChoiceBox.getItems().addAll(classNames);
        
        //populating the student choice box with student names
        List<String> studentNames = dataBase.getStudentNames();
        student_ChoiceBox.getItems().addAll(studentNames);
        dataBase.closeConnection();

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
