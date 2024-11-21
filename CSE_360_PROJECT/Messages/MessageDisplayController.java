

package Messages;

import java.awt.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
public class MessageDisplayController {

    @FXML
    private Label senderLabel;
    @FXML
    private Label roleLabel;  
    @FXML
    private Label receiverLabel;
    @FXML
    private TextArea messagetextArea; 

    
    
    public void setMessageData(Message Message) throws Exception {
        senderLabel.setText(Message.getSender()); 
        roleLabel.setText(Message.getRole());
        receiverLabel.setText(Message.getReceiver());
        messagetextArea.setText(Message.getMessage());
    }

    @FXML
    private void closeWindow() {
        senderLabel.getScene().getWindow().hide(); 
    }


}
