package com.elven.danmaku.core.system.collision;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.listeners.CollisionType;

public interface CollisionEventPropagator {

	public void fireCollision(AbstractPlaceableGameElement element, AbstractPlaceableGameElement collidedWith, CollisionType type);
}
