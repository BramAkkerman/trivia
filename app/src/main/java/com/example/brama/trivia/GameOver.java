package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    private int score;
    private int streak;
    private int streakScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);
        streak = intent.getIntExtra("streak",1);
        streakScore = intent.getIntExtra("streakscore", 1);

        TextView scoreView = findViewById(R.id.scoreView);
        String scoreText = "Your scored " + String.valueOf(score) + " points!";
        scoreView.setText(scoreText);

        Button menu = findViewById(R.id.menu);
        Button saveScore = findViewById(R.id.saveScore);
        Button viewScore = findViewById(R.id.viewScore);

        menu.setOnClickListener(new restartGameClick());
        saveScore.setOnClickListener(new saveScoreClick());
        viewScore.setOnClickListener(new viewScoreClick());
    }

    private class restartGameClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GameOver.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private class saveScoreClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GameOver.this, SaveHighscoreActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("streak", streak);
            intent.putExtra("streakscore", streakScore);
            startActivity(intent);
        }
    }

    private class viewScoreClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent viewScore = new Intent(GameOver.this, ViewHighscoreActivity.class);
            startActivity(viewScore);
        }
    }
}
