package com.elven.danmaku.sample.stage01;

import java.awt.Point;
import java.awt.Rectangle;

import com.elven.danmaku.core.configuration.AbstractStageConfiguration;
import com.elven.danmaku.core.configuration.GameConfiguration;
import com.elven.danmaku.core.configuration.script.StageScript;
import com.elven.danmaku.core.player.DefaultPlayerHitboxSprite;
import com.elven.danmaku.core.player.PlayerFocusedHitboxSpriteElement;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.util.FpsCounter;
import com.elven.danmaku.sample.SampleGUI;

public class Stage01Configuration extends AbstractStageConfiguration {

	private StageController stage;

	@Override
	public void configure(GameConfiguration configuration, StageScript script) {
		stage = new StageController();
		stage.getView().setPlayableArea(new Rectangle(15, 15, 335, 422));
		stage.getView().setElementCutoffArea(new Rectangle(0, 0, 345, 452));

		SampleGUI sampleGUI = new SampleGUI(new Rectangle(5, 5, 355, 442), new Point(460, 60), configuration.getGameInfo(), gameContext());
		stage.getView().getUiLayer().addElement(sampleGUI);

		FpsCounter fpsCounter = new FpsCounter(gameContext().textureLoader());
		fpsCounter.getPosition().set(150.0, 20.0);
		stage.registerElement(fpsCounter);
		stage.getView().getGadgetsLayer().addElement(fpsCounter);

		PlayerFocusedHitboxSpriteElement playerHitboxSprite = new PlayerFocusedHitboxSpriteElement(configuration.getPlayer(), new DefaultPlayerHitboxSprite(configuration.getPlayer(), gameContext()
				.textureLoader()));
		stage.getView().getUiLayer().addElement(playerHitboxSprite);
	}

	@Override
	public StageController getStage() {
		return stage;
	}
}
