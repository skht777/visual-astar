/**
 *
 */
package com.skht777.vastar.algorithm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author skht777
 */
public class AStar implements Executor {

	private ScoredPath spath;
	private List<ScoredPath> pathes;

	public AStar(Solver solver) {
		ScoredPath.setSolver(solver);
	}

	@Override
	public void launch() {
		pathes = ScoredPath.init();
	}

	@Override
	public boolean canContinue() {
		// 停止条件
		return pathes != null && !pathes.stream().findFirst().filter(ScoredPath::isReached).isPresent();
	}

	@Override
	public void continueDo() {
		clear();
		spath = pathes.get(0);
		// その点から行ける全ての点について
		// 正しいスコア順になるように並べ替え
		pathes = Stream.concat(pathes.stream().skip(1),
				spath.createNeighborPathes().stream())
				.sorted().collect(Collectors.toList());
		// 新たに開拓したルートを塗りつぶす
		spath.forEach(Point::set);
	}

	@Override
	public void clear() {
		// 塗りつぶしを消去する
		Optional.ofNullable(spath).ifPresent(sp -> sp.forEach(Point::reset));
	}

}
