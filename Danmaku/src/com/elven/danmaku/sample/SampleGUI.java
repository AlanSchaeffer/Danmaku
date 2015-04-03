package com.elven.danmaku.sample;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.GraphicalElement;
import com.elven.danmaku.core.graphics.texture.DynamicTextTextureSource;
import com.elven.danmaku.core.graphics.texture.Texture;
import com.elven.danmaku.core.listeners.GameInfoChangeListener;
import com.elven.danmaku.core.system.GameContext;

public class SampleGUI implements GraphicalElement {

	private final Rectangle stageViewBounds;
	private final Point statsPosition;
	private final GameInfo gameInfo;
	private final GameContext context;
	private final NumberFormat powerFormat = new DecimalFormat("0.00");

	private final DynamicTextTextureSource livesTextureSource;
	private final DynamicTextTextureSource bombsTextureSource;
	private final DynamicTextTextureSource scoreTextureSource;
	private final DynamicTextTextureSource powerTextureSource;
	private final DynamicTextTextureSource grazeTextureSource;
	
	private Texture livesTexture;
	private Texture bombsTexture;
	private Texture scoreTexture;
	private Texture powerTexture;
	private Texture grazeTexture;

	public SampleGUI(Rectangle stageViewBounds, Point statsPosition, GameInfo gameInfo, GameContext context) {
		this.stageViewBounds = stageViewBounds;
		this.statsPosition = statsPosition;
		this.gameInfo = gameInfo;
		this.context = context;

		livesTextureSource = new DynamicTextTextureSource("gui.lives", new Dimension(200, 20), Font.getFont("Arial"), Color.WHITE);
		bombsTextureSource = new DynamicTextTextureSource("gui.bombs", new Dimension(200, 20), Font.getFont("Arial"), Color.WHITE);
		scoreTextureSource = new DynamicTextTextureSource("gui.score", new Dimension(200, 20), Font.getFont("Arial"), Color.WHITE);
		powerTextureSource = new DynamicTextTextureSource("gui.power", new Dimension(200, 20), Font.getFont("Arial"), Color.WHITE);
		grazeTextureSource = new DynamicTextTextureSource("gui.graze", new Dimension(200, 20), Font.getFont("Arial"), Color.WHITE);
		
		setupTextureSources();
	}
	
	private void setupTextureSources() {
		livesTextureSource.setText("Lives: " + gameInfo.getLifeModel().getValue());
		livesTexture = context.textureLoader().getTexture(livesTextureSource);
		gameInfo.getLifeModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				livesTextureSource.setText("Lives: " + gameInfo.getLifeModel().getValue());
				livesTexture = null;
			}
		});
		
		bombsTextureSource.setText("Bombs: " + gameInfo.getBombModel().getValue());
		bombsTexture = context.textureLoader().getTexture(bombsTextureSource);
		gameInfo.getBombModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				bombsTextureSource.setText("Bombs: " + gameInfo.getBombModel().getValue());
				bombsTexture = null;
			}
		});
		
		scoreTextureSource.setText("Score: " + gameInfo.getScoreModel().getValue());
		scoreTexture = context.textureLoader().getTexture(scoreTextureSource);
		gameInfo.getScoreModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				scoreTextureSource.setText("Score: " + gameInfo.getScoreModel().getValue());
				scoreTexture = null;
			}
		});
		
		powerTextureSource.setText("Power: " + powerFormat.format(gameInfo.getPowerModel().getValue() / 100.0));
		powerTexture = context.textureLoader().getTexture(powerTextureSource);
		gameInfo.getPowerModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				powerTextureSource.setText("Power: " + powerFormat.format(gameInfo.getPowerModel().getValue() / 100.0));
				powerTexture = null;
			}
		});
		
		grazeTextureSource.setText("Graze: " + gameInfo.getGrazeModel().getValue());
		grazeTexture = context.textureLoader().getTexture(grazeTextureSource);
		gameInfo.getGrazeModel().addChangeListener(new GameInfoChangeListener() {
			@Override
			public void valueChanged(Object newValue) {
				grazeTextureSource.setText("Graze: " + gameInfo.getGrazeModel().getValue());
				grazeTexture = null;
			}
		});
	}
	
	@Override
	public void render() {
		livesTexture = livesTexture != null ? livesTexture : context.textureLoader().updateTexture(livesTextureSource);
		bombsTexture = bombsTexture != null ? bombsTexture : context.textureLoader().updateTexture(bombsTextureSource);
		scoreTexture = scoreTexture != null ? scoreTexture : context.textureLoader().updateTexture(scoreTextureSource);
		powerTexture = powerTexture != null ? powerTexture : context.textureLoader().updateTexture(powerTextureSource);
		grazeTexture = grazeTexture != null ? grazeTexture : context.textureLoader().updateTexture(grazeTextureSource);
		
		drawFrame();
		drawLabel(livesTexture, statsPosition.x, statsPosition.y);
		drawLabel(bombsTexture, statsPosition.x, statsPosition.y + 20);
		drawLabel(scoreTexture, statsPosition.x, statsPosition.y + 40);
		drawLabel(powerTexture, statsPosition.x, statsPosition.y + 60);
		drawLabel(grazeTexture, statsPosition.x, statsPosition.y + 80);
	}

	private void drawFrame() {
		glDisable(GL_TEXTURE_2D);

		glBegin(GL_QUADS);
		glColor3f(0.5f, 0.5f, 0.5f);

		glVertex2i(0, 0);
		glVertex2i(0, context.height());
		glVertex2i(stageViewBounds.x, context.height());
		glVertex2i(stageViewBounds.x, 0);

		glVertex2i(0, 0);
		glVertex2i(0, stageViewBounds.y);
		glVertex2i(context.width(), stageViewBounds.y);
		glVertex2i(context.width(), 0);

		glVertex2i(0, stageViewBounds.y + stageViewBounds.height);
		glVertex2i(0, stageViewBounds.y + stageViewBounds.height + (context.height() - (stageViewBounds.y + stageViewBounds.height)));
		glVertex2i(context.width(), stageViewBounds.y + stageViewBounds.height + (context.height() - (stageViewBounds.y + stageViewBounds.height)));
		glVertex2i(context.width(), stageViewBounds.y + stageViewBounds.height);

		glVertex2i(stageViewBounds.x + stageViewBounds.width, 0);
		glVertex2i(stageViewBounds.x + stageViewBounds.width, context.height());
		glVertex2i(stageViewBounds.x + stageViewBounds.width + (context.width() - (stageViewBounds.x + stageViewBounds.width)), context.height());
		glVertex2i(stageViewBounds.x + stageViewBounds.width + (context.width() - (stageViewBounds.x + stageViewBounds.width)), 0);

		glColor3f(1, 1, 1);
		glEnd();

		glEnable(GL_TEXTURE_2D);
	}
	
	private void drawLabel(Texture texture, int x, int y) {
		texture.bind();
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2i(x, y);
		
		glTexCoord2f(0, texture.getHeight());
		glVertex2i(x, y + 20);
		
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2i(x + 200, y + 20);
		
		glTexCoord2f(texture.getWidth(), 0);
		glVertex2i(x + 200, y);
		
		glEnd();
	}
}
