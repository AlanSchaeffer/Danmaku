package com.elven.danmaku.core.listeners.collision.impl;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.fx.EffectFactory;
import com.elven.danmaku.core.graphics.fx.NullEffectFactory;
import com.elven.danmaku.core.listeners.collision.PlayerHitListener;
import com.elven.danmaku.core.stage.StageController;

public class DefaultPlayerHitListener implements PlayerHitListener {

	private final StageController stage;
	private final GameInfo gameInfo;
	
	private EffectFactory playerDeathEffectFactory = new NullEffectFactory();
	private int invincibilityFrames = 360;
	private int disabledFrames = 60;

	
	public DefaultPlayerHitListener(StageController stage, GameInfo gameInfo) {
		this.stage = stage;
		this.gameInfo = gameInfo;
	}

	public void setPlayerDeathEffectFactory(EffectFactory playerDeathEffectFactory) {
		this.playerDeathEffectFactory = playerDeathEffectFactory;
	}
	
	public void setInvincibilityFrames(int invincibilityFrames) {
		this.invincibilityFrames = invincibilityFrames;
	}
	
	public void setDisabledFrames(int disabledFrames) {
		this.disabledFrames = disabledFrames;
	}
	
	@Override
	public void playerHit(AbstractPlaceableGameElement element) {
		if (!stage.getPlayer().isInvincible()) {
			stage.setHostileCollisionsAndItemCollectionActive(false);
			stage.doLater(disabledFrames, new Runnable() {
				@Override
				public void run() {
					stage.setHostileCollisionsAndItemCollectionActive(true);
				}
			});
			
			playerDeathEffectFactory.createEffect(stage.getPlayer().getPosition());
			stage.getPlayer().getController().getInputDisabledController().setFrames(disabledFrames);
			stage.getPlayer().getController().getInvincibilityController().setFrames(invincibilityFrames);
			gameInfo.getLifeModel().decrease(1);
		}
	}
}
