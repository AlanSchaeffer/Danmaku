package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.system.Vector2D;

public interface MoveControllerFactory {

	public Controller createMoveController(Vector2D force);
}
