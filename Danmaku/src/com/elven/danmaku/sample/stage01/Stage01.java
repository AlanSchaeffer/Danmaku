package com.elven.danmaku.sample.stage01;

import org.lwjgl.input.Keyboard;

import com.elven.danmaku.core.configuration.DefaultGameEngineConfiguration;
import com.elven.danmaku.core.configuration.GameConfiguration;
import com.elven.danmaku.core.configuration.GameSetup;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.input.InputReceiver;
import com.elven.danmaku.core.listeners.GameInfoChangeListener;
import com.elven.danmaku.sample.player.SamplePlayerFactory;

public class Stage01 {

	public static void main(String[] args) {
		final GameInfo gameInfo = new GameInfo();
		
		final GameSetup setup = new GameSetup(new DefaultGameEngineConfiguration());
		setup.setCollisionListenerFactory(new DefaultCollisionListenerFactory(setup.gameContext().textureLoader()));
		
		SamplePlayerFactory playerFactory = new SamplePlayerFactory(setup.gameContext().textureLoader());
		final GameConfiguration gameConfiguration = new GameConfiguration(playerFactory.createPlayer(gameInfo), gameInfo);
		setup.setGameConfiguration(gameConfiguration);

		showMenu(setup);

		gameConfiguration.getPlayer().getPosition().set(175.0, 420.0);
		
		gameInfo.getLifeModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				if (newValue.equals(-1)) {
					gameInfo.getLifeModel().setValue(3);
					gameInfo.getBombModel().setValue(2);
					gameInfo.getGrazeModel().setValue(0);
					gameInfo.getScoreModel().setValue(0L);
					
					showMenu(setup);
					
					gameConfiguration.getPlayer().getController().getInvincibilityController().reset();
					gameConfiguration.getPlayer().getController().getInputDisabledController().reset();
					gameConfiguration.getPlayer().getController().getInputManager().reset();
					gameConfiguration.getPlayer().getPosition().set(175.0, 420.0);
				}
			}
		});
		
		setup.start();
	}
	
	private static void showMenu(final GameSetup setup) {
		Stage01Menu menu = new Stage01Menu(setup.gameContext().textureLoader());
		setup.gameContext().changeView(menu);
		setup.gameContext().setCurrentInputReceiver(new InputReceiver() {
			@Override
			public void pressed(int keyCode) {
				if(keyCode == Keyboard.KEY_Z) {
					loadStage01(setup);
				}
			}
			
			@Override
			public void released(int keyCode) {
			}
			
			@Override
			public void clear() {
			}
		});
	}
	
	private static void loadStage01(GameSetup setup) {
		setup.loadStage(new Stage01Configuration(), new Stage01Script());
	}
}
