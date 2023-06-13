package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.controller.ResultScreenController;
import hr.algebra.khruskoj2.data.QuestionRepository;
import hr.algebra.khruskoj2.model.GameState;
import hr.algebra.khruskoj2.model.Question;
import hr.algebra.khruskoj2.model.UserAnswer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameScreenController {

    @FXML
    private Label btnAnswer1;

    @FXML
    private Label btnAnswer2;

    @FXML
    private Label btnAnswer3;

    @FXML
    private Label btnAnswer4;

    @FXML
    private Label lblQuestion;

    @FXML
    private Label lblQuestionNumber;

    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageTextField;

    private List<Question> questions;
    private int currentQuestionIndex;
    private String player1Name;
    private String player2Name;
    private boolean answerSelected = false;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private Pane pMainContent;
    private List<UserAnswer> userAnswers;

    private static GameScreenController instance;
    private ExecutorService executorService;

    private boolean cameFromLoadClick = false;

    public void setCameFromLoadClick(boolean val) {
        this.cameFromLoadClick = val;
    }

    public static GameScreenController getInstance() {
        return instance;
    }

    public void setPMainContent(Pane pMainContent) {
        this.pMainContent = pMainContent;
    }

    public void setPlayerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public void setAnswerSelected(boolean answerSelected) {
        this.answerSelected = answerSelected;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public boolean isAnswerSelected() {
        return answerSelected;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    @FXML
    void btnAnswer1Click(MouseEvent event) {
        if (!answerSelected) {
            answerSelected = true;
            checkAnswer(btnAnswer1.getText());
        }
    }

    @FXML
    void btnAnswer2Click(MouseEvent event) {
        if (!answerSelected) {
            answerSelected = true;
            checkAnswer(btnAnswer2.getText());
        }
    }

    @FXML
    void btnAnswer3Click(MouseEvent event) {
        if (!answerSelected) {
            answerSelected = true;
            checkAnswer(btnAnswer3.getText());
        }
    }

    @FXML
    void btnAnswer4Click(MouseEvent event) {
        if (!answerSelected) {
            answerSelected = true;
            checkAnswer(btnAnswer4.getText());
        }
    }

    private void checkAnswer(String selectedAnswer) {

        Question currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        String defaultStyle = "-fx-background-color: linear-gradient(#686868, #454545); -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-alignment: center;";
        String correctStyle = "-fx-background-color: green; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-alignment: center;";
        String incorrectStyle = "-fx-background-color: red; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-alignment: center;";
        btnAnswer1.setStyle(defaultStyle);
        btnAnswer2.setStyle(defaultStyle);
        btnAnswer3.setStyle(defaultStyle);
        btnAnswer4.setStyle(defaultStyle);

        if (correctAnswer.equals(btnAnswer1.getText())) {
            btnAnswer1.setStyle(correctStyle);
        } else if (correctAnswer.equals(btnAnswer2.getText())) {
            btnAnswer2.setStyle(correctStyle);
        } else if (correctAnswer.equals(btnAnswer3.getText())) {
            btnAnswer3.setStyle(correctStyle);
        } else {
            btnAnswer4.setStyle(correctStyle);
        }

        Question userQuestion = questions.get(currentQuestionIndex);
        userAnswers.add(new UserAnswer(userQuestion, selectedAnswer, player1Name));

        if (selectedAnswer.equals(correctAnswer)) {
            // Answer correct
            correctAnswers++;
            Platform.runLater(() -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> {
                    if (currentQuestionIndex < questions.size() - 1) {
                        showNextQuestion();
                    } else {
                        // All questions have been answered
                        showResultScreen();
                    }
                    btnAnswer1.setStyle(defaultStyle);
                    btnAnswer2.setStyle(defaultStyle);
                    btnAnswer3.setStyle(defaultStyle);
                    btnAnswer4.setStyle(defaultStyle);
                });
                delay.play();
            });
        } else {
            // Answer wrong
            wrongAnswers++;
            Platform.runLater(() -> {
                Label selectedLabel;
                if (selectedAnswer.equals(btnAnswer1.getText())) {
                    selectedLabel = btnAnswer1;
                } else if (selectedAnswer.equals(btnAnswer2.getText())) {
                    selectedLabel = btnAnswer2;
                } else if (selectedAnswer.equals(btnAnswer3.getText())) {
                    selectedLabel = btnAnswer3;
                } else {
                    selectedLabel = btnAnswer4;
                }
                selectedLabel.setStyle(incorrectStyle);

                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> {
                    if (currentQuestionIndex < questions.size() - 1) {
                        showNextQuestion();
                    } else {
                        // All questions have been answered
                        showResultScreen();
                    }
                    btnAnswer1.setStyle(defaultStyle);
                    btnAnswer2.setStyle(defaultStyle);
                    btnAnswer3.setStyle(defaultStyle);
                    btnAnswer4.setStyle(defaultStyle);
                });
                delay.play();
            });
        }
    }


    private void showNextQuestion() {
        currentQuestionIndex++;
        showCurrentQuestion();
        answerSelected = false;
    }

    public void showCurrentQuestion() {

        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            Platform.runLater(() -> {
                lblQuestion.setText(currentQuestion.getQuestion());
                lblQuestionNumber.setText("QUESTION " + (currentQuestionIndex + 1) + "/15");

                List<String> answerOptions = new ArrayList<>(currentQuestion.getWrongAnswers()); // Create a new list for answer options
                answerOptions.add(currentQuestion.getCorrectAnswer());
                Collections.shuffle(answerOptions);

                btnAnswer1.setText(answerOptions.get(0));
                btnAnswer2.setText(answerOptions.get(1));
                btnAnswer3.setText(answerOptions.get(2));
                btnAnswer4.setText(answerOptions.get(3));
            });
        } else {
            // All questions have been answered
            Platform.runLater(this::showResultScreen);
        }
    }

    public void showResultScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/resultScreen.fxml"));
            Parent root1 = loader.load();

            ResultScreenController resultScreenController = loader.getController();

            resultScreenController.setResultData(correctAnswers, wrongAnswers, player1Name, player2Name, userAnswers);

            Platform.runLater(() -> {
                pMainContent.getChildren().clear();
                pMainContent.getChildren().add(root1);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void messageTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String message = messageTextField.getText();

            Platform.runLater(() -> {
                // TODO: Differentiate between players and display their name and message
                chatListView.getItems().add(player1Name + ": " + message);
            });

            messageTextField.clear();
        }
    }

    public void loadGameState(Pane pMainContent, Parent root) {
        chatListView.getItems().clear();
        pMainContent.getChildren().clear();
        try (FileInputStream fileInputStream = new FileInputStream("gameSaveState.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            // Read the serialized GameState object
            GameState gameState = (GameState) objectInputStream.readObject();

            // Update the current question index and user answers
            currentQuestionIndex = gameState.getCurrentQuestionIndex();
            userAnswers = gameState.getUserAnswers();
            List<UserAnswer> temp = gameState.getUserAnswers();
            UserAnswer firstUserAnswerTemp = temp.get(0);
            player1Name = firstUserAnswerTemp.getPlayerName();

            wrongAnswers = gameState.getNumberOfWrongAnswersUser(player1Name);
            correctAnswers = gameState.getNumberOfCorrectAnswersUser(player1Name);

            // Reset the answerSelected flag because if not you wont be able to click anything
            answerSelected = false;

            // Show the current question
            showCurrentQuestion();

            // Set the updated gameScreen.fxml into pMainContent
            pMainContent.getChildren().add(root);

            System.out.println("Game state loaded.");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: gameSaveState.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }






    private void loadQuestions() {

        QuestionRepository questionRepository = new QuestionRepository();
        try {
            questions = questionRepository.getAllQuestions();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

//    public synchronized void loadQuestionsAsync() {
//        Task<List<Question>> task = new Task<List<Question>>() {
//            @Override
//            protected List<Question> call() throws Exception {
//                QuestionRepository questionRepository = new QuestionRepository();
//                return questionRepository.getAllQuestions();
//            }
//        };
//
//        task.setOnSucceeded(event -> {
//            questions = task.getValue();
//            //Collections.shuffle(questions); // Shuffle the questions randomly
//            currentQuestionIndex = 0;
//            showCurrentQuestion();
//        });
//
//        task.setOnFailed(event -> {
//            Throwable exception = task.getException();
//            // Handle the exception
//            exception.printStackTrace();
//        });
//
//        executorService.submit(task);
//    }

    @FXML
    public void initialize() {
        instance = this;
        chatListView.getItems().clear();
        executorService = Executors.newSingleThreadExecutor();
            loadQuestions();
            showCurrentQuestion();
        if (userAnswers == null) {
            userAnswers = new ArrayList<>();
        }
    }
    public void shutdownExecutorService () {
        executorService.shutdownNow();
    }
}