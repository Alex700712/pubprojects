import connections.ClientThread;
import entities.*;
import service.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {
    private static final int PORT_NUMBER = 5555;
    private static ServerSocket serverSocket;
    private static ClientThread clientHandler;
    private static Thread thread;
    private static List<Socket> currentSockets = new ArrayList();

    public Main() {
    }

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(5555);

        while (true) {

            for (Socket socket : currentSockets) {
                if (socket.isClosed()) {
                    currentSockets.remove(socket);
                } else {
                    String socketInfo = "Клиент " + socket.getInetAddress() + ":" + socket.getPort() + " подключен.";
                    System.out.println(socketInfo);
                }
            }
            Socket socket = serverSocket.accept();
            currentSockets.add(socket);
            clientHandler = new ClientThread(socket);
            thread = new Thread(clientHandler);
            thread.start();
            System.out.flush();
        }
    }
}
