package com.elven.danmaku.sample.stagetest;

import java.awt.Color;

import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.BurstBulletSpawner;
import com.elven.danmaku.core.bullets.target.PointTarget;
import com.elven.danmaku.core.elements.hitbox.CircleHitboxFactory;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.BurstListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.BallBulletSpriteFactory;
import com.elven.danmaku.sample.bullets.BubbleBulletSpriteFactory;
import com.elven.danmaku.sample.bullets.DotBulletSpriteFactory;
import com.elven.danmaku.sample.spawners.ExplosionBulletSpawner;

public class ExplosionSpellcard implements Spellcard {

	private final PointTarget target = new PointTarget();
	private StageController stage;
	private BurstBulletSpawner burstSpawner;

	private final Sprite redBallBulletSprite;
	private final Sprite greenBallBulletSprite;
	private final Sprite blueBallBulletSprite;
	private final Sprite yellowBallBulletSprite;
	private final Sprite cyanBallBulletSprite;
	private final Sprite pinkBallBulletSprite;
	private final Sprite orangeBallBulletSprite;

	private final Sprite magentaBubbleBulletSprite;
	private final Sprite redBubbleBulletSprite;
	private final Sprite orangeBubbleBulletSprite;
	private final Sprite yellowBubbleBulletSprite;
	
	private final Sprite dotBulletSprite;

	public ExplosionSpellcard(StageController stage, TextureLoader loader) {
		this.stage = stage;
		redBallBulletSprite = BallBulletSpriteFactory.create(Color.RED, loader);
		greenBallBulletSprite = BallBulletSpriteFactory.create(Color.GREEN, loader);
		blueBallBulletSprite = BallBulletSpriteFactory.create(Color.BLUE, loader);
		yellowBallBulletSprite = BallBulletSpriteFactory.create(Color.YELLOW, loader);
		cyanBallBulletSprite = BallBulletSpriteFactory.create(Color.CYAN, loader);
		pinkBallBulletSprite = BallBulletSpriteFactory.create(Color.PINK, loader);
		orangeBallBulletSprite = BallBulletSpriteFactory.create(Color.ORANGE, loader);

		magentaBubbleBulletSprite = BubbleBulletSpriteFactory.create(Color.MAGENTA, loader);
		redBubbleBulletSprite = BubbleBulletSpriteFactory.create(Color.RED, loader);
		orangeBubbleBulletSprite = BubbleBulletSpriteFactory.create(Color.ORANGE, loader);
		yellowBubbleBulletSprite = BubbleBulletSpriteFactory.create(Color.YELLOW, loader);
		
		dotBulletSprite = DotBulletSpriteFactory.create(loader);

		initialize();
	}

	private void initialize() {
		DefaultBulletFactory bombFactory = createBulletFactory(magentaBubbleBulletSprite, 12.0);

		ExplosionBulletSpawner spawner = new ExplosionBulletSpawner(stage, bombFactory);
		spawner.setOrigin(new Vector2D(200.0, 20.0));
		spawner.addRandomBulletFactory(createBulletFactory(redBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(greenBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(blueBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(yellowBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(cyanBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(pinkBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(orangeBallBulletSprite, 5.0));
		spawner.addRandomBulletFactory(createBulletFactory(redBubbleBulletSprite, 12.0));
		spawner.addRandomBulletFactory(createBulletFactory(orangeBubbleBulletSprite, 12.0));
		spawner.addRandomBulletFactory(createBulletFactory(yellowBubbleBulletSprite, 12.0));
		spawner.addRandomBulletFactory(createBulletFactory(dotBulletSprite, 2.0));
		spawner.setMinShardSpeed(0.5);
		spawner.setMaxShardSpeed(3.0);
		spawner.setBulletsPerExplosion(250);
		spawner.setBombMovementFrames(120);
		spawner.setBombWaitFrames(180);
		spawner.setTarget(target);

		burstSpawner = new BurstBulletSpawner(spawner);
		burstSpawner.setFramesBetweenBursts(500);
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

	private DefaultBulletFactory createBulletFactory(Sprite sprite, double radius) {
		DefaultBulletFactory factory = new DefaultBulletFactory();
		factory.setSprite(sprite);
		factory.setHitboxFactory(new CircleHitboxFactory(radius));
		return factory;
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
