package com.elven.danmaku.sample.stagetest;

import java.awt.Color;

import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.BurstBulletSpawner;
import com.elven.danmaku.core.bullets.target.JitterPointTarget;
import com.elven.danmaku.core.elements.hitbox.CircleHitboxFactory;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.BurstListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.BallBulletSpriteFactory;
import com.elven.danmaku.sample.spawners.RadialBulletSpawner;

public class RadialSpellcard implements Spellcard {

	private final JitterPointTarget target = new JitterPointTarget();
	private StageController stage;
	private BurstBulletSpawner burstSpawner;

	private final Sprite blueBallBulletSprite;
	
	public RadialSpellcard(StageController stage, TextureLoader loader) {
		this.stage = stage;
		blueBallBulletSprite = BallBulletSpriteFactory.create(Color.BLUE, loader);
		
		initialize();
	}

	private void initialize() {
		target.setJitterAmount(60.0);

		DefaultBulletFactory bulletFactory = new DefaultBulletFactory();
		bulletFactory.setSprite(blueBallBulletSprite);
		bulletFactory.setHitboxFactory(new CircleHitboxFactory(5.0));

		RadialBulletSpawner spawner = new RadialBulletSpawner(stage, bulletFactory);
		spawner.setOrigin(new Vector2D(200.0, 300.0));
		spawner.setBulletSpeed(4.0);
		spawner.setBulletsPerRing(15);
		spawner.setTarget(target);

		burstSpawner = new BurstBulletSpawner(spawner);
		burstSpawner.setFramesBetweenBursts(25);
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
				target.updateTarget();
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
