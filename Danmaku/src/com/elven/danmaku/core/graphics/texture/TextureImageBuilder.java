package com.elven.danmaku.core.graphics.texture;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class TextureImageBuilder implements TextureImageSource {

	private final String resourceName;
	private final Dimension size;
	private final boolean hasAlpha;

	public TextureImageBuilder(String resourceName, Dimension size) {
		this(resourceName, size, true);
	}
	
	public TextureImageBuilder(String resourceName, Dimension size, boolean hasAlpha) {
		this.resourceName = resourceName;
		this.size = size;
		this.hasAlpha = hasAlpha;
	}

	@Override
	public final BufferedImage createImage() {
		BufferedImage image = new BufferedImage(size.width, size.height, hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = image.createGraphics();
		drawImage(g);
		g.dispose();
		
		return image;
	}

	@Override
	public final String getResourceName() {
		return resourceName;
	}
	
	protected abstract void drawImage(Graphics2D g);
}
