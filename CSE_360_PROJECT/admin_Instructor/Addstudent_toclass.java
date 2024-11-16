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


	}
	
    public void AddStudentToClass() throws SQLException
    {
    	
    }

   
}
