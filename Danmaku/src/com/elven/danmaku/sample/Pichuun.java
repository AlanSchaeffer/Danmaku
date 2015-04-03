package com.elven.danmaku.sample;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;

import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.graphics.GraphicalElement;
import com.elven.danmaku.core.graphics.texture.TextTextureSource;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.system.Vector2D;

public class Pichuun implements UpdateableGameElement, GraphicalElement {

	private final StageController stage;
	private final Vector2D position;
	private int framesRemaining;
	private Texture texture;

	public Pichuun(StageController stage, Vector2D position, int duration, TextureLoader loader) {
		this.stage = stage;
		this.position = new Vector2D(position);
		framesRemaining = duration;
		
		texture = loader.getTexture(new TextTextureSource("Pichuun~", "pichuun", new Font("Arial", Font.BOLD, 10), Color.YELLOW));
	}
	
	public void install() {
		stage.getModel().getElements().add(this);
		stage.getView().getGadgetsLayer().addElement(this);
	}
	
	@Override
	public void render() {
		texture.bind();
		
		glBegin(GL_QUADS);
		
		glTexCoord2d(0, 0);
		glVertex2d(position.getX(), position.getY() - 25);
		
		glTexCoord2d(texture.getWidth(), 0);
		glVertex2d(position.getX() + texture.getImageWidth(), position.getY() - 25);
		
		glTexCoord2d(texture.getWidth(), texture.getHeight());
		glVertex2d(position.getX() + texture.getImageWidth(), position.getY() + texture.getImageHeight() - 25);
		
		glTexCoord2d(0, texture.getHeight());
		glVertex2d(position.getX(), position.getY() + texture.getImageHeight() - 25);
		
		glEnd();
	}
	
	@Override
	public void tick() {
		if(framesRemaining == 0) {
			stage.getModel().getElements().remove(this);
			stage.getView().getGadgetsLayer().removeElement(this);
		}
		
		position.setY(position.getY() - 0.3);
		framesRemaining--;
	}
}
