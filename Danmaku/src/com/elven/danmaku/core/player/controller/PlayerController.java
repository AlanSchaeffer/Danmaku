package com.elven.danmaku.core.player.controller;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.stage.StageController;

public interface PlayerController extends Controller {

	public BasicPlayerInput getInputManager();

	public void setCurrentStage(StageController stage);

	public CountdownController getInputDisabledController();

	public CountdownController getInvincibilityController();
}
