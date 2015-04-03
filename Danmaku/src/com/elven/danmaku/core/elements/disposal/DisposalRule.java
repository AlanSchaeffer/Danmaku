package com.elven.danmaku.core.elements.disposal;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.stage.StageView;

public interface DisposalRule {

	public boolean shouldDispose(AbstractPlaceableGameElement element, StageView stageView);
}
