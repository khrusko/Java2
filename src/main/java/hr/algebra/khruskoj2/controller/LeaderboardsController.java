package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.data.LeaderboardData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardsController {

    public static class LeaderboardEntry {
        private final String player;
        private final int score;

        public LeaderboardEntry(String player, int score) {
            this.player = player;
            this.score = score;
        }

        public String getPlayer() {
            return player;
        }

        public int getScore() {
            return score;
        }
    }

    @FXML
    private TableView<LeaderboardEntry> tvLeaderboard;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> rankColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> playerColumn;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> scoreColumn;

    private ObservableList<LeaderboardEntry> leaderboardEntries;

    public void initialize() {
        Platform.runLater(() -> {
            System.out.println("Entered leaderboards initialize");

            ObservableList<LeaderboardEntry> allEntries = LeaderboardData.getInstance().getLeaderboardEntries();

            // Filter the entries to only contain the highest scoring entry per player
            Map<String, LeaderboardEntry> highestScoringEntriesMap = new HashMap<>();
            for (LeaderboardEntry entry : allEntries) {
                highestScoringEntriesMap.compute(entry.getPlayer(), (player, currentEntry) ->
                        currentEntry == null || entry.getScore() > currentEntry.getScore() ? entry : currentEntry
                );
            }

            // Create a new list from the map values
            leaderboardEntries = FXCollections.observableArrayList(highestScoringEntriesMap.values());

            // Sort the leaderboard entries in descending order by score
            leaderboardEntries.sort((entry1, entry2) -> Integer.compare(entry2.getScore(), entry1.getScore()));

            // Set up the table columns
            rankColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(leaderboardEntries.indexOf(cellData.getValue()) + 1).asObject());
            playerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayer()));
            scoreColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());

            // Set the data in the table
            tvLeaderboard.setItems(leaderboardEntries);
        });
    }
}
