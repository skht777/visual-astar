/**
 * 
 */
package com.skht777.vastar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * @author skht777
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(FXMLLoader.load(getClass().getResource("resource/main.fxml")));
			primaryStage.setTitle("迷路作るやつ");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
