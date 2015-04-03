package com.elven.danmaku.core.player;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.player.controller.PlayerController;

public interface Player extends AbstractPlaceableGameElement {

	public PlayerModel getModel();

	public PlayerController getController();
	
	public Sprite getSprite();

	public boolean isInvincible();
}
