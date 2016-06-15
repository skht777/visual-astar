/**
 * 
 */
package com.skht777.vastar;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import com.skht777.vastar.algorithm.AStar;
import com.skht777.vastar.algorithm.Point;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * @author skht777
 *
 */
public class Element extends Button implements Point {
	
	private static Element start, goal;
	
	public static void setInit(Element start, Element goal) {
		setStart(start);
		setGoal(goal);
	}
	
	public static void launch(AStar solver) {
		solver.launch(start, goal);
	}
	
	private static void setStart(Element start) {
		Optional.ofNullable(Element.start).ifPresent(Element::reset);
		Element.start = start;
		Element.start.changeMode("start");
	}
	
	private static void setGoal(Element goal) {
		Optional.ofNullable(Element.goal).ifPresent(Element::reset);
		Element.goal = goal;
		Element.goal.changeMode("goal");
	}
	
	public Element(int x, int y) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/Element.fxml"));
		loader.setRoot(this);
        loader.setController(this);
		try {
			loader.load();
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		GridPane.setColumnIndex(this, x);
		GridPane.setRowIndex(this, y);
	}
	
	private void changeMode(String mode) {
		getStyleClass().clear();
		getStyleClass().addAll("button", mode);
	}
	
	@FXML
	private void mouseClicked(MouseEvent e) {
		if (e.getButton().equals(MouseButton.SECONDARY) || !Stream.of("wall", "road").anyMatch(s->s.equals(getStyleClass().get(1)))) return;
		if (canWalk()) changeMode("wall");
		else reset();
		System.out.println(getStyleClass());
	}
	
	@FXML
	private void contextMenu(ContextMenuEvent e) {
		MenuItem start = new MenuItem("スタートにする");
		start.setOnAction(ee->setStart((Element) e.getSource()));
		MenuItem goal = new MenuItem("ゴールにする");
		goal.setOnAction(ee->setGoal((Element) e.getSource()));
		new ContextMenu(start, goal).show(this, e.getScreenX(), e.getScreenY());
	}

	@Override
	public boolean canWalk() {
		return !getStyleClass().get(1).equals("wall");
	}

	@Override
	public void set() {
		changeMode("route");
	}
	
	@Override
	public void reset() {
		changeMode("road");
	}

}
