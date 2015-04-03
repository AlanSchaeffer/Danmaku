package com.elven.danmaku.core.items;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.hitbox.Hitbox;
import com.elven.danmaku.core.player.Player;

public interface CollectableItem extends AbstractPlaceableGameElement {

	public void collect(Player player);
	
	public Hitbox getHitbox();
}
