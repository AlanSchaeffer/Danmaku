package com.elven.danmaku.core.stage;

import java.awt.Rectangle;
import java.util.Comparator;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.frame.GameView;
import com.elven.danmaku.core.graphics.AbstractOrderedGraphicsLayer;
import com.elven.danmaku.core.graphics.BasicGraphicsLayer;
import com.elven.danmaku.core.graphics.BufferedGraphicsLayer;
import com.elven.danmaku.core.graphics.GraphicsLayer;

public class StageView implements GameView {

	private final StageController stage;
	
	private final GraphicsLayer uiLayer;
	private final GraphicsLayer gadgetsLayer;
	private final BufferedGraphicsLayer bulletsLayer;
	private final GraphicsLayer playerBulletsLayer;
	private final GraphicsLayer enemiesLayer;
	private final GraphicsLayer effectsLayer;
	private final GraphicsLayer playerLayer;
	private final BufferedGraphicsLayer itemLayer;
	
	private Rectangle playableArea;
	private Rectangle elementCutoffArea;

	public StageView(StageController stage) {
		this.stage = stage;
		
		uiLayer = new BasicGraphicsLayer();
		gadgetsLayer = new BasicGraphicsLayer();
		playerBulletsLayer = new BasicGraphicsLayer();
		enemiesLayer = new BasicGraphicsLayer();
		playerLayer = new BasicGraphicsLayer();
		
		bulletsLayer = new AbstractOrderedGraphicsLayer(new SpriteSizeComparator());
		effectsLayer = new AbstractOrderedGraphicsLayer(new LowerOnTopComparator());
		itemLayer = new AbstractOrderedGraphicsLayer(new LowerOnTopComparator());
		playableArea = new Rectangle(0, 0, 800, 600);
		elementCutoffArea = new Rectangle(0, 0, 800, 600);
	}
	
	public StageController getStage() {
		return stage;
	}

	public GraphicsLayer getUiLayer() {
		return uiLayer;
	}

	public GraphicsLayer getGadgetsLayer() {
		return gadgetsLayer;
	}

	public GraphicsLayer getBulletsLayer() {
		return bulletsLayer;
	}

	public GraphicsLayer getPlayerBulletsLayer() {
		return playerBulletsLayer;
	}

	public GraphicsLayer getEnemiesLayer() {
		return enemiesLayer;
	}
	
	public GraphicsLayer getEffectsLayer() {
		return effectsLayer;
	}

	public GraphicsLayer getPlayerLayer() {
		return playerLayer;
	}
	
	public GraphicsLayer getItemLayer() {
		return itemLayer;
	}

	public Rectangle getPlayableArea() {
		return playableArea;
	}

	public void setPlayableArea(Rectangle playableArea) {
		this.playableArea = playableArea;
	}

	public Rectangle getElementCutoffArea() {
		return elementCutoffArea;
	}

	public void setElementCutoffArea(Rectangle elementCutoffArea) {
		this.elementCutoffArea = elementCutoffArea;
	}
	
	void destroyLayers() {
		enemiesLayer.destroy();
		playerBulletsLayer.destroy();
		itemLayer.destroy();
		playerLayer.destroy();
		effectsLayer.destroy();
		bulletsLayer.destroy();
		gadgetsLayer.destroy();
		uiLayer.destroy();
	}
	
	@Override
	public void render() {
		bulletsLayer.flush();
		itemLayer.flush();

		enemiesLayer.render();
		playerBulletsLayer.render();
		playerLayer.render();
		effectsLayer.render();
		itemLayer.render();
		bulletsLayer.render();
		gadgetsLayer.render();
		uiLayer.render();
	}

	@Override
	public void destroy() {
		stage.finish();
	}

	private static class SpriteSizeComparator implements Comparator<AbstractPlaceableGameElement> {

		@Override
		public int compare(AbstractPlaceableGameElement o1, AbstractPlaceableGameElement o2) {
			int area1 = o1.getSprite().getSize().width * o1.getSprite().getSize().height;
			int area2 = o2.getSprite().getSize().width * o2.getSprite().getSize().height;
			return area2 - area1;
		}
	}
	
	private static class LowerOnTopComparator implements Comparator<AbstractPlaceableGameElement> {

		@Override
		public int compare(AbstractPlaceableGameElement o1, AbstractPlaceableGameElement o2) {
			return (int) (o1.getPosition().getY() - o2.getPosition().getY());
		}
	}
}
