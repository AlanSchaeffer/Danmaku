package com.elven.danmaku.core.graphics;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.Destroyable;

public class BasicGraphicsLayer implements GraphicsLayer {

	private final Queue<GraphicalElement> elements = new ConcurrentLinkedQueue<>();
	
	@Override
	public void addElement(GraphicalElement element) {
		elements.add(element);
	}
	
	@Override
	public void removeElement(GraphicalElement element) {
		elements.remove(element);
	}
	
	@Override
	public void render() {
		for(GraphicalElement element : elements) {
			element.render();
		}
	}
	
	@Override
	public void destroy() {
		while(!elements.isEmpty()) {
			GraphicalElement element = elements.poll();
			
			if(element instanceof Destroyable) {
				((Destroyable) element).destroy();
			}
		}
	}
}
