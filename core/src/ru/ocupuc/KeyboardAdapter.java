package ru.ocupuc;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;

public class KeyboardAdapter extends InputAdapter {

    private ClientConnection clientConnection;
    private Vector2 direction = new Vector2();
    private boolean up, down, left, right;

    public KeyboardAdapter() {
        try {
            clientConnection = new ClientConnection("localhost", 8189);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A: left = true; break;
            case Input.Keys.D: right = true; break;
            case Input.Keys.W: up = true; break;
            case Input.Keys.S: down = true; break;
        }

        updateCommand();

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A: left = false; break;
            case Input.Keys.D: right = false; break;
            case Input.Keys.W: up = false; break;
            case Input.Keys.S: down = false; break;
        }

        updateCommand();

        return false;
    }

    private void updateCommand() {
        String command = null;

        if (up) {
            command = "UP";
        } else if (down) {
            command = "DOWN";
        } else if (left) {
            command = "LEFT";
        } else if (right) {
            command = "RIGHT";
        }

        if (command == null) {
            command = "STOP";
        }

        try {
            String response = clientConnection.sendCommand(command);
            direction.set(parseResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector2 getDirection(){
        return direction;
    }

    private Vector2 parseResponse(String response) {
        switch (response) {
            case "LEFT": return direction.set(-1, 0);
            case "RIGHT": return direction.set(1, 0);
            case "UP": return direction.set(0, 1);
            case "DOWN": return direction.set(0, -1);
            case "STOP": return direction.set(0, 0);
            default: return direction.set(0, 0);
        }
    }

    public void dispose() {
        try {
            clientConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
