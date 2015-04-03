package com.elven.danmaku.core.bullets.spawner;

import java.util.ArrayList;
import java.util.List;

import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.listeners.BurstListener;

public class BurstBulletSpawner extends AbstractBulletSpawner implements UpdateableGameElement {

	private final BulletSpawner spawner;

	private int framesPerBurst = 10;
	private double framesBetweenBursts = 2000.0;
	private final List<BurstListener> listeners;

	private int framesPaused = 0;
	private int framesFired = 0;

	public BurstBulletSpawner(BulletSpawner spawner) {
		this.spawner = spawner;
		listeners = new ArrayList<>(0);
	}

	public int getFramesPerBurst() {
		return framesPerBurst;
	}

	public void setFramesPerBurst(int framesPerBurst) {
		this.framesPerBurst = framesPerBurst;
	}

	public double getFramesBetweenBursts() {
		return framesBetweenBursts;
	}

	public void setFramesBetweenBursts(int framesBetweenBursts) {
		this.framesBetweenBursts = framesBetweenBursts;
	}

	public void addBurstListener(BurstListener listener) {
		listeners.add(listener);
	}

	public void removeBurstListener(BurstListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		if (!active) {
			fireBurstEnded();
			framesFired = 0;
			framesPaused = 0;
		} else {
			spawner.setActive(true);
		}
	}

	@Override
	public void tick() {
		if (isActive()) {
			if (framesFired == 0 && framesPaused == 0) {
				fireBurstStarted();
			}

			if (framesFired < framesPerBurst) {
				spawner.tick();
				framesFired++;

				if (framesFired == framesPerBurst) {
					spawner.setActive(false);
					fireBurstEnded();
				}
			} else if (framesPaused < framesBetweenBursts) {
				framesPaused++;

				if (framesPaused == framesBetweenBursts) {
					spawner.setActive(true);
					framesFired = 0;
					framesPaused = 0;
				}
			}
		}
	}

	private void fireBurstStarted() {
		for (BurstListener listener : listeners) {
			listener.burstStarted();
		}
	}

	private void fireBurstEnded() {
		for (BurstListener listener : listeners) {
			listener.burstEnded();
		}
	}
}
