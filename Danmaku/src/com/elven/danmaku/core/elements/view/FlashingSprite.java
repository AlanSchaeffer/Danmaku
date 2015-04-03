package com.elven.danmaku.core.elements.view;

import java.awt.Dimension;

public class FlashingSprite implements Sprite {

	private final Sprite sprite;
	private int frequency = 10;
	
	private int frame = 0;

	public FlashingSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public void render() {
		if(frame < frequency) {
			sprite.render();
		}
		
		frame = (frame + 1) % (frequency * 2);
	}

	@Override
	public Dimension getSize() {
		return sprite.getSize();
	}
}
