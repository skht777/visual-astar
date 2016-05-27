/**
 * 
 */
package com.skht777.vastar;

import java.net.URL;
import java.util.ResourceBundle;

import com.skht777.vastar.algorithm.AStar;

import javafx.fxml.Initializable;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Button;

/**
 * @author skht777
 *
 */
public class BoxController implements Initializable {

	@FXML private GridController gridController;
	@FXML private Button button;
	private Timeline timer;
	private AStar astar;

	@FXML
	private void launch(){
		if (timer.getStatus().equals(Status.RUNNING)) {
			button.setText("スタート");
			timer.pause();
		} else {
			if (timer.getStatus().equals(Status.STOPPED) || !astar.canContinue()) {
				Element.launch(astar);
				gridController.setElementDisable(true);
			}
			button.setText("ポーズ");
			timer.play();
		}
	}
	
	@FXML
	private void stop() {
		if (timer.getStatus().equals(Status.STOPPED)) return;
		button.setText("スタート");
		timer.stop();
		astar.clear();
		gridController.setElementDisable(false);
	}
	
	@FXML
	private void reset() {
		if (timer.getStatus().equals(Status.RUNNING)) stop();
		gridController.clear();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		astar = gridController.getSolver();
		timer = new Timeline();
		timer.getKeyFrames().add(new KeyFrame(Duration.millis(100), e->{
			if (astar.canContinue()) astar.continueSolve();
			else launch();
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
	}
	
}
