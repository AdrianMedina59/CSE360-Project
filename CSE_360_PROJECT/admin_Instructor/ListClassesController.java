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

public class ListClassesController {

	
		    @FXML
		    private TableView<String[]> ClassesTable;
		    @FXML
		    private TableColumn<String[], String> ID_column;
		    @FXML
		    private TableColumn<String[], String> Name_column;
		    @FXML
		    private TableColumn<String[], String> Instructor_column;

		    private static final DataBaseHelper dataBaseHelper = new DataBaseHelper();
		    private static final ObservableList<String[]> classDataList = FXCollections.observableArrayList();
		    private String  username;
		
		    public void initialize() throws SQLException {
		        // Bind columns to data fields
		        ID_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[0]));
		        Name_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
		        Instructor_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));

		     
		        // Set data in the TableView
		        ClassesTable.setItems(classDataList);

		     // Load all classes from the database
		        loadAllClasses(); 
		    }
		    

		    public void setUsername(String usernname)
		    {
		    	this.username = usernname;
		    }
		    
		    
		    @FXML
		    public void loadAllClasses() throws SQLException {
		        // Connect to the database
		        dataBaseHelper.connectToDatabase();
		        
		        // Fetch the classes from the database
		        ResultSet resultSet = dataBaseHelper.getClassesByDepartment(dataBaseHelper.getGroupIdByAdminInstructor(username)); // Method to fetch all classes
		        loadClassData(resultSet);
		        // Clear existing data from the list before loading new data
		        classDataList.clear();
		        
		        dataBaseHelper.closeConnection();
		    }

		    
		    public  void loadClassData(ResultSet resultSet) throws SQLException {
		        // Loop through the result set
		        while (resultSet.next()) {
		            // Get values from resultSet for each column
		            int id = resultSet.getInt("id");
		            String name = resultSet.getString("name");
		            String instructor = resultSet.getString("Instructor");

		            // Print the values for debugging
		            System.out.println("ID: " + id + ", Name: " + name + ", Instructor: " + instructor);

		            // Add values to the ObservableList
		            classDataList.add(new String[] {
		                String.valueOf(id),  // Convert id to String
		                name,                // Class name
		                instructor           // Instructor name
		            });
		        }
		    }


}
