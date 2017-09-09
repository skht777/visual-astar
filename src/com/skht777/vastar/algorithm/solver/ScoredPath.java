package com.skht777.vastar.algorithm.solver;

import com.skht777.vastar.algorithm.Point;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 重み付きルート候補
 */
class ScoredPath implements Comparable<ScoredPath> {
	private final int score;
	private final List<Point> path;
	private static Set<Point> visited;
	private static Solver solver;

	static void setSolver(Solver solver) {
		ScoredPath.solver = solver;
	}

	static List<ScoredPath> init() {
		visited = new HashSet<>();
		visited.add(solver.getStart());
		return Collections.singletonList(new ScoredPath(0, solver.getStart()));
	}

	private ScoredPath(ScoredPath spath, Point ext) {
		this(spath.score + solver.calcScore(ext, solver.getGoal()),
				Stream.concat(spath.path.stream(), Stream.of(ext)).toArray(Point[]::new));
	}

	private ScoredPath(int score, Point... path) {
		this.score = score;
		this.path = Arrays.asList(path);
	}

	boolean isReached() {
		return path.get(path.size() - 1).equals(solver.getGoal());
	}

	List<ScoredPath> createNeighborPathes() {
		// その点から行ける場所を全て探す
		return solver.getNeighbors(path.get(path.size() - 1)).stream()
				.filter(p -> p.canWalk() && visited.add(p))
				.map(p -> new ScoredPath(this, p)).collect(Collectors.toList());
	}

	void forEach(Consumer<Point> f) {
		path.stream().skip(1).forEach(f);
	}

	@Override
	public int compareTo(ScoredPath o) {
		return Integer.compare(score, o.score);
	}
}