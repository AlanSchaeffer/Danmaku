package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.BulletFactory;

class SingleScriptedBulletFactory implements BulletFactory {

	private Bullet bullet;

	public SingleScriptedBulletFactory(Bullet bullet) {
		this.bullet = bullet;
	}
	
	@Override
	public Bullet createBullet() {
		return bullet;
	}
}
