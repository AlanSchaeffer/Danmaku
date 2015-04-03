package com.elven.danmaku.core.bullets;

import static org.lwjgl.opengl.GL11.*;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.disposal.DisposalRule;
import com.elven.danmaku.core.elements.disposal.OutsideStageDisposalRule;
import com.elven.danmaku.core.elements.hitbox.Hitbox;
import com.elven.danmaku.core.elements.hitbox.NullHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.system.Vector2D;

public class Bullet implements AbstractPlaceableGameElement {

	private final Sprite sprite;
	private final Controller controller;
	private final Vector2D position;
	private Hitbox hitbox = NullHitbox.instance;
	private DisposalRule disposalRule = new OutsideStageDisposalRule();

	public Bullet(Sprite sprite, Controller controller) {
		this(sprite, controller, new Vector2D());
	}

	public Bullet(Sprite sprite, Controller controller, Vector2D position) {
		this.sprite = sprite;
		this.controller = controller;
		this.position = position;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void render() {
		glPushMatrix();
		glTranslated(position.getX(), position.getY(), 0);
		
		sprite.render();
		
		glPopMatrix();
	}

	@Override
	public void tick() {
		controller.tick(this);
	}

	public boolean hitCheck(Hitbox target) {
		return hitbox.hitCheck(target);
	}

	@Override
	public DisposalRule getDisposalRule() {
		return disposalRule;
	}

	public void setDisposalRule(DisposalRule disposalRule) {
		this.disposalRule = disposalRule;
	}
	
	@Override
	public void destroy() {
	}
}
