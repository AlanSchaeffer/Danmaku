package com.elven.danmaku.core.player;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.system.Vector2D;

public class PlayerBullet extends Bullet {

	private int power = 100;

	public PlayerBullet(Sprite sprite, Controller controller) {
		this(sprite, controller, new Vector2D());
	}

	public PlayerBullet(Sprite sprite, Controller controller, Vector2D position) {
		super(sprite, controller, position);
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
