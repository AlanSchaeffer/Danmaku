package com.elven.danmaku.core.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;

import org.lwjgl.Sys;

import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.graphics.GraphicalElement;
import com.elven.danmaku.core.graphics.texture.DynamicTextTextureSource;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.system.Vector2D;

public class FpsCounter implements UpdateableGameElement, GraphicalElement {

	private static final DecimalFormat format = new DecimalFormat("#0");
	private long lastUpdate = -1L;
	private long previousUpdate = -1L;
	private Vector2D position = new Vector2D(200.0, 20.0);
	private final Color color = new Color(0xAAFFFFFF, true);
	private final TextureLoader loader;

	private DynamicTextTextureSource fpsCountTexture;

	public FpsCounter(TextureLoader loader) {
		this.loader = loader;
		fpsCountTexture = new DynamicTextTextureSource("fps.count", new Dimension(100, 25), new Font("Arial", Font.PLAIN, 12), color);
	}

	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void tick() {
		previousUpdate = lastUpdate;
		lastUpdate = (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	@Override
	public void render() {
		fpsCountTexture.setText(getFpsString());
		Texture texture = loader.updateTexture(fpsCountTexture);
		texture.bind();

		glPushMatrix();
		glTranslated(position.getX(), position.getY(), 0);

		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex2f(0, 0);

		glTexCoord2f(1, 0);
		glVertex2f(100, 0);

		glTexCoord2f(1, 1);
		glVertex2f(100, 25);

		glTexCoord2f(0, 1);
		glVertex2f(0, 25);

		glEnd();

		glPopMatrix();
	}

	private String getFpsString() {
		return format.format(getFps()) + " fps";
	}

	private long getFps() {
		if (previousUpdate == lastUpdate) {
			return 0L;
		} else if (previousUpdate == -1L) {
			return 60L;
		} else {
			return 1000L / (lastUpdate - previousUpdate);
		}
	}
}
