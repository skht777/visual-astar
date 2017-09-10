package com.skht777.vastar;

import com.skht777.vastar.algorithm.Executor;
import com.skht777.vastar.algorithm.NodeOperator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.animation.Animation.Status.RUNNING;
import static javafx.animation.Animation.Status.STOPPED;

/**
 * @author skht777
 */
public class OperateController implements Initializable {
	@FXML
	private String create;
	@FXML
	private Button button;
	private NodeOperator operator;
	private Timeline timer;
	private Executor executor;

	private void finish() {
		button.setText("スタート");
		timer.stop();
		operator.applyNodeDisable(false);
	}

	@FXML
	private void skip() {
		if (timer.getStatus() == STOPPED) executor.launch();
		while (executor.canContinue()) {
			executor.continueDo();
		}
	}

	@FXML
	private void launch() {
		if (timer.getStatus() == RUNNING) {
			button.setText("スタート");
			timer.pause();
		} else {
			if (timer.getStatus() == STOPPED || !executor.canContinue()) {
				executor.launch();
				operator.applyNodeDisable(true);
			}
			button.setText("ポーズ");
			timer.play();
		}
	}

	@FXML
	private void stop() {
		finish();
		executor.clear();
	}

	@FXML
	private void reset() {
		stop();
		operator.reset();
	}

	@FXML
	private void select(ActionEvent e) {
		if (timer.getStatus() == RUNNING) timer.stop();
		ChoiceBox c = (ChoiceBox) e.getSource();
		if (c.getSelectionModel().getSelectedItem().equals(create)) {
			executor = operator.getDigger();
		} else {
			executor = operator.getAstar();
		}
	}

	public void setOperator(NodeOperator operator) {
		this.operator = operator;
		executor = operator.getDigger();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timer = new Timeline();
		timer.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
			if (executor.canContinue()) {
				executor.continueDo();
			} else {
				finish();
			}
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
	}
}
