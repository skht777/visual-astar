/**
 *
 * @author skht777
 *
 */
module com.skht.vastar {
	requires javafx.controls;
	requires javafx.fxml;
	exports com.skht777.vastar to javafx.graphics;
	opens com.skht777.vastar to javafx.fxml;
}