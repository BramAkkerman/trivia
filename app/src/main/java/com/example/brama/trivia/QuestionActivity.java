package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity {

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

    private void runQuestions(int position) {
        Question question = questions.get(position);
        currentAnswer = question.getCorrectAnswer();
        ArrayList<String> answers = question.getIncorrectAnswers();
        answers.add(currentAnswer);
        Collections.shuffle(answers);
        setText(answers, question.getQuestion());
    }

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

        String title = "Question " + String.valueOf(currentQuestion+1);
        TextView questionTitle = findViewById(R.id.questionCount);
        questionTitle.setText(title);
    }

    private class nextQuestion implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (currentQuestion == amount-1) {
                Intent gameOver = new Intent(QuestionActivity.this,GameOver.class);
                gameOver.putExtra("score",score);
                gameOver.putExtra("streak",streak);
                gameOver.putExtra("streakscore",streakScore);
                startActivity(gameOver);
                QuestionActivity.this.finish();
            } else {
                if (currentQuestion == amount-2) {
                    Button vButton = (Button) v;
                    vButton.setText("End game");
                }
                currentQuestion += 1;
                runQuestions(currentQuestion);
                dialog.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String buttonText = (String) ((TextView)v).getText();
            Log.d("blabla",buttonText);
            String dialogText;
            if (buttonText.equals(currentAnswer)) {
                dialogText = "That's correct!";
                streak += 1;
                streakScore += streak;
                score += streak*streakScore;
            } else {
                dialogText = "Wrong, answer is: " + currentAnswer;
                streak = 0;
                streakScore = 0;
            }
            rightWrong.setText(dialogText);
            dialog.setVisibility(View.VISIBLE);
        }
    }

}