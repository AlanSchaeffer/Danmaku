package com.elven.danmaku.sample.stage01;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.configuration.script.ScriptAction;
import com.elven.danmaku.core.configuration.script.StageScript;
import com.elven.danmaku.core.elements.controller.AccelerationMoveController;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.DotBulletSpriteFactory;
import com.elven.danmaku.sample.stage01.enemies.SlowStreamedEnemyFactory;
import com.elven.danmaku.sample.stage01.enemies.Stage01BossFactory;

public class Stage01Script extends StageScript {

	@Override
	protected void configure() {
		wait(20);
		
		bulletSpam();
		wait(120);

		streamedEnemiesOnLeft();
		wait(40);

		streamedEnemiesOnRight();
		wait(120);
		
		boss();
	}

	private void bulletSpam() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 30; y++) {
				bullet().spawn(createRandomBullet());
			}

			wait(90);
		}
	}

	private void streamedEnemiesOnLeft() {
		SlowStreamedEnemyFactory streamedEnemyFactory = new SlowStreamedEnemyFactory(getStage(), gameInfo(), gameContext().textureLoader());
		streamedEnemyFactory.setPosition(new Vector2D(50.0, 0.0));
		for (int x = 0; x < 10; x++) {
			enemy().spawn(streamedEnemyFactory);
			wait(20);
		}
	}

	private void streamedEnemiesOnRight() {
		SlowStreamedEnemyFactory streamedEnemyFactory = new SlowStreamedEnemyFactory(getStage(), gameInfo(), gameContext().textureLoader());
		streamedEnemyFactory.setPosition(new Vector2D(320.0, 0.0));
		for (int x = 0; x < 10; x++) {
			enemy().spawn(streamedEnemyFactory);
			wait(20);
		}
	}
	
	private void boss() {
		final Stage01BossFactory factory = new Stage01BossFactory(getStage(), gameInfo(), gameContext().textureLoader());
		enemy().spawn(factory);
		
		wait(300);
		addAction(new ScriptAction() {
			@Override
			public void tick() {
				getStage().getView().getGadgetsLayer().addElement(factory.createLifeCounter());
			}
			
			@Override
			public boolean shouldResume() {
				return true;
			}
			
			@Override
			public boolean shouldRemove() {
				return true;
			}
		});
	}

	private Bullet createRandomBullet() {
		double x = Math.random() * 2.0 - 1.0;
		double randomAcceleration = 50.0 + (Math.random() * 40.0);
		double randomMaxForceY = 2.0 + (Math.random() * 3.0);

		MoveController controller = new MoveController(new Vector2D(x, 0.01));
		AccelerationMoveController accelerationController = new AccelerationMoveController(controller);
		accelerationController.setDecelerationFactor(randomAcceleration);
		accelerationController.setFinalForce(new Vector2D(x, randomMaxForceY));
		accelerationController.setDuration(180);

		double randomStartX = 10.0 + (Math.random() * 350.0);
		double randomStartY = 30.0 + (Math.random() * 20.0);

		Bullet bullet = new Bullet(DotBulletSpriteFactory.create(gameContext().textureLoader()), accelerationController, new Vector2D(randomStartX, randomStartY));
		bullet.setHitbox(new CircleHitbox(bullet, 2.0));
		return bullet;
	}
}
