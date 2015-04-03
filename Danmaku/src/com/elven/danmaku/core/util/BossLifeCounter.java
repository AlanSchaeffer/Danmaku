package com.elven.danmaku.core.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;

import com.elven.danmaku.core.enemies.boss.Boss;
import com.elven.danmaku.core.graphics.GraphicalElement;
import com.elven.danmaku.core.stage.StageController;

public class BossLifeCounter implements GraphicalElement {

	private final Boss boss;
	private final StageController stage;

	public BossLifeCounter(Boss boss, StageController stage) {
		this.boss = boss;
		this.stage = stage;
	}

	@Override
	public void render() {
		Rectangle playableArea = stage.getView().getPlayableArea();
		Rectangle lifeBar = new Rectangle(playableArea.x + 3, playableArea.y + 9, playableArea.width - playableArea.x - 3, 5);

		glDisable(GL_TEXTURE_2D);

		glBegin(GL_QUADS);
		glColor3f(.75f, 0, 0);

		glVertex2i(lifeBar.x, lifeBar.y);
		glVertex2i(lifeBar.x + lifeBar.width, lifeBar.y);
		glVertex2i(lifeBar.x + lifeBar.width, lifeBar.y + 1);
		glVertex2i(lifeBar.x, lifeBar.y + 1);

		glVertex2i(lifeBar.x, lifeBar.y);
		glVertex2i(lifeBar.x + 1, lifeBar.y);
		glVertex2i(lifeBar.x + 1, lifeBar.y + lifeBar.height);
		glVertex2i(lifeBar.x, lifeBar.y + lifeBar.height);

		glVertex2i(lifeBar.x + lifeBar.width, lifeBar.y);
		glVertex2i(lifeBar.x + lifeBar.width - 1, lifeBar.y);
		glVertex2i(lifeBar.x + lifeBar.width - 1, lifeBar.y + lifeBar.height);
		glVertex2i(lifeBar.x + lifeBar.width, lifeBar.y + lifeBar.height);

		glVertex2i(lifeBar.x, lifeBar.y + lifeBar.height);
		glVertex2i(lifeBar.x + lifeBar.width, lifeBar.y + lifeBar.height);
		glVertex2i(lifeBar.x + lifeBar.width, lifeBar.y + lifeBar.height - 1);
		glVertex2i(lifeBar.x, lifeBar.y + lifeBar.height - 1);

		double percentHpLeft = (double) boss.getCurrentHitPoints() / (double) boss.getMaxHitPoints();
		int lifeBarWidth = (int) ((lifeBar.width - 2) * percentHpLeft);
		
		glColor3f(1, 0, 0);
		glVertex2i(lifeBar.x + 1, lifeBar.y + 1);
		glVertex2i(lifeBar.x + 1 + lifeBarWidth, lifeBar.y + 1);
		glVertex2i(lifeBar.x + 1 + lifeBarWidth, lifeBar.y + lifeBar.height - 1);
		glVertex2i(lifeBar.x + 1, lifeBar.y + lifeBar.height - 1);

		glEnd();

		glEnable(GL_TEXTURE_2D);
		glColor3f(1, 1, 1);
	}
}
