package com.elven.danmaku.core.elements.disposal;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.stage.StageView;
import com.elven.danmaku.core.system.Vector2D;

public class OutsideStageDisposalRule implements DisposalRule {

	private int outsideTolerance;
	
	public OutsideStageDisposalRule() {
		this(0);
	}
	
	public OutsideStageDisposalRule(int outsideTolerance) {
		this.outsideTolerance = outsideTolerance;
	}
	
	public int getOutsideTolerance() {
		return outsideTolerance;
	}
	
	public void setOutsideTolerance(int outsideTolerance) {
		this.outsideTolerance = outsideTolerance;
	}
	
	@Override
	public boolean shouldDispose(AbstractPlaceableGameElement element, StageView stageView) {
		Vector2D position = element.getPosition();
		Dimension spriteSize = element.getSprite().getSize();
		Rectangle cutoff = stageView.getElementCutoffArea();
		return validatePosition(position.getX(), spriteSize.width, cutoff.x, cutoff.x + cutoff.width) || validatePosition(position.getY(), spriteSize.height, cutoff.y, cutoff.y + cutoff.height);
	}
	
	private boolean validatePosition(double elementPosition, int spriteSize, int min, int max) {
		return elementPosition < min - (spriteSize + outsideTolerance) || elementPosition > max + spriteSize + outsideTolerance;
	}
}
