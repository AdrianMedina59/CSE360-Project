package AdminPage;


import java.sql.SQLException;
import javafx.scene.control.TextField; 
import ClassManager.*;
import ConfirmLogin.*;
import javafx.fxml.FXML;

public class GroupController {

	@FXML
	private TextField GroupName_Textfield;
	@FXML 
	private TextField AdminInstructorName_Textfield;
    private DataBaseHelper dataBase = new DataBaseHelper();
	
	public void createGroup() throws SQLException
	{
		//getting the contents of the textfields
		String groupname = GroupName_Textfield.getText();
		String AdminInstructorName = AdminInstructorName_Textfield.getText();
		
		//checking if the user is in the database and role is equal to "Admin Instructor"
		dataBase.connectToDatabase();
		if(dataBase.getUser(AdminInstructorName) != null && "Admin Instructor".equals(dataBase.getRole(AdminInstructorName)))
		{
			//make the group and adding it to database
			Group group = new Group();
			group.setAdminInstructor(AdminInstructorName);
			group.setName(groupname);
			dataBase.saveGroup(group);
			System.out.println("Success!");
			dataBase.printGeneralGroups();
		}
		else {
			{
				System.out.printf("username: %s\n", dataBase.getUser(AdminInstructorName));
				System.out.printf("System role: %s\n", dataBase.getRole(AdminInstructorName));
				System.out.println("User isnt a valid Admin Instructor role!");
			}
		}
		dataBase.closeConnection();
	}
}
