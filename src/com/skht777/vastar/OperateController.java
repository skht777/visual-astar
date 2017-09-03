package com.skht777.vastar;

import com.skht777.vastar.algorithm.AStar;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class OperateController implements Initializable {
	private Timeline timer;
	private AStar astar;

	@FXML
	private void launch() {
		if (timer.getStatus().equals(Animation.Status.RUNNING)) {
			//button.setText("スタート");
			timer.pause();
		} else {
			if (timer.getStatus().equals(Animation.Status.STOPPED) || !astar.canContinue()) {
				Element.launch(astar);
				//gridController.setElementDisable(true);
			}
			//button.setText("ポーズ");
			timer.play();
		}
	}

	@FXML
	private void stop() {
		if (timer.getStatus().equals(Animation.Status.STOPPED)) return;
		//button.setText("スタート");
		timer.stop();
		astar.clear();
		//gridController.setElementDisable(false);
	}

	@FXML
	private void reset() {
		stop();
		//gridController.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*astar = gridController.getSolver();
		timer = new Timeline();
		timer.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
			if (astar.canContinue()) astar.continueSolve();
			else launch();
		}));
		timer.setCycleCount(Timeline.INDEFINITE);*/
	}
}
