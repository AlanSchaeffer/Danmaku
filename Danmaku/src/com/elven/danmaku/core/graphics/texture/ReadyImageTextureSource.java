package com.elven.danmaku.core.graphics.texture;

import java.awt.image.BufferedImage;

public class ReadyImageTextureSource implements TextureImageSource {

	private final String resourceName;
	private final BufferedImage image;

	public ReadyImageTextureSource(String resourceName, BufferedImage image) {
		this.resourceName = resourceName;
		this.image = image;
	}

	@Override
	public BufferedImage createImage() {
		return image;
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}

}
