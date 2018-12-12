package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaveHighscoreActivity extends AppCompatActivity {

    private int score;
    private int streak;
    private int streakScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_highscore);

        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);
        streak = intent.getIntExtra("streak",1);
        streakScore = intent.getIntExtra("streakscore", 1);

        Button submit = findViewById(R.id.loginButton);
        submit.setOnClickListener(new submitClick());
    }

    private class submitClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView nameView = findViewById(R.id.editName);
            String name = nameView.getText().toString();

            SaveHighscoreRequest saveRequest = new SaveHighscoreRequest(SaveHighscoreActivity.this);
            saveRequest.postScore(name,String.valueOf(score),String.valueOf(streak),String.valueOf(streakScore));

            Intent menu = new Intent(SaveHighscoreActivity.this,MainActivity.class);
            startActivity(menu);
        }
    }
}
