package com.elven.danmaku.core.listeners.collision.impl;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.listeners.collision.EnemyHitListener;
import com.elven.danmaku.core.player.PlayerBullet;
import com.elven.danmaku.core.stage.StageController;

public class DefaultEnemyHitListener implements EnemyHitListener {

	private final StageController stage;
	private final GameInfo gameInfo;

	public DefaultEnemyHitListener(StageController stage, GameInfo gameInfo) {
		this.stage = stage;
		this.gameInfo = gameInfo;
	}

	@Override
	public void enemyHit(AbstractHostileEntity enemy, PlayerBullet bullet) {
		enemy.damage(bullet.getPower());
		gameInfo.getScoreModel().increase(10L);
		stage.playerBullets().despawn(bullet);
	}
}
