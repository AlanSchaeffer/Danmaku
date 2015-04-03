package com.elven.danmaku.core.elements.view;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;

public class ScaledSprite implements Sprite {

	private final Sprite sprite;
	private double scale = 1.0;
	private Dimension currentBoundingBox;

	public ScaledSprite(Sprite sprite) {
		this.sprite = sprite;
		currentBoundingBox = sprite.getSize();
	}

	public Sprite getSprite() {
		return sprite;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		recalculateSize();
	}

	private void recalculateSize() {
		currentBoundingBox = new Dimension((int) (sprite.getSize().width * scale), (int) (sprite.getSize().height * scale));
	}

	@Override
	public void render() {
		if (scale > 0.0 && scale != 1.0) {
			glPushMatrix();
			glScaled(scale, scale, 1);

			sprite.render();

			glPopMatrix();
		} else {
			sprite.render();
		}
	}

	@Override
	public Dimension getSize() {
		return currentBoundingBox;
	}
}