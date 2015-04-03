package com.elven.danmaku.core.stage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.items.CollectableItem;
import com.elven.danmaku.core.player.PlayerBullet;

public class StageModel {

	private final BufferedStageElementQueue<Bullet> bullets = new BufferedStageElementQueue<Bullet>();
	private final BufferedStageElementQueue<AbstractHostileEntity> enemies = new BufferedStageElementQueue<AbstractHostileEntity>();
	private final BufferedStageElementQueue<PlayerBullet> playerBullets = new BufferedStageElementQueue<PlayerBullet>();
	private final BufferedStageElementQueue<CollectableItem> items = new BufferedStageElementQueue<CollectableItem>();
	private final Queue<UpdateableGameElement> elements = new ConcurrentLinkedQueue<UpdateableGameElement>();

	public BufferedStageElementQueue<Bullet> getBullets() {
		return bullets;
	}

	public BufferedStageElementQueue<AbstractHostileEntity> getEnemies() {
		return enemies;
	}
	
	public BufferedStageElementQueue<PlayerBullet> getPlayerBullets() {
		return playerBullets;
	}
	
	public BufferedStageElementQueue<CollectableItem> getItems() {
		return items;
	}

	public Queue<UpdateableGameElement> getElements() {
		return elements;
	}
}
