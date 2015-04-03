package com.elven.danmaku.core.configuration;

import com.elven.danmaku.core.configuration.script.StageScript;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.GameContextAware;

public interface StageConfiguration extends GameContextAware {

	public void configure(GameConfiguration configuration, StageScript script);
	
	public StageController getStage();
}
