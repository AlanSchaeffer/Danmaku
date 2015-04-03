package com.elven.danmaku.core.configuration;

import com.elven.danmaku.core.system.GameContext;

public abstract class AbstractStageConfiguration implements StageConfiguration {

	private GameContext context;

	@Override
	public final void setGameContext(GameContext context) {
		this.context = context;
	}

	protected final GameContext gameContext() {
		return context;
	}
}
