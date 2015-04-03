package com.elven.danmaku.sample.stagetest;

import java.awt.Color;

import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.BurstBulletSpawner;
import com.elven.danmaku.core.bullets.target.RandomJitterPointTarget;
import com.elven.danmaku.core.elements.hitbox.CircleHitboxFactory;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.BurstListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.BallBulletSpriteFactory;
import com.elven.danmaku.sample.spawners.RedirectionBulletSpawner;

public class RandomRedirectionSpellcard implements Spellcard {

	private final RandomJitterPointTarget target = new RandomJitterPointTarget();
	private StageController stage;
	private BurstBulletSpawner burstSpawner;

	private final Sprite cyanBallBulletSprite;

	public RandomRedirectionSpellcard(StageController stage, TextureLoader loader) {
		this.stage = stage;
		cyanBallBulletSprite = BallBulletSpriteFactory.create(Color.CYAN, loader);
		
		initialize();
	}

	private void initialize() {
		target.setJitterAmount(35.0);

		DefaultBulletFactory bulletFactory = new DefaultBulletFactory();
		bulletFactory.setSprite(cyanBallBulletSprite);
		bulletFactory.setHitboxFactory(new CircleHitboxFactory(5.0));

		RedirectionBulletSpawner spawner = new RedirectionBulletSpawner(stage, bulletFactory);
		spawner.setOrigin(new Vector2D(200.0, 20.0));
		spawner.setMinStreamedSpeed(1.5);
		spawner.setMaxStreamedSpeed(2.5);
		spawner.setMinRedirectedSpeed(3.5);
		spawner.setMaxRedirectedSpeed(8.0);
		spawner.setBulletsPerFrame(160);
		spawner.setStreamedFrames(120);
		spawner.setWaitFrames(20);
		spawner.setTarget(target);

		burstSpawner = new BurstBulletSpawner(spawner);
		burstSpawner.setFramesBetweenBursts(60);
		burstSpawner.setFramesPerBurst(1);
		burstSpawner.setActive(false);

		burstSpawner.addBurstListener(new BurstListener() {
			@Override
			public void burstStarted() {
				Vector2D vector = new Vector2D(stage.getPlayer().getPosition());
				target.setTarget(vector);
			}

			@Override
			public void burstEnded() {
			}
		});
	}

	public void register() {
		stage.registerElement(burstSpawner);
	}

	public void setActive(boolean active) {
		burstSpawner.setActive(active);
	}

	@Override
	public boolean isActive() {
		return burstSpawner.isActive();
	}
}
