package com.skht777.vastar.algorithm;

import com.skht777.vastar.algorithm.creator.Creator;
import com.skht777.vastar.algorithm.creator.Digger;
import com.skht777.vastar.algorithm.solver.AStar;
import com.skht777.vastar.algorithm.solver.Solver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author skht777
 */
public class NodeOperator {
	private int width;
	private int height;
	private List<Point> nodes;
	private Consumer<Boolean> disable;
	private Digger digger;
	private AStar astar;

	public NodeOperator(int width, int height, List<Point> nodes, Consumer<Boolean> disableConsumer) {
		this.width = width;
		this.height = height;
		this.nodes = nodes;
		disable = disableConsumer;
		digger = new Digger(Creator.createCreator(width, height, this::index));
		astar = new AStar(Solver.cleateSolver(this::neibors, this::calc, this::getStart, this::getGoal));
	}

	public void applyNodeDisable(boolean b) {
		disable.accept(b);
	}

	public void reset() {
		nodes.forEach(Point::road);
	}

	public Digger getDigger() {
		return digger;
	}

	public AStar getAstar() {
		return astar;
	}

	private Point index(int x, int y) {
		return nodes.get(y * width + x);
	}

	private int calc(Point p1, Point p2) {
		ToIntFunction<Point> col = p -> nodes.indexOf(p) % width;
		ToIntFunction<Point> row = p -> nodes.indexOf(p) / width;
		return (int) (Math.pow(Math.abs(col.applyAsInt(p1) - col.applyAsInt(p2)), 2) + Math.pow(Math.abs(row.applyAsInt(p1) - row.applyAsInt(p2)), 2));
	}

	private Stream<Point> neibors(Point p) {
		// フィールドに収まる４方のノードがあれば取得する
		int num = nodes.indexOf(p);
		int x = num % width, y = num / width;

		Stream.Builder<Point> neighbors = Stream.builder();
		if (x > 0) neighbors.add(index(x - 1, y));
		if (x < width - 1) neighbors.add(index(x + 1, y));
		if (y > 0) neighbors.add(index(x, y - 1));
		if (y < height - 1) neighbors.add(index(x, y + 1));
		return neighbors.build();
	}

	private Optional<Point> randomPoint(Consumer<Point> effect) {
		List<Point> walkableNodes = nodes.stream().filter(Point::canWalk).collect(Collectors.toList());
		Collections.shuffle(walkableNodes);
		Optional<Point> p = walkableNodes.stream().findFirst();
		p.ifPresent(effect);
		return p;
	}

	private Optional<Point> getStart() {
		return nodes.stream().filter(Point::isStart).findFirst().or(() -> randomPoint(Point::start));
	}

	private Optional<Point> getGoal() {
		return nodes.stream().filter(Point::isGoal).findFirst().or(() -> randomPoint(Point::goal));
	}
}
