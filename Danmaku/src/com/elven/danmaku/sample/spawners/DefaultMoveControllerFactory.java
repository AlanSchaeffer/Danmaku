package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.system.Vector2D;

public class DefaultMoveControllerFactory implements MoveControllerFactory {

	@Override
	public Controller createMoveController(Vector2D force) {
		return new MoveController(force);
	}
}
