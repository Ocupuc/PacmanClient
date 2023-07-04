package ru.ocupuc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class PacmanClient {
    private final Vector2 position = new Vector2();

    private final Texture texture;

    public PacmanClient(float x, float y) {
        texture = new Texture("pacman11.png");
        position.set(x, y);
    }

    public void render(Batch batch) {

        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(Vector2 direction) {
        System.out.println("Moving to: " + direction);
        position.add(direction);
    }
}
