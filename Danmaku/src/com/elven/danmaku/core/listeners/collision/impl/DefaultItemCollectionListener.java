package com.elven.danmaku.core.listeners.collision.impl;

import com.elven.danmaku.core.items.CollectableItem;
import com.elven.danmaku.core.listeners.collision.ItemCollectionListener;
import com.elven.danmaku.core.stage.StageController;

public class DefaultItemCollectionListener implements ItemCollectionListener {

	private final StageController stage;

	public DefaultItemCollectionListener(StageController stage) {
		this.stage = stage;
	}
	
	@Override
	public void itemCollected(CollectableItem item) {
		item.collect(stage.getPlayer());
		stage.items().despawn(item);
	}
}
