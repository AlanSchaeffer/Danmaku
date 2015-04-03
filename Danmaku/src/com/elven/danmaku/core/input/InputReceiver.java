package com.elven.danmaku.core.input;

public interface InputReceiver {

	public void pressed(int keyCode);
	
	public void released(int keyCode);
	
	public void clear();
}
