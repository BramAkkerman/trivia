package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

// This class handles the question asking activity of the app
public class QuestionActivity extends AppCompatActivity {

    private ArrayList<Highscore> logins;
    private Highscore login;
    private ArrayList<Question> questions;
    private String currentAnswer;
    private int currentQuestion;
    private int amount;

    private int score = 0;
    private int streak = 0;
    private int streakScore = 0;

    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    private LinearLayout dialog;
    private TextView rightWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        logins = intent.getParcelableArrayListExtra("logins");

        // Check if the user is logged in, to set the score and the streak to the corresponding
        // values stored in the API.
        if (logins != null) {
            login = logins.get(0);
            streak = login.getStreak();
            streakScore = login.getStreakScore();
        } else {
            login = null;
        }

        questions = intent.getParcelableArrayListExtra("questions");
        amount = intent.getIntExtra("amount", 20);

        dialog = findViewById(R.id.rightWrongDialog);
        rightWrong = findViewById(R.id.rightWrong);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        answer1.setOnClickListener(new OnClickListener());
        answer2.setOnClickListener(new OnClickListener());
        answer3.setOnClickListener(new OnClickListener());
        answer4.setOnClickListener(new OnClickListener());

        Button nextQuestion = findViewById(R.id.nextQuestion);
        nextQuestion.setOnClickListener(new nextQuestion());

        currentQuestion = 0;
        runQuestions(currentQuestion);
    }

    // Loop through all the questions and set the buttons according to all the answers, randomized
    private void runQuestions(int position) {
        Question question = questions.get(position);
        currentAnswer = question.getCorrectAnswer();
        ArrayList<String> answers = question.getIncorrectAnswers();
        answers.add(currentAnswer);
        Collections.shuffle(answers);
        setText(answers, question.getQuestion());
    }

    // Set the views to match the question
    private void setText(ArrayList<String> answers, String question) {
        String ans1 = answers.get(0);
        answer1.setText(ans1);

        String ans2 = answers.get(1);
        answer2.setText(ans2);

        String ans3 = answers.get(2);
        answer3.setText(ans3);

        String ans4 = answers.get(3);
        answer4.setText(ans4);

        TextView questionView = findViewById(R.id.questionText);
        questionView.setText(question);

        String title = "Question " + String.valueOf(currentQuestion+1)
                + " of " + String.valueOf(amount);
        TextView questionTitle = findViewById(R.id.questionCount);
        questionTitle.setText(title);
    }

    // When nextQuestion is clicked, check whether this was the last question and the new Activity
    // needs to begin, else if it is 1 to last question set the button text on "End game", else run
    // the next question.
    private class nextQuestion implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            answer1.setClickable(true);
            answer2.setClickable(true);
            answer3.setClickable(true);
            answer4.setClickable(true);

            if (currentQuestion == amount-1) {
                Intent gameOver = new Intent(QuestionActivity.this,GameOverActivity.class);

                if (logins != null) {
                    ArrayList<Highscore> logins = new ArrayList<>();
                    login.setScore(score+login.getScore());
                    login.setStreak(streak);
                    login.setStreakScore(streakScore);
                    logins.clear();
                    logins.add(login);
                    gameOver.putParcelableArrayListExtra("logins", logins);
                }
                gameOver.putExtra("score",score);

                gameOver.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gameOver);
                QuestionActivity.this.finish();
            } else {
                if (currentQuestion == amount-2) {
                    Button vButton = (Button) v;
                    String vButtonText = "End game";
                    vButton.setText(vButtonText);
                }
                currentQuestion += 1;
                runQuestions(currentQuestion);
                dialog.setVisibility(View.INVISIBLE);
            }
        }
    }

    // Check if the text on the button clicked equals the right answer and add score if so, else
    // subtract score and set the streak to 0 again.
    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            answer1.setClickable(false);
            answer2.setClickable(false);
            answer3.setClickable(false);
            answer4.setClickable(false);

            String buttonText = (String) ((TextView)v).getText();
            String dialogText;
            if (buttonText.equals(currentAnswer)) {
                dialogText = "That's correct!";
                streak += 1;
                streakScore += streak;
                score += 2 + streakScore;
            } else {
                dialogText = "Wrong, answer is: " + currentAnswer;
                score -= 1;
                streak = 0;
                streakScore = 0;
            }
            rightWrong.setText(dialogText);
            dialog.setVisibility(View.VISIBLE);
        }
    }

}