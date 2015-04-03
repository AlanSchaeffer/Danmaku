package com.elven.danmaku.core.enemies.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.boss.spellcard.BossAttack;
import com.elven.danmaku.core.listeners.SpellcardDestroyedListener;
import com.elven.danmaku.core.system.Vector2D;

public class Boss extends AbstractHostileEntity {

	private long scoreWorth = 10000;
	
	private final Queue<BossAttack> spellcards = new ConcurrentLinkedQueue<>();
	private ActiveSpellcard currentSpellcard;
	
	private final List<SpellcardDestroyedListener> spellcardDestroyedListeners = new ArrayList<>();

	public Boss(Sprite sprite, Controller controller) {
		this(sprite, controller, new Vector2D());
	}

	public Boss(Sprite sprite, Controller controller, Vector2D position) {
		super(sprite, controller, position);
	}

	@Override
	public int getMaxHitPoints() {
		return currentSpellcard != null ? currentSpellcard.getAttack().getMaxHitPoints() : 0;
	}

	@Override
	public int getCurrentHitPoints() {
		return currentSpellcard != null ? currentSpellcard.getCurrentHitPoints() : 0;
	}

	@Override
	public void damage(int damage) {
		if(!getInvincibilityController().isActive()) {
			if(currentSpellcard != null) {
				currentSpellcard.damage(damage);
				
				if (currentSpellcard.isDestroyed()) {
					if(!spellcards.isEmpty()) {
						BossAttack oldSpellcard = currentSpellcard.getAttack();
						currentSpellcard = new ActiveSpellcard(spellcards.poll());
						getInvincibilityController().setFrames(currentSpellcard.getAttack().getStartInvincibilityFrames());
						fireSpellcardDestroyed(oldSpellcard);
					} else {
						fireSpellcardDestroyed(currentSpellcard.getAttack());
						fireEnemyDestroyed();
					}
				}
			}
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(currentSpellcard != null && !currentSpellcard.isDestroyed()) {
			currentSpellcard.getAttack().tick();
		}
	}

	@Override
	public long getScoreWorth() {
		return scoreWorth;
	}

	public void setScoreWorth(long scoreWorth) {
		this.scoreWorth = scoreWorth;
	}
	
	public void addAttack(BossAttack attack) {
		if(currentSpellcard == null) {
			currentSpellcard = new ActiveSpellcard(attack);
			getInvincibilityController().setFrames(attack.getStartInvincibilityFrames());
		} else {
			spellcards.add(attack);
		}
	}
	
	public final void addSpellcardDestroyedListener(SpellcardDestroyedListener listener) {
		spellcardDestroyedListeners.add(listener);
	}
	
	public final void removeSpellcardDestroyedListener(SpellcardDestroyedListener listener) {
		spellcardDestroyedListeners.remove(listener);
	}
	
	protected final void fireSpellcardDestroyed(BossAttack spellcard) {
		for(SpellcardDestroyedListener listener : spellcardDestroyedListeners) {
			listener.spellcardDestroyed(spellcard);
		}
	}
}
