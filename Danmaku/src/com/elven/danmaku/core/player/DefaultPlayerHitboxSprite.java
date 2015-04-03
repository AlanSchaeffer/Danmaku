package com.elven.danmaku.core.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.graphics.texture.TextureImageBuilder;
import com.elven.danmaku.core.graphics.texture.TextureLoader;

public class DefaultPlayerHitboxSprite implements Sprite {

	private final Player player;
	private TextureSprite image;

	public DefaultPlayerHitboxSprite(Player player, TextureLoader loader) {
		this.player = player;
		image = new TextureSprite(loader.getTexture(new HitboxImageSource(radius())));
	}

	@Override
	public void render() {
		image.render();
	}

	@Override
	public Dimension getSize() {
		int diameter = (int) (radius() * 2) + 4;
		return new Dimension(diameter, diameter);
	}

	private double radius() {
		CircleHitbox hitbox = (CircleHitbox) player.getModel().getHitbox();
		return hitbox.getRadius();
	}

	private static class HitboxImageSource extends TextureImageBuilder {

		private double radius;

		public HitboxImageSource(double radius) {
			super("player.hitbox", new Dimension((int) (radius * 2) + 6, (int) (radius * 2) + 6));
			this.radius = radius;
		}
		
		@Override
		protected void drawImage(Graphics2D g) {
			g.setColor(Color.RED);
			g.fillOval(0, 0, (int) radius * 2 + 4, (int) radius * 2 + 4);
			g.setColor(Color.WHITE);
			g.fillOval(2, 2, (int) radius * 2, (int) radius * 2);
		}
	}
}
