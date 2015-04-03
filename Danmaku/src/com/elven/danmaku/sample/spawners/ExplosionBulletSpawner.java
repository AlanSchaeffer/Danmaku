package com.elven.danmaku.sample.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.AbstractBulletSpawner;
import com.elven.danmaku.core.bullets.target.AimTarget;
import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.CompositeController;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.controller.UniformAccelerationMoveController;
import com.elven.danmaku.core.elements.controller.WaitController;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;

//TODO
public class ExplosionBulletSpawner extends AbstractBulletSpawner {

	private final DefaultBulletFactory bombBulletFactory;
	private final StageController stage;
	private final List<DefaultBulletFactory> randomBulletsFactories;
	private double minShardSpeed = 0.5;
	private double maxShardSpeed = 7.5;
	private int bulletsPerExplosion = 15;

	private int bombMovementFrames = 60;
	private int bombWaitFrames = 60;

	private Vector2D origin;
	private AimTarget target;

	public ExplosionBulletSpawner(StageController stage, DefaultBulletFactory bulletFactory) {
		this.stage = stage;
		this.bombBulletFactory = bulletFactory;
		randomBulletsFactories = new ArrayList<>();
	}

	public void addRandomBulletFactory(DefaultBulletFactory factory) {
		randomBulletsFactories.add(factory);
	}

	public void removeRandomBulletFactory(DefaultBulletFactory factory) {
		randomBulletsFactories.remove(factory);
	}

	public double getMinShardSpeed() {
		return minShardSpeed;
	}

	public void setMinShardSpeed(double minShardSpeed) {
		this.minShardSpeed = minShardSpeed;
	}

	public double getMaxShardSpeed() {
		return maxShardSpeed;
	}

	public void setMaxShardSpeed(double maxShardSpeed) {
		this.maxShardSpeed = maxShardSpeed;
	}

	public int getBulletsPerExplosion() {
		return bulletsPerExplosion;
	}

	public void setBulletsPerExplosion(int bulletsPerExplosion) {
		this.bulletsPerExplosion = bulletsPerExplosion;
	}

	public int getBombMovementFrames() {
		return bombMovementFrames;
	}

	public void setBombMovementFrames(int bombMovementFrames) {
		this.bombMovementFrames = bombMovementFrames;
	}

	public int getBombWaitFrames() {
		return bombWaitFrames;
	}

	public void setBombWaitFrames(int bombWaitFrames) {
		this.bombWaitFrames = bombWaitFrames;
	}

	public Vector2D getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2D origin) {
		this.origin = origin;
	}

	public void setTarget(AimTarget target) {
		this.target = target;
	}

	@Override
	public void tick() {
		if (isActive() && !randomBulletsFactories.isEmpty()) {
			CompositeController controller = new CompositeController();
			controller.addController(createMoveToController(), bombMovementFrames);
			controller.addController(new WaitController(), bombWaitFrames);
			controller.addController(new ExplodeController(), 0);
			bombBulletFactory.setController(controller);

			Bullet bullet = bombBulletFactory.createBullet(new Vector2D(origin));
			stage.bullets().spawn(bullet);
		}
	}

	private Controller createMoveToController() {
		Vector2D targetVector = target != null ? target.getTarget() : new Vector2D(origin.getX(), origin.getY() + 1);
		double deltaX = targetVector.getX() - origin.getX();
		double deltaY = targetVector.getY() - origin.getY();

		// Angle angle = new Angle(Math.atan2(deltaY, deltaX));

		double initialVelocityX = 2 * deltaX / bombMovementFrames;
		double initialVelocityY = 2 * deltaY / bombMovementFrames;

		// |v|^2= |u|^2 + 2 \, a \cdot s

		// double decelerationFactor = 1;
		// if (deltaX != 0.0) {
		// double accelX = bombSpeed * bombSpeed / (-2 * deltaX);
		// decelerationFactor = -accelX / (bombMovementFrames * 0.0001);
		// } else if (deltaY != 0.0) {
		// double accelY = bombSpeed * bombSpeed / (-2 * deltaY);
		// decelerationFactor = -accelY / (bombMovementFrames * 0.0001);
		// }

		MoveController moveController = new MoveController(new Vector2D(initialVelocityX, initialVelocityY));
		UniformAccelerationMoveController controller = new UniformAccelerationMoveController(moveController);
		controller.setDuration(bombMovementFrames);
		controller.setFinalForce(new Vector2D(0.0, 0.0));
		return controller;
	}

	private class ExplodeController implements Controller {

		@Override
		public void tick(AbstractPlaceableGameElement element) {
			Random random = new Random();
			double deltaSpeed = maxShardSpeed - minShardSpeed;

			for (int i = 0; i < bulletsPerExplosion; i++) {
				int randomFactory = random.nextInt(randomBulletsFactories.size());
				DefaultBulletFactory factory = randomBulletsFactories.get(randomFactory);

				Angle angle = new Angle(random.nextDouble() * Math.PI * 2);
				Vector2D force = angle.toVector(minShardSpeed + (deltaSpeed * random.nextDouble()));
				factory.setController(new MoveController(force));

				stage.bullets().spawn(factory.createBullet(new Vector2D(element.getPosition())));
			}

			stage.bullets().despawn((Bullet) element);
		}
	}
}
