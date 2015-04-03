package com.elven.danmaku.core.elements.controller;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public class UniformAccelerationMoveController implements Controller {

	private final Vector2D initialForce;
	private MoveController moveController;
	private Vector2D finalForce;
	private Vector2D delta;
	private int duration = 10;
	private boolean active = true;

	private int ellapsedFrames = 0;

	public UniformAccelerationMoveController(MoveController moveController) {
		this.moveController = moveController;
		initialForce = moveController.getForce();
		finalForce = moveController.getForce();
		delta = new Vector2D(0.0, 0.0);
	}

	public void setFinalForce(Vector2D finalForce) {
		this.finalForce = finalForce;
		this.delta = finalForce.delta(initialForce);
	}

	public void setDuration(int duration) {
		this.duration = duration;
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
				currentForce.setX(currentForce.getX() + delta.getX() / duration);
				currentForce.setY(currentForce.getY() + delta.getY() / duration);
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
