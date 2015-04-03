package com.elven.danmaku.core.player;

import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.hitbox.Hitbox;
import com.elven.danmaku.core.player.bomb.Bomb;
import com.elven.danmaku.core.system.Vector2D;

public class DefaultPlayerModel implements PlayerModel {

	private double movementSpeed = 3.0;
	private double focusedSpeed = 1.5;
	private PlayerShotFactory shotFactory;
	private Bomb bomb;
	private Hitbox hitbox;
	private Hitbox grazeHitbox;
	private Hitbox itemCollectionHitbox; 
	
	private final Vector2D position;

	public DefaultPlayerModel() {
		position = new Vector2D();
		hitbox = new CircleHitbox(this, 2.0);
		grazeHitbox = new CircleHitbox(this, 15.0);
		itemCollectionHitbox = new CircleHitbox(this, 20.0);
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
	
	@Override
	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setGrazeHitbox(Hitbox grazeHitbox) {
		this.grazeHitbox = grazeHitbox;
	}
	
	@Override
	public Hitbox getGrazeHitbox() {
		return grazeHitbox;
	}
	
	public void setItemCollectionHitbox(Hitbox itemCollectionHitbox) {
		this.itemCollectionHitbox = itemCollectionHitbox;
	}
	
	@Override
	public Hitbox getItemCollectionHitbox() {
		return itemCollectionHitbox;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public double getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(double movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	@Override
	public double getFocusedSpeed() {
		return focusedSpeed;
	}

	public void setFocusedSpeed(double focusedSpeed) {
		this.focusedSpeed = focusedSpeed;
	}

	@Override
	public PlayerShotFactory getShotFactory() {
		return shotFactory;
	}

	public void setShotFactory(PlayerShotFactory shotFactory) {
		this.shotFactory = shotFactory;
	}

	@Override
	public Bomb getBomb() {
		return bomb;
	}

	public void setBomb(Bomb bomb) {
		this.bomb = bomb;
	}
}
