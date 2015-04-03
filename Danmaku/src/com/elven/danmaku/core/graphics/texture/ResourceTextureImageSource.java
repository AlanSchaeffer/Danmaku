package com.elven.danmaku.core.graphics.texture;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceTextureImageSource implements TextureImageSource {

	private final String resourceName;

	public ResourceTextureImageSource(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public BufferedImage createImage() {
		URL url = TextureLoader.class.getClassLoader().getResource(resourceName);

		if (url == null) {
			throw new RuntimeException("Cannot find: " + resourceName);
		}

		// due to an issue with ImageIO and mixed signed code
		// we are now using good oldfashioned ImageIcon to load
		// images and the paint it on top of a new BufferedImage
		Image img = new ImageIcon(url).getImage();
		BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return bufferedImage;
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}
}
