package com.elven.danmaku.core.listeners;

import java.util.EventListener;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;

public interface ElementRemovedListener extends EventListener {

	public void elementRemoved(AbstractPlaceableGameElement element);
}
