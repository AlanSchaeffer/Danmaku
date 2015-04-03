package com.elven.danmaku.core.elements.controller;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public class MoveController implements Controller {

	private final Vector2D force;

	public MoveController(Vector2D force) {
		this.force = force;
	}

	public Vector2D getForce() {
		return force;
	}

	@Override
	public void tick(AbstractPlaceableGameElement element) {
		Vector2D position = element.getPosition();
		position.setX(position.getX() + force.getX());
		position.setY(position.getY() + force.getY());
	}
}
