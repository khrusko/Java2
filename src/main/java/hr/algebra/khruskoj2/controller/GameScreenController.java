package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.data.QuestionRepository;
import hr.algebra.khruskoj2.model.GameState;
import hr.algebra.khruskoj2.model.Question;
import hr.algebra.khruskoj2.model.UserAnswer;
import hr.algebra.khruskoj2.rmiserver.ChatService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

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
    private TextArea chatListView;

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
    public static final String replayFilePath = "replayFilePath.xml";
    private static GameScreenController instance;
    private ExecutorService executorService;
    ChatService stub = null;
    private ScheduledExecutorService chatRefreshExecutor;
    private ScheduledFuture<?> chatRefreshTask;

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

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
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
        saveUserAnswersForReplay(userAnswers);
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

    private void saveUserAnswersForReplay(List<UserAnswer> userAnswers) {
        GameReplayController gameReplayController = GameReplayController.getInstance();
        if (gameReplayController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/khruskoj2/gameReplay.fxml"));
                Parent root = loader.load();
                gameReplayController = loader.getController();
                gameReplayController.setUserAnswers(userAnswers);
                gameReplayController.setRoot(root);
                saveToXML(userAnswers, replayFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            gameReplayController.setUserAnswers(userAnswers);
            saveToXML(userAnswers, replayFilePath);
        }
    }

    public void saveToXML(List<UserAnswer> userAnswers, String filePath) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document xmlDocument = documentBuilder.newDocument();

            Element rootElement = xmlDocument.createElement("UserAnswers");
            xmlDocument.appendChild(rootElement);

            for (UserAnswer userAnswer : userAnswers) {
                Element userAnswerElement = xmlDocument.createElement("UserAnswer");

                Element questionTextElement = xmlDocument.createElement("QuestionText");
                Node questionTextNode = xmlDocument.createTextNode(userAnswer.getQuestionText());
                questionTextElement.appendChild(questionTextNode);
                userAnswerElement.appendChild(questionTextElement);

                Element correctAnswerElement = xmlDocument.createElement("CorrectAnswer");
                Node correctAnswerTextNode = xmlDocument.createTextNode(userAnswer.getCorrectAnswer());
                correctAnswerElement.appendChild(correctAnswerTextNode);
                userAnswerElement.appendChild(correctAnswerElement);

                Element selectedAnswerElement = xmlDocument.createElement("SelectedAnswer");
                Node selectedAnswerTextNode = xmlDocument.createTextNode(userAnswer.getSelectedAnswer());
                selectedAnswerElement.appendChild(selectedAnswerTextNode);
                userAnswerElement.appendChild(selectedAnswerElement);

                Element wrongAnswersElement = xmlDocument.createElement("WrongAnswers");
                for (String wrongAnswer : userAnswer.getWrongAnswers()) {
                    Element wrongAnswerElement = xmlDocument.createElement("WrongAnswer");
                    Node wrongAnswerTextNode = xmlDocument.createTextNode(wrongAnswer);
                    wrongAnswerElement.appendChild(wrongAnswerTextNode);
                    wrongAnswersElement.appendChild(wrongAnswerElement);
                }
                userAnswerElement.appendChild(wrongAnswersElement);

                rootElement.appendChild(userAnswerElement);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source xmlSource = new DOMSource(xmlDocument);
            Result xmlResult = new StreamResult(new File(filePath));

            transformer.transform(xmlSource, xmlResult);
            System.out.println("File '" + filePath + "' was created!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<UserAnswer> loadFromXML(String filePath) {
        List<UserAnswer> userAnswers = new ArrayList<>();

        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("UserAnswer");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String questionText = eElement.getElementsByTagName("QuestionText").item(0).getTextContent();
                    String correctAnswer = eElement.getElementsByTagName("CorrectAnswer").item(0).getTextContent();
                    String selectedAnswer = eElement.getElementsByTagName("SelectedAnswer").item(0).getTextContent();

                    List<String> wrongAnswers = new ArrayList<>();
                    NodeList wrongAnswerNodes = eElement.getElementsByTagName("WrongAnswer");
                    for (int i = 0; i < wrongAnswerNodes.getLength(); i++) {
                        wrongAnswers.add(wrongAnswerNodes.item(i).getTextContent());
                    }

                    UserAnswer userAnswer = new UserAnswer(new Question(temp, questionText, wrongAnswers, correctAnswer), selectedAnswer, player1Name);
                    userAnswers.add(userAnswer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userAnswers;
    }


    @FXML
    void messageTextFieldKeyPressed(KeyEvent event) throws RemoteException {
        if (event.getCode() == KeyCode.ENTER) {
            String message = messageTextField.getText();
            stub.sendMessage(message, player1Name);

            messageTextField.clear();
            messageTextField.requestFocus();
        }
    }

    private void startChatRefresh() {
        chatRefreshExecutor = Executors.newSingleThreadScheduledExecutor();
        chatRefreshTask = chatRefreshExecutor.scheduleAtFixedRate(() -> {
            try {
                List<String> chatHistory = stub.getChatHistory();
                Platform.runLater(() -> {
                    StringBuilder sb = new StringBuilder();
                    chatHistory.forEach(a -> sb.append(a).append("\n"));
                    chatListView.setText(sb.toString());
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void stopChatRefresh() {
        if (chatRefreshTask != null) {
            chatRefreshTask.cancel(true);
        }
        if (chatRefreshExecutor != null) {
            chatRefreshExecutor.shutdown();
        }
    }

    public void loadGameState(Pane pMainContent, Parent root) {
        pMainContent.getChildren().clear();
        try (FileInputStream fileInputStream = new FileInputStream("gameSaveState.ser"); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            GameState gameState = (GameState) objectInputStream.readObject();

            currentQuestionIndex = gameState.getCurrentQuestionIndex();
            userAnswers = gameState.getUserAnswers();
            List<UserAnswer> temp = gameState.getUserAnswers();
            UserAnswer firstUserAnswerTemp = temp.get(0);
            player1Name = firstUserAnswerTemp.getPlayerName();

            wrongAnswers = gameState.getNumberOfWrongAnswersUser(player1Name);
            correctAnswers = gameState.getNumberOfCorrectAnswersUser(player1Name);

            //Cant click anything if true!
            answerSelected = false;
            showCurrentQuestion();
            pMainContent.getChildren().add(root);

        } catch (FileNotFoundException e) {
            System.out.println("File not found: gameSaveState.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void loadQuestionsAsync() {
        Task<List<Question>> task = new Task<List<Question>>() {
            @Override
            protected List<Question> call() throws Exception {
                QuestionRepository questionRepository = new QuestionRepository();
                return questionRepository.getAllQuestions();
            }
        };
        task.setOnSucceeded(event -> {
            questions = task.getValue();
            showCurrentQuestion();
        });
        executorService.submit(task);
    }

    @FXML
    public void initialize() throws RemoteException, NotBoundException {
        instance = this;
        executorService = Executors.newSingleThreadExecutor();
        loadQuestionsAsync();
        if (userAnswers == null) {
            userAnswers = new ArrayList<>();
        }
        Registry registry = LocateRegistry.getRegistry("localhost", 1919);
        stub = (ChatService) registry.lookup("hr.algebra.khruskoj2.rmiserver");

        startChatRefresh();
    }

    public void shutdownExecutorService() {
        executorService.shutdownNow();
        stopChatRefresh();
    }
}
