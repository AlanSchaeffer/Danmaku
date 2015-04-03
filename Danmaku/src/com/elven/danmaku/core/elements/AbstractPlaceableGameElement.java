package com.elven.danmaku.core.elements;

import com.elven.danmaku.core.elements.disposal.DisposalRule;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.graphics.GraphicalElement;

public interface AbstractPlaceableGameElement extends GraphicalElement, UpdateableGameElement, Placeable, Destroyable {

	public Sprite getSprite();
	
	public DisposalRule getDisposalRule();
}
