package com.elven.danmaku.core.elements.disposal;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.stage.StageView;

public class NeverDisposeRule implements DisposalRule {

	@Override
	public boolean shouldDispose(AbstractPlaceableGameElement element, StageView stageView) {
		return false;
	}
}
