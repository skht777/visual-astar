package com.skht777.vastar.algorithm;

public interface Point {
	boolean canWalk();

	boolean isStart();

	boolean isGoal();

	void start();

	void goal();

	void reset();

	void set();

	void block();
}