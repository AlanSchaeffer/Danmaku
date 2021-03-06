package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.AbstractBulletSpawner;
import com.elven.danmaku.core.bullets.target.AimTarget;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;

public class FastStreamBulletSpawner extends AbstractBulletSpawner {

	private final DefaultBulletFactory bulletFactory;
	private final StageController stage;
	private double bulletSpeed = 5.0;
	private int bulletsPerFrame = 5;
	private MoveControllerFactory moveControllerFactory = new DefaultMoveControllerFactory();

	private Vector2D origin;
	private AimTarget target;

	public FastStreamBulletSpawner(StageController stage, DefaultBulletFactory bulletFactory) {
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

	public double getBulletsPerFrame() {
		return bulletsPerFrame;
	}

	public void setBulletsPerFrame(int bulletsPerFrame) {
		this.bulletsPerFrame = bulletsPerFrame;
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
			for (int i = 0; i < bulletsPerFrame; i++) {
				Vector2D force = createForce();
				Controller controller = moveControllerFactory.createMoveController(force);
				bulletFactory.setController(controller);
				Bullet bullet = bulletFactory.createBullet(new Vector2D(origin));
				stage.bullets().spawn(bullet);
			}
		}
	}

	private Vector2D createForce() {
		Vector2D targetVector = target != null ? target.getTarget() : new Vector2D(origin.getX(), origin.getY() + 1);
		Angle angle = new Angle(origin, targetVector);
		return angle.toVector(bulletSpeed);
	}
}
