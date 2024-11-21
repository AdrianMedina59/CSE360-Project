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

import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.DataBaseHelper;

public class EditSpecialArticleListController {

    @FXML
    private TableView<String[]> SpecialArticlesTable;
    @FXML
    private TableColumn<String[], String> ID_Column;
    @FXML
    private TableColumn<String[], String> Title_Column;
    @FXML
    private TableColumn<String[], String> Authors_Column;
    @FXML
    private Button searchButton;

    private String username;
    private static final DataBaseHelper dataBaseHelper = new DataBaseHelper();
    private final ObservableList<String[]> articleDataList = FXCollections.observableArrayList();

    public void initialize() {
        setCells(); // Configure table columns
        HandleMouse();
    }

    public void setCells() {
        ID_Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[0]));
        Title_Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[1]));
        Authors_Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[2]));
        SpecialArticlesTable.setItems(articleDataList);
    }

    public void setUsername(String username) {
        this.username = username;
        if (username != null) {
            loadSpecialArticles();
        } else {
            System.out.println("Username is not set. Cannot load special articles.");
        }
    }

    @FXML
    public void loadSpecialArticles() {
        if (username == null) {
            System.out.println("Username is not set. Cannot load special articles.");
            return;
        }
        ResultSet resultSet;
        try {
            dataBaseHelper.connectToDatabase();
            if ("Instructor".equals(dataBaseHelper.getRole(username))) {
            	 resultSet = dataBaseHelper.getAllSpecialArticles();
            }
            else {
            	  resultSet = dataBaseHelper.getSpecialArticles(username); // Query articles for the username
			}
           
            articleDataList.clear(); // Clear previous data

            System.out.println("Loading special articles for user: " + username);

            while (resultSet.next()) {
                String id = String.valueOf(resultSet.getInt("id"));
                String title = resultSet.getString("title");
                String authors = resultSet.getString("Authors");

                // Add the article to the observable list
                articleDataList.add(new String[]{id, title, authors});

                // Debugging output
                System.out.printf("ID: %s, Title: %s, Authors: %s%n", id, title, authors);
            }

            resultSet.close(); // Close the ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBaseHelper.closeConnection();
        }
    }

    @FXML
    private void HandleMouse() {
        SpecialArticlesTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click to open article
                String[] selectedArticle = SpecialArticlesTable.getSelectionModel().getSelectedItem();
                if (selectedArticle != null) {
                    try {
                        dataBaseHelper.connectToDatabase();
                        Article article = dataBaseHelper.getSpecialArticleByName(
                                dataBaseHelper.getConnection(), selectedArticle[1]
                        );
                        displayArticleContent(article);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void displayArticleContent(Article article) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("displayArticle.fxml"));
        Parent root = loader.load();

        // Pass article data to the controller
        ArticleDisplayController controller = loader.getController();
        controller.setArticleData(article);

        Stage stage = new Stage();
        stage.setTitle("Article Viewer");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/AdminPage/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) SpecialArticlesTable.getScene().getWindow();
        stage.close();
    }
}
