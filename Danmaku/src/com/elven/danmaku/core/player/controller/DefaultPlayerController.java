package com.elven.danmaku.core.player.controller;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.player.Player;
import com.elven.danmaku.core.stage.StageController;

public class DefaultPlayerController implements PlayerController {

	private final DefaultPlayerInput input;
	private StageController currentStage;
	
	private CountdownController invincibilityController = new CountdownController();
	private CountdownController inputDisabledController = new CountdownController();

	public DefaultPlayerController() {
		input = new DefaultPlayerInput();
	}

	@Override
	public BasicPlayerInput getInputManager() {
		return input;
	}

	@Override
	public void setCurrentStage(StageController stage) {
		this.currentStage = stage;
	}

	@Override
	public void tick(AbstractPlaceableGameElement element) {
		invincibilityController.tick();
		inputDisabledController.tick();
		Player player = (Player) element;
		
		if(player.getModel().getBomb() != null) {
			player.getModel().getBomb().tick();
		}
		
		if(!inputDisabledController.isActive()) {
			double speed = !input.isFocused() ? player.getModel().getMovementSpeed() : player.getModel().getFocusedSpeed();
			int forceX = 0;
			int forceY = 0;
			
			if (input.isMovingUp()) {
				forceY += 1;
			} else if (input.isMovingDown()) {
				forceY -= 1;
			}
			
			if (input.isMovingLeft()) {
				forceX += 1;
			} else if (input.isMovingRight()) {
				forceX -= 1;
			}
			
			if (forceX != 0 || forceY != 0) {
				int angleX = forceX != 0 ? forceX * 90 + 90 : 0;
				int angleY = forceY != 0 ? forceY * 90 + 180 : 0;
				int angle = angleX + angleY;
				
				if (forceX != 0 && forceY != 0) {
					if (angleY == 270 && angleX == 0) {
						angle = 315;
					} else {
						angle /= 2;
					}
				}
				
				double movementX = Math.cos(angle * Math.PI / 180) * speed;
				double movementY = Math.sin(angle * Math.PI / 180) * speed;
				player.getPosition().translate(movementX, movementY);
				
				if (currentStage != null) {
					player.getPosition().constrain(currentStage.getView().getPlayableArea());
				}
			}
			
			if(player.getModel().getShotFactory() != null) {
				if(input.isShooting()) {
					player.getModel().getShotFactory().shootBurst(player, currentStage);
				} else {
					player.getModel().getShotFactory().stopBurst();
				}
			}
			
			if(player.getModel().getBomb() != null) {
				if(input.isBombing() && player.getModel().getBomb().canBomb()) {
					player.getModel().getBomb().bomb(player, currentStage);
				}
			}
		}
	}
	
	@Override
	public CountdownController getInputDisabledController() {
		return inputDisabledController;
	}
	
	@Override
	public CountdownController getInvincibilityController() {
		return invincibilityController;
	}
}
