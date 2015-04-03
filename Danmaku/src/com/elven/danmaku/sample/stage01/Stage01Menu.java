package com.elven.danmaku.sample.stage01;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;

import com.elven.danmaku.core.frame.GameView;
import com.elven.danmaku.core.graphics.texture.TextTextureSource;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class Stage01Menu implements GameView {

	private final Texture textTexture;

	public Stage01Menu(TextureLoader loader) {
		textTexture = loader.getTexture(new TextTextureSource("Press Z to start", "pressztostart", Font.getFont("Arial"), Color.BLACK));
	}

	@Override
	public void render() {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);

		glColor3f(1, 1, 1);
		glVertex2i(0, 0);
		glVertex2i(0, 480);
		glVertex2i(640, 480);
		glVertex2i(640, 0);

		glEnd();
		glEnable(GL_TEXTURE_2D);

		textTexture.bind();
		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex2i(270, 10);
		
		glTexCoord2f(0, textTexture.getHeight());
		glVertex2i(270, 10 + textTexture.getImageHeight());
		
		glTexCoord2f(textTexture.getWidth(), textTexture.getHeight());
		glVertex2i(270 + textTexture.getImageWidth(), 10 + textTexture.getImageHeight());
		
		glTexCoord2f(textTexture.getWidth(), 0);
		glVertex2i(270 + textTexture.getImageWidth(), 10);

		glEnd();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
