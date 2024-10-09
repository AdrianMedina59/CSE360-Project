package AdminPage;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserListController {

    @FXML
    private TableView<String[]> UsersTable;

    @FXML
    private TableColumn<String[], Integer> ID_column;
    @FXML
    private TableColumn<String[], String> FirstName_column;
    @FXML
    private TableColumn<String[], String> PName_column;
    @FXML
    private TableColumn<String[], String> MiddleName_column;
    @FXML
    private TableColumn<String[], String> LastName_column;
    @FXML
    private TableColumn<String[], String> Username_column;
    @FXML
    private TableColumn<String[], String> password_column;
    @FXML
    private TableColumn<String[], String> Irole_column;
    @FXML
    private TableColumn<String[], String> email_column;

    // ObservableList to hold the user data as String arrays
    private ObservableList<String[]> userDataList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize table columns with correct bindings
    	ID_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Integer.parseInt(cellData.getValue()[0])));
        FirstName_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
        PName_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));
        MiddleName_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[3]));
        LastName_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[4]));
        email_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[5]));
        Username_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[6]));
        password_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[7])); // Binding password
        Irole_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[8]));

        // Set the items to the TableView
        UsersTable.setItems(userDataList);
    }

    public void loadUserData(ResultSet resultSet) throws SQLException {
        // Clear existing data
        userDataList.clear();

        while (resultSet.next()) {
            // Add each user data as a String array
            userDataList.add(new String[]{
            		String.valueOf(resultSet.getInt("id")),
                    resultSet.getString("FirstName"),
                    resultSet.getString("PreferredName"),
                    resultSet.getString("MiddleName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("email"),
                    resultSet.getString("username"),
                    resultSet.getString("password"), // Add password here
                    resultSet.getString("role")
            });
        }
    }

    @FXML
    private void closeWindow() {
        // Code to close the window
        Stage stage = (Stage) UsersTable.getScene().getWindow();
        stage.close();
    }
}