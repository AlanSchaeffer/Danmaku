package com.elven.danmaku.core.configuration.script;

public interface ScriptAction {

	public boolean shouldResume();
	
	public boolean shouldRemove();
	
	public void tick();
}
