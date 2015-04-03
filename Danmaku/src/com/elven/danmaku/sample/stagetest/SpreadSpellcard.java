package com.elven.danmaku.sample.stagetest;

import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.BurstBulletSpawner;
import com.elven.danmaku.core.bullets.target.PointTarget;
import com.elven.danmaku.core.elements.hitbox.CircleHitboxFactory;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.BurstListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.DotBulletSpriteFactory;
import com.elven.danmaku.sample.spawners.AccelerationMoveControllerFactory;
import com.elven.danmaku.sample.spawners.RandomSpreadBulletSpawner;

public class SpreadSpellcard implements Spellcard {
	
	private final PointTarget target = new PointTarget();
	private StageController stage;
	private BurstBulletSpawner burstSpawner;
	
	private final Sprite dotBulletSprite;

	public SpreadSpellcard(StageController stage, TextureLoader loader) {
		this.stage = stage;
		dotBulletSprite = DotBulletSpriteFactory.create(loader);
		
		initialize();
	}
	
	private void initialize() {
		AccelerationMoveControllerFactory acceleratorFactory = new AccelerationMoveControllerFactory();
		acceleratorFactory.setDuration(180);
		acceleratorFactory.setFinalForceFactor(0.1);
		acceleratorFactory.setDecelerationFactor(20);
		
		DefaultBulletFactory bulletFactory = new DefaultBulletFactory();
		bulletFactory.setSprite(dotBulletSprite);
		bulletFactory.setHitboxFactory(new CircleHitboxFactory(2.0));
		
		RandomSpreadBulletSpawner spawner = new RandomSpreadBulletSpawner(stage, bulletFactory);
		spawner.setMoveControllerFactory(acceleratorFactory);
		spawner.setBulletsPerFrame(3);
		spawner.setBulletSpeed(15.0);
		spawner.setOrigin(new Vector2D(200.0, 20.0));
		spawner.setSpreadAmount(1.5);
		spawner.setTarget(target);
		spawner.setActive(true);
		
		burstSpawner = new BurstBulletSpawner(spawner);
		burstSpawner.setFramesBetweenBursts(40);
		burstSpawner.setFramesPerBurst(30);
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
