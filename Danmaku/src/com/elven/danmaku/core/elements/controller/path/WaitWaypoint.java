package com.elven.danmaku.core.elements.controller.path;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public class WaitWaypoint implements Waypoint {

	private final Vector2D waitAt;
	private final int duration;

	public WaitWaypoint(Vector2D waitAt, int duration) {
		this.waitAt = waitAt;
		this.duration = duration;
	}
	
	@Override
	public void tick(AbstractPlaceableGameElement element, Vector2D startingPoint, int frame) {
		if(frame == 0) {
			element.getPosition().set(waitAt);
		}
	}

	@Override
	public Vector2D getDestination() {
		return waitAt;
	}

	@Override
	public int getDuration() {
		return duration;
	}

}
