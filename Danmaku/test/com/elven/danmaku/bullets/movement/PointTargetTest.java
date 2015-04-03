package com.elven.danmaku.bullets.movement;

import static org.junit.Assert.*;

import org.junit.Test;

import com.elven.danmaku.core.bullets.target.PointTarget;
import com.elven.danmaku.core.system.Vector2D;

public class PointTargetTest {

	@Test
	public void testTarget() {
		Vector2D vector = new Vector2D(0, 0);
		PointTarget target = new PointTarget(vector);
		assertEquals(vector, target.getTarget());
	}

	@Test
	public void testSetTarget() {
		Vector2D vector = new Vector2D(0, 100);
		PointTarget target = new PointTarget();
		target.setTarget(vector);
		assertEquals(vector, target.getTarget());
	}
}
