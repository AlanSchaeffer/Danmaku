package com.elven.danmaku.sample.item;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.items.AbstractCollectableItem;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.system.Vector2D;

public class PointItem extends AbstractCollectableItem {

	private final GameInfo gameInfo;
	private final long value;

	public PointItem(Controller controller, Sprite sprite, GameInfo gameInfo, long value) {
		super(controller, sprite);
		this.gameInfo = gameInfo;
		this.value = value;
	}
	
	public PointItem(Controller controller, Sprite sprite, GameInfo gameInfo, long value, Vector2D position) {
		super(controller, sprite, position);
		this.gameInfo = gameInfo;
		this.value = value;
	}

	@Override
	public void collect(Player player) {
		gameInfo.getScoreModel().increase(value);
	}
}
