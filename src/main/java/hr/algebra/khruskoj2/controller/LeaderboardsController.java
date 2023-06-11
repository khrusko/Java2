package hr.algebra.khruskoj2.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LeaderboardsController {

    @FXML
    private TableView<LeaderboardEntry> tvLeaderboard;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> rankColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> playerColumn;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> scoreColumn;

    public void initialize() {
        // Set up the table columns
        rankColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(tvLeaderboard.getItems().indexOf(cellData.getValue()) + 1).asObject());
        playerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayer()));
        scoreColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());

        // Add sample data to the table (replace with your actual leaderboard data)
        tvLeaderboard.getItems().add(new LeaderboardEntry("Player 1", 100));
        tvLeaderboard.getItems().add(new LeaderboardEntry("Player 2", 90));
        tvLeaderboard.getItems().add(new LeaderboardEntry("Player 3", 80));
    }

    // Inner class representing a leaderboard entry
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
}
