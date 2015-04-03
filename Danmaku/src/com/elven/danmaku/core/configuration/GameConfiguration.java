package com.elven.danmaku.core.configuration;

import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.player.Player;

public class GameConfiguration {

	private final Player player;
	private final GameInfo gameInfo;

	public GameConfiguration(Player player, GameInfo gameInfo) {
		this.player = player;
		this.gameInfo = gameInfo;
	}
	
	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public Player getPlayer() {
		return player;
	}
}
