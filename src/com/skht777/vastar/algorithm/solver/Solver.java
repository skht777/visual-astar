package com.skht777.vastar.algorithm.solver;

import com.skht777.vastar.algorithm.Point;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Solver {
	static Solver cleateSolver(Function<Point, Stream<Point>> neighbor, ToIntBiFunction<Point, Point> score, Supplier<Optional<Point>> start, Supplier<Optional<Point>> goal) {
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
			public Optional<Point> getStart() {
				return start.get();
			}

			@Override
			public Optional<Point> getGoal() {
				return goal.get();
			}
		};
	}

	List<Point> getNeighbors(Point p);

	int calcScore(Point p1, Point p2);

	Optional<Point> getStart();

	Optional<Point> getGoal();

}
