package com.elven.danmaku.core.configuration;

public interface Configuration<T> {

	public void configure();

	public T getConfiguredObject();
}
