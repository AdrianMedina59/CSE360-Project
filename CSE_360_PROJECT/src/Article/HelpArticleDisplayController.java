package Article;

import java.awt.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
public class HelpArticleDisplayController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label roleLabel;  // Display the role associated with the help article
    @FXML
    private TextArea bodyTextArea; // Display the body of the help article

    // Set help article data into the UI components
    public void setHelpArticleData(helpArticle helpArticle) throws Exception {
        titleLabel.setText(helpArticle.getTitle());  // Set title
        bodyTextArea.setText(helpArticle.getBody()); // Set the body content
    }

    @FXML
    private void closeWindow() {
        titleLabel.getScene().getWindow().hide();  // Close the current window
    }
}
