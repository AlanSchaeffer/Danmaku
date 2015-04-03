package com.elven.danmaku.core.system;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.elven.danmaku.core.elements.UpdateableGameElement;
import com.elven.danmaku.core.frame.GameView;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.input.InputReceiver;
import com.elven.danmaku.core.stage.StageController;
import com.elven.danmaku.core.stage.StageView;

public class Game {

	private final Dimension size;
	private final Queue<UpdateableGameElement> tickListeners = new ConcurrentLinkedQueue<>();
	private final TextureLoader loader = new TextureLoader();
	private final GameContextImpl context = new GameContextImpl();

	private int fps;

	private GameView currentView;
	private InputReceiver currentInputReceiver;

	public Game(Dimension size) {
		this(size, 60);
	}

	public Game(Dimension size, int fps) {
		this.size = size;
		this.fps = fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public void createDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(size.width, size.height));
		Display.create();
	}

	public void start() {
		initGL();

		while (!Display.isCloseRequested()) {
			propagateInputEvents();
			tickElements();
			renderGL();

			Display.update();
			Display.sync(fps);
		}

		if (currentView != null) {
			currentView.destroy();
		}

		cleanupGL();
		Display.destroy();

	}

	private void initGL() {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_LIGHTING);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, size.width, size.height, 0, 1, -1);
		glMatrixMode(GL11.GL_MODELVIEW);
	}

	private void propagateInputEvents() {
		if (currentInputReceiver != null) {
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					currentInputReceiver.pressed(Keyboard.getEventKey());
				} else {
					currentInputReceiver.released(Keyboard.getEventKey());

				}
			}
		}
	}

	private void renderGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		if (currentView != null) {
			currentView.render();
		}
	}

	private void cleanupGL() {
		loader.cleanup();
	}

	private void tickElements() {
		for (UpdateableGameElement element : tickListeners) {
			element.tick();
		}
	}

	public void changeView(GameView view) {
		if (currentView != null) {
			currentView.destroy();
		}

		currentView = view;
		context.viewChanged(view);
	}

	public void registerTickListener(UpdateableGameElement listener) {
		tickListeners.add(listener);
	}

	public void unregisterTickListener(UpdateableGameElement listener) {
		tickListeners.remove(listener);
	}

	public void setCurrentInputReceiver(InputReceiver receiver) {
		this.currentInputReceiver = receiver;
	}

	public GameContext context() {
		return context;
	}

	public int width() {
		return size.width;
	}

	public int height() {
		return size.height;
	}

	private class GameContextImpl implements GameContext {

		private StageController currentStage;

		@Override
		public TextureLoader textureLoader() {
			return loader;
		}

		@Override
		public StageController currentStage() {
			return currentStage;
		}

		@Override
		public int width() {
			return Game.this.width();
		}

		@Override
		public int height() {
			return Game.this.height();
		}

		@Override
		public void setFps(int fps) {
			Game.this.setFps(fps);
		}

		@Override
		public void setCurrentInputReceiver(InputReceiver receiver) {
			Game.this.setCurrentInputReceiver(receiver);
		}

		@Override
		public InputReceiver getCurrentInputReceiver() {
			return currentInputReceiver;
		}
		
		@Override
		public void changeView(GameView view) {
			Game.this.changeView(view);
		}

		void viewChanged(GameView view) {
			if (view instanceof StageView) {
				currentStage = ((StageView) view).getStage();
			} else {
				currentStage = null;
			}
		}
	}
}
