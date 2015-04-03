package com.elven.danmaku.core.graphics.texture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This is for text that might change after created, so a fixed size is used
 * */
public class DynamicTextTextureSource implements TextureImageSource {

	private final String resourceName;
	private final Font font;
	private final Color color;
	private final Dimension size;
	private final BufferedImage image;
	private String text;

	public DynamicTextTextureSource(String resourceName, Dimension size) {
		this(resourceName, size, null);
	}

	public DynamicTextTextureSource(String resourceName, Dimension size, Font font) {
		this(resourceName, size, font, null);
	}

	public DynamicTextTextureSource(String resourceName, Dimension size, Font font, Color color) {
		this.resourceName = resourceName;
		this.size = size;
		this.font = font != null ? font : Font.getFont(Font.DIALOG);
		this.color = color != null ? color : Color.BLACK;

		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void setText(String text) {
		this.text = text;
		updateImage();
	}

	@Override
	public BufferedImage createImage() {
		return image;
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}
	
	private void updateImage() {
		Graphics2D g = image.createGraphics();

		g.setComposite(AlphaComposite.Clear);
		g.fillRect(0, 0, size.width, size.height);
		g.setComposite(AlphaComposite.SrcOver);

		g.setColor(color);
		g.setFont(font);
		g.drawString(text, 0, (int) size.height - g.getFontMetrics().getDescent());
		g.dispose();
	}
}
