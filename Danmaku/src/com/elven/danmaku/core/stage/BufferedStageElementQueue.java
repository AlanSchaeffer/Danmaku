package com.elven.danmaku.core.stage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.UpdateableGameElement;

public class BufferedStageElementQueue<T extends UpdateableGameElement> {

	private final Queue<T> elements;
	private final Queue<T> buffer;

	public BufferedStageElementQueue() {
		elements = new ConcurrentLinkedQueue<>();
		buffer = new ConcurrentLinkedQueue<>();
	}

	public Queue<T> getElements() {
		return elements;
	}

	public Queue<T> getBuffer() {
		return buffer;
	}

	public void bufferElement(T element) {
		buffer.add(element);
	}

	public void flushTo(Queue<UpdateableGameElement> stageElements) {
		stageElements.addAll(buffer);
		buffer.clear();
	}
	
	public void remove(T element) {
		elements.remove(element);
		buffer.remove(element);
	}
}
