package com.elven.danmaku.core.enemies;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.system.Vector2D;

public class Enemy extends AbstractHostileEntity {

	private int maxHitPoints = 10000;
	private int currentHitPoints = 10000;
	private long scoreWorth = 10000;

	public Enemy(Sprite sprite, Controller controller) {
		this(sprite, controller, new Vector2D());
	}

	public Enemy(Sprite sprite, Controller controller, Vector2D position) {
		super(sprite, controller, position);
	}

	@Override
	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
		currentHitPoints = maxHitPoints;
	}

	@Override
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	@Override
	public void damage(int damage) {
		if (!getInvincibilityController().isActive()) {
			currentHitPoints = Math.max(0, currentHitPoints - damage);

			if (currentHitPoints == 0) {
				fireEnemyDestroyed();
			}
		}
	}

	@Override
	public long getScoreWorth() {
		return scoreWorth;
	}

	public void setScoreWorth(long scoreWorth) {
		this.scoreWorth = scoreWorth;
	}
}
