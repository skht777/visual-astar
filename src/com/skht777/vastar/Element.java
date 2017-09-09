/**
 *
 */
package com.skht777.vastar;

import com.skht777.vastar.algorithm.Point;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author skht777
 */
public class Element extends Button implements Point {
	private static String css = Element.class.getResource("../resource/style.css").toExternalForm();
	private static Consumer<Predicate<Point>> reset;

	public static void setFunction(BiConsumer<Predicate<Point>, Consumer<Point>> change) {
		reset = cond -> change.accept(cond, Point::reset);
	}

	public Element() {
		super();
		getStylesheets().add(css);
		reset();
		setOnContextMenuRequested(this::contextMenu);
		setOnMouseClicked(this::mouseClicked);
		setMnemonicParsing(false);
		setPrefSize(20, 20);
		setMinSize(0, 0);
	}

	private void changeMode(String mode) {
		getStyleClass().clear();
		getStyleClass().addAll("button", mode);
	}

	private boolean isMode(String mode) {
		return getStyleClass().get(1).equals(mode);
	}

	@FXML
	private void mouseClicked(MouseEvent e) {
		if (e.getButton().equals(MouseButton.SECONDARY) ||
				Stream.of("wall", "road").noneMatch(this::isMode)) return;
		if (canWalk()) block();
		else reset();
	}

	@FXML
	private void contextMenu(ContextMenuEvent e) {
		MenuItem start = new MenuItem("スタートにする");
		start.setOnAction(ee -> ((Element) e.getSource()).start());
		MenuItem goal = new MenuItem("ゴールにする");
		goal.setOnAction(ee -> ((Element) e.getSource()).goal());
		new ContextMenu(start, goal).show(this, e.getScreenX(), e.getScreenY());
	}

	@Override
	public boolean canWalk() {
		return !isMode("wall");
	}

	@Override
	public boolean isStart() {
		return isMode("start");
	}

	@Override
	public boolean isGoal() {
		return isMode("goal");
	}

	@Override
	public void start() {
		reset.accept(Point::isStart);
		changeMode("start");
	}

	@Override
	public void goal() {
		reset.accept(Point::isGoal);
		changeMode("goal");
	}

	@Override
	public void set() {
		changeMode("route");
	}

	@Override
	public void reset() {
		changeMode("road");
	}

	@Override
	public void block() {
		changeMode("wall");
	}
}
