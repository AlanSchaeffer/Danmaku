package com.elven.danmaku.core.stage;

import com.elven.danmaku.core.elements.UpdateableGameElement;

public class RunLaterElement implements UpdateableGameElement {

	private final Runnable action;
	private final StageController stage;
	private int framesRemaining;

	public RunLaterElement(int framesToWait, Runnable action, StageController stage) {
		this.framesRemaining = framesToWait;
		this.action = action;
		this.stage = stage;
	}

	@Override
	public void tick() {
		if(framesRemaining <= 0) {
			action.run();
			stage.unregisterElement(this);
		} else {
			framesRemaining--;
		}
	}
}
