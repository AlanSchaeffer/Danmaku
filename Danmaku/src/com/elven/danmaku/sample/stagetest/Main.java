package com.elven.danmaku.sample.stagetest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.elven.danmaku.core.configuration.CollisionListenerFactory;
import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.path.BezierWaypoint;
import com.elven.danmaku.core.elements.controller.path.PathController;
import com.elven.danmaku.core.elements.hitbox.CircleHitbox;
import com.elven.danmaku.core.elements.hitbox.CircleHitboxFactory;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.enemies.Enemy;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.ResourceTextureImageSource;
import com.elven.danmaku.core.input.InputReceiver;
import com.elven.danmaku.core.listeners.ElementRemovedListener;
import com.elven.danmaku.core.listeners.EnemyDestroyedListener;
import com.elven.danmaku.core.listeners.GameInfoChangeListener;
import com.elven.danmaku.core.player.DefaultPlayer;
import com.elven.danmaku.core.player.DefaultPlayerHitboxSprite;
import com.elven.danmaku.core.player.DefaultPlayerModel;
import com.elven.danmaku.core.player.PlayerFocusedHitboxSpriteElement;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Game;
import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.core.util.FpsCounter;
import com.elven.danmaku.sample.SampleGUI;
import com.elven.danmaku.sample.bullets.BallBulletSpriteFactory;
import com.elven.danmaku.sample.player.SampleBomb;
import com.elven.danmaku.sample.player.SpreadSealsPlayerShot;
import com.elven.danmaku.sample.stage01.DefaultCollisionListenerFactory;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Game game = new Game(new Dimension(800, 600), 60);
		game.createDisplay();

		final DefaultPlayer player = new DefaultPlayer();
		DefaultPlayerModel model = (DefaultPlayerModel) player.getModel();
		
		player.setSprite(new TextureSprite(game.context().textureLoader().getTexture(new ResourceTextureImageSource("resources/char.png"))));
		player.getPosition().setX(200.0);
		player.getPosition().setY(550.0);

		SpreadSealsPlayerShot shotFactory = new SpreadSealsPlayerShot(game.context().textureLoader());
		shotFactory.setHitboxFactory(new CircleHitboxFactory(15.0));
		shotFactory.setWait(3);
		shotFactory.setPower(100);
		model.setShotFactory(shotFactory);

		final GameInfo gameInfo = new GameInfo();
		gameInfo.getLifeModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				if (newValue.equals(-1)) {
					gameInfo.getLifeModel().setValue(3);
					gameInfo.getBombModel().setValue(2);
					gameInfo.getGrazeModel().setValue(0);
					gameInfo.getScoreModel().setValue(0L);
				}
			}
		});

		final StageController stage = new StageController();
		game.changeView(stage.getView());
		stage.getView().setPlayableArea(new Rectangle(15, 15, 385, 540));
		stage.getView().setElementCutoffArea(new Rectangle(0, 0, 410, 600));
		stage.registerPlayer(player);

		model.setBomb(new SampleBomb(gameInfo, game.context().textureLoader()));

		PlayerFocusedHitboxSpriteElement playerHitboxSprite = new PlayerFocusedHitboxSpriteElement(player, new DefaultPlayerHitboxSprite(player, game.context().textureLoader()));
		stage.getView().getUiLayer().addElement(playerHitboxSprite);

		Rectangle stageViewBounds = new Rectangle(5, 5, 410, 562);

		SampleGUI sampleGUI = new SampleGUI(stageViewBounds, new Point(450, 60), gameInfo, game.context());
		stage.getView().getUiLayer().addElement(sampleGUI);

		FpsCounter fpsCounter = new FpsCounter(game.context().textureLoader());
		stage.registerElement(fpsCounter);
		stage.getView().getGadgetsLayer().addElement(fpsCounter);

		CollisionListenerFactory collisionListenerFactory = new DefaultCollisionListenerFactory(game.context().textureLoader());
		stage.getCollisionDetector().addCollisionListener(collisionListenerFactory.createCollisionListener(stage, gameInfo));

		game.registerTickListener(stage);

		final SpreadSpellcard spreadSpellcard = new SpreadSpellcard(stage, game.context().textureLoader());
		spreadSpellcard.register();

		final StreamSpellcard streamSpellcard = new StreamSpellcard(stage, game.context().textureLoader());
		streamSpellcard.register();

		final RadialSpellcard radialSpellcard = new RadialSpellcard(stage, game.context().textureLoader());
		radialSpellcard.register();

		final RandomRedirectionSpellcard redirectionSpellcard = new RandomRedirectionSpellcard(stage, game.context().textureLoader());
		redirectionSpellcard.register();

		final ExplosionSpellcard explosionSpellcard = new ExplosionSpellcard(stage, game.context().textureLoader());
		explosionSpellcard.register();

		SpellcardActiveIcon spreadActiveIcon = new SpellcardActiveIcon(spreadSpellcard, 1, game.context().textureLoader());
		spreadActiveIcon.setColor(Color.WHITE);
		spreadActiveIcon.getPosition().set(10.0, 10.0);

		SpellcardActiveIcon streamActiveIcon = new SpellcardActiveIcon(streamSpellcard, 2, game.context().textureLoader());
		streamActiveIcon.setColor(Color.RED);
		streamActiveIcon.getPosition().set(25.0, 10.0);

		SpellcardActiveIcon radialActiveIcon = new SpellcardActiveIcon(radialSpellcard, 3, game.context().textureLoader());
		radialActiveIcon.setColor(Color.BLUE);
		radialActiveIcon.getPosition().set(40.0, 10.0);

		SpellcardActiveIcon redirectionActiveIcon = new SpellcardActiveIcon(redirectionSpellcard, 4, game.context().textureLoader());
		redirectionActiveIcon.setColor(Color.CYAN);
		redirectionActiveIcon.getPosition().set(55.0, 10.0);

		SpellcardActiveIcon explosionActiveIcon = new SpellcardActiveIcon(explosionSpellcard, 5, game.context().textureLoader());
		explosionActiveIcon.setColor(Color.MAGENTA);
		explosionActiveIcon.getPosition().set(70.0, 10.0);

		stage.getView().getUiLayer().addElement(spreadActiveIcon);
		stage.getView().getUiLayer().addElement(streamActiveIcon);
		stage.getView().getUiLayer().addElement(radialActiveIcon);
		stage.getView().getUiLayer().addElement(redirectionActiveIcon);
		stage.getView().getUiLayer().addElement(explosionActiveIcon);

		streamSpellcard.getOrigin().set(250.0, 0.0);
		PathController pathController = new PathController();
		BezierWaypoint bezier = new BezierWaypoint(new Vector2D(255.0, 800.0), 320);
		bezier.addPoint(new Vector2D(160.0, 280.0));
		bezier.addPoint(new Vector2D(390.0, 500.0));
		pathController.addWaypoint(bezier);

		final Enemy enemy = new Enemy(BallBulletSpriteFactory.create(Color.GREEN, game.context().textureLoader()), pathController, streamSpellcard.getOrigin());
		enemy.setHitbox(new CircleHitbox(enemy, 5.0));
		enemy.setTargetHitbox(enemy.getHitbox());
		stage.enemies().spawn(enemy);

		enemy.addEnemyDestroyedListener(gameInfo.getScoreListener());
		enemy.addEnemyDestroyedListener(new EnemyDestroyedListener() {
			@Override
			public void enemyDestroyed(AbstractHostileEntity enemy) {
				streamSpellcard.getOrigin().set(200.0, 20.0);
			}
		});

		stage.getPurger().addElementPurgeListener(new ElementRemovedListener() {
			@Override
			public void elementRemoved(AbstractPlaceableGameElement element) {
				if (element == enemy) {
					streamSpellcard.getOrigin().set(200.0, 20.0);
				}
			}
		});

		game.setCurrentInputReceiver(new InputReceiver() {

			@Override
			public void released(int keyCode) {
				if (keyCode == Keyboard.KEY_1) {
					spreadSpellcard.setActive(!spreadSpellcard.isActive());
				} else if (keyCode == Keyboard.KEY_2) {
					streamSpellcard.setActive(!streamSpellcard.isActive());
				} else if (keyCode == Keyboard.KEY_3) {
					radialSpellcard.setActive(!radialSpellcard.isActive());
				} else if (keyCode == Keyboard.KEY_4) {
					redirectionSpellcard.setActive(!redirectionSpellcard.isActive());
				} else if (keyCode == Keyboard.KEY_5) {
					explosionSpellcard.setActive(!explosionSpellcard.isActive());
				} else if (keyCode == Keyboard.KEY_ESCAPE) {
					System.exit(0);
				} else {
					player.getController().getInputManager().getReceiver().released(keyCode);
				}
			}

			@Override
			public void pressed(int keyCode) {
				player.getController().getInputManager().getReceiver().pressed(keyCode);
			}

			@Override
			public void clear() {
			}
		});

		game.start();
	}
}
