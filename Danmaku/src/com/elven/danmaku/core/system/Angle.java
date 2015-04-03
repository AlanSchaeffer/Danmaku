package com.elven.danmaku.core.system;

public final class Angle {

	private double angleInRads;

	public Angle() {
		this(0.0);
	}

	public Angle(double angleInRads) {
		this.angleInRads = angleInRads;
	}

	public Angle(Vector2D origin, Vector2D target) {
		double deltaX = target.getX() - origin.getX();
		double deltaY = target.getY() - origin.getY();
		this.angleInRads = Math.atan2(deltaY, deltaX);
	}

	public void setAngle(double angle) {
		this.angleInRads = angle;
	}

	public double getAngle() {
		return angleInRads;
	}

	public void add(double rads) {
		angleInRads += rads;
	}
	
	public void jitter(double amount) {
		add(Math.random() * amount - amount / 2);
	}

	public Vector2D toVector(double speed) {
		double yForce = speed * Math.sin(angleInRads);
		double xForce = speed * Math.cos(angleInRads);
		return new Vector2D(xForce, yForce);
	}
}
