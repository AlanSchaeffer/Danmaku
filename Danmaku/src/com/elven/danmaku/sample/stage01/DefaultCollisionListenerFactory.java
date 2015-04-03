package com.elven.danmaku.sample.stage01;

import com.elven.danmaku.core.configuration.CollisionListenerFactory;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.collision.CollisionListener;
import com.elven.danmaku.core.listeners.collision.GrazeListener;
import com.elven.danmaku.core.listeners.collision.PlayerHitListener;
import com.elven.danmaku.core.listeners.collision.impl.DefaultCollisionListener;
import com.elven.danmaku.core.listeners.collision.impl.DefaultEnemyHitListener;
import com.elven.danmaku.core.listeners.collision.impl.DefaultGrazeListener;
import com.elven.danmaku.core.listeners.collision.impl.DefaultItemCollectionListener;
import com.elven.danmaku.core.listeners.collision.impl.DefaultPlayerHitListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.sample.GrazeSparksFactory;
import com.elven.danmaku.sample.PichuunEffectFactory;

public class DefaultCollisionListenerFactory implements CollisionListenerFactory {

	private TextureLoader loader;

	public DefaultCollisionListenerFactory(TextureLoader loader) {
		this.loader = loader;
	}
	
	public CollisionListener createCollisionListener(StageController stage, GameInfo gameInfo) {
		DefaultCollisionListener collisionListener = new DefaultCollisionListener();
		collisionListener.setGrazeListener(createGrazeListener(stage, gameInfo));
		collisionListener.setPlayerHitListener(createPlayerHitListener(stage, gameInfo));
		collisionListener.setEnemyHitListener(new DefaultEnemyHitListener(stage, gameInfo));
		collisionListener.setItemCollectionListener(new DefaultItemCollectionListener(stage));
		return collisionListener;
	}
	
	private GrazeListener createGrazeListener(StageController stage, GameInfo gameInfo) {
		DefaultGrazeListener grazeListener = new DefaultGrazeListener(stage, gameInfo);
		grazeListener.setGrazeEffectFactory(new GrazeSparksFactory(stage, loader));
		return grazeListener;
	}
	
	private PlayerHitListener createPlayerHitListener(StageController stage, GameInfo gameInfo) {
		DefaultPlayerHitListener playerHitListener = new DefaultPlayerHitListener(stage, gameInfo);
		playerHitListener.setPlayerDeathEffectFactory(new PichuunEffectFactory(stage, loader));
		return playerHitListener;
	}
}
