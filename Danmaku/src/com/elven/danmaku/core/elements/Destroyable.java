package com.elven.danmaku.core.elements;

/**
 * This is for defining classes that need some special treatment once they are removed from the game
 * (such as cleaning GL buffers)
 * */
public interface Destroyable {

	public void destroy();
}
