package com.elven.danmaku.core.system.collision;

import java.util.LinkedList;
import java.util.List;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.listeners.CollisionType;
import com.elven.danmaku.core.listeners.collision.CollisionListener;

public class CollisionDetector {

	private final List<CollisionTask> collisionTasks = new LinkedList<CollisionTask>();
	private final List<CollisionListener> listeners = new LinkedList<>();
	private final CollisionEventPropagator propagator = new Propagator();

	public void checkCollisions() {
		for (CollisionTask task : collisionTasks) {
			task.run(propagator);
		}
	}

	public void addCollisionTask(CollisionTask task) {
		collisionTasks.add(task);
	}

	public void removeCollisionTask(CollisionTask task) {
		collisionTasks.remove(task);
	}

	public void addCollisionListener(CollisionListener listener) {
		listeners.add(listener);
	}

	public void removeCollisionListener(CollisionListener listener) {
		listeners.remove(listener);
	}

	private void fireCollision(AbstractPlaceableGameElement element, AbstractPlaceableGameElement collidedWith, CollisionType type) {
		for (CollisionListener listener : listeners) {
			listener.collided(element, collidedWith, type);
		}
	}

	private class Propagator implements CollisionEventPropagator {

		@Override
		public void fireCollision(AbstractPlaceableGameElement element, AbstractPlaceableGameElement collidedWith, CollisionType type) {
			CollisionDetector.this.fireCollision(element, collidedWith, type);
		}
	}
}
