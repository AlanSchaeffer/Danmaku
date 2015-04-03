package com.elven.danmaku.core.elements.controller;

import java.util.ArrayList;
import java.util.List;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;

public class CompositeController implements Controller {

	private final List<ControllerNode> nodes;

	public CompositeController() {
		nodes = new ArrayList<>();
	}

	/**
	 * The duration of the last controller doesn't matter, as it will be used
	 * indefinitely
	 * */
	public void addController(Controller controller, int duration) {
		nodes.add(new ControllerNode(controller, duration));
	}

	@Override
	public void tick(AbstractPlaceableGameElement element) {
		if (!nodes.isEmpty()) {
			ControllerNode node = nodes.get(0);
			node.getController().tick(element);
			node.tick();

			if (node.isFinished() && nodes.size() > 1) {
				nodes.remove(0);
			}
		}
	}

	private final class ControllerNode {

		private Controller controller;
		private int framesRemaining;

		public ControllerNode(Controller controller, int duration) {
			this.controller = controller;
			this.framesRemaining = duration;
		}

		public Controller getController() {
			return controller;
		}

		public void tick() {
			if (framesRemaining > 0) {
				--framesRemaining;
			}
		}

		public boolean isFinished() {
			return framesRemaining <= 0;
		}
	}
}
