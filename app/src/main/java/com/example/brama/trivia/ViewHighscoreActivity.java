package com.example.brama.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewHighscoreActivity extends AppCompatActivity implements ViewHighscoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_highscore);

        ViewHighscoreRequest rq = new ViewHighscoreRequest(this);
        rq.getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        highscores = orderToRank(highscores);
        HighscoreAdapter arrayAdapter = new HighscoreAdapter(this,R.layout.highscore,highscores);
        ListView list = findViewById(R.id.scoresList);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this,"Could not download highscores",Toast.LENGTH_LONG);
        Log.d("blabla", "WHOOOPS");
    }

    private ArrayList<Highscore> orderToRank(ArrayList<Highscore> highscores) {
        Comparator<Highscore> comp = new Comparator<Highscore>() {
            @Override
            public int compare(Highscore s1, Highscore s2) {
                return s2.getScore() - s1.getScore();
            }
        };
        Log.d("blabla",String.valueOf(highscores));
        Collections.sort(highscores, comp);
        Log.d("blabla",String.valueOf(highscores));

        int rank = 1;
        for (Highscore highscore : highscores) {
            highscore.setRank(rank);
            rank++;
        }

        return highscores;
    }
}
