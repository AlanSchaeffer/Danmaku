package com.elven.danmaku.core.configuration.script;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.configuration.GameConfiguration;
import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.GameContext;
import com.elven.danmaku.core.system.GameContextAware;

public abstract class StageScript implements UpdateableGameElement, GameContextAware {

	private final Queue<ScriptAction> actions = new ConcurrentLinkedQueue<>();
	private GameConfiguration gameConfiguration;
	private StageController stage;
	
	private final StageScriptEnemyHandler enemyHandler = new StageScriptEnemyHandler(this);
	private final StageScriptBulletHandler bulletHandler = new StageScriptBulletHandler(this);
	private GameContext context;

	public final void install(StageController stage, GameConfiguration gameConfiguration) {
		if (this.stage != null) {
			stage.unregisterElement(this);
		}

		this.gameConfiguration = gameConfiguration;
		this.stage = stage;

		configure();
		stage.registerElement(this);
	}
	
	public StageController getStage() {
		return stage;
	}
	
	public GameContext gameContext() {
		return context;
	}
	
	public Player player() {
		return gameConfiguration.getPlayer();
	}
	
	public GameInfo gameInfo() {
		return gameConfiguration.getGameInfo();
	}
	
	@Override
	public final void tick() {
		if (!actions.isEmpty()) {
			Iterator<ScriptAction> iterator = actions.iterator();

			while (iterator.hasNext()) {
				ScriptAction action = iterator.next();
				action.tick();

				if (action.shouldRemove()) {
					iterator.remove();
				}

				if (!action.shouldResume()) {
					return;
				}
			}
		}
	}

	protected abstract void configure();

	public void wait(int frames) {
		addAction(new WaitAction(frames));
	}

	public StageScriptEnemyHandler enemy() {
		return enemyHandler;
	}

	public StageScriptBulletHandler bullet() {
		return bulletHandler;
	}
	
	public final void addAction(ScriptAction action) {
		actions.offer(action);
	}

	public final void callAction(ScriptAction action) {
		action.tick();
	}
	
	@Override
	public void setGameContext(GameContext context) {
		this.context = context;
	}
}
