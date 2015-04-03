package com.elven.danmaku.core.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BufferedList<T> implements Iterable<T> {

	private final List<T> list;
	private final Queue<BufferCommand> buffer = new LinkedList<>();
	private volatile boolean buffering = false;

	public BufferedList() {
		this(new ArrayList<T>());
	}
	
	public BufferedList(List<T> list) {
		this.list = list;
	}
	
	public void add(T element) {
		if (buffering) {
			buffer.add(new AddCommand(element));
		} else {
			list.add(element);
		}
	}

	public void remove(T element) {
		if (buffering) {
			buffer.add(new RemoveCommand(element));
		} else {
			list.remove(element);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	public void startBuffering() {
		buffering = true;
	}
	
	public void flush() {
		while(!buffer.isEmpty()) {
			buffer.poll().execute();
		}
		
		buffering = false;
	}
	
	public void sort(Comparator<T> comparator) {
		Collections.sort(list, comparator);
	}
	
	private interface BufferCommand {
		public void execute();
	}
	
	private class AddCommand implements BufferCommand {
		private final T element;

		public AddCommand(T element) {
			this.element = element;
		}
		
		@Override
		public void execute() {
			list.add(element);
		}
	}
	
	private class RemoveCommand implements BufferCommand {
		private final T element;

		public RemoveCommand(T element) {
			this.element = element;
		}
		
		@Override
		public void execute() {
			list.remove(element);
		}
	}
}
