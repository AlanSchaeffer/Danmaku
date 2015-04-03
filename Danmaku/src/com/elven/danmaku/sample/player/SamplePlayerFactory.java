package com.elven.danmaku.sample.player;

import com.elven.danmaku.core.elements.hitbox.CircleHitboxFactory;
import com.elven.danmaku.core.elements.view.TextureSprite;
import com.elven.danmaku.core.gameinfo.GameInfo;
import com.elven.danmaku.core.graphics.texture.ResourceTextureImageSource;
import com.elven.danmaku.core.graphics.texture.TextureLoader;
import com.elven.danmaku.core.player.DefaultPlayer;
import com.elven.danmaku.core.player.DefaultPlayerModel;
import com.elven.danmaku.core.player.Player;

public class SamplePlayerFactory {

	private TextureLoader loader;

	public SamplePlayerFactory(TextureLoader loader) {
		this.loader = loader;
	}

	public Player createPlayer(GameInfo gameInfo) {
		DefaultPlayer player = new DefaultPlayer();
		DefaultPlayerModel model = (DefaultPlayerModel) player.getModel();
		
		player.setSprite(new TextureSprite(loader.getTexture(new ResourceTextureImageSource("resources/char.png"))));
		player.getPosition().setX(200.0);
		player.getPosition().setY(550.0);

		SpreadSealsPlayerShot shotFactory = new SpreadSealsPlayerShot(loader);
		shotFactory.setHitboxFactory(new CircleHitboxFactory(15.0));
		shotFactory.setWait(3);
		shotFactory.setPower(100);
		model.setShotFactory(shotFactory);

		model.setBomb(new SampleBomb(gameInfo, loader));

		return player;
	}
}
