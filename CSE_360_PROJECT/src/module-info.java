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
	exports ConfirmLogin to javafx.graphics, javafx.fxml;
    opens ConfirmLogin to javafx.fxml;
    exports AdminPage to javafx.fxml;
    opens AdminPage to javafx.fxml;

}