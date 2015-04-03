package com.elven.danmaku.sample.bullets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageBuilder;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class BubbleBulletSpriteFactory {

	public static Sprite create(Color color, TextureLoader loader) {
		Texture texture = loader.getTexture(new BubbleBulletTextureSource(color));
		return new TextureSprite(texture);
	}
	
	private static class BubbleBulletTextureSource extends TextureImageBuilder {

		private final Color color;
		
		public BubbleBulletTextureSource(Color color) {
			super("bullet.bubble.0x" + Integer.toHexString(color.getRGB()), new Dimension(51, 51));
			this.color = color;
		}
		
		@Override
		protected void drawImage(Graphics2D g) {
			List<Color> colors;
			colors = new ArrayList<>();
			colors.add(color);
			
			Color loopColor = color;
			for (int i = 0; i < 5; i++) {
				loopColor = loopColor.darker();
				colors.add(loopColor);
			}
			
			colors.add(Color.BLACK);
			
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, 50, 50);
			
			for (int i = 0; i < colors.size(); i++) {
				int displace = 2 * i;
				g.setColor(colors.get(i));
				g.fillOval(4 + displace, 4 + displace, 42 - 2 * displace, 42 - 2 * displace);
			}
		}
	}
}
