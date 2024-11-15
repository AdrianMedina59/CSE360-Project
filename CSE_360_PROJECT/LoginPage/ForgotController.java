/**
 * <p> functionality class for Forgot password page. </p>
 * 
 * <p> Description: The class loads houses all the functionality for the Forgot password.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package LoginPage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import ConfirmLogin.DataBaseHelper;

public class ForgotController {
	@FXML
	private AnchorPane ForgotPasswordAnchor;
    @FXML
    private Label CodeLabel, newPasswordLabel, ConfirmPasswordLabel, UsernameLabel;
    @FXML
    private TextField ForgotCode_textfield, username_texfield;
    @FXML
    private PasswordField newPassword_textfield, ConfirmnewPassword_textfield;
    private DataBaseHelper dataBase = new DataBaseHelper();

    public void submit() throws SQLException {
        dataBase.connectToDatabase();
        String forgotPasscode = ForgotCode_textfield.getText();
        String password = newPassword_textfield.getText();
        String Confirmpasscode = ConfirmnewPassword_textfield.getText();
        String username = username_texfield.getText();

        if (!dataBase.isPasscodeInResetCategory(forgotPasscode)) {
            CodeLabel.setText("Code not found!");
            return;
        }
        if (dataBase.getUser(username) == null) {
            UsernameLabel.setText("Username not found!");
            return;
        }
        if (!password.equals(Confirmpasscode)) {
            newPasswordLabel.setText("Please use the same password for both!");
            return;  // You should return here if the passwords don't match
        }
        dataBase.updatePassword(username, password);
        closeWindow();
    }
    
    void closeWindow()
    {
    	 // Code to close the window
        Stage stage = (Stage) ForgotPasswordAnchor.getScene().getWindow();
        stage.close();
    }
}