package com.elven.danmaku.core.player.controller;

import org.lwjgl.input.Keyboard;

import com.elven.danmaku.core.input.InputReceiver;

public class DefaultPlayerInput implements BasicPlayerInput {

	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean focused;
	private boolean shooting;
	private boolean bombing;
	private final PlayerInputReceiver receiver = new PlayerInputReceiver();

	@Override
	public InputReceiver getReceiver() {
		return receiver;
	}

	@Override
	public boolean isMovingUp() {
		return movingUp;
	}

	@Override
	public boolean isMovingDown() {
		return movingDown;
	}

	@Override
	public boolean isMovingLeft() {
		return movingLeft;
	}

	@Override
	public boolean isMovingRight() {
		return movingRight;
	}

	@Override
	public boolean isFocused() {
		return focused;
	}

	@Override
	public boolean isShooting() {
		return shooting;
	}

	@Override
	public boolean isBombing() {
		return bombing;
	}
	
	@Override
	public void reset() {
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;
		focused = false;
		shooting = false;
		bombing = false;
	}

	private final class PlayerInputReceiver implements InputReceiver {

		@Override
		public void pressed(int keyCode) {
			updateKey(keyCode, true);
		}

		@Override
		public void released(int keyCode) {
			updateKey(keyCode, false);
		}

		@Override
		public void clear() {
			reset();
		}

		private void updateKey(int keyCode, boolean pressed) {
			switch (keyCode) {
				case Keyboard.KEY_UP:
					movingUp = pressed;
					break;
				case Keyboard.KEY_DOWN:
					movingDown = pressed;
					break;
				case Keyboard.KEY_LEFT:
					movingLeft = pressed;
					break;
				case Keyboard.KEY_RIGHT:
					movingRight = pressed;
					break;
				case Keyboard.KEY_LSHIFT:
					focused = pressed;
					break;
				case Keyboard.KEY_Z:
					shooting = pressed;
					break;
				case Keyboard.KEY_X:
					bombing = pressed;
			}
		}
	}
}
