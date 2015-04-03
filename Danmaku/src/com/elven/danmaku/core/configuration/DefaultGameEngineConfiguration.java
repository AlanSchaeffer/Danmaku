package com.elven.danmaku.core.configuration;

import java.awt.Dimension;

import com.elven.danmaku.core.system.Game;

public class DefaultGameEngineConfiguration implements Configuration<Game> {

	private Game game;

	@Override
	public void configure() {
		game = createGame();
	}

	@Override
	public Game getConfiguredObject() {
		return game;
	}
	
	protected Game createGame() {
		return new Game(new Dimension(640, 480), 60);
	}
}
