package com.elven.danmaku.core.items;

import static org.lwjgl.opengl.GL11.*;

import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.disposal.DisposalRule;
import com.elven.danmaku.core.elements.disposal.OutsideStageDisposalRule;
import com.elven.danmaku.core.elements.hitbox.Hitbox;
import com.elven.danmaku.core.elements.hitbox.NullHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.system.Vector2D;

public abstract class AbstractCollectableItem implements CollectableItem {

	private final Controller controller;
	private final Sprite sprite;
	private final Vector2D position;
	private DisposalRule disposalRule = new OutsideStageDisposalRule(); 
	private Hitbox hitbox;

	public AbstractCollectableItem(Controller controller, Sprite sprite) {
		this(controller, sprite, new Vector2D());
	}
	
	public AbstractCollectableItem(Controller controller, Sprite sprite, Vector2D position) {
		this.controller = controller;
		this.sprite = sprite;
		this.position = position;
		
		hitbox = NullHitbox.instance;
	}
	
	@Override
	public void tick() {
		controller.tick(this);
	}
	
	@Override
	public void render() {
		glPushMatrix();
		glTranslated(position.getX(), position.getY(), 0);
		
		sprite.render();
		
		glPopMatrix();
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}
	
	@Override
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
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
