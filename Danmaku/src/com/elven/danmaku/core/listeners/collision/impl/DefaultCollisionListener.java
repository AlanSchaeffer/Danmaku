package com.elven.danmaku.core.listeners.collision.impl;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.items.CollectableItem;
import com.elven.danmaku.core.listeners.CollisionType;
import com.elven.danmaku.core.listeners.collision.CollisionListener;
import com.elven.danmaku.core.listeners.collision.EnemyHitListener;
import com.elven.danmaku.core.listeners.collision.GrazeListener;
import com.elven.danmaku.core.listeners.collision.ItemCollectionListener;
import com.elven.danmaku.core.listeners.collision.PlayerHitListener;
import com.elven.danmaku.core.player.PlayerBullet;

public class DefaultCollisionListener implements CollisionListener {

	private GrazeListener grazeListener = new NullGrazeListener();
	private PlayerHitListener playerHitListener = new NullPlayerHitListener();
	private EnemyHitListener enemyHitListener = new NullEnemyHitListener();
	private ItemCollectionListener itemCollectionListener = new NullItemCollectionListener();

	public void setGrazeListener(GrazeListener grazeListener) {
		this.grazeListener = grazeListener;
	}

	public void setPlayerHitListener(PlayerHitListener playerHitListener) {
		this.playerHitListener = playerHitListener;
	}

	public void setEnemyHitListener(EnemyHitListener enemyHitListener) {
		this.enemyHitListener = enemyHitListener;
	}
	
	public void setItemCollectionListener(ItemCollectionListener itemCollectionListener) {
		this.itemCollectionListener = itemCollectionListener;
	}

	@Override
	public void collided(AbstractPlaceableGameElement element, AbstractPlaceableGameElement collidedWith, CollisionType type) {
		if (type == CollisionType.HOSTILE) {
			playerHitListener.playerHit(element);
		} else if (type == CollisionType.GRAZE) {
			grazeListener.elementGrazed(element);
		} else if (type == CollisionType.ENEMY_HIT) {
			PlayerBullet bullet = (PlayerBullet) element;
			AbstractHostileEntity enemy = (AbstractHostileEntity) collidedWith;
			enemyHitListener.enemyHit(enemy, bullet);
		} else if (type == CollisionType.ITEM) {
			itemCollectionListener.itemCollected((CollectableItem) element);
		}
	}

	private static class NullGrazeListener implements GrazeListener {
		@Override
		public void elementGrazed(AbstractPlaceableGameElement element) {
		}
	}

	private static class NullPlayerHitListener implements PlayerHitListener {
		@Override
		public void playerHit(AbstractPlaceableGameElement element) {
		}
	}

	private static class NullEnemyHitListener implements EnemyHitListener {
		@Override
		public void enemyHit(AbstractHostileEntity enemy, PlayerBullet bullet) {
		}
	}

	private static class NullItemCollectionListener implements ItemCollectionListener {
		@Override
		public void itemCollected(CollectableItem item) {
		}
	}
}
