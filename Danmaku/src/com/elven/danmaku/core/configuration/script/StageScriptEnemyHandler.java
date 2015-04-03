package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.EnemyFactory;

public class StageScriptEnemyHandler {

	private final StageScript script;

	StageScriptEnemyHandler(StageScript script) {
		this.script = script;
	}
	
	public void spawn(AbstractHostileEntity enemy) {
		spawn(new SingleScriptedEnemyFactory(enemy));
	}
	
	public void spawn(EnemyFactory factory) {
		script.addAction(new SpawnEnemyAction(factory, script.gameInfo(), script.getStage()));
	}
}
