package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.EnemyFactory;

class SingleScriptedEnemyFactory implements EnemyFactory {

	private AbstractHostileEntity enemy;

	public SingleScriptedEnemyFactory(AbstractHostileEntity enemy) {
		this.enemy = enemy;
	}

	@Override
	public AbstractHostileEntity createEnemy() {
		return enemy;
	}
}
