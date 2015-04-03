package com.elven.danmaku.sample.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageSource;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class DotBulletSpriteFactory {

	public static Sprite create(TextureLoader loader) {
		Texture texture = loader.getTexture(new DotBulletTextureSource());
		return new TextureSprite(texture);
	}
	
	private static class DotBulletTextureSource implements TextureImageSource {

		@Override
		public BufferedImage createImage() {
			BufferedImage image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 4, 4);
			g.dispose();
			return image;
		}
		
		@Override
		public String getResourceName() {
			return "bullets.dot";
		}
	}
}
