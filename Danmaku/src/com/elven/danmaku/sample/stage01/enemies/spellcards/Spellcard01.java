package com.elven.danmaku.sample.stage01.enemies.spellcards;

import java.awt.Color;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.controller.AccelerationMoveController;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.enemies.boss.Boss;
import com.elven.danmaku.core.enemies.boss.spellcard.BossAttack;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.BallBulletSpriteFactory;
import com.elven.danmaku.sample.bullets.BubbleBulletSpriteFactory;

public class Spellcard01 implements BossAttack {

	private final StageController stage;
	private final Boss boss;

	private int wait = 0;
	private int shootingBubbles = 0;
	private int shootingBulletsFromSky = 0;

	private final Sprite bubbleSprite;
	private final Sprite ballSprite;

	public Spellcard01(StageController stage, Boss boss, TextureLoader loader) {
		this.stage = stage;
		this.boss = boss;
		
		ballSprite = BallBulletSpriteFactory.create(Color.MAGENTA, loader);
		bubbleSprite = BubbleBulletSpriteFactory.create(Color.BLUE, loader);
	}

	@Override
	public void tick() {
		if (wait < 70) {
			wait++;
		} else {
			if (shootingBubbles < 30) {
				if (shootingBubbles % 3 == 0) {
					Angle angle = new Angle(boss.getPosition(), stage.getPlayer().getPosition());
					angle.jitter(0.15);
					Bullet bubble = new Bullet(bubbleSprite, new MoveController(angle.toVector(2.0 + Math.random() * 2.5)), new Vector2D(boss.getPosition()));
					bubble.setHitbox(new CircleHitbox(bubble, 12.0));
					stage.bullets().spawn(bubble);
				}

				if (++shootingBubbles == 30) {
					wait = 30;
				}
			} else if (shootingBulletsFromSky < 120) {
				if (shootingBulletsFromSky % 3 == 0) {
					AccelerationMoveController controller = new AccelerationMoveController(new MoveController(new Vector2D()));
					controller.setDuration(60);
					controller.setDecelerationFactor(50.0);
					controller.setFinalForce(new Vector2D(Math.random() * 2.0 - 1.0, 2.0 + Math.random() * 1.0));

					Bullet bullet = new Bullet(ballSprite, controller, new Vector2D(stage.getView().getPlayableArea().x + Math.random() * (stage.getView().getPlayableArea().width), 0.0));
					bullet.setHitbox(new CircleHitbox(bullet, 4.0));
					stage.bullets().spawn(bullet);
				}

				shootingBulletsFromSky++;
			} else {
				shootingBubbles = 0;
				shootingBulletsFromSky = 0;
				wait = 20;
			}
		}
	}

	@Override
	public int getMaxHitPoints() {
		return 100000;
	}

	@Override
	public int getCaptureScore() {
		return 50000;
	}

	@Override
	public int getTimer() {
		return 0;
	}

	@Override
	public int getStartInvincibilityFrames() {
		return 120;
	}
}
