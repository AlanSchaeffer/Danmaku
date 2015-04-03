package com.elven.danmaku.core.player;

import static org.lwjgl.opengl.GL11.*;

import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.GraphicalElement;

public class PlayerFocusedHitboxSpriteElement implements GraphicalElement {

	private final Player player;
	private final Sprite sprite;

	public PlayerFocusedHitboxSpriteElement(Player player, Sprite sprite) {
		this.player = player;
		this.sprite = sprite;
	}
	
	@Override
	public void render() {
		if(player.getController().getInputManager().isFocused()) {
			glPushMatrix();
			glTranslated(player.getPosition().getX(), player.getPosition().getY(), 0);
			
			sprite.render();
			
			glPopMatrix();
		}
	}
}
