package com.elven.danmaku.core.system;

import com.elven.danmaku.core.frame.GameView;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.input.InputReceiver;
import com.elven.danmaku.core.stage.StageController;

public interface GameContext {

	public TextureLoader textureLoader();

	public StageController currentStage();

	public int width();

	public int height();

	public void setFps(int fps);

	public void setCurrentInputReceiver(InputReceiver receiver);

	public InputReceiver getCurrentInputReceiver();
	
	public void changeView(GameView view);
}
