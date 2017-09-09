package com.skht777.vastar.algorithm;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Solver {
	static Solver cleateSolver(Function<Point, Stream<Point>> neighbor, ToIntBiFunction<Point, Point> score, Supplier<Point> start, Supplier<Point> goal) {
		return new Solver() {
			@Override
			public List<Point> getNeighbors(Point p) {
				return Stream.of(p).flatMap(neighbor).collect(Collectors.toList());
			}

			@Override
			public int calcScore(Point p1, Point p2) {
				return score.applyAsInt(p1, p2);
			}

			@Override
			public Point getStart() {
				return start.get();
			}

			@Override
			public Point getGoal() {
				return goal.get();
			}
		};
	}

	List<Point> getNeighbors(Point p);

	int calcScore(Point p1, Point p2);

	Point getStart();

	Point getGoal();

}
