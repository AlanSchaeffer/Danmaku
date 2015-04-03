package com.elven.danmaku.core.configuration.script;

public class WaitAction implements ScriptAction {

	private int framesRemaining;

	public WaitAction(int framesToWait) {
		this.framesRemaining = framesToWait;
	}
	
	@Override
	public boolean shouldResume() {
		return false;
	}

	@Override
	public boolean shouldRemove() {
		return framesRemaining <= 0;
	}

	@Override
	public void tick() {
		framesRemaining--;
	}
}
