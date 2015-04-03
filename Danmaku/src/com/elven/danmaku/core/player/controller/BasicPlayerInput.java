package com.elven.danmaku.core.player.controller;

import com.elven.danmaku.core.input.InputReceiver;

public interface BasicPlayerInput {

	public InputReceiver getReceiver();
	
	public boolean isMovingUp();

	public boolean isMovingDown();

	public boolean isMovingLeft();

	public boolean isMovingRight();

	public boolean isFocused();
	
	public boolean isShooting();

	public boolean isBombing();

	public void reset();
}
