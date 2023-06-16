package hr.algebra.khruskoj2.server;

import hr.algebra.khruskoj2.model.PlayerAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private boolean bothAnswered = false;
    private int questionNumber = 0;
    public boolean isBothAnswered() {
        return bothAnswered;
    }
    public void setBothAnswered(boolean bothAnswered) {
        this.bothAnswered = bothAnswered;
    }
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public interface StartGameCallback {
        void startGame();
    }

    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Client() {
        clientSocket = null;
        oos = null;
        ois = null;
    }

    public void connectToServer(StartGameCallback callback) throws IOException, ClassNotFoundException {
        clientSocket = new Socket(Server.HOST, Server.PORT);
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
        oos.flush();
        process(callback);
    }

    public void process(StartGameCallback callback) throws IOException, ClassNotFoundException {
        oos.writeObject("READY");
        oos.flush();

        while (true) {
            String serverResponse = (String) ois.readObject();
            serverResponse = serverResponse.trim();
            System.out.println("Server response: " + serverResponse);

            if (serverResponse.equals("WAIT")) {
                System.out.println("Waiting for other players to connect...");
            } else if (serverResponse.equals("START")) {
                System.out.println("All players connected. Starting the game...");
                callback.startGame();
                break;
            }
        }

        // Additional
        while (true) {
            Object serverMessage = ois.readObject();
            if (serverMessage.equals("BOTH_ANSWERED")) {
                System.out.println("Both players have answered. Proceeding to the next question");
                bothAnswered = true;
            } else if (serverMessage instanceof PlayerAnswer) {
                PlayerAnswer playerAnswer = (PlayerAnswer) serverMessage;
                System.out.println(questionNumber + ". Question | " + playerAnswer.getPlayerName() + " answered: " + playerAnswer.getSelectedAnswer());
            }
        }
    }

    public void sendPlayerAnswer(String playerName, String selectedAnswer) throws IOException {
        PlayerAnswer playerAnswer = new PlayerAnswer(playerName, selectedAnswer);
        oos.writeObject(playerAnswer);
        oos.flush();
    }

    public void closeConnection() {
        try {
            if (oos != null) oos.close();
            if (ois != null) ois.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}