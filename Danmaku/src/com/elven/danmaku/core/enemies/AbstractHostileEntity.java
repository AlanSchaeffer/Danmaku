package com.elven.danmaku.core.enemies;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import com.elven.danmaku.core.elements.AbstractPlaceableGameElement;
import com.elven.danmaku.core.elements.controller.Controller;
import com.elven.danmaku.core.elements.disposal.DisposalRule;
import com.elven.danmaku.core.elements.disposal.OutsideStageDisposalRule;
import com.elven.danmaku.core.elements.hitbox.Hitbox;
import com.elven.danmaku.core.elements.hitbox.NullHitbox;
import com.elven.danmaku.core.elements.view.Sprite;
import com.elven.danmaku.core.listeners.EnemyDestroyedListener;
import com.elven.danmaku.core.player.controller.CountdownController;
import com.elven.danmaku.core.system.Vector2D;

public abstract class AbstractHostileEntity implements AbstractPlaceableGameElement {

	private final Sprite sprite;
	private final Controller controller;
	private final Vector2D position;
	private Hitbox hitbox = NullHitbox.instance;
	private Hitbox targetHitbox = NullHitbox.instance;
	private DisposalRule disposalRule = new OutsideStageDisposalRule();

	private CountdownController invincibilityController = new CountdownController();

	private final List<EnemyDestroyedListener> listeners = new ArrayList<>();

	public AbstractHostileEntity(Sprite sprite, Controller controller) {
		this(sprite, controller, new Vector2D());
	}

	public AbstractHostileEntity(Sprite sprite, Controller controller, Vector2D position) {
		this.sprite = sprite;
		this.controller = controller;
		this.position = position;
	}

	@Override
	public final Vector2D getPosition() {
		return position;
	}

	@Override
	public final Sprite getSprite() {
		return sprite;
	}

	public final void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}

	public final Hitbox getHitbox() {
		return hitbox;
	}

	public void setTargetHitbox(Hitbox targetHitbox) {
		this.targetHitbox = targetHitbox;
	}

	public Hitbox getTargetHitbox() {
		return targetHitbox;
	}

	protected final Controller getController() {
		return controller;
	}

	public abstract int getMaxHitPoints();

	public abstract int getCurrentHitPoints();

	public abstract long getScoreWorth();

	public abstract void damage(int damage);

	@Override
	public void render() {
		glPushMatrix();
		glTranslated(position.getX(), position.getY(), 0);
		
		sprite.render();
		
		glPopMatrix();
	}

	@Override
	public void tick() {
		invincibilityController.tick();
		controller.tick(this);
	}

	public final boolean hitCheck(Hitbox target) {
		return hitbox.hitCheck(target);
	}

	@Override
	public final DisposalRule getDisposalRule() {
		return disposalRule;
	}

	public final void setDisposalRule(DisposalRule disposalRule) {
		this.disposalRule = disposalRule;
	}

	public CountdownController getInvincibilityController() {
		return invincibilityController;
	}

	public final void addEnemyDestroyedListener(EnemyDestroyedListener listener) {
		listeners.add(listener);
	}

	public final void removeEnemyDestroyedListener(EnemyDestroyedListener listener) {
		listeners.remove(listener);
	}

	protected final void fireEnemyDestroyed() {
		for (EnemyDestroyedListener listener : listeners) {
			listener.enemyDestroyed(this);
		}
	}
	
	@Override
	public void destroy() {
	}
}