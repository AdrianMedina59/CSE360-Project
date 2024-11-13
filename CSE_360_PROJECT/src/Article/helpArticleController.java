package Article;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import ConfirmLogin.*;

public class helpArticleController {

    @FXML
    private TextField Title_textField; // For Help Article Title
    @FXML
    private TextArea Body_textArea; // For Help Article Body
    @FXML
    private Button finish_button; // Button to finalize the help article
    private DataBaseHelper dataBaseHelper = new DataBaseHelper();
    private String role;
    private String name;

    @FXML
    public void initialize() {
        System.out.println(finish_button); // Should not be null
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @FXML
    public void handleFinishHelpArticle() {
        try {
            insertHelpArticle(this.role);
            // Close the help article creation window after finishing
            Stage stage = (Stage) finish_button.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertHelpArticle(String role) throws Exception {
        // Logic to handle finishing the help article
        String title = Title_textField.getText();
        String body = Body_textArea.getText();

        // Validate inputs
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        // Implement your help article creation logic here
        System.out.println("Help Article created: " + title);

        // Create the help article object
        helpArticle helpArticle = new helpArticle(title, body);

        // Storing the help article inside the database
        dataBaseHelper.connectToDatabase();
        //Inserting article based on role who is inserting
        if(role == "Admin")
        {
        	dataBaseHelper.insertHelpArticle(helpArticle,"Admin");
        }
        else if(role == "Instructor")
        {
        	dataBaseHelper.insertHelpArticle(helpArticle,"Instructor");
        }

        else if(role == "Admin Instructor")
        {
        	dataBaseHelper.insertHelpArticle(helpArticle,"Admin Instructor");	
        }
        dataBaseHelper.displayArticles();
        dataBaseHelper.closeConnection();
        
        
        helpArticle.displayContent();
    }
    
    
    public void handleRestoreHelpArticle() {
        try {
            // Get the deleted help articles using the getHelpArticlesD() method from DataBaseHelper
            ResultSet deletedHelpArticles = dataBaseHelper.getHelpArticlesD();

            // Assuming you want to get the title of the selected article, process the ResultSet
            if (deletedHelpArticles.next()) {
                String selectedTitle = deletedHelpArticles.getString("title"); // Assuming "title" is a column
                if (selectedTitle != null) {
                    // Restore the article
                    dataBaseHelper.restoreHelpArticle(selectedTitle);  // Restore the article using its title
                    hArticleListController.loadAllHelpArticles(); // Reload all articles (optional)
                } else {
                    System.out.println("No deleted articles to restore.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
