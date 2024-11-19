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
    
    
    static DataBaseHelper databaseHelper = new DataBaseHelper();
    MessageDisplayController MessageDisplayController = new MessageDisplayController();
    // ObservableList to hold article data
    private static ObservableList<Message> MessageDataList = FXCollections.observableArrayList();

    // Initialize the message table and populate it with existing messages
    @FXML
    public void initialize() {
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
    static void loadAllMessages() {
        try {
            databaseHelper.connectToDatabase();
            ResultSet resultSet = databaseHelper.getMessages();  // Assuming you have a method that fetches all messages
            loadMessageData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHelper.closeConnection();
        }
    }

    public static void loadMessageData(ResultSet resultSet) throws SQLException {
        MessageDataList.clear();  // Clear any previous data
        while (resultSet.next()) {
            // Create new Message object from the result set data
            Message message = new Message(
                resultSet.getString("sender"),
                resultSet.getString("receiver"),
                resultSet.getString("message_content")  // Directly use the String value for message content
            );
            MessageDataList.add(message);  // Add the message to the list
        }
    }

    
    public void displayMessages(Message Message) {
        try {
            // Load the FXML for the MessageDisplayController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Messages.fxml"));
            Parent root = loader.load();
            
            // Get the MessageDisplayController instance from the FXML file
            MessageDisplayController displayController = loader.getController();
            
            Stage stage = new Stage();
            stage.setTitle("Message Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    



    // Method to close the window
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) messageTable.getScene().getWindow();
        stage.close();
    }
}
