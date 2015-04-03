package com.elven.danmaku.core.elements.view;

import java.awt.Dimension;

public class BlankSprite implements Sprite {

	@Override
	public void render() {
	}

	@Override
	public Dimension getSize() {
		return new Dimension(0, 0);
	}
}
