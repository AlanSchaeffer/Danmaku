package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.BulletFactory;
import com.elven.danmaku.core.stage.StageController;

public class SpawnBulletAction implements ScriptAction {

	private final StageController stage;
	private final BulletFactory factory;

	public SpawnBulletAction(StageController stage, BulletFactory factory) {
		this.stage = stage;
		this.factory = factory;
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
		Bullet bullet = factory.createBullet();
		stage.bullets().spawn(bullet);
	}
}
