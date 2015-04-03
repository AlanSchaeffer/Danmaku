package com.elven.danmaku.core.configuration;

import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.listeners.collision.CollisionListener;
import com.elven.danmaku.core.stage.StageController;

public interface CollisionListenerFactory {

	public CollisionListener createCollisionListener(StageController stage, GameInfo gameInfo);
}
