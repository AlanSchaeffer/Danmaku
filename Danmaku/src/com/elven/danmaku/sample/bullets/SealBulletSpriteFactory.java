package com.elven.danmaku.sample.bullets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageBuilder;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class SealBulletSpriteFactory {

	public static Sprite create(TextureLoader loader) {
		Texture texture = loader.getTexture(new SealBulletTextureSource());
		return new TextureSprite(texture);
	}

	private static class SealBulletTextureSource extends TextureImageBuilder {

		private final Color borderColor = new Color(0x880000);
		private final Color innerColor = new Color(0xFF2222);
		private final Color inscriptColor = new Color(0xFFDDDD);

		public SealBulletTextureSource() {
			super("bullet.seal", new Dimension(8, 10), false);
		}
		
		@Override
		protected void drawImage(Graphics2D g) {
			g.setColor(borderColor);
			g.drawRect(0, 0, 8, 10);
			g.setColor(innerColor);
			g.fillRect(1, 1, 7, 9);
			g.setColor(inscriptColor);
			g.drawLine(2, 5, 6, 5);
			g.drawLine(4, 2, 4, 8);
		}
	}
}
