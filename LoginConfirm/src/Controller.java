
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

	@FXML
	private Label titleLabel;
	@FXML
	private TextField FirstName_textField,PFirstName_textField,MiddleName_textField,LastName_textField,Email_textField;
	@FXML
	private Button ContinueButton;
    
	String FirstName,PFirstName,MiddleName,LastName,Email;
	
	
	public void Login(ActionEvent e)
	{
		if(checkValidLogIn() == true) {
			System.out.println("Valid Login");
		}
	}
	
	public boolean checkValidLogIn() {
		//boolean values to see if user input the values as valid
		boolean Fname = true;
		boolean Pname = true;
		boolean Mname = true;
		boolean Lname = true;
		boolean EmailBool = true;
		
		FirstName = FirstName_textField.getText();
		PFirstName = PFirstName_textField.getText();
		MiddleName = MiddleName_textField.getText();
		LastName = LastName_textField.getText();
		Email = Email_textField.getText();
		
		//setting each boolean value to corresponding boolean return value if false
		if(!NameRecognizer.isValidName(FirstName))
		{
			Fname = false;
		}
		if(!NameRecognizer.isValidName(PFirstName))
		{
			Pname = false;
		}
		if(!NameRecognizer.isValidName(MiddleName))
		{
			Mname = false;
		}
		if(!NameRecognizer.isValidName(LastName))
		{
			Lname = false;
		}
		if(!NameRecognizer.isValidEmail(Email))
		{
			EmailBool = false;
		}
		
		//checking for valid login
		if((Fname == true || Pname == true)&&(Mname == true) &&(Lname == true) && (EmailBool == true)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
}
