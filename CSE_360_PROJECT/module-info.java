

module CSE_360_PROJECT {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    opens admin_Instructor to javafx.fxml;

    requires java.sql;
    requires com.h2database;
    requires javafx.base;
    exports Article to javafx.fxml;
    opens Article to javafx.fxml;
    exports InstructorPage; 
    requires java.desktop;
    requires org.bouncycastle.provider;
    requires java.management;
    exports ConfirmLogin to javafx.graphics, javafx.fxml;
    opens ConfirmLogin to javafx.fxml;
    exports AdminPage to javafx.fxml;
    opens AdminPage to javafx.fxml;
    exports LoginPage;
    opens LoginPage to javafx.fxml;
    opens StudentPage to javafx.fxml;
    exports StudentPage;
    opens InstructorPage to javafx.fxml;
    exports admin_Instructor;
    exports Messages;
    opens Messages to javafx.fxml;



}