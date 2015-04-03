package com.elven.danmaku.core.elements.controller;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public class AccelerationMoveController implements Controller {

	private MoveController moveController;
	private int duration = 10;
	private double decelerationFactor = 2.0;
	private Vector2D finalForce;
	private boolean active = true;

	private int ellapsedFrames = 0;

	public AccelerationMoveController(MoveController moveController) {
		this.moveController = moveController;
		finalForce = moveController.getForce();
	}

	public void setFinalForce(Vector2D finalForce) {
		this.finalForce = finalForce;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setDecelerationFactor(double decelerationFactor) {
		this.decelerationFactor = decelerationFactor;
	}

	public void setActive(boolean active) {
		this.active = active;

		if (active) {
			ellapsedFrames = 0;
		}
	}

	@Override
	public void tick(AbstractPlaceableGameElement element) {
		moveController.tick(element);

		if (active) {
			if (ellapsedFrames < duration) {
				Vector2D currentForce = moveController.getForce();
				Vector2D delta = finalForce.delta(currentForce);
				currentForce.setX(currentForce.getX() + (delta.getX() / decelerationFactor));
				currentForce.setY(currentForce.getY() + (delta.getY() / decelerationFactor));
				ellapsedFrames++;
			} else if (ellapsedFrames == duration) {
				Vector2D currentForce = moveController.getForce();
				currentForce.setX(finalForce.getX());
				currentForce.setY(finalForce.getY());
				ellapsedFrames++;
			}
		}
	}
}
