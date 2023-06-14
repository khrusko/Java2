package hr.algebra.khruskoj2.server;

import hr.algebra.khruskoj2.model.GameState;
import hr.algebra.khruskoj2.utils.JNDIHelper;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final String socket_port;

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

    public Server() throws NamingException, IOException {
    }

    public static void main(String[] args) {
        accept();
    }

    private static void accept() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> process(clientSocket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

            GameState state = (GameState) ois.readObject();

            System.out.println("In Server: " + state);


            oos.writeObject(state);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
