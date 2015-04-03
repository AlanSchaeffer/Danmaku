package com.elven.danmaku.core.elements.controller.path;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public interface Waypoint {

	public void tick(AbstractPlaceableGameElement element, Vector2D startingPoint, int frame);
	
	public Vector2D getDestination();
	
	public int getDuration();
}
