package com.skht777.vastar.algorithm;

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
	private static Point goal;

	static void setSolver(Solver solver) {
		ScoredPath.solver = solver;
	}

	static List<ScoredPath> init(Point start, Point goal) {
		visited = new HashSet<>();
		visited.add(start);
		ScoredPath.goal = goal;
		return Collections.singletonList(new ScoredPath(0, start));
	}

	private ScoredPath(ScoredPath spath, Point ext) {
		this(spath.score + solver.calcScore(ext, goal),
				Stream.concat(spath.path.stream(), Stream.of(ext)).toArray(Point[]::new));
	}

	private ScoredPath(int score, Point... path) {
		this.score = score;
		this.path = Arrays.asList(path);
	}

	boolean isReached() {
		return path.get(path.size() - 1).equals(goal);
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