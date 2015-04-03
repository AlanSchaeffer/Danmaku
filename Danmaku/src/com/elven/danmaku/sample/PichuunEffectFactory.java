package com.elven.danmaku.sample;

import com.elven.danmaku.core.graphics.fx.EffectFactory;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;

public class PichuunEffectFactory implements EffectFactory {

	private StageController stage;
	private TextureLoader loader;

	public PichuunEffectFactory(StageController stage, TextureLoader loader) {
		this.stage = stage;
		this.loader = loader;
	}
	
	@Override
	public void createEffect(Vector2D position) {
		Pichuun pichuun = new Pichuun(stage, stage.getPlayer().getPosition(), 80, loader);
		pichuun.install();
	}
}
