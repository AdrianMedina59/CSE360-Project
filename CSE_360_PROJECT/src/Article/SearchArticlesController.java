package Article; // Change this package if needed

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import AdminPage.RemoveController;
import ConfirmLogin.*;
public class SearchArticlesController {

    @FXML
    private AnchorPane searchPage; // Reference to the AnchorPane
    @FXML
    private TextField Title_textField; // Field for search input
    @FXML
    private TextField Keyword_textfield; // Field for search input
    @FXML
    private Button searchButton; // Button to trigger search
    @FXML
    private Button closeButton; // Button to trigger search
    private DataBaseHelper dataBase = new DataBaseHelper();
    		

    public void initialize() {
        // Initialize any necessary components, such as setting up the table columns
    }

    @FXML
	public void search(ActionEvent event) throws Exception
	{
      dataBase.connectToDatabase();
      Connection connection = dataBase.getConnection();
      String title = Title_textField.getText();
      String keyword = Keyword_textfield.getText();
      
      if(!dataBase.isValidArticle(title, keyword))
      {
    	  System.out.println("Article NOT FOUND!");
      }else {
    	  dataBase.displayArticles();
          Article article = dataBase.getArticleByName(connection,title);
         
          if (article != null) {
              displayArticle(article);
          } else {
              System.out.println("Article retrieval failed.");
          }
      }
      
    
    	  dataBase.closeConnection();
	}
    
    public void displayArticle(Article article) throws Exception {
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
    void closeWindow()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

   
}
