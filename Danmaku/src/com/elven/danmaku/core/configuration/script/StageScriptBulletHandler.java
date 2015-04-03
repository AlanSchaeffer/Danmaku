package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.BulletFactory;

public class StageScriptBulletHandler {
	
	private final StageScript script;

	public StageScriptBulletHandler(StageScript script) {
		this.script = script;
	}

	public void spawn(Bullet bullet) {
		spawn(new SingleScriptedBulletFactory(bullet));
	}
	
	public void spawn(BulletFactory factory) {
		script.addAction(new SpawnBulletAction(script.getStage(), factory));
	}

	public void clear() {
		script.addAction(new ClearBulletsAction(script.getStage()));
	}
}
