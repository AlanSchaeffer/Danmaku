package com.elven.danmaku.core.elements.view;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;

import com.elven.danmaku.core.system.Vector2D;
import com.elven.danmaku.core.util.MathUtils;
import com.elven.danmaku.core.util.RotationFunction;

public class RotatedSprite implements Sprite {

	private final Sprite sprite;
	private double rotation = 0.0;
	private Dimension currentBoundingBox;

	public RotatedSprite(Sprite sprite) {
		this.sprite = sprite;
		currentBoundingBox = sprite.getSize();
	}

	public Sprite getSprite() {
		return sprite;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
		recalculateSize();
	}
	
	public void rotate(double rotation) {
		setRotation(this.rotation + rotation);
	}

	private void recalculateSize() {
		RotationFunction function = new RotationFunction(Math.abs(rotation));

		double displaceX = sprite.getSize().width / 2.0;
		double displaceY = sprite.getSize().height / 2.0;

		Vector2D rotatedTopLeft = function.rotatedPoint(-displaceX, -displaceY);
		Vector2D rotatedTopRight = function.rotatedPoint(displaceX, -displaceY);
		Vector2D rotatedBottomLeft = function.rotatedPoint(-displaceX, displaceY);
		Vector2D rotatedBottomRight = function.rotatedPoint(displaceX, displaceY);

		int boundTop = (int) Math.floor(MathUtils.min(rotatedTopLeft.getY(), rotatedTopRight.getY(), rotatedBottomLeft.getY(), rotatedBottomRight.getY()));
		int boundLeft = (int) Math.floor(MathUtils.min(rotatedTopLeft.getX(), rotatedTopRight.getX(), rotatedBottomLeft.getX(), rotatedBottomRight.getX()));
		int boundBottom = (int) Math.ceil(MathUtils.max(rotatedTopLeft.getY(), rotatedTopRight.getY(), rotatedBottomLeft.getY(), rotatedBottomRight.getY()));
		int boundRight = (int) Math.ceil(MathUtils.max(rotatedTopLeft.getX(), rotatedTopRight.getX(), rotatedBottomLeft.getX(), rotatedBottomRight.getX()));

		currentBoundingBox = new Dimension(boundRight - boundLeft, boundBottom - boundTop);
	}

	@Override
	public void render() {
		if (rotation != 0.0) {
			glPushMatrix();
			glRotated(rotation, 0, 0, 1);
			
			sprite.render();

			glPopMatrix();
		} else {
			sprite.render();
		}
	}

	@Override
	public Dimension getSize() {
		return currentBoundingBox;
	}
}
