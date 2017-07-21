package com.skht777.vastar.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author skht777
 */
public class Digger {
	private Creator creator;
	private List<DigPoint> digList;

	public Digger(Creator creator) {
		this.creator = creator;
		DigPoint.setCreator(creator);
	}

	public void launch() {
		IntStream.range(0, creator.getWidth()).forEach(x ->
				IntStream.range(0, creator.getHeight()).forEach(y ->
						creator.getPoint(x, y).block()));
		digList = DigPoint.init();
	}

	public boolean canContinue() {
		return !digList.isEmpty();
	}

	public void continueCreate() {
		DigPoint p = digList.get(0);
		p.dig();
		List<Vector> v = Arrays.asList(Vector.values());
		Collections.shuffle(v);
		digList = Stream.concat(v.stream().map(p::createNextPoint), digList.stream().skip(1))
				.filter(DigPoint::isDigable).collect(Collectors.toList());
	}
}
