/**
 *
 */
package com.skht777.vastar.algorithm.solver;

import com.skht777.vastar.algorithm.Executor;
import com.skht777.vastar.algorithm.Point;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author skht777
 */
public class AStar implements Executor {

	private ScoredPath spath;
	private List<ScoredPath> paths;

	public AStar(Solver solver) {
		ScoredPath.setSolver(solver);
	}

	@Override
	public void launch() {
		paths = ScoredPath.init();
	}

	@Override
	public boolean canContinue() {
		// 停止条件
		return !paths.isEmpty() && !paths.stream().findFirst().filter(ScoredPath::isReached).isPresent();
	}

	@Override
	public void continueDo() {
		clear();
		spath = paths.get(0);
		// その点から行ける全ての点について
		// 正しいスコア順になるように並べ替え
		paths = Stream.concat(paths.stream().skip(1),
				spath.createNeighborPathes().stream())
				.sorted().collect(Collectors.toList());
		// 新たに開拓したルートを塗りつぶす
		spath.forEach(Point::route);
	}

	@Override
	public void clear() {
		// 塗りつぶしを消去する
		Optional.ofNullable(spath).ifPresent(sp -> sp.forEach(Point::road));
	}

}
