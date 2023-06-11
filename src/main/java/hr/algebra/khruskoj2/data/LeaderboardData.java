package hr.algebra.khruskoj2.data;

import hr.algebra.khruskoj2.controller.LeaderboardsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.CompletableFuture;

public class LeaderboardData {

    private static LeaderboardData instance = null;

    private final ObservableList<LeaderboardsController.LeaderboardEntry> leaderboardEntries;

    private LeaderboardData() {
        this.leaderboardEntries = FXCollections.observableArrayList();
    }

    public static synchronized LeaderboardData getInstance() {
        if (instance == null) {
            instance = new LeaderboardData();
        }
        return instance;
    }

    public ObservableList<LeaderboardsController.LeaderboardEntry> getLeaderboardEntries() {
        return this.leaderboardEntries;
    }

    public CompletableFuture<Void> addLeaderboardEntry(LeaderboardsController.LeaderboardEntry entry) {
        return CompletableFuture.runAsync(() -> leaderboardEntries.add(entry));
    }
}
