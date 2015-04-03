package com.elven.danmaku.core.elements.hitbox;

import com.elven.danmaku.core.elements.Placeable;

public class CircleHitboxFactory implements HitboxFactory {

	private double radius;

	public CircleHitboxFactory(double radius) {
		this.radius = radius;
	}
	
	@Override
	public Hitbox createHitboxFor(Placeable element) {
		return new CircleHitbox(element, radius);
	}
}
