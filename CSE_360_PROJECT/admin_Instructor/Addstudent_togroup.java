package admin_Instructor;

import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Addstudent_togroup

{
	@FXML
	private TextField S_Group_Textfield;
	@FXML 
	private TextField Student_Name_Textfield;
	@FXML
    private DataBaseHelper dataBase = new DataBaseHelper();

//    public void AddStudentToClass() throws SQLException
//    {
////    	//getting  contents of the textfields
////		String group  = S_Group_Textfield.getText();
////		String StudentName = Student_Name_Textfield.getText();
////		DataBaseHelper dataBase = new DataBaseHelper();
////		dataBase.connectToDatabase();
////		
////		//checks if the student is valid inside the database
////		if(dataBase.getUser(StudentName) != null && "Student".equals(dataBase.getRole(StudentName)))
////		{
////			
////			
////			
////		}
////    	
////		dataBase.closeConnection();
////    }
    
}
