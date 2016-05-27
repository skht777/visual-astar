/**
 * 
 */
package com.skht777.vastar.algorithm;

import java.util.List;
import java.util.Optional;

/**
 * @author skht777
 *
 */
public class AStar {

	private ScoredPath spath;
	private List<ScoredPath> pathes;

	/**
	 * 
	 */
	public AStar(Solver solver) {
		ScoredPath.setSolver(solver);
	}
	
	public void launch(Point start, Point goal) {
		pathes = ScoredPath.init(start, goal);
	}
	
	public boolean canContinue() {
		// 停止条件
		return Optional.ofNullable(pathes).filter(p->!p.isEmpty() && !pathes.get(0).isReached()).isPresent();
	}
	
	public void continueSolve() {
		clear();
		spath = pathes.remove(0);
		// その点から行ける全ての点について
		spath.getNeighbors().forEach(s->pathes.add(0, new ScoredPath(spath, s)));
		// 正しいスコア順になるように並べ替え
		pathes.sort(ScoredPath::compareTo);
		// 新たに開拓したルートを塗りつぶす
		spath.getPath().forEach(Point::set);
	}
	
	public void clear() {
		// 塗りつぶしを消去する
		Optional.ofNullable(spath).ifPresent(sp->sp.getPath().forEach(Point::reset));
	}

}
