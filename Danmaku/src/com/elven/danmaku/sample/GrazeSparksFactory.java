package com.elven.danmaku.sample;

import com.elven.danmaku.core.graphics.fx.EffectFactory;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;

public class GrazeSparksFactory implements EffectFactory {

	private final GrazeSparks sparks;

	public GrazeSparksFactory(StageController stage, TextureLoader loader) {
		sparks = new GrazeSparks(stage, 30, loader);
		sparks.install();
	}
	
	@Override
	public void createEffect(Vector2D position) {
		sparks.createSparks(position);
	}
}
