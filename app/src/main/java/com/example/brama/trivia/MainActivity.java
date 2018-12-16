package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// This activity handles the main menu of the game. This trivia game consists of multiple choice
// questions and has a login system to handle the score saving.
public class MainActivity extends AppCompatActivity {

    private int amount;
    private final static int STANDARD_AMOUNT = 2;
    private ArrayList<Highscore> logins;

    private Button loginButton;
    private TextView loginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check whether there has been logged in already
        Intent intent = getIntent();
        logins = intent.getParcelableArrayListExtra("logins");

        loginButton = findViewById(R.id.loginButton);
        loginView = findViewById(R.id.loginView);
        if (logins == null) {
            loginButton.setOnClickListener(new loginClick());
        } else {
            loginView.setVisibility(View.INVISIBLE);
            String logoutString = "Log out";
            loginButton.setText(logoutString);
            loginButton.setOnClickListener(new logoutClick());
        }

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new playClick());

        Button highscoresButton = findViewById(R.id.highscoresButton);
        highscoresButton.setOnClickListener(new viewHighscoresClick());
    }

    // When clicked on the play button, retrieve the questions from the API
    private class playClick implements View.OnClickListener, QuestionsRequest.Callback {
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

        // Start the QuestionActivity with the questions retrieved from the API and the login
        @Override
        public void gotQuestions(ArrayList<Question> questions) {
            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            intent.putParcelableArrayListExtra("logins",logins);
            intent.putParcelableArrayListExtra("questions", questions);
            intent.putExtra("amount",amount);
            startActivity(intent);
        }

        @Override
        public void gotQuestionsError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    // Start HighscoresActivity when clicked on Highscores button
    private class viewHighscoresClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent viewScore = new Intent(MainActivity.this,ViewHighscoreActivity.class);
            startActivity(viewScore);
        }
    }

    // Start LoginActivity when clicked on Login button
    private class loginClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        }
    }

    // Clear the logins list and set views and onClickListener on Login again when clicked on Logout
    private class logoutClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"Logout successful",Toast.LENGTH_LONG).show();
            String loginString = "Log in";
            loginButton.setText(loginString);
            loginView.setVisibility(View.VISIBLE);
            logins.clear();
            loginButton.setOnClickListener(new loginClick());
        }
    }
}
