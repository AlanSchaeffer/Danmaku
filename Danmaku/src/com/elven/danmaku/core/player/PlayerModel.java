package com.elven.danmaku.core.player;

import com.elven.danmaku.core.elements.Placeable;
import com.elven.danmaku.core.elements.hitbox.Hitbox;
import com.elven.danmaku.core.player.bomb.Bomb;

public interface PlayerModel extends Placeable {

	public Hitbox getHitbox();

	public Hitbox getGrazeHitbox();
	
	public Hitbox getItemCollectionHitbox();

	public double getMovementSpeed();

	public double getFocusedSpeed();
	
	public PlayerShotFactory getShotFactory();

	public Bomb getBomb();
}
