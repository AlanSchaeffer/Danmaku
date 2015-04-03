package com.elven.danmaku.core.configuration;

import com.elven.danmaku.core.gameinfo.GameInfo;

public class BasicGameInfoConfiguration implements Configuration<GameInfo> {

	private GameInfo gameInfo;

	@Override
	public final void configure() {
		gameInfo = createGameInfo();
	}

	@Override
	public final GameInfo getConfiguredObject() {
		return gameInfo;
	}
	
	protected GameInfo createGameInfo() {
		return new GameInfo();
	}
}
