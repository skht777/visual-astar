package com.skht777.vastar.algorithm;

import java.util.function.BiFunction;

/**
 * @author skht777
 */
public interface Creator {
	static Creator createCreator(int w, int h, BiFunction<Integer, Integer, Point> n2p) {
		return new Creator() {
			@Override
			public int getWidth() {
				return w;
			}

			@Override
			public int getHeight() {
				return h;
			}

			@Override
			public Point getPoint(int x, int y) {
				return n2p.apply(x, y);
			}
		};
	}

	int getWidth();

	int getHeight();

	Point getPoint(int x, int y);
}
