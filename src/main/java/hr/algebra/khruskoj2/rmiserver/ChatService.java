package hr.algebra.khruskoj2.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {
    void sendMessage(String message, String user) throws RemoteException;

    void clearChatHistory() throws RemoteException;

    List<String> getChatHistory() throws RemoteException;
}
