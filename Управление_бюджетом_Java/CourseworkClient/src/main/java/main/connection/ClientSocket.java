package main.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private static final ClientSocket SINGLE_INSTANCE = new ClientSocket() ;
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientSocket() {}

    public static ClientSocket getInstance() {
        return SINGLE_INSTANCE;
    }

    public void setConnection() {
        try {
            socket = new Socket("127.0.0.1", 5555);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            socket = null;
            System.out.println("Ошибка " + e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getInStream() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
}

