package com.elven.danmaku.core.gameinfo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.listeners.GameInfoChangeListener;

public abstract class AbstractGameInfoModel<T extends Number> implements GameInfoModel<T> {

	private T value;
	
	private final Queue<GameInfoChangeListener> listeners = new ConcurrentLinkedQueue<>();

	public AbstractGameInfoModel(T value) {
		this.value = value;
	}
	
	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public void setValue(T value) {
		this.value = value;
		fireValueChanged(value);
	}
	
	@Override
	public void addChangeListener(GameInfoChangeListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeChangeListener(GameInfoChangeListener listener) {
		listeners.remove(listener);
	}
	
	private void fireValueChanged(Object newValue) {
		for(GameInfoChangeListener listener : listeners) {
			listener.valueChanged(newValue);
		}
	}
}
