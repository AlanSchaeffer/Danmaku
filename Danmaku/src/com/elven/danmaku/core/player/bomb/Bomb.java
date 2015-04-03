package com.elven.danmaku.core.player.bomb;

import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.stage.StageController;

public interface Bomb {

	public boolean canBomb();
	
	public void bomb(Player player, StageController stage);

	public void tick();
}
