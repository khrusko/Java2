package hr.algebra.khruskoj2.server;

import hr.algebra.khruskoj2.model.PlayerAnswer;
import hr.algebra.khruskoj2.utils.JNDIHelper;

import javax.naming.NamingException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Server {

    public static final String socket_port;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static final int TOTAL_PLAYERS = 2;
    private int playersAnswered = 0;
    private static ServerSocket serverSocket;
    private static Server instance;

    static {
        try {
            socket_port = JNDIHelper.getConfigurationParameter("socket_port");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final int PORT = Integer.parseInt(socket_port);
    public static final String HOST = "localhost";

    private Server() {
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER MSG: Server started.");

            accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void nextQuestion() throws IOException {
        for (ClientHandler client : clients) {
            client.send("NEXT_QUESTION");
        }
    }

    public synchronized void playerAnswered(ClientHandler answeringClient, String serverMessage) throws IOException {
        playersAnswered++;
        if (playersAnswered == TOTAL_PLAYERS) {
            playersAnswered = 0;
            System.out.println("SERVER MSG: Both players have answered. Proceeding to the next question.");

            PlayerAnswer playerAnswer1 = clients.get(0).getLastPlayerAnswer();
            PlayerAnswer playerAnswer2 = clients.get(1).getLastPlayerAnswer();

            for (ClientHandler client : clients) {
                client.send("BOTH_ANSWERED");
            }

            for (ClientHandler client : clients) {
                client.send(serverMessage);
                client.sendAnswer(playerAnswer1);
                client.sendAnswer(playerAnswer2);
            }
        }
    }


    private void accept() {
        try {
            CountDownLatch latch = new CountDownLatch(TOTAL_PLAYERS);

            while (clients.size() < TOTAL_PLAYERS) {
                Socket clientSocket = serverSocket.accept();
                try {
                    ClientHandler clientHandler = new ClientHandler(clientSocket, latch);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            latch.await();
            startGame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        try {
            for (ClientHandler client : clients) {
                client.send("START");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        try (ServerSocket socket = new ServerSocket(PORT)) {
            return false; // port is not in use, server is not running
        } catch (IOException e) {
            return true; // port is in use, server is running
        }
    }
}