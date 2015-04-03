package com.elven.danmaku.core.elements.hitbox;

import com.elven.danmaku.core.elements.Placeable;

public interface HitboxFactory {

	public Hitbox createHitboxFor(Placeable element);
}
