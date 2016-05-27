package com.skht777.vastar.algorithm;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Solver {
	
	public static Solver cleateSolver(Function<Point, Stream<Point>> neighbor, ToIntBiFunction<Point, Point> score) {
		return new Solver() {
			
			@Override
			public List<Point> getNeighbors(Point p) {
				return Stream.of(p).flatMap(neighbor).collect(Collectors.toList());
			}
			
			@Override
			public int calcScore(Point p1, Point p2) {
				return score.applyAsInt(p1, p2);
			}
		};
	}
	
	List<Point> getNeighbors(Point p);
	
	int calcScore(Point p1, Point p2);
	
}
