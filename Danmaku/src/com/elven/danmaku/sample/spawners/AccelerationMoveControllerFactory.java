package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.elements.controller.AccelerationMoveController;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.system.Vector2D;

public class AccelerationMoveControllerFactory implements MoveControllerFactory {

	private int duration = 10;
	private double decelerationFactor = 2.0;
	private double finalForceFactor = 1.0;

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setDecelerationFactor(double decelerationFactor) {
		this.decelerationFactor = decelerationFactor;
	}
	
	public void setFinalForceFactor(double finalForceFactor) {
		this.finalForceFactor = finalForceFactor;
	}
	
	@Override
	public AccelerationMoveController createMoveController(Vector2D force) {
		MoveController moveController = new MoveController(force);
		AccelerationMoveController accelerator = new AccelerationMoveController(moveController);
		accelerator.setDuration(duration);
		accelerator.setDecelerationFactor(decelerationFactor);
		accelerator.setFinalForce(force.multiply(finalForceFactor));
		return accelerator;
	}
}
