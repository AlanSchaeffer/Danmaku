package com.elven.danmaku.core.listeners.collision;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.listeners.CollisionType;

public interface CollisionListener {

	public void collided(AbstractPlaceableGameElement element, AbstractPlaceableGameElement collidedWith, CollisionType type);
}
