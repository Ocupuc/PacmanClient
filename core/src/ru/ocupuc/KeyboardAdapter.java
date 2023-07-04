package ru.ocupuc;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;

public class KeyboardAdapter extends InputAdapter {

    private ClientConnection clientConnection;
    private Vector2 direction = new Vector2();

    private String currentCommand = null;

    public KeyboardAdapter() {
        try {
            clientConnection = new ClientConnection("localhost", 8189);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        String command = null;
        switch (keycode) {
            case Input.Keys.A: command = "LEFT"; break;
            case Input.Keys.D: command = "RIGHT"; break;
            case Input.Keys.W: command = "UP"; break;
            case Input.Keys.S: command = "DOWN"; break;
        }

        if (command != null) {
            try {
                String response = clientConnection.sendCommand(command);
                System.out.println("Получен ответ от сервера: " + response);
                currentCommand = response;  // сохраняем полученную команду
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // сбрасываем текущую команду, когда кнопка отпущена
        currentCommand = null;
        return false;
    }

    public Vector2 getDirection(){
        direction.set(0,0);

        // Используем сохранённую команду для задания направления
        if(currentCommand != null) {
            switch(currentCommand) {
                case "UP":
                    direction.add(0, 5);
                    break;
                case "DOWN":
                    direction.add(0, -5);
                    break;
                case "LEFT":
                    direction.add(-5, 0);
                    break;
                case "RIGHT":
                    direction.add(5, 0);
                    break;
                default:
                    // Неизвестная команда, ничего не делаем
                    break;
            }
        }

        return direction;
    }

    private Vector2 parseResponse(String response) {
        // Предположим, что эхо-сервер просто возвращает команду, которую мы отправили.
        // Мы преобразуем эту команду обратно в направление.
        switch (response) {
            case "LEFT": return direction.add(-1, 0);
            case "RIGHT": return direction.add(1, 0);
            case "UP": return direction.add(0, 1);
            case "DOWN": return direction.add(0, -1);
            default: return direction.add(0, 0); // если получен неизвестный ответ, то не двигаемся
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
