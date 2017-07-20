package com.skht777.vastar.algorithm;

import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * @author skht777
 */
class DigPoint implements Comparable<DigPoint> {
	private static Creator creator;
	private final String id;
	private final int x;
	private final int y;
	private final int nx;
	private final int ny;

	static void setCreator(Creator creator) {
		DigPoint.creator = creator;
	}

	static List<DigPoint> init() {
		IntUnaryOperator o = n -> n % 2 == 0 ? n / 2 + 1 : n / 2 | 1;
		return Collections.singletonList(
				new DigPoint("", 1, o.applyAsInt(creator.getWidth()),
						o.applyAsInt(creator.getHeight()), Vector.N));
	}

	private DigPoint(String id, int prefix, int x, int y, Vector v) {
		this.id = id + prefix;
		this.x = x + v.getX();
		this.y = y + v.getY();
		this.nx = x + v.getX() * 2;
		this.ny = y + v.getY() * 2;
	}

	boolean isDigable() {
		return ny > 0 && ny < creator.getHeight() - 1
				&& nx > 0 && nx < creator.getWidth() - 1
				&& !creator.getPoint(nx, ny).canWalk();
	}

	DigPoint createNextPoint(int prefix) {
		return new DigPoint(id, prefix, nx, ny, Vector.values()[prefix]);
	}

	void dig() {
		creator.getPoint(x, y).reset();
		creator.getPoint(nx, ny).reset();
	}

	@Override
	public int compareTo(DigPoint o) {
		return IntStream.range(0, Integer.min(id.length(), o.id.length()))
				.map(i -> Integer.compare(id.codePointAt(i), o.id.codePointAt(i)))
				.filter(i -> i != 0).findFirst()
				.orElseGet(() -> Integer.compare(id.length(), o.id.length()));
	}
}
