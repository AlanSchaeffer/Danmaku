package com.elven.danmaku.core.system.collision;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.listeners.CollisionType;
import com.elven.danmaku.core.player.PlayerBullet;
import com.elven.danmaku.core.stage.StageController;

public class PlayerBulletAgainstEnemiesCollisionTask implements CollisionTask {

	private StageController stage;

	public PlayerBulletAgainstEnemiesCollisionTask(StageController stage) {
		this.stage = stage;
	}
	
	@Override
	public void run(CollisionEventPropagator propagator) {
		for (PlayerBullet bullet : stage.getModel().getPlayerBullets().getElements()) {
			for (AbstractHostileEntity enemy : stage.getModel().getEnemies().getElements()) {
				if (bullet.hitCheck(enemy.getTargetHitbox())) {
					propagator.fireCollision(bullet, enemy, CollisionType.ENEMY_HIT);
				}
			}
		}
	}
}
