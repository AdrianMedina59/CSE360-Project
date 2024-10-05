




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
		FirstName = FirstName_textField.getText();
		NameRecognizer.CheckName(FirstName);
		System.out.printf("First Name: %s\n",FirstName);
		PFirstName = PFirstName_textField.getText();
		System.out.printf("Preferred First Name: %s\n",PFirstName);
		MiddleName = MiddleName_textField.getText();
		System.out.printf("Middle Name: %s\n",MiddleName);
		LastName = LastName_textField.getText();
		System.out.printf("Last Name: %s\n",LastName);
		Email = Email_textField.getText();
		System.out.printf("Email: %s\n",Email);
	}
	
}
