package com.elven.danmaku.sample.stage01.enemies;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.spawner.AbstractBulletSpawner;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.enemies.Enemy;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.DotBulletSpriteFactory;

class StreamedEnemyBulletSpawner extends AbstractBulletSpawner {

	private final StageController stage;
	private final Enemy enemy;
	private int timeBetweenAttacks = 60;
	private int wait = 0;
	
	private final Sprite sprite;
	
	public StreamedEnemyBulletSpawner(StageController stage, Enemy enemy, TextureLoader loader) {
		this.stage = stage;
		this.enemy = enemy;
		
		sprite = DotBulletSpriteFactory.create(loader);
	}
	
	@Override
	public void tick() {
		if(wait == 0) {
			Angle angle = new Angle(enemy.getPosition(), stage.getPlayer().getPosition());
			Vector2D movement = angle.toVector(2.0);
			Bullet bullet = createBullet(new MoveController(movement));
			stage.bullets().spawn(bullet);
		}
		
		wait = (wait + 1) % timeBetweenAttacks;
	}
	
	private Bullet createBullet(Controller controller) {
		Bullet bullet = new Bullet(sprite, controller, new Vector2D(enemy.getPosition()));
		bullet.setHitbox(new CircleHitbox(bullet, 2.0));
		return bullet;
	}
}
