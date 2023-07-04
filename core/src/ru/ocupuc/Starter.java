package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Starter extends ApplicationAdapter {
	SpriteBatch batch;
	private PacmanClient pacman;
	private KeyboardAdapter inputProcessor = new KeyboardAdapter();

	@Override
	public void create() {
		Gdx.input.setInputProcessor(inputProcessor);
		batch = new SpriteBatch();

		pacman = new PacmanClient(100, 200);
	}

	@Override
	public void render() {
		pacman.moveTo(inputProcessor.getDirection());

		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		pacman.render(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		pacman.dispose();
	}
}
