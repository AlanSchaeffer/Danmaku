package com.elven.danmaku.core.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.elven.danmaku.core.bullets.Bullet;
import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.enemies.AbstractHostileEntity;
import com.elven.danmaku.core.listeners.ElementRemovedListener;
import com.elven.danmaku.core.player.PlayerBullet;

public class ElementPurger {

	private List<ElementRemovedListener> listeners;
	private final StageController stage;
	private Timer timer;

	public ElementPurger(StageController stage) {
		this.stage = stage;
		listeners = Collections.synchronizedList(new ArrayList<ElementRemovedListener>());
	}

	public void start() {
		timer = new Timer(true);
		timer.schedule(createPurgeTask(), 0L, 1000L);
	}

	public void stop() {
		timer.cancel();
	}

	private TimerTask createPurgeTask() {
		return new TimerTask() {
			@Override
			public void run() {
				for (Bullet bullet : stage.getModel().getBullets().getElements()) {
					if (bullet.getDisposalRule().shouldDispose(bullet, stage.getView())) {
						stage.bullets().despawn(bullet);
						fireElementPurged(bullet);
					}
				}

				for (AbstractHostileEntity enemy : stage.getModel().getEnemies().getElements()) {
					if (enemy.getDisposalRule().shouldDispose(enemy, stage.getView())) {
						stage.enemies().despawn(enemy);
						fireElementPurged(enemy);
					}
				}
				
				for(PlayerBullet bullet : stage.getModel().getPlayerBullets().getElements()) {
					if(bullet.getDisposalRule().shouldDispose(bullet, stage.getView())) {
						stage.playerBullets().despawn(bullet);
						fireElementPurged(bullet);
					}
				}
			}
		};
	}

	public void addElementPurgeListener(ElementRemovedListener listener) {
		listeners.add(listener);
	}

	public void removeElementPurgeListener(ElementRemovedListener listener) {
		listeners.remove(listener);
	}

	private void fireElementPurged(AbstractPlaceableGameElement element) {
		synchronized (listeners) {
			for (ElementRemovedListener listener : listeners) {
				listener.elementRemoved(element);
			}
		}
	}
}
