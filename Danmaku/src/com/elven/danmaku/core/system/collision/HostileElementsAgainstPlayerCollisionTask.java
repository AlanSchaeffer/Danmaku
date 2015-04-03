package com.elven.danmaku.core.system.collision;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.listeners.CollisionType;
import com.elven.danmaku.core.stage.StageController;

public class HostileElementsAgainstPlayerCollisionTask implements CollisionTask {

	private StageController stage;
	private boolean active = true;

	public HostileElementsAgainstPlayerCollisionTask(StageController stage) {
		this.stage = stage;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void run(CollisionEventPropagator propagator) {
		if (isActive()) {
			for (Bullet bullet : stage.getModel().getBullets().getElements()) {
				if (bullet.hitCheck(stage.getPlayer().getModel().getGrazeHitbox())) {
					if (!stage.getPlayer().isInvincible() && bullet.hitCheck(stage.getPlayer().getModel().getHitbox())) {
						propagator.fireCollision(bullet, stage.getPlayer(), CollisionType.HOSTILE);
						break;
					} else {
						propagator.fireCollision(bullet, stage.getPlayer(), CollisionType.GRAZE);
					}
				}
			}

			for (AbstractHostileEntity enemy : stage.getModel().getEnemies().getElements()) {
				if (enemy.hitCheck(stage.getPlayer().getModel().getGrazeHitbox())) {
					if (!stage.getPlayer().isInvincible() && enemy.hitCheck(stage.getPlayer().getModel().getHitbox())) {
						propagator.fireCollision(enemy, stage.getPlayer(), CollisionType.HOSTILE);
						break;
					} else {
						propagator.fireCollision(enemy, stage.getPlayer(), CollisionType.GRAZE);
					}
				}
			}
		}
	}
}
