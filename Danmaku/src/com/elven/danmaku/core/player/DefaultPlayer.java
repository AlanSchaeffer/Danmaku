package com.elven.danmaku.core.player;

import static org.lwjgl.opengl.GL11.*;

import com.elven.danmaku.core.elements.disposal.DisposalRule;
import com.elven.danmaku.core.elements.disposal.NeverDisposeRule;
import com.elven.danmaku.core.elements.view.BlankSprite;
import com.elven.danmaku.core.elements.view.FlashingSprite;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.player.controller.DefaultPlayerController;
import com.elven.danmaku.core.player.controller.PlayerController;
import com.elven.danmaku.core.system.Vector2D;

public class DefaultPlayer implements Player {

	private PlayerModel model;
	private PlayerController controller;
	private final DisposalRule disposalRule = new NeverDisposeRule();
	
	private Sprite sprite;
	private Sprite flashingSprite;

	public DefaultPlayer() {
		sprite = new BlankSprite();
		flashingSprite = sprite;
		
		model = new DefaultPlayerModel();
		controller = new DefaultPlayerController();
	}

	@Override
	public PlayerModel getModel() {
		return model;
	}

	public void setModel(PlayerModel model) {
		this.model = model;
	}

	@Override
	public PlayerController getController() {
		return controller;
	}

	public void setController(PlayerController controller) {
		this.controller = controller;
	}

	@Override
	public Vector2D getPosition() {
		return model.getPosition();
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void render() {
		glPushMatrix();
		glTranslated(model.getPosition().getX(), model.getPosition().getY(), 0);
		
		if(isInvincible()) {
			flashingSprite.render();
		} else {
			sprite.render();
		}
		
		glPopMatrix();
	}

	@Override
	public void tick() {
		controller.tick(this);
	}

	@Override
	public DisposalRule getDisposalRule() {
		return disposalRule;
	}
	
	@Override
	public boolean isInvincible() {
		return controller.getInvincibilityController().isActive();
	}
	
	@Override
	public void destroy() {
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		flashingSprite = new FlashingSprite(sprite);
	}
}
