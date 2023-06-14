package hr.algebra.khruskoj2.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(Server.HOST, Server.PORT)) {

            process(clientSocket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void process(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

        //prvi spojeni = host -flag
        //dok se drugi spoji cekanje


        //oos.writeObject(gameState);

        System.out.println("In client: " + ois.readObject());
    }
}