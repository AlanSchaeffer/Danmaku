package com.elven.danmaku.core.player.controller;

public class CountdownController {

	private int remainingFrames = 0;

	public void setFrames(int frames) {
		remainingFrames = Math.max(remainingFrames, frames);
	}

	public void tick() {
		if (remainingFrames > 0) {
			remainingFrames--;
		}
	}

	public boolean isActive() {
		return remainingFrames > 0;
	}

	public void reset() {
		remainingFrames = 0;
	}
}
