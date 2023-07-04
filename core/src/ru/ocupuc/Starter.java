package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;

public class Starter extends ApplicationAdapter {
	SpriteBatch batch;
	private PacmanClient pacman;
	private KeyboardAdapter inputProcessor = new KeyboardAdapter();
	private ClientConnection clientConnection;

	@Override
	public void create() {
		Gdx.input.setInputProcessor(inputProcessor);
		batch = new SpriteBatch();

		pacman = new PacmanClient(100, 200);

		try {
			clientConnection = new ClientConnection("localhost", 8189);
			System.out.println("Пытаемся получить данные уровня...");
			int[][] levelData = clientConnection.getLevelData();
			System.out.println("Данные уровня получены.");
			clientConnection.printLevelData(levelData);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
