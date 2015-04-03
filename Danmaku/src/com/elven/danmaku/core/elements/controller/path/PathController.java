package com.elven.danmaku.core.elements.controller.path;

import java.util.ArrayDeque;
import java.util.Deque;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.system.Vector2D;

public class PathController implements Controller {

	private Deque<WaypointNode> waypoints = new ArrayDeque<>();
	
	public void addWaypoint(Waypoint waypoint) {
		Vector2D startingPoint = !waypoints.isEmpty() ? waypoints.getLast().getStartingPoint() : null;
		WaypointNode node = new WaypointNode(waypoint, startingPoint);
		waypoints.add(node);
	}
	
	@Override
	public void tick(AbstractPlaceableGameElement element) {
		if(!waypoints.isEmpty()) {
			WaypointNode node = waypoints.getFirst();
			
			if(node.getStartingPoint() == null) {
				node.setStartingPoint(new Vector2D(element.getPosition()));
			}
			
			node.getWaypoint().tick(element, node.getStartingPoint(), node.getCurrentFrame());
			node.tick();
			
			if(node.isFinished()) {
				element.getPosition().set(node.getWaypoint().getDestination());
				waypoints.removeFirst();
			}
		}
	}

	private class WaypointNode {

		private Waypoint waypoint;
		private Vector2D startingPoint;
		private int remainingFrames;

		public WaypointNode(Waypoint waypoint, Vector2D startingPoint) {
			this.waypoint = waypoint;
			this.startingPoint = startingPoint;
			remainingFrames = waypoint.getDuration();
		}

		public Waypoint getWaypoint() {
			return waypoint;
		}
		
		public Vector2D getStartingPoint() {
			return startingPoint;
		}
		
		public void setStartingPoint(Vector2D startingPoint) {
			this.startingPoint = startingPoint;
		}

		public void tick() {
			remainingFrames--;
		}
		
		public boolean isFinished() {
			return remainingFrames <= 0;
		}
		
		public int getCurrentFrame() {
			return waypoint.getDuration() - remainingFrames;
		}
	}
}
