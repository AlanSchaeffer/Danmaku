package com.elven.danmaku.core.bullets.target;

import java.awt.MouseInfo;
import java.awt.Point;

import com.elven.danmaku.core.system.Vector2D;

public class MouseTarget implements AimTarget {

	@Override
	public Vector2D getTarget() {
		Point location = MouseInfo.getPointerInfo().getLocation();
		Vector2D target = new Vector2D();
		target.setX(location.x);
		target.setY(location.y);
		return target;
	}
}
