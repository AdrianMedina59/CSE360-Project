package admin_Instructor;

import javafx.scene.control.TextField;

import java.awt.desktop.PrintFilesEvent;
import java.sql.SQLException;
import ClassManager.schoolClass;
import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;

public class AddClassController {

	private Admin_Instructor_Controller adminInstructorController;
	@FXML
	private TextField className_TextField, InstructorName_TextField;
	private DataBaseHelper dataDataBase = new DataBaseHelper();
	public void addClass() throws SQLException
	{
		dataDataBase.connectToDatabase();
	
		
		
		String adminInstructor = adminInstructorController.getUserName();
		System.out.printf("Admin instructor: %s",adminInstructor);
		//getting textfield content
		String className = className_TextField.getText();
		String instructorName = InstructorName_TextField.getText();
		
		schoolClass schoolClass = new schoolClass();
		
		//setting the class variables then adding it to the databases
		schoolClass.setInstructor(instructorName);
		schoolClass.setName(className);
		int generalGroupId = dataDataBase.getGroupIdByAdminInstructor(adminInstructor);
		
		if (generalGroupId == -1) {
	        throw new SQLException("No group found for the given Admin Instructor: " + adminInstructor);
	    }
		//getting the group from the current admin instructor logged in
		schoolClass.setInstructor(instructorName);
		schoolClass.setGeneralID(generalGroupId);
		dataDataBase.saveClasses(schoolClass);
		dataDataBase.printClassesTable();
		dataDataBase.closeConnection();
		
	}
	 public void setAdminInstructorController(Admin_Instructor_Controller controller) {
	        this.adminInstructorController = controller;
	    }
}
