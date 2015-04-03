package com.elven.danmaku.core.bullets.target;

import com.elven.danmaku.core.system.Vector2D;

public class JitterPointTarget implements AimTarget {

	private Vector2D target;
	private Vector2D randomizedTarget;
	private double jitterAmount = 0.0;

	public JitterPointTarget() {
		this(null);
	}

	public JitterPointTarget(Vector2D target) {
		setTarget(target);
	}
	
	public void setJitterAmount(double jitterAmount) {
		this.jitterAmount = jitterAmount;
	}

	@Override
	public Vector2D getTarget() {
		return randomizedTarget;
	}

	public void setTarget(Vector2D target) {
		this.target = target;
		
		if(target != null) {
			updateTarget();
		}
	}
	
	public void updateTarget() {
		randomizedTarget = generateJitteredTarget();
	}

	private Vector2D generateJitteredTarget() {
		double displace = jitterAmount / 2;
		double randomJitterX = Math.random() * jitterAmount - displace;
		double randomJitterY = Math.random() * jitterAmount - displace;
		return new Vector2D(target.getX() + randomJitterX, target.getY() + randomJitterY);
	}
}
