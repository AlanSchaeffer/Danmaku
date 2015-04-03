package com.elven.danmaku.core.elements.hitbox;

import java.awt.Rectangle;

import com.elven.danmaku.core.elements.Placeable;
import com.elven.danmaku.core.system.Vector2D;

public class CircleHitbox implements Hitbox {

	private final Placeable element;
	private double radius;

	public CircleHitbox(Placeable element, double radius) {
		this.element = element;
		this.radius = radius;
	}
	
	@Override
	public Vector2D getPosition() {
		return element.getPosition();
	}

	@Override
	public boolean hitCheck(Hitbox target) {
		if (target.type() == HitboxType.CIRCLE) {
			CircleHitbox circleTarget = (CircleHitbox) target;
			double deltaX = element.getPosition().getX() - circleTarget.getPosition().getX();
			double deltaY = element.getPosition().getY() - circleTarget.getPosition().getY();
			double totalRadius = radius + circleTarget.getRadius();

			if (Math.abs(deltaX) < totalRadius && Math.abs(deltaY) < totalRadius) {
				if (deltaX * deltaX + deltaY * deltaY < totalRadius * totalRadius) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Rectangle boundingBox() {
		int diameter = (int) (radius * 2);
		return new Rectangle((int) (element.getPosition().getX() - radius), (int) (element.getPosition().getY() - radius), diameter, diameter);
	}

	@Override
	public HitboxType type() {
		return HitboxType.CIRCLE;
	}
	
	public double getRadius() {
		return radius;
	}
}
