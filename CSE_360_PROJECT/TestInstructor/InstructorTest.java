package TestInstructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import admin_Instructor.Addstudent_toclass;
import ConfirmLogin.DataBaseHelper;
import admin_Instructor.removeStudentController;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.ChoiceBox;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstructorTest {

    private Addstudent_toclass addStudentController;
    private DataBaseHelper dataBaseHelper;
    private removeStudentController removeStudentController;
    @BeforeEach
    public void setUp() throws SQLException {
        addStudentController = new Addstudent_toclass();
        dataBaseHelper = new DataBaseHelper();
        dataBaseHelper.connectToDatabase();


        
    }

    @AfterEach
    public void tearDown() {
        try {
            dataBaseHelper.connectToDatabase();
            dataBaseHelper.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddStudentToClass() throws SQLException {
        dataBaseHelper.connectToDatabase();
        addStudentController.initialize();
        addStudentController.populateChoiceBoxes();
        addStudentController.AddStudentToClass();
        


        
    }
    
    @Test
    public void testRemoveStudentToClass() throws SQLException {
    	dataBaseHelper.connectToDatabase();
    	removeStudentController.initialize();
    	removeStudentController.populateChoiceBoxes();
    	removeStudentController.removeStudent();
    }
}

