package com.skht777.vastar.algorithm;

/**
 * @author skht777
 */
public interface Executor {
	void launch();

	boolean canContinue();

	void continueDo();

	void clear();
}
