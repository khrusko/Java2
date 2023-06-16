package hr.algebra.khruskoj2.server;

import hr.algebra.khruskoj2.model.PlayerAnswer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final CountDownLatch latch;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean running;
    private PlayerAnswer lastPlayerAnswer = null;

    public PlayerAnswer getLastPlayerAnswer() {
        return lastPlayerAnswer;
    }

    public ClientHandler(Socket socket, CountDownLatch latch) throws IOException {
        this.socket = socket;
        this.latch = latch;
        // For ClientHandler
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.running = true;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Object clientMessage = null;
                try {
                    clientMessage = ois.readObject();
                } catch (EOFException e) {
                    System.err.println("End of file reached.");
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }

                if (clientMessage instanceof String) {
                    System.out.println("SERVER MSG: Received message: " + clientMessage);
                    if (clientMessage.equals("READY")) {
                        send("WAIT");
                        latch.countDown();
                        System.out.println("Latch" + latch);
                    } else if (clientMessage.equals("NEXT_QUESTION")) {
                        System.out.println("SERVER MSG: Both players have answered. Proceeding to the next question.");
                        Server.getInstance().nextQuestion();
                    } else if (clientMessage.equals("BOTH_ANSWERED")) {
                        System.out.println("SERVER MSG: Both players have answered. Proceeding to the next question.");
                    }
                } else if (clientMessage instanceof PlayerAnswer) {
                    PlayerAnswer playerAnswer = (PlayerAnswer) clientMessage;
                    lastPlayerAnswer = playerAnswer;

                    String serverMessage = playerAnswer.getPlayerName() + " answered: " + playerAnswer.getSelectedAnswer();
                    Server.getInstance().playerAnswered(this, serverMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        running = false;
        ois.close();
        oos.close();
        socket.close(); // not clientSocket
    }

    public void sendAnswer(PlayerAnswer playerAnswer) throws IOException {
        oos.writeObject(playerAnswer);
        oos.flush();
    }

    public void send(String message) throws IOException {
        oos.writeObject(message);
        oos.flush();
    }
}