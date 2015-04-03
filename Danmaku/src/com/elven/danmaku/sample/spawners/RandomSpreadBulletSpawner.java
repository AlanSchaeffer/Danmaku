package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.AbstractBulletSpawner;
import com.elven.danmaku.core.bullets.target.AimTarget;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;

public class RandomSpreadBulletSpawner extends AbstractBulletSpawner {

	private final DefaultBulletFactory bulletFactory;
	private final StageController stage;
	private MoveControllerFactory moveControllerFactory = new DefaultMoveControllerFactory();
	private double bulletSpeed = 5.0;
	private double spreadAmount = 0.0;
	private int bulletsPerFrame = 1;
	private Vector2D origin;
	private AimTarget target;

	public RandomSpreadBulletSpawner(StageController stage, DefaultBulletFactory bulletFactory) {
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

	public double getSpreadAmount() {
		return spreadAmount;
	}

	public void setSpreadAmount(double spreadAmount) {
		this.spreadAmount = spreadAmount;
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
			for(int i = 0; i < bulletsPerFrame; i++) {
				Vector2D force = createRandomizedForce();
				Controller controller = moveControllerFactory.createMoveController(force);
				bulletFactory.setController(controller);
				
				Bullet bullet = bulletFactory.createBullet(new Vector2D(origin));
				stage.bullets().spawn(bullet);
			}
		}
	}

	private Vector2D createRandomizedForce() {
		Vector2D targetVector = target != null ? target.getTarget() : new Vector2D(origin.getX(), origin.getY() + 1);
		
		double randomization = Math.random() * spreadAmount - spreadAmount / 2;
		
		Angle angle = new Angle(origin, targetVector);
		angle.add(randomization);
		return angle.toVector(bulletSpeed);
	}
}
