package com.elven.danmaku.sample.player;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.hitbox.HitboxFactory;
import com.elven.danmaku.core.elements.view.RotatedSprite;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.player.PlayerBullet;
import com.elven.danmaku.core.player.PlayerShotFactory;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.bullets.SealBulletSpriteFactory;

public class SpreadSealsPlayerShot implements PlayerShotFactory {

	private final Sprite sprite;
	private final RotatedSprite leftSprite;
	private final RotatedSprite rightSprite;
	private final Controller upController = new MoveController(new Vector2D(0.0, -8.0));
	private final Controller leftController = new MoveController(new Vector2D(-1.0, -8.0));
	private final Controller rightController = new MoveController(new Vector2D(1.0, -8.0));
	private HitboxFactory hitboxFactory;
	private int power = 100;
	private int wait = 0;

	private int currentFrame = 0;
	
	public SpreadSealsPlayerShot(TextureLoader loader) {
		sprite = SealBulletSpriteFactory.create(loader);
		
		leftSprite = new RotatedSprite(sprite);
		leftSprite.setRotation(-7.125);
		
		rightSprite = new RotatedSprite(sprite);
		rightSprite.setRotation(7.125);
	}

	public void setHitboxFactory(HitboxFactory hitboxFactory) {
		this.hitboxFactory = hitboxFactory;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setWait(int wait) {
		this.wait = wait;
		currentFrame = wait;
	}

	@Override
	public void shootBurst(Player player, StageController stage) {
		if (currentFrame >= wait) {
			Vector2D displacedVector = new Vector2D(player.getPosition().getX(), player.getPosition().getY() - 20);
			
			PlayerBullet bullet = new PlayerBullet(sprite, upController, new Vector2D(displacedVector));
			bullet.setHitbox(hitboxFactory.createHitboxFor(bullet));
			bullet.setPower(power);
			stage.playerBullets().spawn(bullet);

			PlayerBullet leftBullet = new PlayerBullet(leftSprite, leftController, new Vector2D(displacedVector));
			leftBullet.setHitbox(hitboxFactory.createHitboxFor(leftBullet));
			leftBullet.setPower(power);
			stage.playerBullets().spawn(leftBullet);

			PlayerBullet rightBullet = new PlayerBullet(rightSprite, rightController, new Vector2D(displacedVector));
			rightBullet.setHitbox(hitboxFactory.createHitboxFor(rightBullet));
			rightBullet.setPower(power);
			stage.playerBullets().spawn(rightBullet);
			currentFrame = 0;
		} else {
			currentFrame++;
		}
	}

	@Override
	public void stopBurst() {
		currentFrame = wait;
	}
}
