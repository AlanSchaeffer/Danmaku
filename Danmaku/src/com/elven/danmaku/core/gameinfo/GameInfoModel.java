package com.elven.danmaku.core.gameinfo;

import com.elven.danmaku.core.listeners.GameInfoChangeListener;

public interface GameInfoModel<T extends Number> {

	public void addChangeListener(GameInfoChangeListener listener);
	
	public void removeChangeListener(GameInfoChangeListener listener);

	public T getValue();

	public void setValue(T value);

	public void increase(T amount);

	public void decrease(T amount);
}
