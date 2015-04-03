package com.elven.danmaku.core.elements.controller.path;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public class SimpleWaypoint implements Waypoint {

	private final Vector2D destination;
	private final int duration;

	public SimpleWaypoint(Vector2D destination, int duration) {
		this.destination = destination;
		this.duration = duration;
	}

	@Override
	public void tick(AbstractPlaceableGameElement element, Vector2D startingPoint, int frame) {
		Vector2D delta = destination.delta(startingPoint);
		element.getPosition().set(startingPoint.getX() + delta.getX() / duration * frame, startingPoint.getY() + delta.getY() / duration * frame);
	}

	@Override
	public Vector2D getDestination() {
		return destination;
	}

	@Override
	public int getDuration() {
		return duration;
	}
}
