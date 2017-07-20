package com.skht777.vastar.algorithm;

/**
 * @author skht777
 */
enum Vector {
	UP(0, -1), DOWN(0, 1), LEFR(-1, 0), RIGHT(1, 0), N(0, 0);

	private int x;
	private int y;

	Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
