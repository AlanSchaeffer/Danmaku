package com.elven.danmaku.sample.stage01.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.path.PathController;
import com.elven.danmaku.core.elements.controller.path.SimpleWaypoint;
import com.elven.danmaku.core.elements.disposal.NeverDisposeRule;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.EnemyFactory;
import com.elven.danmaku.core.enemies.boss.Boss;
import com.elven.danmaku.core.enemies.boss.spellcard.BossAttack;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageSource;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.listeners.EnemyDestroyedListener;
import com.elven.danmaku.core.listeners.SpellcardDestroyedListener;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.core.util.BossLifeCounter;
import com.elven.danmaku.sample.stage01.enemies.spellcards.NonSpell01;
import com.elven.danmaku.sample.stage01.enemies.spellcards.Spellcard01;

public class Stage01BossFactory implements EnemyFactory {

	private final Boss boss;
	private BossLifeCounter lifeCounter;
	private final PointItemFactory pointItemFactory;

	public Stage01BossFactory(final StageController stage, GameInfo gameInfo, TextureLoader loader) {
		Texture texture = loader.getTexture(new BossTextureSource());
		Sprite bossSprite = new TextureSprite(texture);
		
		pointItemFactory = new PointItemFactory(stage, gameInfo, 7500L, loader);

		boss = new Boss(bossSprite, createController(), new Vector2D(200.0, -50.0));
		boss.setHitbox(new CircleHitbox(boss, 5.0));
		boss.setTargetHitbox(new CircleHitbox(boss, 40.0));
		boss.setDisposalRule(new NeverDisposeRule());
		boss.setScoreWorth(50000L);

		boss.addAttack(new NonSpell01(stage, boss, loader));
		boss.addAttack(new Spellcard01(stage, boss, loader));

		boss.addSpellcardDestroyedListener(new SpellcardDestroyedListener() {
			@Override
			public void spellcardDestroyed(BossAttack spellcard) {
				stage.bullets().clear();
				pointItemFactory.createItems(boss.getPosition(), 10);
			}
		});

		boss.addEnemyDestroyedListener(new EnemyDestroyedListener() {
			@Override
			public void enemyDestroyed(AbstractHostileEntity enemy) {
				stage.getView().getGadgetsLayer().removeElement(lifeCounter);
				
				stage.doLater(35, new Runnable() {
					@Override
					public void run() {
						pointItemFactory.createItems(boss.getPosition(), 15);
					}
				});
			}
		});

		lifeCounter = new BossLifeCounter(boss, stage);
	}

	@Override
	public Boss createEnemy() {
		return boss;
	}

	public BossLifeCounter createLifeCounter() {
		return lifeCounter;
	}

	private Controller createController() {
		PathController controller = new PathController();
		controller.addWaypoint(new SimpleWaypoint(new Vector2D(200.0, 50.0), 300));
		return controller;
	}

	private static class BossTextureSource implements TextureImageSource {

		@Override
		public BufferedImage createImage() {
			BufferedImage image = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);

			Graphics g = image.createGraphics();
			g.setColor(Color.BLACK);
			g.fillOval(0, 0, 30, 30);

			g.setColor(Color.YELLOW);
			g.fillOval(1, 1, 28, 28);

			g.setColor(Color.BLACK);
			g.drawLine(10, 13, 10, 17);
			g.drawLine(20, 13, 20, 17);
			g.drawArc(8, 20, 14, 8, 0, 180);

			return image;
		}

		@Override
		public String getResourceName() {
			return "stage01.boss";
		}
	}
}
