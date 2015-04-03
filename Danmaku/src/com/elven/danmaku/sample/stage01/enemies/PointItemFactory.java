package com.elven.danmaku.sample.stage01.enemies;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.elements.controller.UniformAccelerationMoveController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.TextureImageBuilder;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.sample.item.PointItem;

public class PointItemFactory {

	private final StageController stage;
	private final long itemValue;
	private final GameInfo gameInfo;

	private final Sprite sprite;

	public PointItemFactory(StageController stage, GameInfo gameInfo, long itemValue, TextureLoader loader) {
		this.stage = stage;
		this.gameInfo = gameInfo;
		this.itemValue = itemValue;

		sprite = new TextureSprite(loader.getTexture(new PointItemTextureSource()));
	}

	public void createItems(Vector2D position, int amountOfItems) {
		double degreesPerItem = 210.0 / (amountOfItems - 1);

		for (int i = 0; i < amountOfItems; i++) {
			Angle angle = new Angle(Math.toRadians(degreesPerItem * i + 165));
			PointItem item = new PointItem(createItemController(angle.toVector(3.0)), sprite, gameInfo, itemValue, new Vector2D(position));
			item.setHitbox(new CircleHitbox(item, 30.0));
			stage.items().spawn(item);
		}
	}

	private Controller createItemController(Vector2D initialForce) {
		UniformAccelerationMoveController controller = new UniformAccelerationMoveController(new MoveController(initialForce));
		controller.setFinalForce(new Vector2D(0.0, 4.0));
		controller.setDuration(30);
		return controller;
	}

	private static class PointItemTextureSource extends TextureImageBuilder {

		public PointItemTextureSource() {
			super("point.item", new Dimension(15, 15), false);
		}

		@Override
		protected void drawImage(Graphics2D g) {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 15, 15);
			g.setColor(Color.BLUE.brighter());
			g.fillRect(1, 1, 13, 13);
			g.setColor(Color.WHITE);
			g.fillRect(2, 2, 11, 11);
		}
	}
}
