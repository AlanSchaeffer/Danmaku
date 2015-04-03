package com.elven.danmaku.sample.player;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.CompositeController;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.controller.MoveController;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.player.bomb.Bomb;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;

public class SampleBomb implements Bomb {

	private final GameInfo gameInfo;
	private final TextureLoader loader;
	private int waitFrames = 0;

	public SampleBomb(GameInfo gameInfo, TextureLoader loader) {
		this.gameInfo = gameInfo;
		this.loader = loader;
	}
	
	@Override
	public void tick() {
		if (waitFrames > 0) {
			waitFrames--;
		}
	}

	@Override
	public boolean canBomb() {
		return gameInfo.getBombModel().getValue() > 0 && waitFrames <= 0;
	}

	@Override
	public void bomb(final Player player, final StageController stage) {
		waitFrames = 160;
		gameInfo.getBombModel().decrease(1);
		player.getController().getInvincibilityController().setFrames(160);
		spawnProjectile(player, stage, -15.0);
		
		stage.doLater(20, new Runnable() {
			@Override
			public void run() {
				spawnProjectile(player, stage, 15.0);
			}
		});
		
		stage.doLater(40, new Runnable() {
			@Override
			public void run() {
				spawnProjectile(player, stage, -10.0);
			}
		});
	}

	private void spawnProjectile(Player player, StageController stage, double xForce) {
		MoveController moveController = new MoveController(new Vector2D(xForce, 0.5)) {
			@Override
			public void tick(AbstractPlaceableGameElement element) {
				super.tick(element);
				getForce().setX(getForce().getX() * 0.6);
				getForce().setY(getForce().getY() - 0.05);
			}
		};
		CompositeController controller = new CompositeController();
		BombProjectile projectile = new BombProjectile(new Vector2D(player.getPosition()), controller, loader);

		controller.addController(moveController, 80);
		controller.addController(createExplodeController(projectile, stage), 1);

		stage.registerElement(projectile);
		stage.getView().getPlayerBulletsLayer().addElement(projectile);
	}

	private Controller createExplodeController(final BombProjectile projectile, final StageController stage) {
		return new Controller() {
			@Override
			public void tick(AbstractPlaceableGameElement element) {
				stage.unregisterElement(projectile);
				stage.getView().getPlayerBulletsLayer().removeElement(projectile);

				for (AbstractHostileEntity enemy : stage.getModel().getEnemies().getElements()) {
					enemy.damage(5000);
				}
				
				stage.bullets().clear();
			}
		};
	}
}
