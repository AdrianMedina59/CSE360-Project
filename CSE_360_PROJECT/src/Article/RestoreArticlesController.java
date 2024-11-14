package Article;

import ConfirmLogin.*;

import java.awt.Button;
import javafx.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class RestoreArticlesController {
    @FXML
    private Button restoreGeneralButton;

    @FXML
    private Button restoreHelpButton;

    @FXML
    private Button restoreGroupButton;

    @FXML
    private Button restoreClassButton;

	private DataBaseHelper dataBase = new DataBaseHelper();


    
    public void restoreDeletedGeneralArticles(ActionEvent event) {
	    // Confirm restoration action
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Restore Help Articles");
	    alert.setHeaderText("Restore Deleted General Articles");
	    alert.setContentText("Are you sure you want to restore all deleted articles?");

	    if (alert.showAndWait().get() == ButtonType.OK) {
	        try {
	            // Connect to the database
	            dataBase.connectToDatabase();  // Use the already existing connection from dataBase
	            
	            // Create SQL query to restore deleted articles
	            String restoreQuery = "UPDATE articles SET deleted = false WHERE deleted = true";
	            
	            // Create a statement to execute the query
	            Statement statement = dataBase.getConnection().createStatement();
	            statement.executeUpdate(restoreQuery);  // Executes the query
	            
	            // Inform the user that the articles were restored
	            showInfoAlert("Success", "Articles Restored", "All deleted articles have been restored.");
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Show error message if something goes wrong
	            showErrorAlert("Failed to Restore Articles", "There was an error restoring the articles.");
	        } finally {
	            // Ensure the connection is closed after the operation
	            dataBase.closeConnection();
	        }
	    }
    }

    
    public void restoreDeletedHelpArticles(ActionEvent event) {
	    // Confirm restoration action
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Restore Help Articles");
	    alert.setHeaderText("Restore Deleted Help Articles");
	    alert.setContentText("Are you sure you want to restore all deleted articles?");

	    if (alert.showAndWait().get() == ButtonType.OK) {
	        try {
	            // Connect to the database
	            dataBase.connectToDatabase();  // Use the already existing connection from dataBase
	            
	            // Create SQL query to restore deleted articles
	            String restoreQuery = "UPDATE help_articles SET deleted = false WHERE deleted = true";
	            
	            // Create a statement to execute the query
	            Statement statement = dataBase.getConnection().createStatement();
	            statement.executeUpdate(restoreQuery);  // Executes the query
	            
	            // Inform the user that the articles were restored
	            showInfoAlert("Success", "Articles Restored", "All deleted articles have been restored.");
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Show error message if something goes wrong
	            showErrorAlert("Failed to Restore Articles", "There was an error restoring the articles.");
	        } finally {
	            // Ensure the connection is closed after the operation
	            dataBase.closeConnection();
	        }
	    }
	}    

    
    public void restoreDeletedGroupArticles(ActionEvent event) {
    }

    
    public void restoreDeletedClassArticles(ActionEvent event) {
    }


    
	// Utility method to show success alerts
	private void showInfoAlert(String title, String header, String content) {
	    Alert successAlert = new Alert(AlertType.INFORMATION);
	    successAlert.setTitle(title);
	    successAlert.setHeaderText(header);
	    successAlert.setContentText(content);
	    successAlert.showAndWait();
	}

	// Utility method to show error alerts
	private void showErrorAlert(String header, String content) {
	    Alert errorAlert = new Alert(AlertType.ERROR);
	    errorAlert.setTitle("Error");
	    errorAlert.setHeaderText(header);
	    errorAlert.setContentText(content);
	    errorAlert.showAndWait();
	}
    
    
}
