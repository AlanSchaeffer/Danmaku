package com.elven.danmaku.core.graphics.texture;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TextTextureSource implements TextureImageSource {

	private final String text;
	private final String resourceName;
	private final Font font;
	private final Color color;

	public TextTextureSource(String text, String resourceName) {
		this(text, resourceName, null);
	}

	public TextTextureSource(String text, String resourceName, Font font) {
		this(text, resourceName, font, null);
	}
	
	public TextTextureSource(String text, String resourceName, Font font, Color color) {
		this.text = text;
		this.resourceName = resourceName;
		this.font = font != null ? font : Font.getFont(Font.DIALOG);
		this.color = color != null ? color : Color.BLACK;
	}

	@Override
	public BufferedImage createImage() {
		Rectangle2D bounds = getTextBounds();

		BufferedImage actualImage = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = actualImage.createGraphics();
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, 0, (int)bounds.getHeight() - g.getFontMetrics().getDescent());
		g.dispose();
		
		return actualImage;
	}
	
	private Rectangle2D getTextBounds() {
		BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics g = temp.createGraphics();
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle2D bounds = fontMetrics.getStringBounds(text, g);
		g.dispose();
		
		return bounds;
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}
}
