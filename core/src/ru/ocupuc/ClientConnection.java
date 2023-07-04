package ru.ocupuc;

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
}
