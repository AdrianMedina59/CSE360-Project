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
import java.sql.ResultSet;
import java.sql.SQLException;

import AdminPage.RemoveController;

public class SearchArticlesController {

    @FXML
    private AnchorPane searchPage; // Reference to the AnchorPane
    @FXML
    private TextField searchTextField; // Field for search input
    @FXML
    private Button searchButton; // Button to trigger search
    @FXML
    private Button closeButton; // Button to trigger search
   

    public void initialize() {
        // Initialize any necessary components, such as setting up the table columns
    }

    @FXML
	public void search(ActionEvent event) throws IOException
	{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchArticle.fxml"));
		Parent searchArticleRoot  = loader.load();		
        // Set up the new stage and scene for the user list
        Stage newStage = new Stage();
        Scene searchScene = new Scene(searchArticleRoot);
        newStage.setTitle("Search Article");
        newStage.setScene(searchScene);
        newStage.show();
	}
    @FXML
    void closeWindow()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

   
}
