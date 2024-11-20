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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;

public class SpecialArticleListController {

    @FXML
    private TableView<String[]> SpecialArticlesTable;
    @FXML
    private TableColumn<String[], String> ID_Column;
    @FXML
    private Button searchButton;
    private String username;


    @FXML
    private TableColumn<String[], String> TItle_Column;
    @FXML
    private TableColumn<String[], String> Authors_Column;
    
    @FXML
	static
    DataBaseHelper dataBaseHelper = new DataBaseHelper();
    ArticleDisplayController articleDisplayController = new ArticleDisplayController();

    private static ObservableList<String[]> articleDataList = FXCollections.observableArrayList();

    public void initialize() {
        // Bind columns to data fields
        ID_Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[0]));
        TItle_Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
        Authors_Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));
        SpecialArticlesTable.setItems(articleDataList);
        
     // Add listener to handle item clicks
        SpecialArticlesTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to open article
                String[] selectedArticle = SpecialArticlesTable.getSelectionModel().getSelectedItem();
                if (selectedArticle != null) {
                    try {
                    	dataBaseHelper.connectToDatabase();
                    	Article article = dataBaseHelper.getSpecialArticleByName(dataBaseHelper.getConnection(), selectedArticle[1]);
						
						displayArticleContent(article);
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });

        loadAllArticles();
    }
    
    public void displayArticleContent(Article article) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayArticle.fxml"));
            Parent root = loader.load();

            // Get controller of the FXML to set article data
            ArticleDisplayController controller = loader.getController();
            controller.setArticleData(article);

            // Create a new stage for the article viewer
            Stage stage = new Stage();
            stage.setTitle("Article Viewer");

            // Create a new scene with the loaded root and set the CSS stylesheet
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/AdminPage/application.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    public void loadInstructorArticles() {
        try {
            dataBaseHelper.connectToDatabase();
            ResultSet resultSet = dataBaseHelper.getInstructorArticles(); // Only instructor articles
            loadArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	dataBaseHelper.closeConnection();
        }
    }
    
    @FXML
    public void loadSpecialArticles() {
        try {
            dataBaseHelper.connectToDatabase();
            ResultSet resultSet = dataBaseHelper.getSpecialArticles(username); // Only instructor articles
            loadArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	dataBaseHelper.closeConnection();
        }
    }
    
    public void setUsername(String username)
    {
    	this.username = username;
    }


    public static void loadArticleData(ResultSet resultSet) throws SQLException {
        articleDataList.clear();

        while (resultSet.next()) {
            articleDataList.add(new String[]{
            	String.valueOf(resultSet.getInt("id")),
                resultSet.getString("title"),
                resultSet.getString("Authors"),
            });
        }
    }

   
    

    public static void loadAllArticles() {
        try {
            dataBaseHelper.connectToDatabase();
            ResultSet resultSet = dataBaseHelper.getArticles(); // Assuming this method fetches all articles
            loadArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	dataBaseHelper.closeConnection();
        }
    }
    @FXML
    private void closeWindow() {
        // Code to close the window
        Stage stage = (Stage) SpecialArticlesTable.getScene().getWindow();
        stage.close();
    

    }

	public void loadAdminArticles() {
		try {
            dataBaseHelper.connectToDatabase();
            ResultSet resultSet = dataBaseHelper.getAdminArticles(); // Only instructor articles
            loadArticleData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	dataBaseHelper.closeConnection();
        }
		
	}
	
}

