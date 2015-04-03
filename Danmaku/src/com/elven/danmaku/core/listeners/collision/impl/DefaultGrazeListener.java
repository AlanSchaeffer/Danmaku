package com.elven.danmaku.core.listeners.collision.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.fx.EffectFactory;
import com.elven.danmaku.core.graphics.fx.NullEffectFactory;
import com.elven.danmaku.core.listeners.ElementRemovedListener;
import com.elven.danmaku.core.listeners.collision.GrazeListener;
import com.elven.danmaku.core.stage.StageController;

public class DefaultGrazeListener implements GrazeListener {

	private final StageController stage;
	private final GameInfo gameInfo;
	private EffectFactory grazeEffectFactory = new NullEffectFactory();
	private Queue<AbstractPlaceableGameElement> grazedElements = new ConcurrentLinkedQueue<>();
	private long grazeValue = 10L;

	public DefaultGrazeListener(StageController stage, GameInfo gameInfo) {
		this.stage = stage;
		this.gameInfo = gameInfo;
		
		stage.addElementRemovedListener(new ElementRemovedListener() {
			@Override
			public void elementRemoved(AbstractPlaceableGameElement element) {
				grazedElements.remove(element);
			}
		});
	}
	
	public void setGrazeEffectFactory(EffectFactory grazeEffectFactory) {
		this.grazeEffectFactory = grazeEffectFactory;
	}
	
	public void setGrazeValue(long grazeValue) {
		this.grazeValue = grazeValue;
	}
	
	@Override
	public void elementGrazed(AbstractPlaceableGameElement element) {
		grazeEffectFactory.createEffect(stage.getPlayer().getPosition());

		if (!grazedElements.contains(element)) {
			gameInfo.getGrazeModel().increase(1);
			gameInfo.getScoreModel().increase(grazeValue);
			grazedElements.add(element);
		}
	}
}
