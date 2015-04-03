package com.elven.danmaku.core.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.Destroyable;

public class AbstractOrderedGraphicsLayer implements BufferedGraphicsLayer {

	private final List<AbstractPlaceableGameElement> elements = new ArrayList<>();
	private final Queue<AbstractPlaceableGameElement> awaitingAdd = new ConcurrentLinkedQueue<>();
	private final Queue<AbstractPlaceableGameElement> awaitingRemove = new ConcurrentLinkedQueue<>();
	private final Comparator<AbstractPlaceableGameElement> elementComparator;

	public AbstractOrderedGraphicsLayer(Comparator<AbstractPlaceableGameElement> elementComparator) {
		this.elementComparator = elementComparator;
	}

	@Override
	public final void addElement(GraphicalElement element) {
		awaitingAdd.offer((AbstractPlaceableGameElement) element);
		awaitingRemove.remove(element);
	}

	@Override
	public final void removeElement(GraphicalElement element) {
		awaitingRemove.offer((AbstractPlaceableGameElement) element);
		awaitingAdd.remove(element);
	}

	@Override
	public final void render() {
		Collections.sort(elements, elementComparator);

		for (AbstractPlaceableGameElement element : elements) {
			element.render();
		}
	}

	@Override
	public final void flush() {
		while (!awaitingAdd.isEmpty()) {
			elements.add(awaitingAdd.poll());
		}

		while (!awaitingRemove.isEmpty()) {
			elements.remove(awaitingRemove.poll());
		}
	}

	@Override
	public void destroy() {
		for (GraphicalElement element : elements) {
			if (element instanceof Destroyable) {
				((Destroyable) element).destroy();
			}
		}

		while (!awaitingAdd.isEmpty()) {
			AbstractPlaceableGameElement element = awaitingAdd.poll();

			if (element instanceof Destroyable) {
				element.destroy();
			}
		}
	}
}
