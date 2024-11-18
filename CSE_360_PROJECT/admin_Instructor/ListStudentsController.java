package admin_Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListStudentsController {

	@FXML
    private TableView<String[]> Students_TableView;
    @FXML
    private TableColumn<String[], String> IdColumn;
    @FXML
    private TableColumn<String[], String> StudentsColumn;
    @FXML
    private TableColumn<String[], String> ClassesColumn;

    private static final DataBaseHelper dataBaseHelper = new DataBaseHelper();
    private static final ObservableList<String[]> classDataList = FXCollections.observableArrayList();
    private String  username;
    public void initialize() throws SQLException {
        // Bind columns to data fields
    	IdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[0]));
    	StudentsColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
    	ClassesColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));

     
        // Set data in the TableView
    	Students_TableView.setItems(classDataList);

     // Load all classes from the database
        loadAllStudents(); 
    }
    
    public void setUsername(String usernname)
    {
    	this.username = usernname;
    }
    
    @FXML
    public void loadAllStudents() throws SQLException {
        // Connect to the database
        dataBaseHelper.connectToDatabase();
        
        // Fetch the classes from the database
        ResultSet resultSet = dataBaseHelper.getStudentsByDepartment(dataBaseHelper.getGroupIdByAdminInstructor(username)); // Method to fetch all classes
        loadStudentsData(resultSet);
        // Clear existing data from the list before loading new data
        classDataList.clear();
        
        dataBaseHelper.closeConnection();
    }
    public  void loadStudentsData(ResultSet resultSet) throws SQLException {
        // Loop through the result set
        while (resultSet.next()) {
            // Get values from resultSet for each column
        	int studentId = resultSet.getInt("studentId"); // Ensure "studentId" matches the query alias
            String studentName = resultSet.getString("studentName"); // Ensure alias matches here too
            String enrolledClasses = resultSet.getString("enrolledClasses"); // Same for this column

          

            // Add values to the ObservableList
            classDataList.add(new String[] {
                String.valueOf(studentId), 
                studentName,                
                enrolledClasses         
            });
        }
    }
}
