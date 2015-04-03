package com.elven.danmaku.sample.bullets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageBuilder;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class BallBulletSpriteFactory {

	public static Sprite create(Color color, TextureLoader loader) {
		Texture texture = loader.getTexture(new BallBulletTextureSource(color));
		return new TextureSprite(texture);
	}
	
	private static class BallBulletTextureSource extends TextureImageBuilder {

		private final Color color;

		public BallBulletTextureSource(Color color) {
			super("bullet.ball.0x" + Integer.toHexString(color.getRGB()), new Dimension(16, 16));
			this.color = color;
		}

		@Override
		protected void drawImage(Graphics2D g) {
			g.setColor(color);
			g.fillOval(0, 0, 16, 16);
			g.setColor(Color.WHITE);
			g.fillOval(3, 3, 10, 10);
		}
	}
}
