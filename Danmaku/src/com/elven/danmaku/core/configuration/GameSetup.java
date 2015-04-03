package com.elven.danmaku.core.configuration;

import org.lwjgl.LWJGLException;

import com.elven.danmaku.core.configuration.script.StageScript;
import com.elven.danmaku.core.frame.GameView;
import com.elven.danmaku.core.listeners.collision.CollisionListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Game;
import com.elven.danmaku.core.system.GameContext;

public class GameSetup {

	private Game game;
	private GameConfiguration gameConfiguration;
	private CollisionListenerFactory collisionListenerFactory;

	private StageController currentStage;

	public GameSetup(Configuration<Game> engineConfiguration) {
		engineConfiguration.configure();
		game = engineConfiguration.getConfiguredObject();
		
		try {
			game.createDisplay();
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}
	}

	public void setGameConfiguration(GameConfiguration gameConfiguration) {
		this.gameConfiguration = gameConfiguration;
	}

	public void setCollisionListenerFactory(CollisionListenerFactory collisionListenerFactory) {
		this.collisionListenerFactory = collisionListenerFactory;
	}
	
	public GameContext gameContext() {
		return game.context();
	}
	
	public void start() {
		game.start();
	}
	
	public void changeView(GameView view) {
		game.changeView(view);
	}
	
	public void loadStage(StageConfiguration stageConfiguration, StageScript script) {
		if (currentStage != null) {
			game.unregisterTickListener(currentStage);
		}
		
		stageConfiguration.setGameContext(gameContext());
		stageConfiguration.configure(gameConfiguration, script);
		
		currentStage = stageConfiguration.getStage();
		game.registerTickListener(currentStage);
		currentStage.registerPlayer(gameConfiguration.getPlayer());

		CollisionListener listener = collisionListenerFactory.createCollisionListener(currentStage, gameConfiguration.getGameInfo());
		currentStage.getCollisionDetector().addCollisionListener(listener);

		script.setGameContext(gameContext());
		script.install(currentStage, gameConfiguration);
		
		changeView(currentStage.getView());
		game.setCurrentInputReceiver(gameConfiguration.getPlayer().getController().getInputManager().getReceiver());
	}
}
