package com.elven.danmaku.core.elements.hitbox;

import java.awt.Rectangle;

import com.elven.danmaku.core.system.Vector2D;

public class NullHitbox implements Hitbox {

	public static final NullHitbox instance = new NullHitbox();

	private Rectangle box = new Rectangle(-1, -1, 0, 0);

	private NullHitbox() {
	}

	@Override
	public Vector2D getPosition() {
		return null;
	}

	@Override
	public boolean hitCheck(Hitbox target) {
		return false;
	}

	@Override
	public Rectangle boundingBox() {
		return box;
	}

	@Override
	public HitboxType type() {
		return null;
	}

}
