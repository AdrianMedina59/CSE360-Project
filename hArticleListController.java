package Article;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import ConfirmLogin.DataBaseHelper;

public class hArticleListController {

    @FXML
    private TableView<String[]> HelpArticlesTable;
    @FXML
    private TableColumn<String[], String> ID_column;
    @FXML
    private TableColumn<String[], String> Title_column;
    @FXML
    private TableColumn<String[], String> Body_column;
    @FXML
    private Button searchButton;
    @FXML
	static
    DataBaseHelper databaseHelper = new DataBaseHelper();
    HelpArticleDisplayController HelpArticleDisplayController = new HelpArticleDisplayController();
    // ObservableList to hold article data
    private static ObservableList<String[]> helpArticleDataList = FXCollections.observableArrayList();


    
    
    public void initialize() {
        // Bind columns to data fields
        ID_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[0]));
        Title_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
        Body_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));
        HelpArticlesTable.setItems(helpArticleDataList);
        
        // Add listener to handle item clicks (double-click to open the article)
    

        // Load help articles when initialized
        loadAllHelpArticles();
    }

    // Method to load all help articles into the table
    static void loadAllHelpArticles() {
        try {
            databaseHelper.connectToDatabase();
            ResultSet resultSet = databaseHelper.getHelpArticles(); // Assuming this method fetches all help articles
            loadHelpArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHelper.closeConnection();
        }
    }

    // Method to load help article data into the table
    public static void loadHelpArticleData(ResultSet resultSet) throws SQLException {
        helpArticleDataList.clear(); // Clear the list before adding new data

        while (resultSet.next()) {
            helpArticleDataList.add(new String[]{
                String.valueOf(resultSet.getInt("id")),  // ID column
                resultSet.getString("title"),
                resultSet.getString("Body"),// Title column
            });
        }
    }

    // Method to display the content of the selected help article
    public void displayHelpArticleContent(helpArticle helpArticle) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpArticleList.fxml"));
            Parent root = loader.load();

            // Get controller of the FXML to set article data
            HelpArticleDisplayController controller = loader.getController();
            controller.setHelpArticleData(helpArticle);

            // Create a new stage for the article viewer
            Stage stage = new Stage();
            stage.setTitle("Help Article Viewer");

            // Create a new scene with the loaded root and set the CSS stylesheet
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/AdminPage/application.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    // Method to close the window
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) HelpArticlesTable.getScene().getWindow();
        stage.close();
    }
    
}
