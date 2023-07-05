package ru.ocupuc;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientConnection(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public String sendCommand(String command) throws IOException {
        output.println(command);
        return input.readLine();
    }

    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }

    public int[][] getLevelData() throws IOException {
        String levelDataJson = readDataFromServer(); // получаем данные от сервера в формате JSON
        Gson gson = new Gson();
        int[][] levelData = gson.fromJson(levelDataJson, int[][].class); // десериализуем данные в двумерный массив
        return levelData;
    }

    // Вспомогательный метод для чтения данных от сервера
    private String readDataFromServer() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = input.readLine()) != null && !line.equals("END")) {
            sb.append(line);
        }

        return sb.toString();
    }

    public void printLevelData(int[][] levelData) {
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length; j++) {
                System.out.print(levelData[i][j] + " ");
            }
            System.out.println();
        }
    }
}

