package Messages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

import ConfirmLogin.DataBaseHelper;

public class MessageController {

    @FXML
    private TextField message_Input; 
    @FXML
    private Button AdminInstructor, YourInstructor; 
    private DataBaseHelper dataBaseHelper = new DataBaseHelper();
    private String role;
    private String sender;
    private String receiver;
    @FXML
    private ChoiceBox<String> InstructorChoiceBox,adminInstructorChoiceBox;
    private String username;
    
    private List<String> classes;
    
    @FXML
    public void initialize() {
        System.out.println(AdminInstructor);
        System.out.println(YourInstructor);
    }


    
    public void populateChoiceBoxes() throws SQLException
	 {
		dataBaseHelper.connectToDatabase(); 
		// populating the admin instructor choice box
		
       List<String> adminInstructors = dataBaseHelper.getAdminInstructors(dataBaseHelper.getGeneralGroupIds(classes));
       if (adminInstructors.isEmpty()) {
    	    System.out.println("Admin Instructors list is empty.");
    	} else {
    	    System.out.println("Admin Instructors retrieved successfully:");
    	    for (int i = 0; i < adminInstructors.size(); i++) {
    	        System.out.println("Index " + i + ": " + adminInstructors.get(i));
    	    }
    	}
       adminInstructorChoiceBox.getItems().addAll(adminInstructors);
       
    // Populating the instructor choice box with instructor names
       List<String> Instructors = dataBaseHelper.getInstructorsFromClasses(classes);
       InstructorChoiceBox.getItems().addAll(Instructors);
	 }
    
    public void setReceiver(String receiver) {
        this.receiver = receiver; // Set the receiver's name
    }
    
    public void setUsername(String usernname)
    {
    	this.username = usernname;
    }
    


    public void insertMessage() throws Exception {
    	
        dataBaseHelper.connectToDatabase();
        String messageInput = message_Input.getText();
        
        if (messageInput.isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }

      
        if (InstructorChoiceBox.getValue() != null)
        {
        	// Create a new Message object
        	Message message = new Message(username, InstructorChoiceBox.getValue(), messageInput);
        	dataBaseHelper.insertMessage(message);
        }
        else if (adminInstructorChoiceBox.getValue() != null)
        {
        	  // Create a new Message object
        	Message message = new Message(username, adminInstructorChoiceBox.getValue(), messageInput);
        	dataBaseHelper.insertMessage(message);
        }
        else {
        	System.out.println("Please select a someone to send message");
        }
    
        dataBaseHelper.displayMessages(); 
        dataBaseHelper.closeConnection();
   
    }


	public void setClasses(List<String> classesFromStudent) throws SQLException {
			this.classes = classesFromStudent;
			if (this.classes == null) {
			    System.out.println("Error: this.classes is null.");
			} else if (this.classes.isEmpty()) {
			    System.out.println("Error: this.classes is empty. No classes provided.");
			} else {
			    System.out.println("Debugging this.classes (size: " + this.classes.size() + "):");
			    for (String cls : this.classes) {
			        System.out.println("- " + cls);
			    }
			}
			populateChoiceBoxes();
	}



	
}
