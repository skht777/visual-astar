package com.skht777.vastar.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 重み付きルート候補
 *
 */
class ScoredPath implements Comparable<ScoredPath> {

	private int score;
	private List<Point> path;
	private static Set<Point> visited;
	private static Solver solver;
	private static Point goal;
	
	public static void setSolver(Solver solver) {
		ScoredPath.solver = solver;
		visited = new HashSet<>();
	}
	
	public static List<ScoredPath> init(Point start, Point goal) {
		visited.clear();
		visited.add(start);
		ScoredPath.goal = goal;
		return new ArrayList<>(Arrays.asList(new ScoredPath(0, start)));
	}
	
	ScoredPath(ScoredPath spath, Point ext) {
		this(spath.score + solver.calcScore(ext, goal), ext);
		this.path.addAll(0, spath.path);
	}
	
	ScoredPath(int score,  Point... path) {
		this.score = score;
		this.path = new ArrayList<>(Arrays.asList(path));
	}
	
	public boolean isReached() {
		return path.get(path.size() - 1).equals(goal);
	}
	
	public List<Point> getNeighbors() {
		// その点から行ける場所を全て探す
		return solver.getNeighbors(path.get(path.size() - 1)).stream()
				.filter(pp->pp.canWalk() && visited.add(pp)).collect(Collectors.toList());
	}
	
	public List<Point> getPath() {
		return path.subList(1, path.size());
	}

	@Override
	public int compareTo(ScoredPath o) {
		return Integer.compare(score, o.score);
	}

}