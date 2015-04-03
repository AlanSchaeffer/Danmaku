package com.elven.danmaku.core.elements.controller.path;

import java.util.ArrayList;
import java.util.List;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.system.Vector2D;

public class BezierWaypoint implements Waypoint {

	private final List<Vector2D> midpoints = new ArrayList<>();
	private final Vector2D destination;
	private final int duration;
	
	public BezierWaypoint(Vector2D destination, int duration) {
		this.destination = destination;
		this.duration = duration;
	}
	
	public void addPoint(Vector2D point) {
		midpoints.add(point);
	}
	
	@Override
	public void tick(AbstractPlaceableGameElement element, Vector2D startingPoint, int frame) {
		double t = (double) frame / (double) duration;
		double[] bezierFormula = bezier(midpoints.size() + 1, t);
		element.getPosition().set(getPoint(bezierFormula, startingPoint));
	}
	
	private Vector2D getPoint(double[] bezierFormula, Vector2D startingPoint) {
		double x = 0.0;
		double y = 0.0;
		
		for(int i = 0; i < bezierFormula.length; i++) {
			double value = bezierFormula[i];
			
			Vector2D point;
			if(i == 0) {
				point = startingPoint;
			} else if(i == bezierFormula.length - 1) {
				point = destination;
			} else {
				point = midpoints.get(i - 1);
			}
			
			x += point.getX() * value;
			y += point.getY() * value;
		}
		
		return new Vector2D(x, y);
	}
	
	private double[] bezier(int order, double t) {
		double[] bezierValues = new double[order + 1];
		double binomialA = 1 - t;
		double binomialB = t;
		
		for(int i = 0; i <= order; i++) {
			bezierValues[i] = binomialCoefficient(order, i) * Math.pow(binomialA, order - i) * Math.pow(binomialB, i);
		}
		
		return bezierValues;
	}
	
	private int binomialCoefficient(int n, int k) {
		return factorial(n) / (factorial(k) * factorial(n - k));
	}
	
	private int factorial(int number) {
		if(number == 0) {
			return 1;
		} else {
			return number * factorial(number - 1);
		}
	}
	
	@Override
	public Vector2D getDestination() {
		return destination;
	}

	@Override
	public int getDuration() {
		return duration;
	}
}
