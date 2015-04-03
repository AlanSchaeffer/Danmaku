package com.elven.danmaku.core.system.collision;

import com.elven.danmaku.core.items.CollectableItem;
import com.elven.danmaku.core.listeners.CollisionType;
import com.elven.danmaku.core.stage.StageController;

public class ItemCollectionCollisionTask implements CollisionTask {

	private final StageController stage;
	private boolean active = true;

	public ItemCollectionCollisionTask(StageController stage) {
		this.stage = stage;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void run(CollisionEventPropagator propagator) {
		if(isActive()) {
			for(CollectableItem item : stage.getModel().getItems().getElements()) {
				if(stage.getPlayer().getModel().getItemCollectionHitbox().hitCheck(item.getHitbox())) {
					propagator.fireCollision(item, stage.getPlayer(), CollisionType.ITEM);
				}
			}
		}
	}
}
