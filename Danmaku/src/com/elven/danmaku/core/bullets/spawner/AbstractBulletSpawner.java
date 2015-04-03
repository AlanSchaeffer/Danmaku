package com.elven.danmaku.core.bullets.spawner;

public abstract class AbstractBulletSpawner implements BulletSpawner {

	private boolean active;

	@Override
	public final boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
}
