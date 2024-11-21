package Messages;

public class Message {
    private String sender;
    private String receiver;
    private String role;
    private String message;

    // Constructor
    public Message(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.role = role;
        this.message = message;
    }

    // Getters for the properties
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }
}

	
