package com.elven.danmaku.core.bullets.target;

import com.elven.danmaku.core.system.Vector2D;

public class PointTarget implements AimTarget {

	private Vector2D target;

	public PointTarget() {
		this(null);
	}

	public PointTarget(Vector2D target) {
		this.target = target;
	}

	@Override
	public Vector2D getTarget() {
		return target;
	}

	public void setTarget(Vector2D target) {
		this.target = target;
	}
}
