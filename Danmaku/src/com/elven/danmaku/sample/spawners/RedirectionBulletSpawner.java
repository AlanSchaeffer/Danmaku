package com.elven.danmaku.sample.spawners;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.bullets.factory.DefaultBulletFactory;
import com.elven.danmaku.core.bullets.spawner.AbstractBulletSpawner;
import com.elven.danmaku.core.bullets.target.AimTarget;
import com.elven.danmaku.core.elements.controller.AccelerationMoveController;
import com.elven.danmaku.core.elements.controller.CompositeController;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.controller.WaitController;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;

public class RedirectionBulletSpawner extends AbstractBulletSpawner {

	private final DefaultBulletFactory bulletFactory;
	private final StageController stage;
	private MoveControllerFactory moveControllerFactory = new DefaultMoveControllerFactory();
	private double minStreamedSpeed = 3.0;
	private double maxStreamedSpeed = 6.0;
	private double minRedirectedSpeed = 5.0;
	private double maxRedirectedSpeed = 5.0;
	private double spreadAmount = 0.0;
	private int bulletsPerFrame = 1;

	private int streamedFrames = 60;
	private int waitFrames = 60;

	private Vector2D origin;
	private AimTarget target;

	public RedirectionBulletSpawner(StageController stage, DefaultBulletFactory bulletFactory) {
		this.stage = stage;
		this.bulletFactory = bulletFactory;
	}

	public void setMoveControllerFactory(MoveControllerFactory moveControllerFactory) {
		this.moveControllerFactory = moveControllerFactory;
	}

	public double getMinStreamedSpeed() {
		return minStreamedSpeed;
	}

	public void setMinStreamedSpeed(double minStreamedSpeed) {
		this.minStreamedSpeed = minStreamedSpeed;
	}

	public double getMaxStreamedSpeed() {
		return maxStreamedSpeed;
	}

	public void setMaxStreamedSpeed(double maxStreamedSpeed) {
		this.maxStreamedSpeed = maxStreamedSpeed;
	}

	public double getMinRedirectedSpeed() {
		return minRedirectedSpeed;
	}

	public void setMinRedirectedSpeed(double minRedirectedSpeed) {
		this.minRedirectedSpeed = minRedirectedSpeed;
	}

	public double getMaxRedirectedSpeed() {
		return maxRedirectedSpeed;
	}

	public void setMaxRedirectedSpeed(double maxRedirectedSpeed) {
		this.maxRedirectedSpeed = maxRedirectedSpeed;
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

	public int getStreamedFrames() {
		return streamedFrames;
	}

	public void setStreamedFrames(int streamedFrames) {
		this.streamedFrames = streamedFrames;
	}

	public int getWaitFrames() {
		return waitFrames;
	}

	public void setWaitFrames(int waitFrames) {
		this.waitFrames = waitFrames;
	}

	@Override
	public void tick() {
		if (isActive()) {
			for (int i = 0; i < bulletsPerFrame; i++) {
				CompositeController controller = new CompositeController();
				controller.addController(createStreamedController(), streamedFrames);
				controller.addController(new WaitController(), waitFrames);
				controller.addController(createRandomController(), 0);
				bulletFactory.setController(controller);

				Bullet bullet = bulletFactory.createBullet(new Vector2D(origin));
				stage.bullets().spawn(bullet);
			}
		}
	}

	private Controller createStreamedController() {
		double bulletSpeed = minStreamedSpeed + ((maxStreamedSpeed - minStreamedSpeed) * Math.random());

		Vector2D targetVector = target != null ? target.getTarget() : new Vector2D(origin.getX(), origin.getY() + 1);
		Angle angle = new Angle(origin, targetVector);
		Vector2D force = angle.toVector(bulletSpeed);

		MoveController moveController = new MoveController(force);
		AccelerationMoveController controller = new AccelerationMoveController(moveController);
		controller.setFinalForce(new Vector2D(0.0, 0.0));
		controller.setDecelerationFactor(40);
		controller.setDuration(streamedFrames);
		return controller;
	}

	private Controller createRandomController() {
		double bulletSpeed = minRedirectedSpeed + ((maxRedirectedSpeed - minRedirectedSpeed) * Math.random());
		Angle randomAngle = new Angle(Math.random() * Math.PI * 2);
		return moveControllerFactory.createMoveController(randomAngle.toVector(bulletSpeed));
	}
}
