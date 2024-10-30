/**
 * <p> Main class for Login page. </p>
 * 
 * <p> Description: The class loads all the FXML files and css files used for scene.</p>
 * 
 * <p> Copyright: Adrian Medina © 2024 </p>
 * 
 * @author Adrian Medina
 * 
 * @version 1.00		2024-10-06
 *  
 */

package LoginPage; // Replace with the correct package name if needed

import java.io.IOException;

import AdminPage.AdminPageHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginHandler extends Application {
	
	// Track successful and unsuccessful tests
    static int numPassed = 0;
    static int numFailed = 0;

    // Test credentials
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    	//root is set to the .fxml file which houses the buttons and text fields
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(root,Color.GOLD);
        //setting the login css 
        loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public void testing() {
        // Print basic UI separators
        System.out.println("____________________________________________________________________________");
        System.out.println("\nLogin Testing Automation");

        // Test cases for different login scenarios
        performTestCase(1, VALID_USERNAME, VALID_PASSWORD, true);  // valid admin credentials
        performTestCase(2, VALID_USERNAME, "wrongpass", false); // invalid password
        performTestCase(3, "wronguser", VALID_PASSWORD, false); // invalid username
        performTestCase(4, "", VALID_PASSWORD, false); // empty username
        performTestCase(5, VALID_USERNAME, "", false); // empty password
        performTestCase(6, "", "", false); // both empty
        performTestCase(7, "user@123", VALID_PASSWORD, false); // username with special characters
        performTestCase(8, "a", VALID_PASSWORD, false); // username too short
        performTestCase(9, VALID_USERNAME, "short", false); // password too short
        
        // Print summary
        System.out.println("____________________________________________________________________________");
        System.out.println();
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    private static void performTestCase(int testCase, String username, String password, boolean expectedPass) {
        // Print test case details
        System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
        System.out.println("Username: \"" + username + "\"");
        System.out.println("Password: \"" + password + "\"");
        System.out.println("______________");
        System.out.println("\nLogin attempt execution trace:");

        // Verify login credentials
        String loginResult = verifyLogin(username, password);
        boolean loginSuccess = loginResult.isEmpty();
        
        if (!loginSuccess) {
            if (expectedPass) {
                System.out.println("***Failure*** Login with credentials <" + username + ", " + password + "> failed." +
                        "\nBut it was supposed to succeed, so this is a failure!\n");
                System.out.println("Error message: " + loginResult);
                numFailed++;
            } else {
                System.out.println("***Success*** Login with credentials <" + username + ", " + password + "> failed." +
                        "\nAnd it was supposed to fail, so this is a pass!\n");
                System.out.println("Error message: " + loginResult);
                numPassed++;
            }
        } else {
            if (expectedPass) {
                System.out.println("***Success*** Login with credentials <" + username + ", " + password +
                        "> succeeded, so this is a pass!");
                numPassed++;
            } else {
                System.out.println("***Failure*** Login with credentials <" + username + ", " + password +
                        "> succeeded" +
                        "\nBut it was supposed to fail, so this is a failure!");
                numFailed++;
            }
        }
        displayLoginEvaluation(username, password);
    }

    private static void displayLoginEvaluation(String username, String password) {
        // Display validation checks
        if (!username.isEmpty())
            System.out.println("Username provided - Satisfied");
        else
            System.out.println("Username provided - Not Satisfied");

        if (!password.isEmpty())
            System.out.println("Password provided - Satisfied");
        else
            System.out.println("Password provided - Not Satisfied");

        if (username.length() >= 4)
            System.out.println("Username length (min 4 chars) - Satisfied");
        else
            System.out.println("Username length (min 4 chars) - Not Satisfied");

        if (username.matches("^[a-zA-Z0-9]+$"))
            System.out.println("Username format (alphanumeric only) - Satisfied");
        else
            System.out.println("Username format (alphanumeric only) - Not Satisfied");

        if (password.length() >= 8)
            System.out.println("Password length (min 8 chars) - Satisfied");
        else
            System.out.println("Password length (min 8 chars) - Not Satisfied");
        if (password.equals(username))
        	System.out.println("Passwords match - Satisfied");
        else
        	System.out.println("Passwords do not match - Not Satisfied");

        	
    }

    private static String verifyLogin(String username, String password) {
        // Check for empty fields
        if (username.isEmpty()) {
            return "Username cannot be empty";
        }
        if (password.isEmpty()) {
            return "Password cannot be empty";
        }

        // Check username requirements
        if (username.length() < 4) {
            return "Username must be at least 4 characters long";
        }
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            return "Username must contain only letters and numbers";
        }

        // Check password requirements
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }

        // Check credentials match
        if (!username.equals(VALID_USERNAME) || !password.equals(VALID_PASSWORD)) {
            return "Invalid username or password";
        }

        return ""; // Empty string indicates successful login
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}