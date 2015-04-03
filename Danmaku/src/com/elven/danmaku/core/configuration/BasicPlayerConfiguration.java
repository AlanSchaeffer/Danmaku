package com.elven.danmaku.core.configuration;

import com.elven.danmaku.core.player.DefaultPlayer;
import com.elven.danmaku.core.player.DefaultPlayerModel;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.player.PlayerModel;
import com.elven.danmaku.core.player.controller.DefaultPlayerController;
import com.elven.danmaku.core.player.controller.PlayerController;

public class BasicPlayerConfiguration implements Configuration<Player> {

	private Player player;

	@Override
	public final void configure() {
		player = createPlayer();
	}

	@Override
	public final Player getConfiguredObject() {
		return player;
	}

	protected Player createPlayer() {
		DefaultPlayer player = new DefaultPlayer();
		player.setController(createController());
		player.setModel(createModel());
		return this.player;
	}

	protected PlayerController createController() {
		return new DefaultPlayerController();
	}

	private PlayerModel createModel() {
		return new DefaultPlayerModel();
	}
}
