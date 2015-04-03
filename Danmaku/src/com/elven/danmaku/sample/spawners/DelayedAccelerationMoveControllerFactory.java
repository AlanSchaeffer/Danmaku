package com.elven.danmaku.sample.spawners;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.controller.AccelerationMoveController;
import com.elven.danmaku.core.system.Vector2D;

public class DelayedAccelerationMoveControllerFactory extends AccelerationMoveControllerFactory {

	private final Queue<AccelerationMoveController> inactiveControllers = new ConcurrentLinkedQueue<>();

	public void activateAll() {
		while(!inactiveControllers.isEmpty()) {
			AccelerationMoveController controller = inactiveControllers.poll();
			controller.setActive(true);
		}
	}
	
	@Override
	public AccelerationMoveController createMoveController(Vector2D force) {
		AccelerationMoveController controller = super.createMoveController(force);
		controller.setActive(false);
		inactiveControllers.add(controller);
		return controller;
	}
}
