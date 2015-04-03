package com.elven.danmaku.core.stage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.Destroyable;
import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.items.CollectableItem;
import com.elven.danmaku.core.listeners.ElementRemovedListener;
import com.elven.danmaku.core.listeners.EnemyDestroyedListener;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.player.PlayerBullet;
import com.elven.danmaku.core.system.collision.CollisionDetector;
import com.elven.danmaku.core.system.collision.HostileElementsAgainstPlayerCollisionTask;
import com.elven.danmaku.core.system.collision.ItemCollectionCollisionTask;
import com.elven.danmaku.core.system.collision.PlayerBulletAgainstEnemiesCollisionTask;

public class StageController implements UpdateableGameElement {

	private final StageModel model = new StageModel();
	private final StageView view = new StageView(this);
	private final CollisionDetector collisionDetector = new CollisionDetector();
	private final HostileElementsAgainstPlayerCollisionTask hostileCollisionsTask;
	private final ItemCollectionCollisionTask itemCollectionTask;

	private final EnemiesManager enemiesManager = new EnemiesManager();
	private final BulletsManager bulletsManager = new BulletsManager();
	private final PlayerBulletsManager playerBulletsManager = new PlayerBulletsManager();
	private final ItemsManager itemsManager = new ItemsManager();
	
	private final ElementPurger purger;
	private Player player;
	
	private final Queue<ElementRemovedListener> listeners = new ConcurrentLinkedQueue<>();

	public StageController() {
		purger = new ElementPurger(this);
		purger.start();
		
		hostileCollisionsTask = new HostileElementsAgainstPlayerCollisionTask(this);
		itemCollectionTask = new ItemCollectionCollisionTask(this);
		collisionDetector.addCollisionTask(new PlayerBulletAgainstEnemiesCollisionTask(this));
		collisionDetector.addCollisionTask(hostileCollisionsTask);
		collisionDetector.addCollisionTask(itemCollectionTask);
	}

	public StageView getView() {
		return view;
	}

	public StageModel getModel() {
		return model;
	}

	public CollisionDetector getCollisionDetector() {
		return collisionDetector;
	}

	public ElementPurger getPurger() {
		return purger;
	}

	public Player getPlayer() {
		return player;
	}

	public void finish() {
		purger.stop();
		
		bullets().clear();
		enemies().clear();
		playerBullets().clear();
		items().clear();
		
		while(!model.getElements().isEmpty()) {
			UpdateableGameElement element = model.getElements().poll();
			
			if(element instanceof Destroyable) {
				((Destroyable) element).destroy();
			}
		}
		
		view.destroyLayers();
	}

	@Override
	public void tick() {
		for (UpdateableGameElement element : model.getElements()) {
			element.tick();
		}

		if (player != null) {
			collisionDetector.checkCollisions();
		}

		model.getBullets().flushTo(model.getElements());
		model.getEnemies().flushTo(model.getElements());
		model.getItems().flushTo(model.getElements());
		model.getPlayerBullets().flushTo(model.getElements());
		
		view.render();
	}
	
	public BulletsManager bullets() {
		return bulletsManager;
	}

	public EnemiesManager enemies() {
		return enemiesManager;
	}
	
	public PlayerBulletsManager playerBullets() {
		return playerBulletsManager;
	}
	
	public ItemsManager items() {
		return itemsManager;
	}

	public void registerElement(UpdateableGameElement element) {
		model.getElements().add(element);
	}

	public void unregisterElement(UpdateableGameElement element) {
		model.getElements().remove(element);
	}

	public void registerPlayer(Player player) {
		this.player = player;
		view.getPlayerLayer().addElement(player);
		player.getController().setCurrentStage(this);
		model.getElements().add(player);
	}
	
	public void setHostileCollisionsAndItemCollectionActive(boolean active) {
		hostileCollisionsTask.setActive(active);
		itemCollectionTask.setActive(active);
	}
	
	public void doLater(int framesToWait, Runnable action) {
		RunLaterElement element = new RunLaterElement(framesToWait, action, this);
		registerElement(element);
	}
	
	public void addElementRemovedListener(ElementRemovedListener listener) {
		listeners.add(listener);
	}
	
	public void removeElementRemovedListener(ElementRemovedListener listener) {
		listeners.remove(listener);
	}
	
	private void fireElementRemoved(AbstractPlaceableGameElement element) {
		for(ElementRemovedListener listener : listeners) {
			listener.elementRemoved(element);
		}
	}
	
	public class BulletsManager {
		
		public void spawn(Bullet bullet) {
			model.getBullets().getElements().add(bullet);
			view.getBulletsLayer().addElement(bullet);
			model.getBullets().bufferElement(bullet);
		}
		
		public void despawn(Bullet bullet) {
			unregisterElement(bullet);
			model.getBullets().remove(bullet);
			view.getBulletsLayer().removeElement(bullet);
			fireElementRemoved(bullet);
			bullet.destroy();
		}
		
		public void clear() {
			for (Bullet bullet : model.getBullets().getElements()) {
				despawn(bullet);
			}
		}
	}
	
	public class EnemiesManager {
		
		public void spawn(AbstractHostileEntity enemy) {
			model.getEnemies().getElements().add(enemy);
			view.getEnemiesLayer().addElement(enemy);
			model.getEnemies().bufferElement(enemy);
			
			enemy.addEnemyDestroyedListener(new EnemyDestroyedListener() {
				@Override
				public void enemyDestroyed(AbstractHostileEntity enemy) {
					despawn(enemy);
				}
			});
		}
		
		public void despawn(AbstractHostileEntity enemy) {
			unregisterElement(enemy);
			model.getEnemies().remove(enemy);
			view.getEnemiesLayer().removeElement(enemy);
			fireElementRemoved(enemy);
			enemy.destroy();
		}
		
		public void clear() {
			for(AbstractHostileEntity enemy : model.getEnemies().getElements()) {
				despawn(enemy);
			}
		}
	}
	
	public class PlayerBulletsManager {
		
		public void spawn(PlayerBullet bullet) {
			model.getPlayerBullets().getElements().add(bullet);
			view.getPlayerBulletsLayer().addElement(bullet);
			model.getPlayerBullets().bufferElement(bullet);
		}
		
		public void despawn(PlayerBullet bullet) {
			unregisterElement(bullet);
			model.getPlayerBullets().remove(bullet);
			view.getPlayerBulletsLayer().removeElement(bullet);
			fireElementRemoved(bullet);
			bullet.destroy();
		}
		
		public void clear() {
			for(PlayerBullet bullet : model.getPlayerBullets().getElements()) {
				despawn(bullet);
			}
		}
	}
	
	public class ItemsManager {
		
		public void spawn(CollectableItem item) {
			model.getItems().getElements().add(item);
			view.getItemLayer().addElement(item);
			model.getItems().bufferElement(item);
		}
		
		public void despawn(CollectableItem item) {
			unregisterElement(item);
			model.getItems().remove(item);
			view.getItemLayer().removeElement(item);
			fireElementRemoved(item);
			item.destroy();
		}
		
		public void clear() {
			for(CollectableItem item : model.getItems().getElements()) {
				despawn(item);
			}
		}
	}
}
