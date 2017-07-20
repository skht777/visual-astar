/**
 *
 */
package com.skht777.vastar;

import com.skht777.vastar.algorithm.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author skht777
 *
 */
public class GridController implements Initializable {

	@FXML private GridPane rootPane;

	private Element getElement(int n) {
		return (Element) rootPane.getChildren().get(n);
	}

	private Stream.Builder<Point> neighbors(Element p) {
		// フィールドに収まる４方のノードがあれば取得する
		Element m = getElement(rootPane.getChildren().size() - 1);
		int x = GridPane.getColumnIndex(p), y = GridPane.getRowIndex(p);
		BiFunction<Integer, Integer, Point> index = (ix, iy)->getElement(iy * (GridPane.getColumnIndex(m) + 1) + ix);

		Stream.Builder<Point> neighbors = Stream.builder();
		if (x > 0) neighbors.add(index.apply(x - 1, y));
		if (x < GridPane.getColumnIndex(m)) neighbors.add(index.apply(x + 1, y));
		if (y > 0) neighbors.add(index.apply(x, y - 1));
		if (y < GridPane.getRowIndex(m)) neighbors.add(index.apply(x, y + 1));
		return neighbors;
	}

	public AStar getSolver() {
		return new AStar(Solver.cleateSolver(p -> neighbors((Element) p).build(),
				(p1, p2) -> (int) (Math.pow(Math.abs(GridPane.getColumnIndex((Element) p1) - GridPane.getColumnIndex((Element) p2)), 2) +
						Math.pow(Math.abs(GridPane.getRowIndex((Element) p1) - GridPane.getRowIndex((Element) p2)), 2))));
	}

	public Digger getCreator() {
		Element m = getElement(rootPane.getChildren().size() - 1);
		return new Digger(Creator.createCreator(() -> GridPane.getColumnIndex(m) + 1, () -> GridPane.getRowIndex(m) + 1,
				(ix, iy) -> iy * (GridPane.getColumnIndex(m) + 1) + ix, this::getElement));
	}

	public void clear() {
		rootPane.getChildren().stream().map(c->(Element) c).filter(e->!e.canWalk()).forEach(Element::reset);
	}

	public void setElementDisable(boolean value) {
		rootPane.getChildren().forEach(e->e.setDisable(value));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootPane.getChildren().clear();
		final int row = 21, col = 25;
		IntStream.range(0, row).forEach(i->IntStream.range(0, col).forEach(j->rootPane.getChildren().add(new Element(j, i))));
		Digger digger = getCreator();
		digger.launch();
		while (digger.canContinue()) {
			digger.continueCreate();
		}
	}

}
