package hr.algebra.khruskoj2.data;

import hr.algebra.khruskoj2.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private List<Question> questions;

    public QuestionRepository() {
        questions = new ArrayList<>();
        // Initialize the list of questions
        loadQuestions();
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    private void loadQuestions() {
// Question 1
        List<String> wrongAnswers1 = new ArrayList<>();
        wrongAnswers1.add("New York");
        wrongAnswers1.add("Tokyo");
        wrongAnswers1.add("London");
        String correctAnswer1 = "Paris";
        Question question1 = new Question(1, "What is the capital of France?", wrongAnswers1, correctAnswer1);

// Question 2
        List<String> wrongAnswers2 = new ArrayList<>();
        wrongAnswers2.add("Congo");
        wrongAnswers2.add("Yangtze");
        wrongAnswers2.add("Amazon");
        String correctAnswer2 = "Nile";
        Question question2 = new Question(2, "What is the longest river in the world?", wrongAnswers2, correctAnswer2);

// Question 3
        List<String> wrongAnswers3 = new ArrayList<>();
        wrongAnswers3.add("Soccer");
        wrongAnswers3.add("Basketball");
        wrongAnswers3.add("Tennis");
        String correctAnswer3 = "Cricket";
        Question question3 = new Question(3, "Which sport is known as \"the gentleman's game\"?", wrongAnswers3, correctAnswer3);

// Question 4
        List<String> wrongAnswers4 = new ArrayList<>();
        wrongAnswers4.add("Cristiano Ronaldo");
        wrongAnswers4.add("Neymar");
        wrongAnswers4.add("Lionel Messi");
        String correctAnswer4 = "Ali Daei";
        Question question4 = new Question(4, "Who is the all-time leading goalscorer in international football?", wrongAnswers4, correctAnswer4);

// Question 5
        List<String> wrongAnswers5 = new ArrayList<>();
        wrongAnswers5.add("Spain");
        wrongAnswers5.add("Germany");
        wrongAnswers5.add("Brazil");
        String correctAnswer5 = "France";
        Question question5 = new Question(5, "Which country won the FIFA World Cup in 2018?", wrongAnswers5, correctAnswer5);

// Question 6
        List<String> wrongAnswers6 = new ArrayList<>();
        wrongAnswers6.add("Werner Heisenberg");
        wrongAnswers6.add("Isaac Newton");
        wrongAnswers6.add("Marie Curie");
        String correctAnswer6 = "Albert Einstein";
        Question question6 = new Question(6, "Who is credited with the theory of general relativity?", wrongAnswers6, correctAnswer6);

// Question 7
        List<String> wrongAnswers7 = new ArrayList<>();
        wrongAnswers7.add("64");
        wrongAnswers7.add("512");
        wrongAnswers7.add("128");
        String correctAnswer7 = "256";
        Question question7 = new Question(7, "How much is 2^8", wrongAnswers7, correctAnswer7);

// Question 8
        List<String> wrongAnswers8 = new ArrayList<>();
        wrongAnswers8.add("Johann Philipp Reis");
        wrongAnswers8.add("Thomas Edison");
        wrongAnswers8.add("Nikola Tesla");
        String correctAnswer8 = "Alexander Graham Bell";
        Question question8 = new Question(8, "Who is credited with inventing the telephone?", wrongAnswers8, correctAnswer8);

// Question 9
        List<String> wrongAnswers9 = new ArrayList<>();
        wrongAnswers9.add("Canada");
        wrongAnswers9.add("Russia");
        wrongAnswers9.add("China");
        String correctAnswer9 = "USA";
        Question question9 = new Question(9, "Which country was the first to send a human to the moon?", wrongAnswers9, correctAnswer9);

// Question 10
        List<String> wrongAnswers10 = new ArrayList<>();
        wrongAnswers10.add("Sydney");
        wrongAnswers10.add("Melbourne");
        wrongAnswers10.add("Brisbane");
        String correctAnswer10 = "Canberra";
        Question question10 = new Question(10, "What is the capital of Australia?", wrongAnswers10, correctAnswer10);

// Question 11
        List<String> wrongAnswers11 = new ArrayList<>();
        wrongAnswers11.add("Rome");
        wrongAnswers11.add("Paris");
        wrongAnswers11.add("Athens");
        String correctAnswer11 = "Cairo";
        Question question11 = new Question(11, "What is the capital city of Egypt?", wrongAnswers11, correctAnswer11);


// Question 12
        List<String> wrongAnswers12 = new ArrayList<>();
        wrongAnswers12.add("Hawaii");
        wrongAnswers12.add("Maldives");
        wrongAnswers12.add("Bora Bora");
        String correctAnswer12 = "Fiji";
        Question question12 = new Question(12, "Which country is known as the \"Pearl of the Pacific\"?", wrongAnswers12, correctAnswer12);

// Question 13
        List<String> wrongAnswers13 = new ArrayList<>();
        wrongAnswers13.add("Guernica");
        wrongAnswers13.add("Starry Night");
        wrongAnswers13.add("The Scream");
        String correctAnswer13 = "Mona Lisa";
        Question question13 = new Question(13, "Which famous painting was created by Leonardo da Vinci?", wrongAnswers13, correctAnswer13);

// Question 14
        List<String> wrongAnswers14 = new ArrayList<>();
        wrongAnswers14.add("Sahara Desert");
        wrongAnswers14.add("Gobi Desert");
        wrongAnswers14.add("Atacama Desert");
        String correctAnswer14 = "Antarctica";
        Question question14 = new Question(14, "Which continent is the driest desert located in?", wrongAnswers14, correctAnswer14);

// Question 15
        List<String> wrongAnswers15 = new ArrayList<>();
        wrongAnswers15.add("Microsoft");
        wrongAnswers15.add("Amazon");
        wrongAnswers15.add("Apple");
        String correctAnswer15 = "Sun Microsystems";
        Question question15 = new Question(15, "Which company created the Java programming language?", wrongAnswers15, correctAnswer15);

        // Add questions to the list
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);
        questions.add(question8);
        questions.add(question9);
        questions.add(question10);
        questions.add(question11);
        questions.add(question12);
        questions.add(question13);
        questions.add(question14);
        questions.add(question15);
    }

}
