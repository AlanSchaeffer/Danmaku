package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.AbstractBulletSpawner;
import com.elven.danmaku.core.bullets.target.AimTarget;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;

public class RadialBulletSpawner extends AbstractBulletSpawner {

	private final DefaultBulletFactory bulletFactory;
	private final StageController stage;
	private double bulletSpeed = 5.0;
	private int bulletsPerRing = 6;
	private MoveControllerFactory moveControllerFactory = new DefaultMoveControllerFactory();

	private Vector2D origin;
	private AimTarget target;

	private double preCalculatedAngleVariation = 60.0 * (Math.PI / 180.0);

	public RadialBulletSpawner(StageController stage, DefaultBulletFactory bulletFactory) {
		this.stage = stage;
		this.bulletFactory = bulletFactory;
	}

	public void setMoveControllerFactory(MoveControllerFactory moveControllerFactory) {
		this.moveControllerFactory = moveControllerFactory;
	}

	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public double getBulletsPerRing() {
		return bulletsPerRing;
	}

	public void setBulletsPerRing(int bulletsPerRing) {
		this.bulletsPerRing = bulletsPerRing;
		preCalculatedAngleVariation = ((360 / bulletsPerRing) * (Math.PI / 180.0));
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
		if (isActive()) {
			for (int i = 0; i < bulletsPerRing; i++) {
				Vector2D force = createForce(i);
				Controller controller = moveControllerFactory.createMoveController(force);
				bulletFactory.setController(controller);
				Bullet bullet = bulletFactory.createBullet(new Vector2D(origin));
				stage.bullets().spawn(bullet);
			}
		}
	}

	private Vector2D createForce(int iteration) {
		Vector2D targetVector = target != null ? target.getTarget() : new Vector2D(origin.getX(), origin.getY() + 1);
		Angle angle = new Angle(origin, targetVector);
		angle.add(preCalculatedAngleVariation * iteration);
		return angle.toVector(bulletSpeed);
	}
}
