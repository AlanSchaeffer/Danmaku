package com.elven.danmaku.core.configuration.script;

import com.elven.danmaku.core.stage.StageController;

public class ClearBulletsAction implements ScriptAction {

	private StageController stage;

	public ClearBulletsAction(StageController stage) {
		this.stage = stage;
	}
	
	@Override
	public boolean shouldResume() {
		return true;
	}

	@Override
	public boolean shouldRemove() {
		return true;
	}

	@Override
	public void tick() {
		stage.bullets().clear();
	}
}
