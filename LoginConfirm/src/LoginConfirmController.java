/**
 * <p> Controller Class. </p>
 * 
 * <p> Description: Controller class controls the continue button which communicates with SQL database.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
 * The controller class is mainly to handle the text input inside the text fields as well as to 
 * make sure that once the person presses the continue button it saves the data to the database if
 * valid input was given.
 * 
 */

public class LoginConfirmController {

	@FXML
	private Label titleLabel;	//title label
	@FXML
	private TextField FirstName_textField,PFirstName_textField,MiddleName_textField,LastName_textField,Email_textField;  //text fields used
	@FXML
	private Button ContinueButton;   //continue button
    
	String FirstName,PFirstName,MiddleName,LastName,Email;  //strings to use for names and email

	//database for the contents to put in
	private static final DataBaseHelper DATA_BASE_HELPER = new DataBaseHelper();
	
	//button method that checks for valid login, then directs to other page
	public void Login(ActionEvent e) throws SQLException
	{
		//trying to connect to data base first, if problem catch the exception
		try {
            DATA_BASE_HELPER.connectToDatabase();
            //once connected check for valid login- (debugging purposes input "valid login" to terminal)
            if (checkValidLogIn()) {
                System.out.println("Valid Login");
            }
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        } finally {
        	//after doing the login process we close the connection, then go to the user page (not implemented yet)
            DATA_BASE_HELPER.closeConnection();
        }
	}
	
	
	

	//method checks for valid login
	public boolean checkValidLogIn() throws SQLException {
		//boolean values to see if user input the values as valid
		boolean Fname = true;
		boolean Pname = true;
		boolean Mname = true;
		boolean Lname = true;
		boolean EmailBool = true;
		
		//setting string values to the text field values
		FirstName = FirstName_textField.getText();
		PFirstName = PFirstName_textField.getText();
		MiddleName = MiddleName_textField.getText();
		LastName = LastName_textField.getText();
		Email = Email_textField.getText();
		
		
		//setting each boolean value to corresponding boolean return value if false
		if(!NameRecognizer.isValidName(FirstName))
		{
			titleLabel.setText("Invalid First Name!");
			Fname = false;
		}
		if(!NameRecognizer.isValidName(PFirstName))
		{
			
			Pname = false;
		}
		if(!NameRecognizer.isValidName(MiddleName))
		{
			titleLabel.setText("Invalid Middle Name!");
			Mname = false;
		}
		if(!NameRecognizer.isValidName(LastName))
		{
			titleLabel.setText("Invalid Last Name!");
			Lname = false;
		}
		if(!NameRecognizer.isValidEmail(Email))
		{
			titleLabel.setText("Invalid Email!");
			EmailBool = false;
		}
		
		//checking for valid login
		if((Fname == true || Pname == true)&&(Mname == true) &&(Lname == true) && (EmailBool == true)) {
			
			//checks if someone already used the email
			if(isEmailExists(Email))
			{
				titleLabel.setText("Email already exists!");
				return false;
			}
			
			//boolean that sees if database is empty if it is set the 1st person to sign up to admin, others students
			Boolean isDataBaseEmpty = DATA_BASE_HELPER.isDatabaseEmpty();
			String role = isDataBaseEmpty ? "Admin" : "Student";
			
			//inputting content to database 
			DATA_BASE_HELPER.register(FirstName, PFirstName, MiddleName, LastName, Email,"itzel","password",role);
			
			//LINE HELPS WITH DEBUGGING DATA BASE//
//			DATA_BASE_HELPER.PrintUserTables();
			titleLabel.setText("Thanks!");
			
			return true;
		}
		else {
			return false;
		}
		
	}
	
	//checks if the email is already in data base
	private boolean isEmailExists(String email) throws SQLException{
		String query = "SELECT COUNT(*) AS count FROM users WHERE email = ?";
		try(PreparedStatement pstmt = DATA_BASE_HELPER.getConnection().prepareStatement(query))
				{
					pstmt.setString(1, email);
					ResultSet resultSet = pstmt.executeQuery();
					if(resultSet.next())
					{
						return resultSet.getInt("count") > 0;
					}
				}
				return false;
			}
}
