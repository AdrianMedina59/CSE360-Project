package admin_Instructor;

import java.sql.SQLException;
import java.util.List;

import ConfirmLogin.DataBaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class removeStudentController {

    @FXML
	public ChoiceBox<String> RemoveStudent_choiceBox;
	@FXML
	public ChoiceBox<String> class_ChoiceBox;
    @FXML
    private DataBaseHelper dataBase = new DataBaseHelper();
    private String username;
    private boolean isChoiceBoxInitialized = false;

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Child Controller Username: " + username);

        // Populate choice boxes if they haven't been initialized yet
        if (!isChoiceBoxInitialized) {
            populateChoiceBoxes();
        }
    }

    public void initialize() {
        System.out.println("initialize() called.");

        // Add listener to update the student choice box when a class is selected
        class_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                System.out.println("Class selected: " + newValue);
                updateStudentChoiceBox(newValue);
            } catch (SQLException e) {
                System.out.println("Error fetching students: " + e.getMessage());
            }
        });
    }

    public void populateChoiceBoxes() {
        try {
            if (username == null) {
                System.out.println("Null username! Cannot populate choice boxes.");
                return;
            }

            dataBase.connectToDatabase();

            // Populating the class choice box with class names
            List<String> classNames = dataBase.getClassNamesByDepartment(dataBase.getGroupIdByAdminInstructor(username));
            System.out.println("Classes in Department " + dataBase.getGroupIdByAdminInstructor(username) + ":");
            class_ChoiceBox.getItems().addAll(classNames);

            dataBase.closeConnection();
            isChoiceBoxInitialized = true; // Mark initialization as complete
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudentChoiceBox(String selectedClass) throws SQLException {
        if (selectedClass == null) {
            System.out.println("No class selected.");
            return;
        }

        // Clear the current items in the student choice box
        RemoveStudent_choiceBox.getItems().clear();

        // Fetch students in the selected class
        dataBase.connectToDatabase();
        List<String> studentsInClass = dataBase.getStudentsInClass(selectedClass);
        dataBase.closeConnection();

        if (studentsInClass.isEmpty()) {
            System.out.println("No students found in class: " + selectedClass);
        } else {
            System.out.println("Students found in class: " + studentsInClass);
        }

        // Populate the student choice box with the retrieved student list
        RemoveStudent_choiceBox.getItems().addAll(studentsInClass);
    }

    public void removeStudent() throws SQLException {
        // Get selected values from choice boxes
        String selectedClass = class_ChoiceBox.getValue();
        String selectedStudent = RemoveStudent_choiceBox.getValue();

        if (selectedClass == null || selectedStudent == null) {
            System.out.println("Please select both a class and a student.");
            return;
        }

        dataBase.connectToDatabase();

        // Retrieve the classId and userId for the selected names
        int classId = dataBase.getClassIdByName(selectedClass);
        int userId = dataBase.getUserIdByName(selectedStudent);

        if (classId == -1 || userId == -1) {
            System.out.println("Could not find class or student in the database.");
            return;
        }

        // Remove the student from the class
        dataBase.removeStudentFromClass(userId, classId);
        System.out.println("Student successfully removed from the class.");
        dataBase.printClassStudentsTable();

        dataBase.closeConnection();
    }
}
