package AdminPage;

import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;          // Import JavaFX Label
import javafx.scene.control.TextField;     // Import JavaFX TextField
import javafx.scene.layout.AnchorPane;

public class ChangeRolesController {
    @FXML
    private AnchorPane changeAnchor;
    @FXML
    private TextField Username_textfield;   // Use JavaFX TextField
    @FXML
    private Label username_label;            // Use JavaFX Label
    private DataBaseHelper dataBase = new DataBaseHelper();
    
    public void setStudent() throws SQLException {
        String username = Username_textfield.getText();
        dataBase.connectToDatabase();
        if (dataBase.getUser(username) == null) {
            username_label.setText("Username not found");
        } else {
            dataBase.updateUserRole(username, "Student");
            username_label.setText("Role updated to Student"); // Optional feedback
        }
        dataBase.closeConnection();
    }
    
    public void setInstructor() throws SQLException {
        String username = Username_textfield.getText();
        dataBase.connectToDatabase();
        if (dataBase.getUser(username) == null) {
            username_label.setText("Username not found");
        } else {
            dataBase.updateUserRole(username, "Instructor");
            username_label.setText("Role updated to Instructor"); // Optional feedback
        }
        dataBase.closeConnection();
    } 
}