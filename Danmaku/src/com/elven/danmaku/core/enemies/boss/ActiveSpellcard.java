package com.elven.danmaku.core.enemies.boss;

import com.elven.danmaku.core.enemies.boss.spellcard.BossAttack;

class ActiveSpellcard {

	private BossAttack currentAttack;
	private int currentHitPoints;
	
	public ActiveSpellcard(BossAttack bossAttack) {
		currentAttack = bossAttack;
		currentHitPoints = currentAttack.getMaxHitPoints();
	}
	
	public BossAttack getAttack() {
		return currentAttack;
	}
	
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}
	
	public void damage(int damage) {
		currentHitPoints = Math.max(0, currentHitPoints - damage);
	}
	
	public boolean isDestroyed() {
		return currentHitPoints == 0;
	}
}
