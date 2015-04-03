package com.elven.danmaku.core.player;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.hitbox.HitboxFactory;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;

public class DefaultPlayerShotFactory implements PlayerShotFactory {

	private Sprite sprite;
	private Controller controller;
	private HitboxFactory hitboxFactory;
	private int power = 100;
	private int wait = 0;
	
	private int currentFrame = 0;

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void setController(Controller controller) {
		this.controller = controller;
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
		if(currentFrame >= wait) {
			PlayerBullet bullet = new PlayerBullet(sprite, controller, new Vector2D(player.getPosition()));
			bullet.setHitbox(hitboxFactory.createHitboxFor(bullet));
			bullet.setPower(power);
			stage.playerBullets().spawn(bullet);
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
