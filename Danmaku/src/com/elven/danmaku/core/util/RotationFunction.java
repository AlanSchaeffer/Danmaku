package com.elven.danmaku.core.util;

import com.elven.danmaku.core.system.Vector2D;


public class RotationFunction {

	private final double cosine;
	private final double sine;

	public RotationFunction(double rotation) {
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);
	}

	public Vector2D rotatedPoint(double x, double y) {
		double newX = cosine * x - sine * y;
		double newY = sine * x + cosine * y;
		return new Vector2D(newX, newY);
	}
}
