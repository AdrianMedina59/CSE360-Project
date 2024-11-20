package Messages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

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
    public void initialize() {
        System.out.println(AdminInstructor);
        System.out.println(YourInstructor);
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSender(String sender) {
        this.sender = sender; // Set the sender's name
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver; // Set the receiver's name
    }

    @FXML
    public void handleFinishAdminIns() {
        try {
            setReceiver(sender);
            insertMessage(this.role);
            System.out.println("Admin Instructor will be notified.");
            Stage stage = (Stage) AdminInstructor.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFinishInstructor() {
        try {
            insertMessage(this.role);
            setReceiver(sender);
            System.out.println("Instructor will be notified.");
            Stage stage = (Stage) YourInstructor.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertMessage(String role) throws Exception {
        // Get the message content
        String messageInput = message_Input.getText();

        if (messageInput.isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }

        System.out.println("Preparing to insert message with details:");
        System.out.println("Sender: " + sender);
        System.out.println("Receiver: " + receiver);
        System.out.println("Role: " + role);
        System.out.println("Message: " + messageInput);

        char[] messageContent = messageInput.toCharArray();

        // Create a new Message object
        Message message = new Message(sender, receiver, messageInput);
        dataBaseHelper.connectToDatabase();
        if(role == "Admin Instructor") {
            dataBaseHelper.insertMessage(message, "Admin Instructor");
        } else if(role == "Instructor") {
            dataBaseHelper.insertMessage(message, "Instructor");
        } else if(role == "Student") {
            dataBaseHelper.insertMessage(message, "Student");
        }

        dataBaseHelper.displayMessages(); 
        dataBaseHelper.closeConnection();
   
    }
}
