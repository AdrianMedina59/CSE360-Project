package Article;

import java.awt.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ArticleDisplayController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label keywordsLabel;
    @FXML
    private Label abstractTextArea;
    @FXML
    private Label bodyTextArea;
    @FXML
    private Label refrencesTextArea;

    // Set article data into the UI components
    public void setArticleData(Article article) throws Exception {
        titleLabel.setText(article.getTitle());
        authorLabel.setText(String.join(", ", article.getAuthors()));
        keywordsLabel.setText(String.join(", ", article.getKeywords()));
        abstractTextArea.setText(article.getAbstractText());
        bodyTextArea.setText(article.getBody());
        refrencesTextArea.setText(String.join(", ", article.getReferences()));
    }

    @FXML
    private void closeWindow() {
        titleLabel.getScene().getWindow().hide();
    }
}