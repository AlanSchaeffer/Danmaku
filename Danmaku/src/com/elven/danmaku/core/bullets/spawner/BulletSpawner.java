package com.elven.danmaku.core.bullets.spawner;

import com.elven.danmaku.core.elements.UpdateableGameElement;

public interface BulletSpawner extends UpdateableGameElement {
	
	public boolean isActive();

	public void setActive(boolean active);
}
