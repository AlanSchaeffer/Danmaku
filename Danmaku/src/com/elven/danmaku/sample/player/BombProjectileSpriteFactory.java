package com.elven.danmaku.sample.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageBuilder;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class BombProjectileSpriteFactory {

	public static Sprite createSprite(TextureLoader loader) {
		Texture texture = loader.getTexture(new BombProjectileTextureSource());
		return new TextureSprite(texture );
	}
	
	private static class BombProjectileTextureSource extends TextureImageBuilder {

		private final Polygon starShape;
		private final Polygon innerShape;
		private final Polygon innerShape2;
		
		public BombProjectileTextureSource() {
			super("bomb.star", new Dimension(32, 32));
			
			starShape = new Polygon();

			boolean inner = false;
			for (double a = 0.0; a < 2 * Math.PI; a += Math.PI / 15) {
				int radius = inner ? 13 : 16;
				starShape.addPoint((int) (Math.cos(a) * radius), (int) (Math.sin(a) * radius));
				inner = !inner;
			}
			
			innerShape = new Polygon();
			innerShape2 = new Polygon();
			
			for(int i = 0; i < starShape.npoints; i++) {
				innerShape.addPoint((int) (starShape.xpoints[i] * 0.8), (int) (starShape.ypoints[i] * 0.8));
				innerShape2.addPoint((int) (starShape.xpoints[i] * 0.6), (int) (starShape.ypoints[i] * 0.6));
			}
			
			starShape.translate(16, 16);
			innerShape.translate(16, 16);
			innerShape2.translate(16, 16);
		}
		
		@Override
		protected void drawImage(Graphics2D g) {
			g.setColor(Color.YELLOW.darker());
			g.fillPolygon(starShape);
			g.setColor(Color.YELLOW);
			g.fillPolygon(innerShape);
			g.setColor(new Color(0xFFFF66));
			g.fillPolygon(innerShape2);
		}
	}
}
