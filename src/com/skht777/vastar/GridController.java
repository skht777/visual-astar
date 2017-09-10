/**
 *
 */
package com.skht777.vastar;

import com.skht777.vastar.algorithm.NodeOperator;
import com.skht777.vastar.algorithm.Point;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * @author skht777
 */
public class GridController implements Initializable {
	@FXML
	private GridPane rootPane;
	@FXML
	private Integer row, col;
	private NodeOperator operator;

	public NodeOperator getOperator() {
		return operator;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootPane.getChildren().clear();
		operator = new NodeOperator(col, row,
				() -> (List<Point>) ((List<? super Element>) rootPane.getChildren()),
				b -> rootPane.getChildren().forEach(e -> e.setDisable(b)));
		IntStream.range(0, row).forEach(y -> IntStream.range(0, col).forEach(x -> rootPane.add(new Element(), x, y)));
		Element.setFunction((cond, f) -> rootPane.getChildren().stream().map(n -> (Point) n).filter(cond).findFirst().ifPresent(f));
	}

}
