package com.example.brama.trivia;
// Run API:
// cd rester
// FLASK_APP=rester.py flask run

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int amount;
    private final static int STANDARD_AMOUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener());

        Button highscoresButton = findViewById(R.id.highscoresButton);
        highscoresButton.setOnClickListener(new viewHighscoresClick());
    }

    private class OnClickListener implements View.OnClickListener, QuestionsRequest.Callback {
        private ArrayList<Question> questions;

        @Override
        public void onClick(View v) {
            TextView questionAmount = findViewById(R.id.questionAmount);
            CharSequence amountString = questionAmount.getText();
            String type = "multiple";

            if (amountString.length() != 0) {
                amount = Integer.parseInt(amountString.toString());
            } else {
                amount = STANDARD_AMOUNT;
            }

            QuestionsRequest rq = new QuestionsRequest(MainActivity.this, amount, type);
            rq.getQuestions(this);
        }

        @Override
        public void gotQuestions(ArrayList<Question> questions) {
            this.questions = questions;
            Log.d("blabla",String.valueOf(this.questions));

            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            intent.putParcelableArrayListExtra("questions", this.questions);
            intent.putExtra("amount",amount);
            startActivity(intent);
        }

        @Override
        public void gotQuestionsError(String message) {
            Log.d("blabla", "WHOOOPS");
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private class viewHighscoresClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent viewScore = new Intent(MainActivity.this,ViewHighscoreActivity.class);
            startActivity(viewScore);
        }
    }
}
