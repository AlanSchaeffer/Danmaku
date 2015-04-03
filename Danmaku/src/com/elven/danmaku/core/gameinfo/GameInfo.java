package com.elven.danmaku.core.gameinfo;

import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.listeners.EnemyDestroyedListener;

public class GameInfo {

	private final GameInfoModel<Long> scoreModel = new LongGameInfoModel(0L);
	private final GameInfoModel<Integer> grazeModel = new IntegerGameInfoModel(0);
	private final GameInfoModel<Integer> powerModel = new IntegerGameInfoModel(0);
	private final GameInfoModel<Integer> lifeModel = new IntegerGameInfoModel(3);
	private final GameInfoModel<Integer> bombModel = new IntegerGameInfoModel(2);
	private final EnemyDestroyedListener scoreListener = new ScoreListener();

	public GameInfoModel<Long> getScoreModel() {
		return scoreModel;
	}

	public GameInfoModel<Integer> getGrazeModel() {
		return grazeModel;
	}

	public GameInfoModel<Integer> getPowerModel() {
		return powerModel;
	}

	public GameInfoModel<Integer> getLifeModel() {
		return lifeModel;
	}

	public GameInfoModel<Integer> getBombModel() {
		return bombModel;
	}

	public EnemyDestroyedListener getScoreListener() {
		return scoreListener;
	}

	private class ScoreListener implements EnemyDestroyedListener {

		@Override
		public void enemyDestroyed(AbstractHostileEntity enemy) {
			scoreModel.increase(enemy.getScoreWorth());
		}
	}
}
