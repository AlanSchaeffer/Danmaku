package com.elven.danmaku.sample.stage01.enemies;

import java.awt.Color;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.Enemy;
import com.elven.danmaku.core.enemies.EnemyFactory;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.ElementRemovedListener;
import com.elven.danmaku.core.listeners.EnemyDestroyedListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.BallBulletSpriteFactory;

public class SlowStreamedEnemyFactory implements EnemyFactory {

	private final StageController stage;
	private Vector2D position;
	
	private final Sprite greenBallBulletSprite;
	private final TextureLoader loader;
	
	private final PointItemFactory pointItemFactory;
	
	public SlowStreamedEnemyFactory(StageController stage, GameInfo gameInfo, TextureLoader loader) {
		this.stage = stage;
		this.loader = loader;
		greenBallBulletSprite = BallBulletSpriteFactory.create(Color.GREEN, loader);
		
		pointItemFactory = new PointItemFactory(stage, gameInfo, 7500, loader);
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	@Override
	public Enemy createEnemy() {
		final Enemy enemy = new Enemy(greenBallBulletSprite, new MoveController(new Vector2D(0.0, 1.0)), new Vector2D(position));
		enemy.setHitbox(new CircleHitbox(enemy, 5.0));
		enemy.setTargetHitbox(enemy.getHitbox());
		enemy.setMaxHitPoints(250);
		enemy.setScoreWorth(500);
		
		enemy.addEnemyDestroyedListener(new EnemyDestroyedListener() {
			@Override
			public void enemyDestroyed(AbstractHostileEntity enemy) {
				pointItemFactory.createItems(enemy.getPosition(), 6);
			}
		});
		
		final StreamedEnemyBulletSpawner spawner = new StreamedEnemyBulletSpawner(stage, enemy, loader);
		stage.registerElement(spawner);
		
		stage.addElementRemovedListener(new ElementRemovedListener() {
			
			@Override
			public void elementRemoved(AbstractPlaceableGameElement element) {
				if(element == enemy) {
					stage.unregisterElement(spawner);
					stage.removeElementRemovedListener(this);
				}
			}
		});
		
		return enemy;
	}
}
