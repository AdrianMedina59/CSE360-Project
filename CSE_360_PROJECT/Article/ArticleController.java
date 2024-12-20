package Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.bouncycastle.pqc.crypto.DigestingMessageSigner;
import org.h2.expression.function.StringFunction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ConfirmLogin.*;

public class ArticleController {

    @FXML
	public TextField Title_textField; // For Title
    @FXML
	public TextField Keyword_textField; // For Keywords
    @FXML
	public TextArea Abstract_textArea; // For Abstract text
    @FXML
	public TextArea Body_textArea; // For Body
    @FXML
	public TextArea Links_textArea; // For Links
    @FXML
    private ChoiceBox<String> groupCategory; // For Class Category input
    @FXML
    private Button finish_button; // Button to finalize the article
    @FXML
    private ChoiceBox<String> studentArticleSend; // For choice of students to send to for article
    
    public DataBaseHelper dataBaseHelper = new DataBaseHelper();
    private String role;
    private String name;
    private String articleType;
 
    
    public void populateArticleFields(Article article) throws Exception {
        if (article != null) {
            Title_textField.setText(article.getTitle());
            Keyword_textField.setText(String.join(", ", article.getKeywords())); // Convert keywords to a comma-separated string
            Abstract_textArea.setText(article.getAbstractText());
            Body_textArea.setText(article.getBody());
            Links_textArea.setText(String.join(", ", article.getReferences())); // Convert references to a comma-separated string
            // Assuming `groupCategory` and `studentArticleSend` are populated elsewhere
        }
    }
    
    private void updateStudentChoiceBox(String className) throws SQLException {
        System.out.println("Updating ChoiceBox for class: " + className); // Debugging
        studentArticleSend.getItems().clear();
        List<String> students = dataBaseHelper.getStudentsInClass(className);

        if (students.isEmpty()) {
            System.out.println("No students found for class: " + className); // Debugging
        } else {
            System.out.println("Students found: " + students); // Debugging
        }

        studentArticleSend.getItems().addAll(students);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @FXML
    public void handleFinishArticle() {
        try {
            insertArticle(this.role);
            // Close the article creation window after finishing
            Stage stage = (Stage) finish_button.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void insertArticle(String role) throws Exception {
        // Logic to handle finishing the article
        String title = Title_textField.getText();
        String keywords = Keyword_textField.getText();
        String abstractText = Abstract_textArea.getText();
        String body = Body_textArea.getText();
        String linkText = Links_textArea.getText();
        

        // Implement your article creation logic here
        System.out.println("Article created: " + title);

        // Parse keywords, assume they are comma-separated
        String[] keywordString = keywords.split("\\s*,\\s*");

        // Parse references, assume they are comma-separated in Links_textArea
        String[] references = linkText.split("\\s*,\\s*");

        // Dummy authors - replace this with actual author data if you add an authors field
        String[] authors = new String[] {name}; // Default author for now

        // Create the article object
        Article article = new Article(title, authors, abstractText, keywordString, body, references);

        // Storing the article inside the database
        dataBaseHelper.connectToDatabase();
        
        // Inserting article based on role who is inserting
        if(role == "Admin") {
            dataBaseHelper.insertArticle(article, "Admin");
        } else if(role == "Instructor") {
            dataBaseHelper.insertArticle(article, "Instructor");
        } else if(role == "Student") {
            dataBaseHelper.insertArticle(article, "Student");
        } else if(role == "Admin Instructor") {
            dataBaseHelper.insertArticle(article, "Admin Instructor");    
        }
        else if(articleType == "Special")
        {
        	dataBaseHelper.insertSpecialArticles(article, role, name);
        }
       
        dataBaseHelper.displayArticles();
        dataBaseHelper.closeConnection();

        article.displayContent();
    }

    public void handleRestoreArticle() {
        try {
            // Get the deleted help articles using the getHelpArticlesD() method from DataBaseHelper
            ResultSet deletedArticles = dataBaseHelper.getArticlesD();

            // Assuming you want to get the title of the selected article, process the ResultSet
            if (deletedArticles.next()) {
                String selectedTitle = deletedArticles.getString("title"); // Assuming "title" is a column
                if (selectedTitle != null) {
                    // Restore the article
                    dataBaseHelper.restoreArticle(selectedTitle);  // Restore the article using its title
                    ArticleListController.loadAllArticles(); // Reload all articles (optional)
                } else {
                    System.out.println("No deleted articles to restore.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public void setArticleType(String type) {
		this.articleType = type;
	}
}