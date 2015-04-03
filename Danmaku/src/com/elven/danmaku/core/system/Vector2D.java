package com.elven.danmaku.core.system;

import java.awt.Rectangle;

public final class Vector2D {

	private double x;
	private double y;

	public Vector2D() {
	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D vector) {
		set(vector);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(Vector2D vector) {
		this.x = vector.getX();
		this.y = vector.getY();
	}

	public void translate(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void constrain(Rectangle rectangle) {
		x = Math.min(Math.max(rectangle.x, x), rectangle.x + rectangle.width);
		y = Math.min(Math.max(rectangle.y, y), rectangle.y + rectangle.height);
	}

	public Vector2D delta(Vector2D vector) {
		return new Vector2D(x - vector.getX(), y - vector.getY());
	}

	public Vector2D multiply(double factor) {
		return new Vector2D(x * factor, y * factor);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof Vector2D) {
			Vector2D other = (Vector2D) obj;
			return other.getX() == x && other.getY() == y;
		} else {
			return false;
		}
	}
}
