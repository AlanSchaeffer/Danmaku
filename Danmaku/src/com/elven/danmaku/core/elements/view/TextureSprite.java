package com.elven.danmaku.core.elements.view;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;

import org.lwjgl.opengl.GL11;

import com.elven.danmaku.core.graphics.texture.Texture;

/**
 * Texture sprites will always draw in the middle
 * */
public class TextureSprite implements Sprite {

	private final Texture texture;
	private final Dimension size;

	public TextureSprite(Texture texture) {
		this.texture = texture;
		size = new Dimension(texture.getImageWidth(), texture.getImageHeight());
	}

	@Override
	public void render() {
		texture.bind();

		float halfWidth = size.width / 2;
		float halfHeight = size.height / 2;
		
		glBegin(GL11.GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex2f(-halfWidth, -halfHeight);

		glTexCoord2f(0, texture.getHeight());
		glVertex2f(-halfWidth, halfHeight);

		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(halfWidth, halfHeight);

		glTexCoord2f(texture.getWidth(), 0);
		glVertex2f(halfWidth, -halfHeight);

		glEnd();
	}

	@Override
	public Dimension getSize() {
		return size;
	}
}
