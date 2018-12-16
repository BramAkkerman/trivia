package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

// This activity shows how many points the user gathered and puts the score if the user is logged in
public class GameOverActivity extends AppCompatActivity {

    private Highscore login;
    private ArrayList<Highscore> logins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Check if there has been logged in, else retrieve the score only
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        logins = intent.getParcelableArrayListExtra("logins");
        if (logins != null) {
            login = logins.get(0);
            saveScore();
        } else {
            login = null;
        }

        TextView scoreView = findViewById(R.id.scoreView);
        String scoreText = "You scored " + String.valueOf(score) + " points!";
        scoreView.setText(scoreText);

        Button menu = findViewById(R.id.menu);
        Button viewScore = findViewById(R.id.viewScore);

        menu.setOnClickListener(new restartGameClick());
        viewScore.setOnClickListener(new viewScoreClick());
    }

    // Make a put request to update the score of the account that's been logged in to.
    private void saveScore() {
        PutHighscoreRequest putRq = new PutHighscoreRequest(this);
        putRq.putScore(login.getScore(),login.getStreak(),login.getStreakScore(),login.getId());
    }

    // Go back to the MainActivity, saving the login
    private class restartGameClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putParcelableArrayListExtra("logins",logins);
            startActivity(intent);
        }
    }

    // Start the ViewHighscoreActivity
    private class viewScoreClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent viewScore = new Intent(GameOverActivity.this, ViewHighscoreActivity.class);
            startActivity(viewScore);
        }
    }
}
