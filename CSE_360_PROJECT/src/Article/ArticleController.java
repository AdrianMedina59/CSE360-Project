package Article;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ArticleController {

    @FXML
    private TextField Username_textField; // For Header
    @FXML
    private TextField Username_textField1; // For Title
    @FXML
    private TextField Username_textField11; // For Keywords
    @FXML
    private TextArea shortDescription; // For Short Description
    @FXML
    private TextArea bodyOfArticle; // For Body
    @FXML
    private TextArea links; // For Links
    @FXML
    private TextArea sensitiveInfo; // For Sensitive Information
    @FXML
    private Button finishArticleButton; // Button to finalize the article

    @FXML
    public void initialize() {
        System.out.println(finishArticleButton); // Should not be null
    }

    @FXML
    public void handleFinishArticle() {
        try {
            // Logic to handle finishing the article
            String header = Username_textField.getText();
            String title = Username_textField1.getText();
            String keywords = Username_textField11.getText();
            String description = shortDescription.getText();
            String body = bodyOfArticle.getText();
            String linkText = links.getText();
            String sensitive = sensitiveInfo.getText();

            // Validate inputs
            if (title.isEmpty()) {
                System.out.println("Title cannot be empty.");
                return;
            }

            // Implement your article creation logic here
            System.out.println("Article created: " + title);

            // Close the article creation window after finishing
            Stage stage = (Stage) finishArticleButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}