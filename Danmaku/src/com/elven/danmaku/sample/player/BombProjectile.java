package com.elven.danmaku.sample.player;

import static org.lwjgl.opengl.GL11.*;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.disposal.DisposalRule;
import com.elven.danmaku.core.elements.disposal.NeverDisposeRule;
import com.elven.danmaku.core.elements.view.RotatedSprite;
import com.elven.danmaku.core.elements.view.ScaledSprite;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.system.Vector2D;

public class BombProjectile implements AbstractPlaceableGameElement {

	private final Vector2D position;
	private final Controller controller;
	private RotatedSprite rotatedSprite;
	private ScaledSprite scaledSprite;

	public BombProjectile(Vector2D position, Controller controller, TextureLoader loader) {
		this.position = position;
		this.controller = controller;

		Sprite sprite = BombProjectileSpriteFactory.createSprite(loader);
		rotatedSprite = new RotatedSprite(sprite);
		scaledSprite = new ScaledSprite(rotatedSprite);
		scaledSprite.setScale(8.0);
	}

	@Override
	public Sprite getSprite() {
		return scaledSprite;
	}

	@Override
	public void render() {
		glPushMatrix();
		glTranslated(position.getX(), position.getY(), 0);

		scaledSprite.render();

		glPopMatrix();
	}

	@Override
	public void tick() {
		if (scaledSprite.getScale() > 1.0) {
			scaledSprite.setScale(Math.max(1.0, scaledSprite.getScale() * 0.98));
		}

		rotatedSprite.rotate(3);
		controller.tick(this);
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public DisposalRule getDisposalRule() {
		return new NeverDisposeRule();
	}

	@Override
	public void destroy() {
	}
}
