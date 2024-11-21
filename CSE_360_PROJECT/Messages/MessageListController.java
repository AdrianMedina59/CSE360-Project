package Messages;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Article.HelpArticleDisplayController;
import ConfirmLogin.DataBaseHelper;

public class MessageListController {

    @FXML
    private TableView<Message> messageTable;

    @FXML
    private TableColumn<Message, String> Sender_column;

    @FXML
    private TableColumn<Message, String> Receiver_column;

    @FXML
    private TableColumn<Message, String> MessageContent_column;

    @FXML
    private TextArea messageInput;

    @FXML
    private Button sendMessageButton;
    
    
    
    private static final DataBaseHelper databaseHelper = new DataBaseHelper();

	private String name;
    // ObservableList to hold article data
	private static final ObservableList<Message> MessageDataList = FXCollections.observableArrayList();

    // Initialize the message table and populate it with existing messages
    @FXML
    public void initialize() throws SQLException {
    	
        // Ensure these columns are set up correctly
        Sender_column.setCellValueFactory(new PropertyValueFactory<>("sender"));
        Receiver_column.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        MessageContent_column.setCellValueFactory(new PropertyValueFactory<>("message"));


        // Populate the table with initial messages
        messageTable.setItems(MessageDataList);

        // Load all messages when initialized
        loadAllMessages();
    }

    // Method to load all messages into the table (from database or any other source)
    public void loadAllMessages() throws SQLException {
        
            databaseHelper.connectToDatabase();
            ResultSet resultSet = databaseHelper.getMessages();  // Assuming you have a method that fetches all messages
            // Debug: Print the ResultSet contents
            while (resultSet.next()) {
                // Print the contents of each row in the ResultSet
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                String messageContent = resultSet.getString("message_content");
                System.out.println("Sender: " + sender + ", Receiver: " + receiver + ", Message: " + messageContent);
            }
            loadMessageData(resultSet);
            MessageDataList.clear();
            databaseHelper.closeConnection();
        
    }

    public static void loadMessageData(ResultSet resultSet) throws SQLException {
        MessageDataList.clear();  // Clear any previous data
        while (resultSet.next()) {
            // Create new Message object from the result set data
            Message message = new Message(
                resultSet.getString("sender"),
                resultSet.getString("receiver"),
                resultSet.getString("message_content") 

            );
            MessageDataList.add(message);  // Add the message to the list
        }
    }

    
  
  



    // Method to close the window
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) messageTable.getScene().getWindow();
        stage.close();
    }

	public void setName(String userName) throws SQLException {
		this.name = userName;
		loadAllMessages();
	}
}
