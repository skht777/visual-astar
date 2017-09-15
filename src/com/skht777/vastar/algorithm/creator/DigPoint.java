package com.skht777.vastar.algorithm.creator;

import java.util.Collections;
import java.util.List;

/**
 * @author skht777
 */
class DigPoint {
	private static Creator creator;
	private final int x;
	private final int y;
	private final int nx;
	private final int ny;

	static void setCreator(Creator creator) {
		DigPoint.creator = creator;
	}

	static List<DigPoint> init() {
		return Collections.singletonList(
				new DigPoint(creator.getWidth() / 2 | 1,
						creator.getHeight() / 2 | 1, Vector.N));
	}

	private DigPoint(int x, int y, Vector v) {
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

	DigPoint createNextPoint(Vector v) {
		return new DigPoint(nx, ny, v);
	}

	void dig() {
		creator.getPoint(x, y).road();
		creator.getPoint(nx, ny).road();
	}
}
