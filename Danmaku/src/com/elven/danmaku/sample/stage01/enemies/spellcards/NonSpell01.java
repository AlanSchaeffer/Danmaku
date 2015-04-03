package com.elven.danmaku.sample.stage01.enemies.spellcards;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.enemies.boss.Boss;
import com.elven.danmaku.core.enemies.boss.spellcard.BossAttack;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.DotBulletSpriteFactory;

public class NonSpell01 implements BossAttack {

	private final StageController stage;
	private final Boss boss;
	private final Sprite bulletSprite;
	
	private int initWait = 0;
	private int waitBullets = 0;

	public NonSpell01(StageController stage, Boss boss, TextureLoader loader) {
		this.stage = stage;
		this.boss = boss;
		
		bulletSprite = DotBulletSpriteFactory.create(loader);
	}
	
	@Override
	public void tick() {
		if (initWait < 320) {
			initWait++;
		} else {
			if (waitBullets == 0) {
				for (int i = 0; i < 360; i += 20) {
					Angle angle = new Angle(boss.getPosition(), stage.getPlayer().getPosition());
					angle.add(Math.toRadians(i));
					Bullet bullet = new Bullet(bulletSprite, new MoveController(angle.toVector(2.0)), new Vector2D(boss.getPosition()));
					bullet.setHitbox(new CircleHitbox(bullet, 2.0));
					stage.bullets().spawn(bullet);
				}
			}

			waitBullets = (waitBullets + 1) % 20;
		}
	}

	@Override
	public int getMaxHitPoints() {
		return 50000;
	}

	@Override
	public int getCaptureScore() {
		return 1000;
	}

	@Override
	public int getTimer() {
		return 0;
	}

	@Override
	public int getStartInvincibilityFrames() {
		return 330;
	}

}
