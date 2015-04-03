package com.elven.danmaku.core.graphics.texture;

import java.awt.image.BufferedImage;

public interface TextureImageSource {

	public String getResourceName();
	
	public BufferedImage createImage();
}
