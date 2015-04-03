package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.EnemyFactory;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.stage.StageController;

public class SpawnEnemyAction implements ScriptAction {

	private final EnemyFactory factory;
	private final GameInfo gameInfo;
	private final StageController stage;

	public SpawnEnemyAction(EnemyFactory factory, GameInfo gameInfo, StageController stage) {
		this.factory = factory;
		this.gameInfo = gameInfo;
		this.stage = stage;
	}
	
	@Override
	public boolean shouldResume() {
		return true;
	}

	@Override
	public boolean shouldRemove() {
		return true;
	}

	@Override
	public void tick() {
		AbstractHostileEntity enemy = factory.createEnemy();
		enemy.addEnemyDestroyedListener(gameInfo.getScoreListener());
		stage.enemies().spawn(enemy);
	}
}
