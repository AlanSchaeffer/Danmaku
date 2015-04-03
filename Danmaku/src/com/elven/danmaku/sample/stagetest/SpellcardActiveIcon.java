package com.elven.danmaku.sample.stagetest;

import static org.lwjgl.opengl.GL11.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.elven.danmaku.core.graphics.GraphicalElement;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageSource;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.system.Vector2D;

public class SpellcardActiveIcon implements GraphicalElement {

	private final Spellcard spellcard;
	private final Vector2D position;

	private SpellcardActiveIconTextureSource textureSource;
	private TextureLoader loader;
	private Texture texture;

	public SpellcardActiveIcon(Spellcard spellcard, int number, TextureLoader loader) {
		this(spellcard, number, new Vector2D(), loader);
	}

	public SpellcardActiveIcon(Spellcard spellcard, int number, Vector2D position, TextureLoader loader) {
		this.spellcard = spellcard;
		this.position = position;
		this.loader = loader;
		textureSource = new SpellcardActiveIconTextureSource(number);
		textureSource.setActive(spellcard.isActive());
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setColor(Color color) {
		textureSource.setColor(color);
	}

	@Override
	public void render() {
		if (texture == null || spellcard.isActive() != textureSource.isActive()) {
			textureSource.setActive(spellcard.isActive());
			texture = loader.updateTexture(textureSource);
		}

		texture.bind();
		
		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glVertex2d(position.getX(), position.getY());

		glTexCoord2f(0, texture.getHeight());
		glVertex2d(position.getX(), position.getY() + texture.getImageHeight());

		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2d(position.getX() + texture.getImageWidth(), position.getY() + texture.getImageHeight());

		glTexCoord2f(texture.getWidth(), 0);
		glVertex2d(position.getX() + texture.getImageWidth(), position.getY());

		glEnd();
	}

	private static class SpellcardActiveIconTextureSource implements TextureImageSource {

		private final BufferedImage image;
		private final int number;
		private Color color = Color.WHITE;
		private boolean active;

		public SpellcardActiveIconTextureSource(int number) {
			this.number = number;
			image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		}

		@Override
		public BufferedImage createImage() {
			Graphics2D g = image.createGraphics();

			g.setComposite(AlphaComposite.Clear);
			g.fillRect(0, 0, 10, 10);
			g.setComposite(AlphaComposite.SrcOver);

			g.setColor(active ? color : Color.GRAY);
			g.drawRect(0, 0, 9, 9);
			g.setFont(g.getFont().deriveFont(10.0f));
			g.drawString(String.valueOf(number), 2, 9);

			g.dispose();

			return image;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public boolean isActive() {
			return active;
		}

		@Override
		public String getResourceName() {
			return "spellcard.icon.n" + number;
		}
	}
}
