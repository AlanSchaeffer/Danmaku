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
import com.elven.danmaku.sample.spawners.DelayedAccelerationMoveControllerFactory;
import com.elven.danmaku.sample.spawners.FastStreamBulletSpawner;

public class StreamSpellcard implements Spellcard {

	private final RandomJitterPointTarget target = new RandomJitterPointTarget();
	private StageController stage;
	private DelayedAccelerationMoveControllerFactory delayedAccelerator;
	private BurstBulletSpawner burstSpawner;
	private Vector2D origin = new Vector2D(200.0, 20.0);

	private final Sprite redBallBulletSprite;
	
	public StreamSpellcard(StageController stage, TextureLoader loader) {
		this.stage = stage;
		redBallBulletSprite = BallBulletSpriteFactory.create(Color.RED, loader);
		
		initialize();
	}

	private void initialize() {
		target.setJitterAmount(40.0);

		delayedAccelerator = new DelayedAccelerationMoveControllerFactory();
		delayedAccelerator.setDuration(40);
		delayedAccelerator.setFinalForceFactor(0.2);
		delayedAccelerator.setDecelerationFactor(20);

		DefaultBulletFactory bulletFactory = new DefaultBulletFactory();
		bulletFactory.setSprite(redBallBulletSprite);
		bulletFactory.setHitboxFactory(new CircleHitboxFactory(5.0));

		FastStreamBulletSpawner spawner = new FastStreamBulletSpawner(stage, bulletFactory);
		spawner.setMoveControllerFactory(delayedAccelerator);
		spawner.setBulletsPerFrame(2);
		spawner.setBulletSpeed(10.0);
		spawner.setOrigin(origin);
		spawner.setTarget(target);

		burstSpawner = new BurstBulletSpawner(spawner);
		burstSpawner.setFramesBetweenBursts(60);
		burstSpawner.setFramesPerBurst(10);
		burstSpawner.setActive(false);

		burstSpawner.addBurstListener(new BurstListener() {
			@Override
			public void burstStarted() {
				Vector2D vector = new Vector2D(stage.getPlayer().getPosition());
				target.setTarget(vector);
			}

			@Override
			public void burstEnded() {
				delayedAccelerator.activateAll();
			}
		});
	}

	public Vector2D getOrigin() {
		return origin;
	}

	public void register() {
		stage.registerElement(burstSpawner);
	}

	public void setActive(boolean active) {
		burstSpawner.setActive(active);

		if (active) {
			delayedAccelerator.activateAll();
		}
	}

	@Override
	public boolean isActive() {
		return burstSpawner.isActive();
	}
}
