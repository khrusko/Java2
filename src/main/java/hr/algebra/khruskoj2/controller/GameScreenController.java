package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.data.QuestionRepository;
import hr.algebra.khruskoj2.model.Question;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

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

    private static GameScreenController instance;

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

        if (selectedAnswer.equals(correctAnswer)) {
            //Answer correct
            correctAnswers++;
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
        } else {
            //Answer wrong
            wrongAnswers++;
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
        }
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        showCurrentQuestion();
        answerSelected = false;
    }

    private void showCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            lblQuestion.setText(currentQuestion.getQuestion());
            lblQuestionNumber.setText("QUESTION " + (currentQuestionIndex + 1) + "/15");

            List<String> answerOptions = new ArrayList<>(currentQuestion.getWrongAnswers()); // Create a new list for answer options
            answerOptions.add(currentQuestion.getCorrectAnswer());
            Collections.shuffle(answerOptions);

            btnAnswer1.setText(answerOptions.get(0));
            btnAnswer2.setText(answerOptions.get(1));
            btnAnswer3.setText(answerOptions.get(2));
            btnAnswer4.setText(answerOptions.get(3));
        } else {
            // All questions have been answered
            showResultScreen();
        }
    }

    public void showResultScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/resultScreen.fxml"));
            Parent root = loader.load();

            // Get the controller of the result screen
            ResultScreenController resultScreenController = loader.getController();

            // Perform any necessary operations or pass data to the result screen controller
            // For example, you can call a method on the result screen controller
            resultScreenController.setResultData(correctAnswers, wrongAnswers);

            // Set the loaded pane as the content of pMainContent
            pMainContent.getChildren().clear();
            pMainContent.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void messageTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String message = messageTextField.getText();
            // TODO: Differentiate between players and display their name and message
            chatListView.getItems().add(player1Name+": " + message);

            messageTextField.clear();
        }
    }


    public void loadQuestions() {
        QuestionRepository questionRepository = new QuestionRepository();
        questions = questionRepository.getAllQuestions();
        Collections.shuffle(questions); // Shuffle the questions randomly
        currentQuestionIndex = 0;
        showCurrentQuestion();
    }

    @FXML
    public void initialize() {
        instance = this;
        loadQuestions();
    }
}
