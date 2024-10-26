package Article;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleListController {

    @FXML
    private TableView<String[]> ArticlesTable;
    @FXML
    private TableColumn<String[], String> ID_column;
    @FXML
    private Button searchButton;


    @FXML
    private TableColumn<String[], String> Title_column;
    @FXML
    private TableColumn<String[], String> Authors_column;
    @FXML
    private TableColumn<String[], String> Keywords_column;
    @FXML
    private TableColumn<String[], String> Body_column;
    @FXML
    private TableColumn<String[], String> Links_column;
    @FXML

    private ObservableList<String[]> articleDataList = FXCollections.observableArrayList();

    public void initialize() {
        // Bind columns to data fields
        ID_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[0]));
        Title_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
        Authors_column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));
        ArticlesTable.setItems(articleDataList);
    }

    public void loadArticleData(ResultSet resultSet) throws SQLException {
        articleDataList.clear();

        while (resultSet.next()) {
            articleDataList.add(new String[]{
            	String.valueOf(resultSet.getInt("id")),
                resultSet.getString("title"),
                resultSet.getString("Authors"),
            });
        }
    }

    
    @FXML
    private void closeWindow() {
        // Code to close the window
        Stage stage = (Stage) ArticlesTable.getScene().getWindow();
        stage.close();
    

    }
    
}
