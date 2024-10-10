/**
 * 
 */
/**
 * 
 */
module CSE_360_PROJECT {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires java.sql;
	requires com.h2database;
	requires javafx.base;
	exports InstructorPage; 
	requires java.desktop;
	exports ConfirmLogin to javafx.graphics, javafx.fxml;
    opens ConfirmLogin to javafx.fxml;
    exports AdminPage to javafx.fxml;
    opens AdminPage to javafx.fxml;
    exports LoginPage;
    opens LoginPage to javafx.fxml;
    opens StudentPage to javafx.fxml;
    exports StudentPage;
    opens InstructorPage to javafx.fxml;
    


}