package com.elven.danmaku.core.elements.hitbox;

import java.awt.Rectangle;

import com.elven.danmaku.core.system.Vector2D;

public interface Hitbox {

	public Vector2D getPosition();

	public boolean hitCheck(Hitbox target);

	public Rectangle boundingBox();

	public HitboxType type();
}
