package admin_Instructor;

import ConfirmLogin.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class studentgroupremove_Controller
{
	private AnchorPane Remove_student;
	
	private Button RemoveStudentFromGroup;
	private TextField Delete_User;
	private TextField Group_name;
	
	private DataBaseHelper dataBase = new DataBaseHelper();
	

    String Username = Delete_User.getText();
    String Group = Group_name.getText();

    // dataBase.connectToDatabase();











}
