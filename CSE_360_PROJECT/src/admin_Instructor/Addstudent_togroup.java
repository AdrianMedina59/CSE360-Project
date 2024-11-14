package admin_Instructor;

import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Addstudent_togroup

{
	@FXML
	private TextField Group_Name;
	@FXML 
	private TextField Student_Name;
	@FXML
    private DataBaseHelper dataBase = new DataBaseHelper();

    public void Add_student_to_group() throws SQLException
    {
    	//getting  contents of the textfields
		String group  = Group_Name.getText();
		String StudentName = Student_Name.getText();
		
		dataBase.connectToDatabase();
		
		if(dataBase.getUser(StudentName) != null && "Student".equals(dataBase.getRole(StudentName)))
		{
			
		}
    	
    	
    }
    
}
