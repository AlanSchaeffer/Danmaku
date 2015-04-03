package com.elven.danmaku.core.graphics;

import com.elven.danmaku.core.elements.Destroyable;

public interface GraphicsLayer extends GraphicalElement, Destroyable {
	
	public void addElement(GraphicalElement element);
	
	public void removeElement(GraphicalElement element);
}
