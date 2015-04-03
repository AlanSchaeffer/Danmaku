package com.elven.danmaku.core.player;

import com.elven.danmaku.core.stage.StageController;

public interface PlayerShotFactory {

	public void shootBurst(Player player, StageController stage);

	public void stopBurst();
}
