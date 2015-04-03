package com.elven.danmaku.core.bullets.target;

import com.elven.danmaku.core.system.Vector2D;

public class RandomJitterPointTarget extends JitterPointTarget {

	public RandomJitterPointTarget() {
		this(null);
	}

	public RandomJitterPointTarget(Vector2D target) {
		super(target);
	}
	
	@Override
	public Vector2D getTarget() {
		updateTarget();
		return super.getTarget();
	}
}
