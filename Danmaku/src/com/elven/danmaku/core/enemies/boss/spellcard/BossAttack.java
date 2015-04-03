package com.elven.danmaku.core.enemies.boss.spellcard;

import com.elven.danmaku.core.elements.UpdateableGameElement;

public interface BossAttack extends UpdateableGameElement {

	public int getMaxHitPoints();
	
	public int getCaptureScore();
	
	public int getTimer();
	
	public int getStartInvincibilityFrames();
}
