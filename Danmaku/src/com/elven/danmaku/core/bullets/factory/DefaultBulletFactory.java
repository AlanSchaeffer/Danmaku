package com.elven.danmaku.core.bullets.factory;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.hitbox.HitboxFactory;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.system.Vector2D;

public class DefaultBulletFactory implements BulletFactory {

	private Sprite sprite;
	private Controller controller;
	private HitboxFactory hitboxFactory;

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setHitboxFactory(HitboxFactory hitboxFactory) {
		this.hitboxFactory = hitboxFactory;
	}

	@Override
	public Bullet createBullet() {
		return createBullet(new Vector2D());
	}
	
	public Bullet createBullet(Vector2D position) {
		Bullet bullet = new Bullet(sprite, controller, position);
		bullet.setHitbox(hitboxFactory.createHitboxFor(bullet));
		return bullet;
	}
}
