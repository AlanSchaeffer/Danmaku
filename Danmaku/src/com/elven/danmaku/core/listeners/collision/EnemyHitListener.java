package com.elven.danmaku.core.listeners.collision;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.player.PlayerBullet;

public interface EnemyHitListener {

	public void enemyHit(AbstractHostileEntity enemy, PlayerBullet bullet);
}
