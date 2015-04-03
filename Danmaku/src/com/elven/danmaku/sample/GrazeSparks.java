package com.elven.danmaku.sample;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.graphics.GraphicalElement;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureImageSource;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Angle;
import com.elven.danmaku.core.system.Vector2D;

public class GrazeSparks implements UpdateableGameElement, GraphicalElement {

	private final StageController stage;
	private final Queue<Spark> sparks = new ConcurrentLinkedQueue<>();
	private final int maxSparks = 30;
	private int duration;
	private Texture texture;

	public GrazeSparks(StageController stage, int duration, TextureLoader loader) {
		this.stage = stage;
		this.duration = duration;
		texture = loader.getTexture(new SparkTextureSource());
	}

	public void createSparks(Vector2D position) {
		Random random = new Random();
		int numSparks = (int) 5 + random.nextInt(5);

		for (int i = 0; i < numSparks && sparks.size() < maxSparks; i++) {
			double sparkSize = 1.0 + random.nextDouble() * 4.0;
			Vector2D size = new Vector2D(sparkSize, sparkSize);

			Angle angle = new Angle(random.nextDouble() * 360.0);
			Vector2D force = angle.toVector(0.3 + random.nextDouble() * 2.0);

			double shrinkAmount = 0.01 + random.nextDouble() * 0.5;
			Vector2D shrink = new Vector2D(shrinkAmount, shrinkAmount);

			int shrinkStart = 10 + random.nextInt(duration - 5);

			int randomAlpha = 0x11 + random.nextInt(0x99);
			int randomBlue = random.nextInt(0x88);
			int color = randomAlpha << 31 | 0xFFFF00 | randomBlue;

			Spark spark = new Spark(new Vector2D(position), size, force, shrink, shrinkStart, new Color(color, true), duration);
			sparks.add(spark);
		}
	}

	public void install() {
		stage.registerElement(this);
		stage.getView().getGadgetsLayer().addElement(this);
	}

	@Override
	public void render() {
		texture.bind();

		for (Spark spark : sparks) {
			glBegin(GL_QUADS);

			glColor4d((double) spark.getColor().getRed() / 255.0, (double) spark.getColor().getGreen() / 255.0, (double) spark.getColor().getBlue() / 255.0,
					(double) spark.getColor().getAlpha() / 255.0);
			glTexCoord2f(0, 0);
			glVertex2d(spark.getPosition().getX(), spark.getPosition().getY());

			glTexCoord2f(0, texture.getHeight());
			glVertex2d(spark.getPosition().getX(), spark.getPosition().getY() + spark.getSize().getY());

			glTexCoord2f(texture.getWidth(), texture.getHeight());
			glVertex2d(spark.getPosition().getX() + spark.getSize().getX(), spark.getPosition().getY() + spark.getSize().getY());

			glTexCoord2f(texture.getWidth(), 0);
			glVertex2d(spark.getPosition().getX() + spark.getSize().getX(), spark.getPosition().getY());

			glEnd();
		}

		glColor4f(1, 1, 1, 1);
	}

	@Override
	public void tick() {
		Iterator<Spark> iterator = sparks.iterator();

		while (iterator.hasNext()) {
			Spark spark = iterator.next();
			
			if(spark.isDone()) {
				iterator.remove();
			} else {
				spark.getPosition().translate(spark.getForce().getX(), spark.getForce().getY());
				spark.getForce().setY(spark.getForce().getY() + 0.04);
				
				if (spark.isShrinking()) {
					spark.getSize().translate(-spark.getShrink().getX(), -spark.getShrink().getY());
					
					if (spark.getSize().getX() < 1.0) {
						iterator.remove();
					}
				}
				
				spark.tick();
			}
		}
	}

	private final class Spark {

		private final Vector2D position;
		private final Vector2D size;
		private final Vector2D force;
		private final Vector2D shrink;
		private final int shrinkStart;
		private Color color;
		private int framesRemaining;

		public Spark(Vector2D position, Vector2D size, Vector2D force, Vector2D shrink, int shrinkStart, Color color, int frames) {
			this.position = position;
			this.size = size;
			this.force = force;
			this.shrink = shrink;
			this.shrinkStart = shrinkStart;
			this.color = color;
			this.framesRemaining = frames;
		}

		public Vector2D getPosition() {
			return position;
		}

		public Vector2D getSize() {
			return size;
		}

		public Vector2D getForce() {
			return force;
		}

		public Vector2D getShrink() {
			return shrink;
		}

		public Color getColor() {
			return color;
		}

		public void tick() {
			framesRemaining--;
		}

		public boolean isShrinking() {
			return framesRemaining < shrinkStart;
		}

		public boolean isDone() {
			return framesRemaining <= 0;
		}
	}

	private final class SparkTextureSource implements TextureImageSource {

		@Override
		public BufferedImage createImage() {
			BufferedImage image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = image.createGraphics();
			g.setColor(Color.YELLOW);
			g.fillOval(0, 0, 5, 5);

			return image;
		}

		@Override
		public String getResourceName() {
			return "graze.spark";
		}
	}
}
