package com.skht777.vastar.algorithm;

import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;

/**
 * @author skht777
 */
public interface Creator {
	static Creator createCreator(IntSupplier w, IntSupplier h, IntBinaryOperator xy2n, IntFunction<Point> n2p) {
		return new Creator() {
			@Override
			public int getWidth() {
				return w.getAsInt();
			}

			@Override
			public int getHeight() {
				return h.getAsInt();
			}

			@Override
			public Point getPoint(int x, int y) {
				return n2p.apply(xy2n.applyAsInt(x, y));
			}
		};
	}

	int getWidth();

	int getHeight();

	Point getPoint(int x, int y);
}
